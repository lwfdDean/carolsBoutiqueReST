package co.za.carolsBoutique.employee.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String id;
    private String name;
    private String surname;
    private String emailAddress;
    private String password;
    private String managerCode;
    private Role role;
    private String boutique;
}
