package co.za.carolsBoutique.Sale.repository;

import co.za.carolsBoutique.Sale.model.Sale;
import java.sql.Timestamp;

public interface ISaleRepository {
    boolean addSale(Sale sale);
    boolean updateSale(String saleId, Double totalPrice,String productId);
    Sale findSale(String saleId);
    boolean updateSaleLineItem(String saleId, String returnedProductId, String newProductId, double productPrice);
    Timestamp findSaleDate(String saleId);

}
