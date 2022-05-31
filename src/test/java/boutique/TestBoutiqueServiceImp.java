/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package boutique;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.repository.IBoutiqueRepository;
import co.za.carolsBoutique.boutique.service.BoutiqueIdGenerator;
import co.za.carolsBoutique.boutique.service.BoutiqueServiceImp;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;



/**
 *
 * @author wille
 */
public class TestBoutiqueServiceImp {
	
	IBoutiqueRepository dao;
	BoutiqueIdGenerator gen;
	BoutiqueServiceImp boutiqueService = null;
	
	public TestBoutiqueServiceImp() {
	}
	
	@BeforeAll
	public static void setUpClass() {
	}
	
	@AfterAll
	public static void tearDownClass() {
	}
	
	@BeforeEach
	public void setUp() {
		boutiqueService = new BoutiqueServiceImp(dao, gen);
	}
	
	@AfterEach
	public void tearDown() {
		boutiqueService = null;
	}

	@Test
	public void testRegisterNewBoutique() {
		Boutique testBoutique = new Boutique("123", "joburg", 200.23, "123456789012");
		Assert.assertEquals("Boutique added, boutique Id =" + testBoutique.getId(), boutiqueService.registerNewBoutique(testBoutique));
	}
	
	@Test
	public void testEmptyIdRegisterNewBoutique() {
		Boutique testBoutique = new Boutique(null, "joburg", 200.23, "123456789012");
		Assert.assertEquals("Boutique added, boutique Id =" + testBoutique.getId(), boutiqueService.registerNewBoutique(testBoutique));
	}
	
	@Test
	public void testEmptyLocationRegisterNewBoutique() {
		Boutique testBoutique = new Boutique("123", null, 200.23, "123456789012");
		Assert.assertEquals("Couldnt add boutique", boutiqueService.registerNewBoutique(testBoutique));
	}
	
	
	@Test
	public void testChangePassword() {
		Map<String, String> testpasswordDetails = new HashMap<String, String>();
		testpasswordDetails.put("12", "123asd56789t");
		Assert.assertEquals("password Updated", boutiqueService.changePassword(testpasswordDetails));
	}
	
	@Test
	public void testChangeDailyTarget() {
		Map<String, Double> testNewTarget = new HashMap<String, Double>();
		testNewTarget.put("12", 500.0);
		Assert.assertEquals("target updated", boutiqueService.changeDailyTarget(testNewTarget));
	}
	
}
