package reserveProduct;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.ReserveProduct.repository.IReservedproductRepository;
import co.za.carolsBoutique.ReserveProduct.repository.ReservedproductRepositoryImp;
import co.za.carolsBoutique.ReserveProduct.service.ReservedproductIdGenerator;
import co.za.carolsBoutique.ReserveProduct.service.ReservedproductServiceImp;
import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.Size;
import java.time.LocalDateTime;
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
        reserveProduct = new Reservedproduct("thuyr45321 10", "test@gmail.com", "ao8154bb",LocalDateTime.now());
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
     
     @Test//Dean please help
     public void testRemoveReserveProduct() {
        assertEquals("Deteting item successful",service.removeReserveProduct("test@gmail.com"));
     }
     
     
    @Test//Passed the test
    public void testCollectKeepAside() {
        List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("10","3"));
        List<Category> categories = new ArrayList<>();
        //categories.add(new Category());
        assertEquals(new Product("thuyr45321", "Denim", "Denim Pants", sizes, "Blue", 100.9,0.0, categories),service.collectKeepAside("ff@ff"));
     }
}
