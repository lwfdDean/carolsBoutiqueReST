package co.za.carolsBoutique.EmailTemplate;

import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.product.model.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailReader {
    FileInputStream fis;
    BufferedReader dis;
    StringBuilder content;
    File file;
    //string type, obj source
    public synchronized String readInEmail(String type, Object source) throws IOException{
        switch(type){
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"PurchaseReceipt":
                Sale sale = (Sale)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\PurchaseReceipt.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"AmmendedReceiptExchange":
                sale = (Sale)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\AmmendedReceiptExchange.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"AmmendedReceiptRefund":  
                sale = (Sale)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\AmmendedReceiptRefund.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"KeepAside36":
                Product product = (Product)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\KeepAside36.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"KeepAside24":
                product = (Product)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\KeepAside24.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"KeepAside12":
                product = (Product)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\KeepAside12.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"KeepAsideExpired":
                product = (Product)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\KeepAsideExpired.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"IbtRequest":
                IBT ibt = (IBT)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\IbtRequest.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"IbtApprove":
                ibt = (IBT)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\IbtApprove.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"LowStock":
                product = (Product)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\LowStock.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"Promotion":
                String messege = (String)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\Promotion.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    while (holder!=null) {                        
                        holder = dis.readLine();
                        content.append(holder+"\n");
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EmailReader.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    dis.close();
                }
                break;
        }
        return content.toString();
    }
}
