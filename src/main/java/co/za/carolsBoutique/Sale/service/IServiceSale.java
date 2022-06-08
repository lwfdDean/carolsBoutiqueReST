package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.Sale.model.Sale;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface IServiceSale {

    String checkout(Sale sale);

    Sale findSale(String saleId);

    String refund(Map<String, String> refundInfo);

    String exchange(List<String> exchangeInfo);

    String addPromotionCode(String code, Double discount, String productId, Date ExpiryDate);
}
