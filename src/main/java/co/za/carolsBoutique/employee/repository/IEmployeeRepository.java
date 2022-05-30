package co.za.carolsBoutique.employee.repository;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.model.Role;
import java.util.List;

public interface IEmployeeRepository {
    Employee findEmployee(String employeeId);
    List<Employee> findAllEmployees();
    boolean addEmployee(Employee employee);
    boolean deleteEmployee(String employeeId);
    boolean updateToTeller(String employeeId, String password);
    boolean updatToeManager(String employeeId, String managerUniqueCode);
    
    Role findRole(String roleId);
    List<Role> findAllRoles();
    boolean addRole(Role role);
    boolean deleteRole(String roleId);
}
