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
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Disabled;

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
        empTest = new Employee("fm101", "Fred", "Mojapelo", "frm@b.com", "1234", "4321", new Role("FRM101","Fred",2), "PtaB101");
    }
    
    @After
    public void tearDown() {
    }

     @Test
     public void testRegisterEmployee() {
        
     }
     
     @Test
     public void testPromoteToTeller(){
         List<String> employeeInfo = new ArrayList<>();
         employeeInfo.add("1");
         employeeInfo.add("123");
         employeeInfo.add("1");
         assertEquals("Employee promoted",employeeService.promoteToTeller(employeeInfo));
           
     }
     @Test
     public void testRemoveEmployee(){
         
         assertEquals("employee removed",employeeService.removeemployee("1"));
      
     }
     
     @Test //Test passed
     public  void testLogin(){
         Map<String,String> userLogin = new HashMap<>();
         userLogin.put("fm101","1234");
        assertEquals(empTest,employeeService.login(userLogin));
     }
     
     @Test  //Test passed
     public  void testGetAllEmployees(){
         List<Employee> emps = dao.findAllEmployees("PtaB101");
         assertEquals(emps,employeeService.getAllEmployees("PtaB101"));
     }
     
     @Test  //Test passed
     public  void testGetAllRoles(){
         List<Role> employeeRole = dao.findAllRoles();
         assertEquals(employeeRole,employeeService.getAllRoles());
        
     }
     
}
