package model;

import exception.UnsupportedEmailTypeException;

import java.util.Objects;

public class Email {
    private String username;
    private String password;

    public Email(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public EmailType getEmailType() {
        if (username.endsWith("@gmail.com")) {
            return EmailType.GMAIL;
        } else if (username.endsWith("@yahoo.com")) {
            return EmailType.YAHOO;
        } else if (username.endsWith("@outlook.com")) {
            return EmailType.OUTLOOK;
        } else {
            throw new UnsupportedEmailTypeException();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return Objects.equals(username, email.username) && Objects.equals(password, email.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "Email{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
