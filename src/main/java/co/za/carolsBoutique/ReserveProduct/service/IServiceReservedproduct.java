package co.za.carolsBoutique.ReserveProduct.service;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.product.model.Product;

public interface IServiceReservedproduct {
    String makeReserveProduct(Reservedproduct reserveProduct);
    String removeReserveProduct(String email);
    Product collectKeepAside(String customerEmail);
    void emailNotifyCustomers();
}
