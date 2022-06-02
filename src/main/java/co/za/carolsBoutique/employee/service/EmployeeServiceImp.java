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
        String id = loginDetails.keySet().iterator().next();    //this line is to get the keys in the map(id)
        Employee employee = dao.findEmployee(id);               //looks in the database if a user exists with this id
        if (employee!=null) {
            return employee.getPassword().equals(loginDetails.get(id))?employee:null;
        }
        return null;
    }

    private boolean verifyKey(String password, int length){
        if (password == null || password.isEmpty() || password.length()==length) {
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
            if (verifyKey(employee.getPassword(),8)) {
                return dao.addEmployee(employee)?"Employee added your employeeId = "+employee.getId():"Couldn't add employee";
            }
            return "invalid password";
        }
        return "employee already exists";
    }

    @Override
    public String promoteToTeller(List<String> employeeDetails) {
        String empId = employeeDetails.get(0);
        String password = employeeDetails.get(1);
        String roleId = employeeDetails.get(2);
        if (verifyKey(password,8)) {
            return dao.updateToTeller(empId, password, roleId)?"Employee promoted":"Problem promoting the employee";
        }
        return "The password provided is invalid";
    }
    
    @Override
    public String promoteToManager(List<String> employeeDetails) {
        String empId = employeeDetails.get(0);
        String managerCode = employeeDetails.get(1);
        String roleId = employeeDetails.get(2);
        if (verifyKey(managerCode,6)) {
            return dao.updateToManager(empId, managerCode, roleId)?"Employee promoted":"Problem promoting the employee";
        }
        return "The password provided is invalid";
    }
    
    @Override
    public List<Employee> getAllByRole(String roleId,String boutiqueId) {
        return dao.findAllByRole(roleId,boutiqueId);
    }
    
    @Override
    public String removeemployee(String employeeId) {
        return dao.deleteEmployee(employeeId)?"employee removed":"could not remove employee";
    }
    
    @Override
    public List<Employee> getAllEmployees(String boutiqueId) {
        return dao.findAllEmployees(boutiqueId);
    }

    @Override
    public List<Role> getAllRoles() {
        return dao.findAllRoles();
    }

    @Override
    public Role getRole(String roleId) {
        return dao.findRole(roleId);
    }

    @Override
    public String addRole(Role role) {
        if (dao.findRole(role.getId())==null) {
            return dao.addRole(role)?"Success":"Failure";
        }
        return "Role already exists";
    }    

	@Override/////////Dont test yet
	public Boolean verifyManagerCode(Map<String, String> managerCode) {
		return true;
	}
}