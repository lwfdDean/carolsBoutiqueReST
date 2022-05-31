package co.za.carolsBoutique.ReserveProduct.service;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;

public interface IServiceReservedproduct {
    Reservedproduct findReserveProduct(String reserveProductid);
    String makeReserveProduct(Reservedproduct reserveProduct);
    String removeReserveProduct(String reserveProductid);
}
