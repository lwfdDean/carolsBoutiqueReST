package co.za.carolsBoutique.Sale.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
public class SaleLineItem {
    private String saleId;
    private String productId;
}
