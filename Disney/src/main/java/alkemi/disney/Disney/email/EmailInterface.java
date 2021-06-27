package alkemi.disney.Disney.email;

public interface EmailInterface {
    void sendText(String from, String to, String subject, String body);
    void sendHTML(String from, String to, String subject, String body);
}
