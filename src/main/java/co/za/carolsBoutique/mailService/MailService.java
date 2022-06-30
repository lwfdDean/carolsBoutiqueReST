package co.za.carolsBoutique.mailService;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.Properties;


public class MailService{
    final String senderEmailId = "lggousie46@gmail.com";
    final String senderPassword = "feqexecjlwubfvpl";
    final String emailSMTPSever = "smtp.mail.gmail.com";
    final String emailServerPort = "587";
    String receiverEmail;
    String emailSubject;
    String emailBody;

    public MailService(String receiverEmail, String emailSubject, String emailBody) {
        this.receiverEmail = receiverEmail;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
    }
    
    public synchronized void sendMail() throws AddressException, MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.from", senderEmailId);
        props.put("mail.smtp.user", senderEmailId);
        props.put("mail.smtp.host", emailSMTPSever);
        props.put("mail.smtp.port", emailServerPort);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.socketFactory.port", emailServerPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(senderEmailId, senderPassword);
            }
        });
        session.setDebug(true);
        
        Message message = new MimeMessage(session);
        MimeMultipart mmp = new MimeMultipart();
        message.setFrom(new InternetAddress(senderEmailId));
        message.setSubject(this.emailSubject);
        message.setText(emailBody);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.receiverEmail));
        MimeBodyPart content = new MimeBodyPart();
        content.setText(emailBody,"utf-8","html");
        mmp.addBodyPart(content);;
        message.setContent(mmp);
        
        Transport.send(message);
    }
}
