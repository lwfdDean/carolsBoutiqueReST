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
import java.util.List;
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
                    file = new File("C:\\Users\\27609\\Desktop\\LWFD showRoom\\sale_receipt.txt");
                    fis = new FileInputStream(file);
                    dis = new BufferedReader(new InputStreamReader(fis));
                    String holder = "";
                    double discount = 0.0;
                    while (true) {                        
                        holder = dis.readLine();
                        if(holder==null){
                            break;
                        }
                        if (holder.contains("<tr height=\"35px\">")) {
                            dis.readLine();
                            dis.readLine();
                            dis.readLine();
                            dis.readLine();
                            dis.readLine();
                            for (int i = 0; i < sale.getItems().size(); i++) {
                                holder+="                                                        <td>"+sale.getItems().get(i).getName() +"</td>\n";
                                holder+="                                                        <td class=\"text-center\">"+1 +"</td>\n";
                                holder+="                                                        <td class=\"text-center\">"+sale.getItems().get(i).getPrice() +"</td>\n";
                                if(sale.getItems().get(i).getDiscountedPrice()!=null){
                                    holder+="                                                        <td class=\"text-center\">"+sale.getItems().get(i).getDiscountedPrice() +"</td>\n";
                                }else{
                                    holder+="                                                        <td class=\"text-center\">"+0 +"</td>\n";
                                }
                                holder+="                                                    </tr>\n";
                                if(i!=sale.getItems().size()){
                                    holder+="                                                    <tr height=\"35px\">\n";
                                }
                            }  
                        }
                        if(holder.contains("*")){
                            String firstHalf = "";
                            String secondHalf = "";
                            String middle = "";
                            for (int i = 0; i < holder.length(); i++) {
                                if(holder.charAt(i)=='*'){
                                    if(holder.charAt(i+1)=='1'){
                                        firstHalf = holder.substring(0, i);
                                        middle = sale.getId();
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='2'){
                                        firstHalf = holder.substring(0, i);
                                        middle = sale.getEmployee();
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='3'){
                                        firstHalf = holder.substring(0, i);
                                        middle = sale.getBoutique();
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='8'){
                                        firstHalf = holder.substring(0, i);
                                        middle = sale.getTotalPrice().toString();
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='9'){
                                        List<Product> items = sale.getItems();
                                        for (int j = 0; j < items.size(); j++) {
                                            if(items.get(j)!=null){
                                                discount += items.get(j).getDiscountedPrice();
                                            }   
                                        }
                                        firstHalf = holder.substring(0, i);
                                        middle = discount+"";
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='1'){
                                        if(holder.charAt(i+2)=='0'){
                                            firstHalf = holder.substring(0, i);
                                            middle = "15%";
                                            secondHalf = holder.substring(i+3);
                                        }
                                    }
                                    if(holder.charAt(i+1)=='1'){
                                        if(holder.charAt(i+2)=='1'){
                                            System.out.println("Discount = " +discount);
                                            firstHalf = holder.substring(0, i);
                                            Double d = sale.getTotalPrice()+(sale.getTotalPrice()*0.15)-discount;
                                            middle = d.toString();
                                            secondHalf = holder.substring(i+3);
                                        }
                                    }
                                }
                            }
                            holder = firstHalf+middle+secondHalf;
                        }
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