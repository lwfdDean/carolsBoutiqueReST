package co.za.carolsBoutique.employee.service;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.model.Role;
import java.util.List;
import java.util.Map;

public interface IServiceEmployee {
    Employee login(Map<String, String> loginDetails);
    String register(Employee employee);
    String promoteToTeller(List<String> employeeDetails);
    String promoteToManager(List<String> employeeDetails);
    List<Employee> getAllEmployees(String boutiqueId);
    List<Employee> getAllByRole(String roleId, String boutiqueId);
    String removeemployee(String employeeId);
    
    List<Role> getAllRoles();
    Role getRole(String roleId);
    String addRole(Role role);

    String verifyManagerCode(Map<String, String> managerCode);
    
}
