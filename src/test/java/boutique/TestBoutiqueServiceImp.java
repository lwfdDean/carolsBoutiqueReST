
package boutique;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.boutique.repository.IBoutiqueRepository;
import co.za.carolsBoutique.boutique.service.BoutiqueIdGenerator;
import co.za.carolsBoutique.boutique.service.BoutiqueServiceImp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBoutiqueServiceImp {
	
	IBoutiqueRepository dao;
	BoutiqueIdGenerator gen;
	BoutiqueServiceImp service = null;
        Boutique bTest;
        List<Boutique> allBoutiques;
	
	public TestBoutiqueServiceImp() {   
        }
	
	@BeforeAll
	public static void setUpClass() {
            
	}
	
	@AfterAll
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
            System.out.println("okokok");
            dao = new BoutiqueRepositoryImp();
            gen = new BoutiqueIdGenerator();
            service = new BoutiqueServiceImp(dao, gen);
            bTest = new Boutique("3", "Pretoria", 500.0, "123");
            allBoutiques =  new ArrayList<Boutique>();
            allBoutiques.add(new Boutique("1", "Johannesburg", 200.0, "123") );
            allBoutiques.add(new Boutique("2", "CapeTown", 300.0, "123") );
	}
	
	@AfterEach
	public void tearDown() {
            
	}

	@Test
	public void testGetAllBoutiques() {
            
            assertEquals("Boutique added, boutique Id =3",service.registerNewBoutique(bTest));
	}
	
}
