package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.Sale.model.ExchangeInfo;
import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.repository.ISaleRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.mailService.MailService;
import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import co.za.carolsBoutique.product.model.Product;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleServiceImp implements IServiceSale {

    private ISaleRepository dao;
    private CodeGenerator gen;
    private PaymentGateway pg;

    public SaleServiceImp(ISaleRepository dao, CodeGenerator gen, PaymentGateway pg) {
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

            prepareMail(sale.getCustomerEmail(), "Receipt", "bruh");

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
    public String refund(Map<String, String> refundInfo) {
        String saleId = refundInfo.keySet().iterator().next();
        if (dao.findSaleDate(saleId).toLocalDateTime().getDayOfYear() + 10 <= LocalDateTime.now().getDayOfYear()) {
            return "10 day return policy has exceeded";
        }
        Sale sale = dao.findSale(saleId);
        String[] pInfo = refundInfo.get(saleId).split(" ");
        double refundAmmount = 0.0;
        for (Product p : sale.getItems()) {
            if (p.getId().equals(pInfo[0])) {
                refundAmmount = p.getDiscountedPrice();
            }
        }
        sale.setTotalPrice(sale.getTotalPrice() - refundAmmount);
        if (sale.getCardNumber() != null) {
            try {
                new MailService("", "Refund", "").sendMail();
            } catch (MessagingException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dao.updateSale(sale.getId(), sale.getTotalPrice(), pInfo[0]) ? "refund completed" : "couldnt complete refund";
    }

    @Override
    public String exchange(ExchangeInfo exchangeInfo) {
        if (dao.findSaleDate(exchangeInfo.getSaleId()).toLocalDateTime().getDayOfYear() + 10 <= LocalDateTime.now().getDayOfYear()) {
            return "10 day return policy has exceeded";
        }
        boolean b = dao.updateSaleLineItem(exchangeInfo.getSaleId(), exchangeInfo.getReturnedProductId(),
                exchangeInfo.getNewProductId(), exchangeInfo.getPrice());
        if (b) {
            prepareMail(exchangeInfo.getCustomerEmail(), "Exchange", "");
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
