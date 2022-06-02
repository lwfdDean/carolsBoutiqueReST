package co.za.carolsBoutique.employee.service;

import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.model.Role;
import co.za.carolsBoutique.employee.repository.IEmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeServiceImp implements IServiceEmployee{
    private IEmployeeRepository dao;
    private CodeGenerator gen;

    public EmployeeServiceImp(IEmployeeRepository dao, CodeGenerator gen) {
        this.dao = dao;
        this.gen = gen;
    }

    @Override
    public Employee login(Map<String, String> loginDetails) {
        String id = loginDetails.keySet().iterator().next();
        Employee employee = dao.findEmployee(id);
        if (employee!=null) {
            return employee.getPassword().equals(loginDetails.get(id))?employee:null;
        }
        return null;
    }

    private boolean verifyPassword(String password){
        if (password == null || password.isEmpty() || password.length()==8) {
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
                return dao.addEmployee(employee)?"Employee added your employeeId = "+employee.getId():"Couldn't add employee";
            }
            return "invalid password";
        }
        return "employee already exists";
    }

    @Override
    public String promoteToTeller(Map<String, String> employeeDetails) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public List<Employee> getAllEmployees() {
        return dao.findAllEmployees();
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> role = new ArrayList<>();
        return role;
    }

    @Override
    public Role getRole(String roleId) {
        return dao.findRole(roleId);
    }

    @Override
    public String removeemployee(String employeeId) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public String addRole(Role role) {
        if (dao.findRole(role.getId())==null) {
            return dao.addRole(role)?"Success":"Failure";
        }
        return "Role already exists";
    }    
    
    
}
