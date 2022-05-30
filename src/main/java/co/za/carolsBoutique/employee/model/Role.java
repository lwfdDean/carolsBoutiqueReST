package co.za.carolsBoutique.employee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String id;
    private String name;
    private Integer authorizationLevel;
}
