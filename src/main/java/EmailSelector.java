import model.Email;
import model.EmailType;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Map;
import java.util.Properties;

public class EmailSelector {
    private static final Map<EmailType, Properties> emailProperties = Map.of(
            EmailType.GMAIL, getGmailProperties(),
            EmailType.YAHOO, getYahooProperties()
    );

    private EmailSelector() {
    }

    public static Session getSession(Email email) {
        return Session.getInstance(emailProperties.get(email.getEmailType()), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email.getUsername(), email.getPassword());
            }
        });
    }

    private static Properties getGmailProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }

    private static Properties getYahooProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        return properties;
    }
}
