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
    private String employee;
    private boolean approved;
    private Double totalPrice;
    private List<Product> items;
    private String boutique;
    private String cardNumber;
    private String customerEmail;

    public Sale(String Id, String employee, boolean approved, Double totalPrice, List<Product> items, String boutique, String cardNumber) {
        this.Id = Id;
        this.employee = employee;
        this.approved = approved;
        this.totalPrice = totalPrice;
        this.items = items;
        this.boutique = boutique;
        this.cardNumber = cardNumber;
    }   
}