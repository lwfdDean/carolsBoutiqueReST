package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.Sale.model.Sale;

public interface IServiceSale {

    String checkout(Sale sale);

    Sale findSale(String saleId);

    String updateTotalSalePrice(String saleId, Double totalPrice);

    String updateSaleLineItem(String oldProductId, String newProductId, String saleId);
}
