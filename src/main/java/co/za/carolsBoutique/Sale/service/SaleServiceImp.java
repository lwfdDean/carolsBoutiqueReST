package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.repository.ISaleRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
		if (sale.getPromoCode().isEmpty()) {
			return dao.addSale(sale) ? "accepted" : "declined";
		}
        return dao.addSaleWithPromo(sale) ? "accepted" : "declined";
    }

    @Override
    public Sale findSale(String saleId) {
        return dao.findSaleDate(saleId).toLocalDateTime().compareTo(LocalDateTime.now().minusDays(10L))>=0?dao.findSale(saleId):null;
    }
    
    @Override
    public String refund(Map<String, String> refundInfo) {
        String saleId = refundInfo.keySet().iterator().next();
        Sale sale = dao.findSale(saleId);
        double refundAmmount = 0;
        sale.setTotalPrice(sale.getTotalPrice()-refundAmmount);
        if (sale.getCardNumber()!=null) {
            //send Email
        }
        String productId = refundInfo.get(saleId).split(" ")[0];
        return dao.updateSale(sale.getId(), sale.getTotalPrice(),productId)?"refund completed":"couldnt complete refund";
    }
    
    @Override
    public String exchange(List<String> exchangeInfo) {
        boolean b = dao.updateSaleLineItem(exchangeInfo.get(0), exchangeInfo.get(1), exchangeInfo.get(2));
        if (b) {
            //send email
            return "Exchange Successful";
        }
        return "exchange Failed";
    }

	@Override
	public String addPromotionCode(String code, Double discount, String productId, Date ExpiryDate) {
		return dao.addPromotionCode(code, discount, productId, ExpiryDate)?"promotion code added":"couldn't add promotion code";
	}
}
