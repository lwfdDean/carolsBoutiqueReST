package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.model.SaleLineItem;
import co.za.carolsBoutique.Sale.repository.ISaleRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.mailService.MailService;
import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import co.za.carolsBoutique.product.model.PromoCode;
import jakarta.mail.MessagingException;
import static java.nio.file.Files.size;
import java.sql.Date;
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

    @Override//generating ID
    public String checkout(Sale sale) {
        String id = gen.generateId(sale.getBoutique(), true);
        sale.setId(id);
        sale.setApproved(pg.makePayment(sale));
        if (sale.getApproved()) {
            try {
                new MailService(sale.getCustomerEmail(), "Receipt", "");
            } catch (MessagingException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dao.addSale(sale) ? "accepted" : "declined";
    }

    @Override
    public Sale findSale(String saleId) {
        return dao.findSaleDate(saleId).toLocalDateTime().compareTo(LocalDateTime.now().minusDays(10L))>=0?dao.findSale(saleId):null;
    }
    
    @Override
    public String refund(Map<String, String> refundInfo) {
        String saleId = refundInfo.keySet().iterator().next();
        Sale sale = dao.findSale(saleId);
        String[] pInfo = refundInfo.get(saleId).split(" ");
        //pass in two Strings i.e Barcode (two part code )
        
        double refundAmmount = dao.findSale(saleId).getTotalPrice();
        sale.setTotalPrice(sale.getTotalPrice()-refundAmmount);
        if (sale.getCardNumber()!=null) {
            try {
                new MailService("", "Refund", "");
            } catch (MessagingException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String productId = refundInfo.get(saleId).split(" ")[0];
        return dao.updateSale(sale.getId(), sale.getTotalPrice(),productId)?"refund completed":"couldnt complete refund";
    }
    
    @Override
    public String exchange(List<String> exchangeInfo) {
        boolean b = dao.updateSaleLineItem(exchangeInfo.get(0), exchangeInfo.get(1), exchangeInfo.get(2));
        if (b) {
            try {
                new MailService(exchangeInfo.get(3), "Ammended Receipt", "");
                return "Exchange Successful";
            } catch (MessagingException ex) {
                Logger.getLogger(SaleServiceImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "exchange Failed";
    }

	@Override
	public String addPromotionCode(PromoCode promoCode) {
		return dao.addPromotionCode(
                        promoCode.getCode(), 
                        promoCode.getDiscount(), 
                        promoCode.getCategory(), 
                        Date.valueOf(promoCode.getDate()))
                        ?"promotion code added":"couldn't add promotion code";
	}
}
