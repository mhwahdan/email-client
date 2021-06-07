package EmailClientOOP;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Client implements ClientBehavior {

    // Sender's email ID needs to be mentioned
    final private String email;

    // Sender's email password
    final private String password;


    private Session emailSession;

    private Store store;

    private boolean unauthenticated;

    public List<email> messages;

    public static List<emailconfigration> configerations = new ArrayList<>();

    private emailconfigration configeration;



    public Client(String email, String password) throws Exception  {
        boolean flag = false;
        this.email = email;
        this.password = password;
        for (emailconfigration x: configerations)
        {
            Pattern pattern = Pattern.compile(x.getName(), Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if(matcher.find())
            {
                flag = true;
                configeration = x;
                break;
            }
        }
        this.emailSession = Session.getDefaultInstance(new Properties( ));
        this.messages = new ArrayList<>();
        if(!flag || !this.Authenticate())
        {
            this.unauthenticated = true;
            return;
        }
        this.unauthenticated = false;
    }

    public String getEmail() {
        return email;
    }

    public boolean is_authenticated()
    {
        return unauthenticated;
    }

    @Override
    public boolean Authenticate()
    {
        try
        {
            this.store = this.emailSession.getStore("imaps");
            this.store.connect(configeration.getHost_imap(), configeration.getPort_imap(), this.email,this.password);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean ReadMessages () throws Exception
    {
        int counter = 0;
        //create the POP3 store object and connect with the pop server
        if(this.unauthenticated)
            return false;
        try {
        Folder emailFolder = store.getFolder("INBOX");
        emailFolder.open(Folder.READ_ONLY);
            for (Message message: emailFolder.getMessages())
            {
                if(counter > 100)
                    return true;
                this.messages.add(new email(message.getFrom()[0],message.getSubject(),Client.getTextFromMessage(message)));
                counter++;
            }
    } catch (NoSuchProviderException e) {
        return false;
        }
        catch (MessagingException e)
        {
        return false;
        } catch (Exception e) {
        return false;
        }
        return true;
    }

    @Override
    public boolean Send(String receiver, String subject, String body) {
        if(this.unauthenticated)
            return false;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", configeration.getHost_smtp());
        props.put("mail.smtp.port", String.valueOf(configeration.getPort_smtp()));
        // Get the default Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(this.email));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receiver));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setText(body);

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            return false;
        }
        return true;
    }

    public static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }
}
