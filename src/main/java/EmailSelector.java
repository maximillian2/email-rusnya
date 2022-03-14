import model.Email;
import model.EmailType;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Map;
import java.util.Properties;

public class EmailSelector {
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    private static final Map<EmailType, Properties> emailProperties = Map.of(
            EmailType.GMAIL, getGmailProperties(),
            EmailType.YAHOO, getYahooProperties(),
            EmailType.OUTLOOK, getOutlookProperties()
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
        Properties properties = new Properties();
        properties.put(MAIL_SMTP_HOST, "smtp.gmail.com");
        properties.put(MAIL_SMTP_PORT, "465");
        properties.put(MAIL_SMTP_SSL_ENABLE, "true");
        properties.put(MAIL_SMTP_AUTH, "true");
        return properties;
    }

    private static Properties getYahooProperties() {
        Properties properties = new Properties();
        properties.put(MAIL_SMTP_HOST, "smtp.mail.yahoo.com");
        properties.put(MAIL_SMTP_PORT, "465");
        properties.put(MAIL_SMTP_SSL_ENABLE, "true");
        properties.put(MAIL_SMTP_AUTH, "true");
        return properties;
    }

    private static Properties getOutlookProperties() {
        Properties properties = new Properties();
        properties.put(MAIL_SMTP_HOST, "smtp-mail.outlook.com");
        properties.put(MAIL_SMTP_PORT, "587");
//        properties.put(MAIL_SMTP_SSL_ENABLE, "true");
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, "true");
        properties.put(MAIL_SMTP_AUTH, "true");
        return properties;
    }
}
