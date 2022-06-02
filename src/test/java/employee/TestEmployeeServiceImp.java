/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.model.Role;
import co.za.carolsBoutique.employee.repository.EmployeeRepositoryImp;
import co.za.carolsBoutique.employee.repository.IEmployeeRepository;
import co.za.carolsBoutique.employee.service.EmployeeIdGenerator;
import co.za.carolsBoutique.employee.service.EmployeeServiceImp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class TestEmployeeServiceImp {
        Employee empTest;
        IEmployeeRepository dao;
	EmployeeIdGenerator gen;
	EmployeeServiceImp employeeService = null;
    
    public TestEmployeeServiceImp() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dao = new EmployeeRepositoryImp();
        gen = new EmployeeIdGenerator ();
        employeeService = new EmployeeServiceImp(dao,gen);
        empTest = new Employee("1", "Laurence", "Gouws", "gg@gg", "123", "123", new Role("1","Manager",2), "1");
    }
    
    @After
    public void tearDown() {
        dao = null;
        gen = null;
        employeeService = null;
        empTest = null;
    }
     @Test //Test passed
     public  void testLogin(){
         Map<String,String> userLogin = new HashMap<>();
         userLogin.put("1","123");
        assertEquals(empTest,employeeService.login(userLogin));
     }

     @Test//Test passed, left a comment about password verification location (EmployeeServiceImp line 28)
     public void testRegister() {
        assertEquals("Employee added your employeeId = 2",employeeService.register(new Employee("3", "2", "2", "2", "a222222", "2", new Role("1", "Manager", 2), "1")));
     }
     
     @Test//Passed the test
     public void testPromoteToTeller(){
         List<String> employeeInfo = new ArrayList<>();
         employeeInfo.add("2");
         employeeInfo.add("a222222");
         employeeInfo.add("2");
         assertEquals("Employee promoted",employeeService.promoteToTeller(employeeInfo));
           
     }
      @Test//Passed the test
     public void testPromoteToManager(){
         List<String> employeeInfo = new ArrayList<>();
         employeeInfo.add("2");
         employeeInfo.add("a222222");
         employeeInfo.add("1");
         assertEquals("Employee promoted",employeeService.promoteToTeller(employeeInfo));
     }
     
      @Test  //Passed the test
     public  void testGetAllRoles(){
         List<Employee> employeeRole = dao.findAllByRole("1","1");
         assertEquals(employeeRole,employeeService.getAllByRole("1","1"));
        
     }
      @Test  //Passed the test
     public  void testGetAllByRoles(){
         List<Role> employeeRole = dao.findAllRoles();
         assertEquals(employeeRole,employeeService.getAllRoles());
        
     }
     
     @Test//Passed the test
     public void testRemoveEmployee(){
         assertEquals("employee removed",employeeService.removeemployee("3"));
     }
     
    
     
     @Test  //Passed the test
     public  void testGetAllEmployees(){
         List<Employee> emps = dao.findAllEmployees("1");
         System.out.println(emps.get(0).getName());
         assertEquals(emps,employeeService.getAllEmployees("1"));
     }
     
       @Test//Passed the test
     public void testGetRole(){
         assertEquals(new Role("1", "Manager", 2),employeeService.getRole("1"));
     }
     
    @Test//Passed the test
     public void testAddRole(){
         assertEquals("Success",employeeService.addRole(new Role("5","Sweeper",3)));
     }
     
}
