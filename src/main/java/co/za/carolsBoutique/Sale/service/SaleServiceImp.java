package co.za.carolsBoutique.Sale.service;

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
            try {
                new MailService(sale.getCustomerEmail(), "Receipt", "").sendMail();
            } catch (MessagingException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
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
        if (dao.findSaleDate(saleId).toLocalDateTime().compareTo(LocalDateTime.now().minusDays(10L))>=0) {
            return "10 day return policy has exceeded";
        }
        Sale sale = dao.findSale(saleId);
        String[] pInfo = refundInfo.get(saleId).split(" ");
        double refundAmmount = 0.0;
        for (Product p: sale.getItems()) {
            if (p.getId().equals(pInfo[0])) {
                refundAmmount = p.getDiscountedPrice();
            }
        }
        sale.setTotalPrice(sale.getTotalPrice()-refundAmmount);
        if (sale.getCardNumber()!=null) {
            try {
                new MailService("", "Refund", "").sendMail();
            } catch (MessagingException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dao.updateSale(sale.getId(), sale.getTotalPrice(),pInfo[0])?"refund completed":"couldnt complete refund";
    }
    
    @Override
    public String exchange(List<String> exchangeInfo) {
        String saleId = exchangeInfo.get(0);
        if (dao.findSaleDate(saleId).toLocalDateTime().compareTo(LocalDateTime.now().minusDays(10L))>=0) {
            return "10 day return policy has exceeded";
        }
        boolean b = dao.updateSaleLineItem(saleId, exchangeInfo.get(1),
                exchangeInfo.get(2),Double.parseDouble(exchangeInfo.get(3)));
        if (b) {
            try {
                new MailService(exchangeInfo.get(3), "Ammended Receipt", "").sendMail();
                return "Exchange Successful";
            } catch (MessagingException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "exchange Failed";
    }
}
