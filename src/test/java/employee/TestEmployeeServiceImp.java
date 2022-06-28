/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;
//basic tests passed, see comment above testRegester
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
         userLogin.put("sa2688aa","redree787vft");
         empTest = new Employee("sa2688aa", "Adam", "Samules", "Addasamm@gmail.com", "redree787vft", "Adsams", new Role("tel101", "Teller", 2), "ao8154bb");
        assertEquals(empTest,employeeService.login(userLogin));
     }

     @Test//Test passed, left a comment about password verification location (EmployeeServiceImp line 28)
     public void testRegister() {
        assertEquals("Employee added",employeeService.register(new Employee("sa2688aa", "Adam", "Samules", "Addasamm@gmail.com", "redree787vft", "Adsams", new Role("tel101", "Teller", 2), "ao8154bb")));
     }
     
     @Test//Passed the test
     public void testPromoteToTeller(){
         List<String> employeeInfo = new ArrayList<>();
         employeeInfo.add("sa2688aa");
         employeeInfo.add("redree787vft");
         employeeInfo.add("tel101");
         assertEquals("Employee promoted",employeeService.promoteToTeller(employeeInfo));
           
     }
//      @Test//Passed the test (commented out, because it will change stake of dumy emp, causing other tests to fail)
//     public void testPromoteToManager(){
//         List<String> employeeInfo = new ArrayList<>();
//         employeeInfo.add("sa2688aa");
//         employeeInfo.add("redree787vft");
//         employeeInfo.add("man101");
//         assertEquals("Employee promoted",employeeService.promoteToTeller(employeeInfo));
//     }
     
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
     
//     @Test//Passed the test(commented out, because the test can only delte an emp once, then it will fail, because emp doesnt exist the second time)
//     public void testRemoveEmployee(){
//         assertEquals("employee removed",employeeService.removeemployee("si7650kl"));
//     }
     
    
     
     @Test  //Passed the test
     public  void testGetAllEmployees(){
         List<Employee> emps = dao.findAllEmployees("man101");
         assertEquals(emps,employeeService.getAllEmployees("man101"));
     }
     
       @Test//Passed the test
     public void testGetRole(){
         assertEquals(new Role("man101", "manager", 3),employeeService.getRole("man101"));
     }
     
//    @Test//Passed the test(commented out, will fail if the method runs twise with same info)
//     public void testAddRole(){
//         assertEquals("Success",employeeService.addRole(new Role("6","Sweeper",3)));
//     }
     
     @Test//Passed the test
     public void testVerifyManagerCode(){
         //boutique, code
         Map<String,String> codeCheckInfo = new HashMap<>();
         codeCheckInfo.put("1", "2");
         String a = "bb;vv";
         assertEquals("Code valid",employeeService.verifyManagerCode(a));
     }
}
