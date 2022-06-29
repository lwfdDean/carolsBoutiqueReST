package ibt;
//basic tests done, need discussion
import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.ibt.repository.IBTRepositoryImp;
import co.za.carolsBoutique.ibt.repository.IIBTRepository;
import co.za.carolsBoutique.ibt.service.IBTIdGenerator;
import co.za.carolsBoutique.ibt.service.IBTServiceImp;
import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.Size;
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
//        IBT ibtTest;
//        IIBTRepository dao;
//	IBTIdGenerator gen;
//	IBTServiceImp service = null;
//        Product product2;
//        List<IBT> ibts;
//    
//    public TestIBTServiceImp() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before//(Laurence) think we should use productCode, instaid of whole product and size
//    public void setUp() {
//        System.out.println("okokok");
//        dao = new IBTRepositoryImp();
//        gen = new IBTIdGenerator();
//        service = new IBTServiceImp(dao, gen);
//        List<Category> sCats = new ArrayList<>();
//        sCats.add(new Category());
//        List<Size> sizes = new ArrayList<>();
//        sizes.add(new Size());
//        product2 = new Product("1234567891", "PAnts", "Long pants", sizes, "Green", 50.00,20.0,sCats);
//        ibtTest = new IBT("n61atan", "new@Test", true,"1234567891", "aa6082aa", "ab3197aa");
//        ibts = new ArrayList();
//        ibts.add(ibtTest);
//    }
//    
//    @After
//    public void tearDown() {
//        dao = null;
//        gen = null;
//        service = null;
//        product2 = null;
//        ibtTest = null;
//    }
//
//    //@Test//Test all
//    //public boolean testAll
//    
//
//     @Test//Passes the test
//     public void testRequestIBT() {
//        assertEquals("IBT has been requested", service.requestIBT(ibtTest));
//     }
//     
//     @Test//Category and size is returning ID
//     public void testGetIBT() {
//        assertEquals(ibtTest, service.getIBT("n61atan"));
//     }
//     
//      @Test//Category and size is returning ID
//     public void testFindStoreIBTS() {
//        assertEquals(dao.findStoreIBTS("aa6082aa"), service.findStoreIBTS("aa6082aa"));
//     }
//     
//      @Test//Passed the test
//     public void testApproveIBT() {
//         Map<String,Boolean> entry = new HashMap();
//         entry.put("n61atan", true);
//        assertEquals("Successfully Updated", service.approveIBT(entry));
//     }

}