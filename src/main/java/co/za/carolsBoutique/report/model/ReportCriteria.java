package co.za.carolsBoutique.report.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data @ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReportCriteria {
    private String boutique;
    private String product;
    private Integer month;
    private Integer results;
    
}
