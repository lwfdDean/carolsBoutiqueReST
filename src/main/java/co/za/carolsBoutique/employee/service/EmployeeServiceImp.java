package co.za.carolsBoutique.employee.service;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.model.Role;
import co.za.carolsBoutique.employee.repository.IEmployeeRepository;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImp implements IServiceEmployee{
    private IEmployeeRepository dao;

    public EmployeeServiceImp(IEmployeeRepository dao) {
        this.dao = dao;
    }

    @Override
    public Employee login(Map<String, String> loginDetails) {
        Employee employee = dao.findEmployee(loginDetails.keySet().iterator().next());
        if (employee!=null) {
            return employee.getPassword().equals(loginDetails.get(loginDetails.keySet().iterator().next()))?employee:null;
        }
        return null;
    }

    private boolean verifyPassword(String password){
        if (password == null || password.isBlank() || password.isEmpty() || password.length()==12) {
            return false;
        }
        int nums = 0;
        int chars = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                nums++;
            }
            if (Character.isLetter(password.charAt(i))) {
                chars++;
            }
        }
        return nums>0 && chars>0;
    }
    
    @Override
    public String register(Employee employee) {
        if (dao.findEmployee(employee.getId())==null) {
            if (verifyPassword(employee.getPassword())) {
                return dao.addEmployee(employee)?"Employee added your employeeId = "+employee.getId():"Couldnt add employee";
            }
            return "invalid password";
        }
        return "employee already exists";
    }

    @Override
    public String promoteToTeller(Map<String, String> employeeDetails) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Employee> getAllEmployees() {
        return dao.findAllEmployees();
    }

    @Override
    public List<Role> getAllRoles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Role getRole(String roleId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String removeemployee(String employeeId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
