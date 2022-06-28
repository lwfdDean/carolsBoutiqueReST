package co.za.carolsBoutique.EmailTemplate;

import co.za.carolsBoutique.Sale.model.ExchangeInfo;
import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.PromoCode;
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
                    file = new File("C:\\Users\\User\\Desktop\\LWFD showRoom\\Repository\\carolsBoutiqueRest\\src\\main\\webapp\\Templates\\sale_receipt.txt");
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
                            holder+="\n";
                            for (int i = 0; i < sale.getItems().size(); i++) {
                                holder+="                                                        <td>"+sale.getItems().get(i).getName() +"</td>\n";
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
                ExchangeInfo exchangeInfo = (ExchangeInfo)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\User\\Desktop\\LWFD showRoom\\Repository\\carolsBoutiqueRest\\src\\main\\webapp\\Templates\\sale_receipt.txt");
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
                            holder+="\n";
                            holder+="                                                        <td>"+exchangeInfo.getNewProductId()+" *new Item"+"</td>\n";
                            holder+="                                                        <td class=\"text-center\">"+exchangeInfo.getPrice() +"</td>\n";
                            holder+="                                                        <td class=\"text-center\">"+0 +"</td>\n";
                            holder+="                                                    </tr>\n";
                            holder+="                                                    <tr height=\"35px\">\n";holder+="                                                        <td>"+exchangeInfo.getNewProductId()+" *new Item"+"</td>\n";
                            holder+="                                                        <td>"+exchangeInfo.getReturnedProductId()+" *exchanged Item"+"</td>\n";

                            holder+="                                                        <td class=\"text-center\">"+exchangeInfo.getPrice() +"</td>\n";
                            holder+="                                                        <td class=\"text-center\">"+0 +"</td>\n";
                            holder+="                                                    </tr>\n";
                            
                        }
                        if(holder.contains("*")){
                            String firstHalf = "";
                            String secondHalf = "";
                            String middle = "";
                            for (int i = 0; i < holder.length(); i++) {
                                if(holder.charAt(i)=='*'){
                                    if(holder.charAt(i+1)=='1'){
                                        firstHalf = holder.substring(0, i);
                                        middle = exchangeInfo.getSaleId();
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='2'){
                                        firstHalf = holder.substring(0, i);
                                        middle = "";
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='3'){
                                        firstHalf = holder.substring(0, i);
                                        middle = "";
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='8'){
                                        firstHalf = holder.substring(0, i);
                                        middle = exchangeInfo.getPrice().toString();
                                        secondHalf = holder.substring(i+2);
                                    }
                                    if(holder.charAt(i+1)=='9'){
                                        firstHalf = holder.substring(0, i);
                                        middle = "";
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
                                            Double d = 0.0;
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
            case"AmmendedReceiptRefund":  
                sale = (Sale)source;
                try{
                    content = new StringBuilder();
                    file = new File("C:\\Users\\User\\Desktop\\LWFD showRoom\\Repository\\carolsBoutiqueRest\\src\\main\\webapp\\Templates\\sale_receipt.txt");
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
                            holder+="\n";
                            for (int i = 0; i < sale.getItems().size(); i++) {
                                holder+="                                                        <td>"+sale.getItems().get(i).getName() +"</td>\n";
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
            case"KeepAside36":
                Product product = (Product)source;
                content = new StringBuilder("Your keep aside request have been approved for "+product.getName()+"\nYou have 36 hours to collect it");
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"KeepAside24":
                product = (Product)source;
                content = new StringBuilder("Your keep aside request have been approved for "+product.getName()+"\nYou have 24 hours to collect it");
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"KeepAside12":
                product = (Product)source;
                content = new StringBuilder("Your keep aside request have been approved for "+product.getName()+"\nYou have 12 hours to collect it");
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"KeepAsideExpired":
                product = (Product)source;
                content = new StringBuilder("Your keep aside request has expired, please come again!");
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"IbtRequest":
                IBT ibt = (IBT)source;
                content = new StringBuilder(ibt.getRequestingBoutique()+" has requested an IBT for product("+ibt.getProductCode()+") from "+ibt.getApprovingBoutique()+"\nPlease go on the system and ");
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"IbtApprove":
                ibt = (IBT)source;
               content = new StringBuilder(ibt.getApprovingBoutique()+"Approved the ibt for product"+ibt.getProductCode());
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"LowStock":
                product = (Product)source;
                content = new StringBuilder("Stock for "+product.getId()+" is low on stock");
                break;
///////////////////////////////case\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            case"Promotion":
                PromoCode messege = (PromoCode)source;
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