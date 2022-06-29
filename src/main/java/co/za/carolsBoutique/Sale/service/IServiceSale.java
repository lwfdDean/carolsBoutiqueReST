package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.Sale.model.ExchangeInfo;
import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.product.model.refundedProduct;
import java.util.List;
import java.util.Map;

public interface IServiceSale {

    String checkout(Sale sale);

    Sale findSale(String saleId);
    
    String refund(refundedProduct refundInfo);

    public String exchange(ExchangeInfo exchangeInfo);
}
