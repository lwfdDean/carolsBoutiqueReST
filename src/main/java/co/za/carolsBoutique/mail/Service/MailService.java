package co.za.carolsBoutique.mail.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class MailService {
    public static String sendMail(){
        String url = "http://localhost:8080/MailService/MailService/email/sendMail";
        Client rest = ClientBuilder.newClient();
        WebTarget webTarget = rest.target(url);
        String string = new String();
        Response response = webTarget.request(MediaType.APPLICATION_JSON)
                        .post(Entity.json(toJsonString(
                        "hey")));
        try {
            string = new ObjectMapper().readValue(response.readEntity(String.class),
                    String.class);
        } catch (IOException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(string);
        return string;
    }
     private static String toJsonString(Object o){
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
