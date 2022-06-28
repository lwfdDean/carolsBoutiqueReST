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
        reserveProduct = new Reservedproduct("1234567891 1", "test@gmail.com", "1",LocalDateTime.now());
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
        assertEquals("There is no stock of the product",service.makeReserveProduct(reserveProduct));
     }
     
     @Test//Dean please help
     public void testRemoveReserveProduct() {
        assertEquals("Deteting item successful",service.removeReserveProduct("test@gmail.com"));
     }
     
     
    @Test//Passed the test
    public void testCollectKeepAside() {
        List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("1","XXXL"));
        List<Category> categories = new ArrayList<>();
        //categories.add(new Category());
        assertEquals(new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99,20.2, categories),service.collectKeepAside("lol@gmail.com"));
     }
}
