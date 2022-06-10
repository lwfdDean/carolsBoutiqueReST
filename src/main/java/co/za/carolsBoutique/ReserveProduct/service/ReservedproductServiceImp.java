/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.ReserveProduct.service;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.product.model.Product;
import java.util.Map;
import co.za.carolsBoutique.ReserveProduct.repository.IReservedproductRepository;
import co.za.carolsBoutique.mailService.MailService;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservedproductServiceImp implements IServiceReservedproduct {

    private IReservedproductRepository dao;
    private CodeGenerator gen;

    public ReservedproductServiceImp(IReservedproductRepository dao, CodeGenerator gen) {
        this.dao = dao;
        this.gen = gen;
    }

    @Override
    public String makeReserveProduct(Reservedproduct reserveProduct) {
        String[] productInfo = reserveProduct.getProductCode().split(" ");
        Map<String, Integer> entry = dao.findStockEntry(productInfo[0], reserveProduct.getBoutiqueId(), productInfo[1]);
        String id = entry.keySet().iterator().next();
        return dao.addReserveProduct(reserveProduct, id, entry.get(id)) ? "product reserved" : "error occured";
    }

    @Override
    public String removeReserveProduct(String email) {
        return dao.deleteReserveProduct(email) ? "Deteting item successful" : "Deletion failed";
    }

    @Override
    public Product collectKeepAside(String customerEmail) {
        String stockId = dao.findReserveProduct(customerEmail);
        Map<String, String> productInfo = dao.addStock(stockId); //this line returns null
        String productId = productInfo.keySet().iterator().next();
        String size = productInfo.get(productId);
        return dao.findProductByProductCode(productId, size);
    }//index out of bounds thrown

    @Override
    public void emailNotifyCustomers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    List<Reservedproduct> prods = dao.getAllReservedproducts();
                    for (Reservedproduct prod : prods) {
                        String email = prod.getCustomerEmail();
                        try {
                            if (prod.getDate().plusHours(12L).isAfter(LocalDateTime.now())) {

                                new MailService(email, "Carols Boutique keep-aside", "").sendMail();

                                continue;
                            }
                            if (prod.getDate().plusDays(1L).isAfter(LocalDateTime.now())) {

                                new MailService(email, "Carols Boutique keep-aside", "").sendMail();

                                continue;
                            }
                            if (prod.getDate().plusDays(2L).isAfter(LocalDateTime.now())) {

                                new MailService(email, "Carols Boutique keep-aside", "").sendMail();
                                dao.deleteReserveProduct(email);

                                continue;
                            }
                            if (prod.getDate().plusHours(36L).isAfter(LocalDateTime.now())) {

                                new MailService(email, "Carols Boutique keep-aside", "").sendMail();

                            }
                        } catch (MessagingException ex) {
                            Logger.getLogger(ReservedproductServiceImp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }).start();
    }

}
