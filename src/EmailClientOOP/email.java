package EmailClientOOP;

import javax.mail.Address;

public class email {
    private String from;
    private String subject;
    private String body;

    public email(Address from, String subject, String body) {
        this.from = from.toString();
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
