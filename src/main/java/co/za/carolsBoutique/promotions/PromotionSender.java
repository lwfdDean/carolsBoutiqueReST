package co.za.carolsBoutique.promotions;

import co.za.carolsBoutique.Sale.repository.SaleRepositoryImp;
import co.za.carolsBoutique.mailService.MailService;
import co.za.carolsBoutique.messageService.MessageService;
import jakarta.mail.MessagingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PromotionSender extends Thread {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    public PromotionSender() {
        String url = "jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, "root", "Root");
        } catch (SQLException ex) {
            Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {            
            sendPromotionalMessages();
        }
    }
    
    
    
    public void sendPromotionalMessages(){
        while (LocalDate.now().getDayOfWeek().getValue()!=4) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(PromotionSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List info = new ArrayList();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select * from subscriberlist");
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    String contactInfo = rs.getString("contactInfo");
                    if (contactInfo.contains("@")) {
                        try {
                            new MailService(contactInfo,"Promotion","").sendMail();
                        } catch (MessagingException ex) {
                            Logger.getLogger(PromotionSender.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        new MessageService(contactInfo, "");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(PromotionSender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
