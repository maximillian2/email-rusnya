package model;

import java.util.List;
import java.util.Objects;

public class Response {
    private long numberOfRequest;
    private String subject;
    private String body;
    private List<List<String>> emails;

    public long getNumberOfRequest() {
        return numberOfRequest;
    }

    public void setNumberOfRequest(long numberOfRequest) {
        this.numberOfRequest = numberOfRequest;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<List<String>> getEmails() {
        return emails;
    }

    public void setEmails(List<List<String>> emails) {
        this.emails = emails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Response)) return false;
        Response response = (Response) o;
        return numberOfRequest == response.numberOfRequest && Objects.equals(subject, response.subject) && Objects.equals(body, response.body) && Objects.equals(emails, response.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfRequest, subject, body, emails);
    }

    @Override
    public String toString() {
        return "model.Response{" +
                "numberOfRequest=" + numberOfRequest +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", emails=" + emails +
                '}';
    }
}
