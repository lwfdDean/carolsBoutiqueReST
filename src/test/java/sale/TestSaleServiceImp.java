package sale;
//basic tests complete, need discussion tho
import co.za.carolsBoutique.Sale.model.ExchangeInfo;
import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.repository.ISaleRepository;
import co.za.carolsBoutique.Sale.repository.SaleRepositoryImp;
import co.za.carolsBoutique.Sale.service.SaleIdGenerator;
import co.za.carolsBoutique.Sale.service.SaleServiceImp;
import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.Size;
import co.za.carolsBoutique.product.model.refundedProduct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;



public class TestSaleServiceImp {
    ISaleRepository dao;
    SaleIdGenerator gen;
    SaleServiceImp service;
    Sale saleTest;
    List<Size> sizes;
    List<Category> sCats;
    List<Product> products;
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
        service = new SaleServiceImp(dao, gen, new PaymentGateway());
        products = new ArrayList();
        sizes = new ArrayList<Size>();
        sizes.add(new Size("1", "XXXL"));
        sCats = new ArrayList<Category>();
        sCats.add(new Category("22", "hats"));
        products.add(new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99,2.0,sCats));
        saleTest = new Sale("asdew12311", "12werw23", false, 19.99, products, "ao8154bb","123");
        saleTest.setCustomerEmail("dean.swanepoel13@gmail.com");
    }
    
    @After
    public void tearDown() {
        dao = null;
        gen = null;
        service = null;
        products = null;
        sizes = null;
        sCats = null;
        saleTest = null;
    }

     @Test//Id generator is blocking the method for some reason
     public void testCheckout() {
        assertEquals("accepted", service.checkout(saleTest));
     }
	  @Test//Id generator is blocking the method for some reason
     public void testCheckoutWithDiscount() {
        assertEquals("accepted", service.checkout(saleTest));
     }
     @Test//test passed(need to discuss the id or name return of cats and size)
     public void testFindSale() {
         List<Product> products = new ArrayList();
         List<Category> cats = new ArrayList();
         List<Category> cats2 = new ArrayList();
         cats2.add(new Category("3", "Pants"));
         cats.add(new Category("3", "Pants"));
         cats.add(new Category("2", "Shirts"));
         products.add(new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99,20.2, cats));
         products.add(new Product("1321321321", "Pant", "Green Pant", new ArrayList(), "Green", 69.0,420.45, cats2));
        assertEquals(new Sale("asdew12343", "12werw23", false, 19.99, products, "ao8154bb", "2169420"), service.findSale("asdew12343"));
     }
     
     @Test//test passed
     public void testRefund() {
         refundedProduct refundInfo= new refundedProduct();
         refundInfo.setSaleId("asdew12343");
         refundInfo.setRefundProductId(("12343456"));
        assertEquals("10 day return policy has exceeded", service.refund(refundInfo));
     }
     
     @Test//test passed, 
     public void testExchange() {
         ExchangeInfo ei = new ExchangeInfo();
         ei.setSaleId("asdew12343");
         ei.setReturnedProductId("1234567891");
         ei.setNewProductId("1321321321");
         ei.setCustomerEmail("lggousie46@gmail.com");
         ei.setPrice(19.99);
        assertEquals("10 day return policy has exceeded", service.exchange(ei));
     }
     
}
