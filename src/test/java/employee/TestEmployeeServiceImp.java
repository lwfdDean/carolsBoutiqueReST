/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employee;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.repository.IEmployeeRepository;
import co.za.carolsBoutique.employee.service.EmployeeIdGenerator;
import co.za.carolsBoutique.employee.service.EmployeeServiceImp;
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
        employeeService = new EmployeeServiceImp(dao,gen);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     //@Disabled
     public void testRegisterEmployee() {
         Employee testRegEmployee = new Employee(
                 "Fred123",
                 "Freddy",
                 "Mojapelo",
                 "myPass",
                 "1234",
                 "3",
                 "MyBout123"      
                  );
        //Assert.assertEquals("Employee added, employeeId=" + testRegEmployee.getId(), employeeService.register(testRegEmployee));
     Assert.assertEquals(employeeService,testRegEmployee);
     }
     
     @Test
     public  void testGetAllEmployees(){
         Employee getAllMyEmployees;
            getAllMyEmployees = new Employee(
                    dao.findAllEmployees()+"id","name","surname","password","managerUniqueCode","role",
                    gen.generateId("id", true));
         assertEquals(employeeService,getAllMyEmployees);
     }
    //EXAMPLE
    //   String message = "Hello World";	
    //   MessageUtil messageUtil = new MessageUtil(message);
    //
    //   @Test
    //   public void testPrintMessage() {
    //   assertEquals(message,messageUtil.printMessage());
     
     @Test
     public  void testGetAllRoles(){
         Employee getAllR = new Employee();
         Assert.assertEquals(employeeService,getAllR);
        
     }
}
