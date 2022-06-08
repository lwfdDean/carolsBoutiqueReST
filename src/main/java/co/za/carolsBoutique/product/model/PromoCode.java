package co.za.carolsBoutique.product.model;

import java.time.LocalDate;
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
    private int type;
    private LocalDate date;
    private String category;

    public PromoCode(String code, Double discount, int type, LocalDate date) {
        this.code = code;
        this.discount = discount;
        this.type = type;
        this.date = date;
    }  
}