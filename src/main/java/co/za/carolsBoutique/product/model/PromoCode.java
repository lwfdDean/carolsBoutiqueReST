package co.za.carolsBoutique.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
public class PromoCode {
    private String code;
    private Double discount;
    private Integer type;
    private String date;
    private String category;

    public PromoCode(String code, Double discount, int type, String date) {
        this.code = code;
        this.discount = discount;
        this.type = type;
        this.date = date;
    }  
}