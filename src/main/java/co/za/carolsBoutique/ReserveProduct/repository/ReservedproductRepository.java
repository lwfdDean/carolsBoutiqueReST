package co.za.carolsBoutique.ReserveProduct.repository;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;

public interface ReservedproductRepository {
    Reservedproduct findReserveProduct(String reserveProductid);
    boolean addReserveProduct(Reservedproduct reserveProduct);
    boolean deleteReserveProduct(String reserveProductid);
    
}
