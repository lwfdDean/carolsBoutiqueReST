package co.za.carolsBoutique.ibt.model;

import co.za.carolsBoutique.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
public class IBT {
    private String id;
    private String customerEmail;
    private Boolean approved;
    private String productCode;
    private String approvingBoutique;
    private String requestingBoutique;
}
