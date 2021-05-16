package EmailClientOOP;

public class emailconfigration {
    private String Name;
    private String Host_imap;
    private String Host_smtp;
    private int port_imap;
    private int port_smtp;

    public emailconfigration(String name, String host_imap, String host_smtp, int port_imap, int port_smtp) {
        Name = name;
        Host_imap = host_imap;
        Host_smtp = host_smtp;
        this.port_imap = port_imap;
        this.port_smtp = port_smtp;
    }

    public String getName() {
        return Name;
    }

    public String getHost_imap() {
        return Host_imap;
    }

    public String getHost_smtp() {
        return Host_smtp;
    }

    public int getPort_imap() {
        return port_imap;
    }

    public int getPort_smtp() {
        return port_smtp;
    }

}
