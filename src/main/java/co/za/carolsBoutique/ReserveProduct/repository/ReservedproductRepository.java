package co.za.carolsBoutique.ReserveProduct.repository;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import java.util.Map;

public interface ReservedproductRepository {
    String findReserveProduct(String customerEmail);
    boolean addReserveProduct(Reservedproduct reserveProduct, String id, int quantity);
    boolean deleteReserveProduct(String reserveProductid);
    Map<String,Integer> findStockEntry(String productId,String boutiqueId,String size);
    Map<String,String> addStock(String stockId);
}
