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
    Boutique boutique;
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
            boutique = new Boutique("ae1673nn", "Langebaan", 300.0,7000.0, "lng333axs376");
            allBoutiques =  new ArrayList<Boutique>();
            allBoutiques.add(boutique);
            loginDetails = new HashMap<String,String>();
            loginDetails.put("Pe9781iP", "123456789aaaa");
            changeTarget = new HashMap<String,Double>();
            changeTarget.put("1", 600.00);
    }

    @After
    public void tearDown() {
        dao = null;
        gen = null;
        service = null;
        boutique = null;
        allBoutiques = null;
    }
    @Test//because the regester boutique generates a random id, its impossible to pass this test, but the method works works
    public void testGetAllBoutiques() {
        assertEquals(dao.findAllBoutiques(), service.getAllBoutiques());
    }
    @Test//Passed the test
    public void testRegisterNewBoutique() {
        assertEquals("Boutique added, boutique location =Langebaan", service.registerNewBoutique(boutique));
    }
    
    @Test//
    public void testRateTheBoutique() {
        assertEquals("Thank you for rating our store", service.rateTheBoutique(new Review("5", "nice service","gg@gg", "1")));
     }
     @Test//
    public void testUpdateBoutique() {
        assertEquals("Successfully updated", service.updateBoutique(boutique));
     }
    @Test//
    public void testFindBoutique() {
        assertEquals(boutique, service.findBoutique(boutique.getId()));
     }
   
}
