package model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ParsedEmail {
    private String subject;
    private String body;
    private String to;
    private List<String> bccList;

    public ParsedEmail(Response response) {
        this.subject = Objects.requireNonNull(response).getSubject();
        this.body = Objects.requireNonNull(response).getBody();
        var emailsList = Objects.requireNonNull(response).getEmails().stream()
                .flatMap(List::stream).filter(item -> !item.isBlank()).collect(Collectors.toList());

        this.to = emailsList.get(0);
        emailsList.remove(0);
        this.bccList = emailsList;
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

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<String> getBccList() {
        return bccList;
    }

    public void setBccList(List<String> bccList) {
        this.bccList = bccList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParsedEmail)) return false;
        ParsedEmail that = (ParsedEmail) o;
        return Objects.equals(subject, that.subject) && Objects.equals(body, that.body) && Objects.equals(to, that.to) && Objects.equals(bccList, that.bccList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, body, to, bccList);
    }

    @Override
    public String toString() {
        return "model.ParsedEmail{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", to='" + to + '\'' +
                ", bccList=" + bccList +
                '}';
    }
}
