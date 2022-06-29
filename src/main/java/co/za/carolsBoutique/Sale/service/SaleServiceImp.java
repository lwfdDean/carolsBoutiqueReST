package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.EmailTemplate.EmailReader;
import co.za.carolsBoutique.Sale.model.ExchangeInfo;
import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.repository.ISaleRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.mailService.MailService;
import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.refundedProduct;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleServiceImp implements IServiceSale {

    private ISaleRepository dao;
    private CodeGenerator gen;
    private PaymentGateway pg;
    private EmailReader er;

    public SaleServiceImp(ISaleRepository dao, CodeGenerator gen, PaymentGateway pg) {
        this.er = new EmailReader();
        this.dao = dao;
        this.gen = gen;
        this.pg = pg;
    }

    @Override
    public String checkout(Sale sale) {
        String id = gen.generateId(sale.getBoutique(), true);
        sale.setId(id);
        sale.setApproved(pg.makePayment(sale));
        if (sale.getApproved()) {
            try {
                prepareMail(sale.getCustomerEmail(), "Receipt", er.readInEmail("PurchaseReceipt", sale));
            } catch (IOException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            prepareMail(sale.getCustomerEmail(), "Payment declined", "not epic bruh");

            return "Payment declined";
        }
        return dao.addSale(sale) ? "accepted" : "An error occured";
    }

    @Override
    public Sale findSale(String saleId) {
        return dao.findSale(saleId);
    }

    @Override
    public String refund(refundedProduct refundInfo) {//create vrefund obj bcoz cardNum was removed + products is null
        String saleId = refundInfo.getSaleId();
        if (dao.findSaleDate(saleId).toLocalDateTime().getDayOfYear() + 10 <= LocalDateTime.now().getDayOfYear()) {
            return "10 day return policy has exceeded";
        }
        Sale sale = dao.findSale(saleId);
        sale.setCardNumber(refundInfo.getCardNumber());
        String[] pInfo = refundInfo.getSaleId().split(" ");
        double refundAmmount = 0.0;
        for (Product p : sale.getItems()) {
            if (p.getId().equals(pInfo[0])) {
                refundAmmount = p.getDiscountedPrice();
            }
        }
        sale.setTotalPrice(sale.getTotalPrice() - refundAmmount);
        if (sale.getCardNumber() != null) {
            try {
                new MailService(refundInfo.getCustomerEmail(), "Refund", "").sendMail();
            } catch (MessagingException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dao.updateSale(sale.getId(), sale.getTotalPrice(), pInfo[0]) ? "refund completed" : "couldnt complete refund";
    }

    @Override
    public String exchange(ExchangeInfo exchangeInfo) {
        System.out.println("check hit the the exchange in serviceImp");
        Timestamp time = dao.findSaleDate(exchangeInfo.getSaleId());
        System.out.println(exchangeInfo.getSaleId());
        if(time==null){
            System.out.println("returned null on date");
        }
        System.out.println(time.toString());
        int iTime = time.toLocalDateTime().getDayOfYear();
        int now = LocalDateTime.now().getDayOfYear();
//        if (iTime+ 10 <= now) {
//            return "10 day return policy has exceeded";
//        }
        boolean b = dao.updateSaleLineItem(exchangeInfo.getSaleId(), exchangeInfo.getReturnedProductId(),
                exchangeInfo.getNewProductId(), exchangeInfo.getPrice());
        if (b) {
            try {
                prepareMail(exchangeInfo.getCustomerEmail(), "Exchange", er.readInEmail("AmmendedReceiptExchange", exchangeInfo));
            } catch (IOException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "Exchange Successful";
        }
        return "exchange Failed";
    }

    private void prepareMail(String emailAddress, String subject, String body) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new MailService(emailAddress, subject, body).sendMail();
                } catch (MessagingException ex) {
                    Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t1.start();
    }
}
