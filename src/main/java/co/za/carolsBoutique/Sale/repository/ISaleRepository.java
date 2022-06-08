package co.za.carolsBoutique.Sale.repository;

import co.za.carolsBoutique.Sale.model.Sale;
import java.sql.Date;
import java.sql.Timestamp;

public interface ISaleRepository {
    boolean addSale(Sale sale);
    boolean updateSale(String saleId, Double totalPrice,String productId);
    Sale findSale(String saleId);
    boolean updateSaleLineItem(String saleId, String returnedProductId, String newProductId);
    Timestamp findSaleDate(String saleId);
	public boolean addSaleWithPromo(Sale sale);
	public boolean addPromotionCode(String code,Double discount,String productId, Date ExpiryDate);
}
