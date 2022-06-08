package co.za.carolsBoutique.report.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Report implements Comparable<Object>{
    private String id;
    private String store;
    private Double total;
    private Integer rating;
    private Integer amount;

    public Report(String id, Integer rating) {
        this.id = id;
        this.rating = rating;
    }

    public Report(String id, Double total) {
        this.id = id;
        this.total = total;
    }

    public Report(String id, String store) {
        this.id = id;
        this.store = store;
    }
    
    public Report(String id, String store, Integer amount) {
        this.id = id;
        this.store = store;
        this.amount = amount;
    }

    @Override
    public int compareTo(Object obj) {
        return Double.compare(total, ((Report)obj).getTotal());
    }
}
