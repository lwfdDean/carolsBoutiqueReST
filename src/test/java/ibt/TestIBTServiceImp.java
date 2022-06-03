package ibt;
//basic tests done, need discussion
import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.ibt.repository.IBTRepositoryImp;
import co.za.carolsBoutique.ibt.repository.IIBTRepository;
import co.za.carolsBoutique.ibt.service.IBTIdGenerator;
import co.za.carolsBoutique.ibt.service.IBTServiceImp;
import co.za.carolsBoutique.product.model.Product;
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

public class TestIBTServiceImp {
        IBT ibtTest;
        IIBTRepository dao;
	IBTIdGenerator gen;
	IBTServiceImp service = null;
        Product product2;
        List<IBT> ibts;
    
    public TestIBTServiceImp() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before//(Laurence) think we should use productCode, instaid of whole product and size
    public void setUp() {
        System.out.println("okokok");
        dao = new IBTRepositoryImp();
        gen = new IBTIdGenerator();
        service = new IBTServiceImp(dao, gen);
        List<String> sCats = new ArrayList<String>();
        sCats.add("Shirts");
        List sizes = new ArrayList<String>();
        sizes.add("Universal");
        product2 = new Product("1234567891", "PAnts", "Long pants", sizes, "Green", 50.00,sCats);
        ibtTest = new IBT("1", "new@Test", false, product2, "22", "1", "2");
        ibts = new ArrayList();
        ibts.add(ibtTest);
    }
    
    @After
    public void tearDown() {
        dao = null;
        gen = null;
        service = null;
        product2 = null;
        ibtTest = null;
    }

    //@Test//Test all
    //public boolean testAll
    

     @Test//Passes the test
     public void testRequestIBT() {
        assertEquals("IBT has been requested", service.requestIBT(ibtTest));
     }
     
     @Test//Category and size is returning ID
     public void testGetIBT() {
        assertEquals(ibtTest, service.getIBT(ibtTest.getId()));
     }
     
      @Test//Category and size is returning ID
     public void testFindStoreIBTS() {
        assertEquals(ibts, service.findStoreIBTS("1"));
     }
     
      @Test//Passed the test
     public void testApproveIBT() {
         Map<String,Boolean> entry = new HashMap();
         entry.put("1", true);
        assertEquals("IBT was approved", service.approveIBT(entry));
     }

}