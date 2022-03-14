import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import model.Email;
import model.ParsedEmail;
import model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static java.net.URLDecoder.decode;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final Gson GSON = new Gson();
    private static final int FAIL_THRESHOLD = 10;
    private static final HttpRequest REQUEST = HttpRequest.newBuilder()
            .uri(URI.create("https://api.stop-rf-army.com/email"))
            .header("Content-Type", "application/json; charset=utf-8")
            .POST(HttpRequest.BodyPublishers.ofString("{\"initial\": false}"))
            .build();

    // first CLI argument should be path to file with email credentials
    public static void main(String[] args) throws IOException, InterruptedException {
        var jsonReader = new JsonReader(new FileReader(args[0]));
        List<Email> emailList = GSON.fromJson(jsonReader, new TypeToken<List<Email>>() {
        }.getType());

        for (Email email : emailList) {
            LOGGER.info("Starting working with email {}", email.getUsername());
            var session = EmailSelector.getSession(email);
            var iterationCounter = 0;
            var failCount = 0;

            while (true) {
                LOGGER.info("Iteration #{}", ++iterationCounter);
                var parsedEmail = new ParsedEmail(processApi());

                try {
                    var message = prepareMail(session, email.getUsername(), parsedEmail);
                    Transport.send(message);
                    LOGGER.info("{} -> sent successfully.", Arrays.toString(message.getAllRecipients()));
                } catch (AddressException ae) {
                    LOGGER.warn("Looks like incorrect address, not incrementing fail counter.", ae);
                    LOGGER.info("Fail counter: {}", failCount);
                } catch (MessagingException mex) {
                    LOGGER.warn("Message was not sent with an exception", mex);
                    LOGGER.info("Fail counter: {}", ++failCount);
                }

                if (failCount > FAIL_THRESHOLD) {
                    LOGGER.warn("Seems like we have repetitive fails for more than {} times, breaking out of loop.",
                            FAIL_THRESHOLD);
                    break;
                }
            }
        }
    }

    private static Response processApi() throws IOException, InterruptedException {
        LOGGER.debug("Trying to reach an API.");
        var apiResponse = HTTP_CLIENT.send(REQUEST, HttpResponse.BodyHandlers.ofString());
        LOGGER.debug("Received and API response.");
        var response = GSON.fromJson(apiResponse.body(), Response.class);
        LOGGER.debug("Parsed API response from JSON.");
        return response;
    }

    private static MimeMessage prepareMail(Session session, String emailFrom, ParsedEmail email)
            throws MessagingException, IOException {

        MimeMessage message = new MimeMessage(session);
        // set From: header field of the header
        message.setFrom(new InternetAddress(emailFrom));
        // set To: header field of the header
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
        message.addRecipients(Message.RecipientType.BCC, String.join(",", email.getBccList()));
        // set Subject: header field
        message.setSubject(email.getSubject());
        // set the actual message
        message.setText(decode(email.getBody(), StandardCharsets.UTF_8));
        LOGGER.debug("Composed a mime-message.");
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Subject: {}", message.getSubject());
            LOGGER.trace("Content: {}", message.getContent());
            LOGGER.trace("Recipients: {}", Arrays.toString(message.getAllRecipients()));
        }

        return message;
    }
}
