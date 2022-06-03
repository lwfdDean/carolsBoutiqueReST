/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package boutique;
//basic tests passed
import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.model.Review;
import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.boutique.repository.IBoutiqueRepository;
import co.za.carolsBoutique.boutique.service.BoutiqueIdGenerator;
import co.za.carolsBoutique.boutique.service.BoutiqueServiceImp;
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
check dean added a method rateBoutique
 */
public class TestBoutiqueServiceImp {

    IBoutiqueRepository dao;
    BoutiqueIdGenerator gen;
    BoutiqueServiceImp service = null;
    Boutique bTest;
    List<Boutique> allBoutiques;
    Map<String,String> loginDetails;
    Map<String,Double> changeTarget;

    public TestBoutiqueServiceImp() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        System.out.println("okokok");
            dao = new BoutiqueRepositoryImp();
            gen = new BoutiqueIdGenerator();
            service = new BoutiqueServiceImp(dao, gen);
            bTest = new Boutique("3", "Pretoria", 500.0, "123456789aaa");
            allBoutiques =  new ArrayList<Boutique>();
            allBoutiques.add(new Boutique("1", "Johannesburg", 600.0, "123456789aaa") );
            allBoutiques.add(new Boutique("2", "CapeTown", 300.0, "123") );
            loginDetails = new HashMap<String,String>();
            loginDetails.put("1", "123456789aaa");
            changeTarget = new HashMap<String,Double>();
            changeTarget.put("1", 600.00);
    }

    @After
    public void tearDown() {
        dao = null;
        gen = null;
        service = null;
        bTest = null;
        allBoutiques = null;
    }
    @Test//Passed the test
    public void testGetAllBoutiques() {
        assertEquals(allBoutiques, service.getAllBoutiques());
    }
    @Test//Passed the test(will be a different Id)
    public void testRegisterNewBoutique() {
        assertEquals("Boutique added, boutique Id =3", service.registerNewBoutique(bTest));
    }
    
     @Test//Passed the test
     public void testLogin() {
         assertEquals(new Boutique("1", "Johannesburg", 200.00, "123"), service.login(loginDetails));
     }
     
    @Test//Passed the test
    public void testChangePassword() {
        assertEquals("password Updated", service.changePassword(loginDetails));
     }

    @Test//Passed the test
    public void testChangeDailyTarget() {
        assertEquals("target updated", service.changeDailyTarget(changeTarget));
     }
    
    @Test//Passed the test
    public void testRateTheBoutique() {
        assertEquals("Thank you for rating our store", service.rateTheBoutique(new Review("5", "nice service", "emailAddress", "gg@gg", "1")));
     }
    
   
}
