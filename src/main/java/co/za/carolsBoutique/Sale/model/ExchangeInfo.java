package co.za.carolsBoutique.Sale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeInfo {
    private String saleId;
    private String returnedProductId;
    private String newProductId;
    private Double price;
    private String customerEmail;
}
