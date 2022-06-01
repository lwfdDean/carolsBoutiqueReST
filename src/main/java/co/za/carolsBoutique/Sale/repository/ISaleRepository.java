package co.za.carolsBoutique.Sale.repository;

import co.za.carolsBoutique.Sale.model.Sale;


public interface ISaleRepository {
    boolean addSale(Sale sale);
    boolean removeSaleLineItem(String saleId, String productId);
    boolean updateSale(String saleId, Double totalPrice);
	Sale findSale(String saleId);
	boolean updateSaleLineItem(String oldProductId, String newProductId, String saleId);
}
