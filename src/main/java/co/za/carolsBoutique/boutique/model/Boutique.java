package co.za.carolsBoutique.boutique.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Boutique {
    private String id;
    private String location;
    private Double dailyTarget;
    private String password;
}
