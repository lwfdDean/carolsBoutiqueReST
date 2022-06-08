package reserveProduct;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.ReserveProduct.repository.IReservedproductRepository;
import co.za.carolsBoutique.ReserveProduct.repository.ReservedproductRepositoryImp;
import co.za.carolsBoutique.ReserveProduct.service.ReservedproductIdGenerator;
import co.za.carolsBoutique.ReserveProduct.service.ReservedproductServiceImp;
import co.za.carolsBoutique.product.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestReservedproductServiceImp {
    Reservedproduct reserveProduct;
    IReservedproductRepository dao;
    ReservedproductIdGenerator gen;
    ReservedproductServiceImp service = null;
    public TestReservedproductServiceImp() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dao = new ReservedproductRepositoryImp();
        gen = new ReservedproductIdGenerator();
        service = new ReservedproductServiceImp(dao, gen);
        reserveProduct = new Reservedproduct("1234567891 22", "gg@gg", "1");
    }
    
    @After
    public void tearDown() {
        dao = null;
        gen = null;
        service = null;
        reserveProduct = null;
    }

     @Test//test passed, changed the Id in db to auto inc.
     public void testMakeReserveProduct() {
        assertEquals("product reserved",service.makeReserveProduct(reserveProduct));
     }
     
     @Test//Passed the test
     public void testRemoveReserveProduct() {
        assertEquals("Deteting item successful",service.removeReserveProduct("2"));
     }
     
     
    @Test//Passed the test
    public void testCollectKeepAside() {
        List<String> sizes = new ArrayList<>();
        sizes.add("22");
        List<String> categories = new ArrayList<>();
        categories.add("4");
        assertEquals(new Product("1234567891", "PAnts", "Long pants", sizes, "Green", 50.00,40.00, categories),service.collectKeepAside("gg@gg"));
     }
}
