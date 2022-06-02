package sale;

import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.repository.ISaleRepository;
import co.za.carolsBoutique.Sale.repository.SaleRepositoryImp;
import co.za.carolsBoutique.Sale.service.SaleIdGenerator;
import co.za.carolsBoutique.Sale.service.SaleServiceImp;
import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import co.za.carolsBoutique.product.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestSaleServiceImp {
    ISaleRepository dao;
    SaleIdGenerator gen;
    SaleServiceImp service;
    Sale saleTest;
    public TestSaleServiceImp() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dao = new SaleRepositoryImp();
        gen = new SaleIdGenerator();
        service = new SaleServiceImp(dao, gen,new PaymentGateway());
        List<Product> products = new ArrayList();
        List sizes = new ArrayList<String>();
        sizes.add("Large");
        List sCats = new ArrayList<String>();
        sCats.add("Shirts");
        products.add(new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99,sCats));
        saleTest = new Sale("2", "new@Test", "1", true, 200.00, products, "1","");
    }
    
    @After
    public void tearDown() {
        dao = null;
        gen = null;
        service = null;
    }

     @Test//test passed
     public void testCreateNewSale() {
        assertEquals("Sale added, sale Id = "+saleTest.getId(), service.checkout(saleTest));
     }
     
     @Test//test passed, accept not returning products in sale
     public void testFindSale() {
        assertEquals(saleTest, service.findSale(saleTest.getId()));
     }
     
//     @Test//test passed
//     public void testVerfyManagersUniqueCode() {
//        assertEquals("Sale added, sale Id = "+saleTest.getId(), service.verfyManagersUniqueCode(saleTest));
//     }
}
