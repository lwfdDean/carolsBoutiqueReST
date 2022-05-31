package co.za.carolsBoutique.Sale.repository;

import co.za.carolsBoutique.Sale.model.Sale;


public interface ISaleRepository {
    public boolean addSale(Sale sale);
    public boolean removeSaleLineItem(String saleId, String itemId);
    
}
