package co.za.carolsBoutique.employee.service;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.model.Role;
import java.util.List;
import java.util.Map;

public interface IServiceEmployee {
    Employee login(Map<String, String> loginDetails);
    String register(Employee employee);
    String promoteToTeller(Map<String,String> employeeDetails);
    List<Employee> getAllEmployees();
    String removeemployee(String employeeId);
    
    List<Role> getAllRoles();
    Role getRole(String roleId);
    String addRole(Role role);
    
}
