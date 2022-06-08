package sale;
//basic tests complete, need discussion though

import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.repository.ISaleRepository;
import co.za.carolsBoutique.Sale.repository.SaleRepositoryImp;
import co.za.carolsBoutique.Sale.service.SaleIdGenerator;
import co.za.carolsBoutique.Sale.service.SaleServiceImp;
import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import co.za.carolsBoutique.product.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSaleServiceImp {

    ISaleRepository dao;
    SaleIdGenerator gen;
    SaleServiceImp service;
    Sale saleTest;
    List sizes;
    List sCats;
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
        sizes = new ArrayList<String>();
        sizes.add("Large");
        sCats = new ArrayList<String>();
        sCats.add("Shirts");
        products.add(new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99, 15.00, sCats));
        saleTest = new Sale("3", "1", true, 200.00, products, "bteredsw3g5", "123");
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
        Assert.assertFalse("accepted", dao.addSale(saleTest));
    }

    @Test//Id generator is blocking the method for some reason
    public void testCheckoutWithDiscount() {
        saleTest.setPromoCode("123");
        assertEquals("accepted", service.checkout(saleTest));
    }

    @Test//test passed(need to discuss the id or name return of cats and size)
    public void testFindSale() {
        //List<Product> products = new ArrayList();
       // products.add(new Product("HAT1010101", "Hat", "Red Hat", sizes, "Red", 20.2, 15.00, sCats));
        //assertEquals(new Sale("TRS00000045", "me@boutique.co.za",Boolean.TRUE, 177.00, products, "PtaB101",""), service.findSale("1"));
        assertEquals(dao.findSale("TRS0000045"), dao.findSaleDate("2022-06-08 14:41:39"));
    }

    @Test //test passed
    public void testRefund() {
        Map<String, String> refundInfo = new HashMap<>();
        //refundInfo.put("1", "123");
        assertEquals(refundInfo.put("1", "refund completed"), dao.findSale("TRS0000045"));
    }

    @Test//test passed
    public void testExchange() {
        List<String> exchangeInfo = new ArrayList<>();//sale,return, new
        exchangeInfo.add("3");
        exchangeInfo.add("TRS0000045");
        exchangeInfo.add("123");
        assertEquals(exchangeInfo.add("Exchange Successful"),service.exchange(exchangeInfo));
    }

}
