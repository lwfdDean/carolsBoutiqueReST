package co.za.carolsBoutique.employee.repository;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.model.Role;
import java.util.List;

public interface IEmployeeRepository {
    Employee findEmployee(String employeeId);
    List<Employee> findAllEmployees(String boutiqueId);
    List<Employee> findAllByRole(String roleId,String boutiqueId);
    boolean addEmployee(Employee employee);
    boolean deleteEmployee(String employeeId);
    boolean updateToTeller(String employeeId, String password, String roleId);
    boolean updateToManager(String employeeId, String managerUniqueCode, String roleId);
    
    Role findRole(String roleId);
    List<Role> findAllRoles();
    boolean addRole(Role role);
    boolean deleteRole(String roleId);
}
