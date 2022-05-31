package co.za.carolsBoutique.Sale.model;

import co.za.carolsBoutique.product.model.Product;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    private String Id;
    private String customerEmail;
    private String employee;
    private boolean approved;
    private Double totalPrice;
    private List<Product> items;
}
