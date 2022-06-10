package co.za.carolsBoutique.messageService;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageService extends Thread{
    private String user;
    private String password;
    private String cellPhoneNumber;
    private String message;

    public MessageService(String cellPhoneNumber, String message) {
        this.cellPhoneNumber = cellPhoneNumber;
        this.message = message;
        this.user = "Group1";
        this.password = "group1";
    }

    @Override
    public void run() {
        sendMessage();
    }

    private synchronized void sendMessage(){
        String uri = "http://196.41.180.157:8080/sms/sms_request";
        String req = buildMessage();
        Client client = ClientBuilder.newClient();
        WebTarget webT = client.target(uri);
        Response rep = webT.request(MediaType.APPLICATION_XML).post(Entity.xml(req));
        System.out.println(rep.readEntity(String.class));
    }
    
    private synchronized String buildMessage(){
        StringBuilder sb = new StringBuilder();
        List<String> tags = List.of(
                "<smsreq>",
                "<datetime>","</datetime>",
                "<user>","</user>",
                "<pass>","</pass>",
                "<msisdn>","</msisdn>",
                "<message>","</message>",
                "</smsreq>"
        );
        for (String tag : tags) {
            sb.append(tag);
            switch (tag) {
                case "<datetime>":
                    sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/mm/dd,hh,mm,ss")));
                    break;
                case "<user>":
                    sb.append(user);
                    break;
                case "<pass>":
                    sb.append(password);
                    break; 
                case "<msisdn>":
                    sb.append(cellPhoneNumber);
                    break;
                case "<message>":
                    sb.append(message);
                    break;    
            }
        }
        return sb.toString();
    }
    
}
