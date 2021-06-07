package EmailClientOOP;

public interface ClientBehavior {

    abstract public boolean Authenticate();
    abstract public boolean ReadMessages () throws Exception;
    abstract public boolean Send(String receiver, String subject, String body);
}
