package co.za.carolsBoutique.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockEntry {
    private String productCode;
    private String boutiqueId;
    private int quantity;
    private String employeeId;
}
