package product;
//done with basic tests, need to test overloaded method of find product
import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.NewProduct;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.Size;
import co.za.carolsBoutique.product.model.StockEntry;
import co.za.carolsBoutique.product.repository.IProductRepository;
import co.za.carolsBoutique.product.repository.ProductRepositoryImp;
import co.za.carolsBoutique.product.service.ProductIdGenerator;
import co.za.carolsBoutique.product.service.ProductServiceImp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestProductServiceImp {
     StockEntry stockEntry;
    List<String> sCats;
    List<Category> cats;
    List<Size> sizes;
    Product product = null;
    Product product2 = null;
    List<Product> allProducts;
    IProductRepository dao;
    ProductIdGenerator gen;
    ProductServiceImp service = null;
    public TestProductServiceImp() {
    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//        allProducts = new ArrayList();
//        dao = new ProductRepositoryImp();
//        gen = new ProductIdGenerator();
//        service = new ProductServiceImp(dao);
//        sCats = new ArrayList<>();
//        sCats.add("");
//        cats = new ArrayList<>();
//        cats.add(new Category("2", "Shirts"));
//        sizes = new ArrayList<>();
//        sizes.add(new Size());
//        product = new Product("123", "Hat", "Red Hat", sizes, "Red", 20.2,2.00,cats);
//        product2 = new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99,15.00,cats);
//        sizes = new ArrayList<>();
//        sizes.add("1");
//        sizes.add("2");
//        sizes.add("3");
//        sizes.add("4");
//        sizes.add("5");
//        sizes.add("6");
//        sizes.add("7");
//        product = new Product("thuyr45321", "Denim", "Denim Pants", sizes, "Blue", 100.90,0.00,sCats);
//        product2 = new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99,15.00,sCats);
//        allProducts.add(product);
//        stockEntry =new StockEntry("blden1212 32", "ao8154bb", 10, "Dl3983nl");
//        //10 diget id and space and 2 diget code for size
//    }
//    
//    @After
//    public void tearDown() {
//        service = null;
//        product = null;
//        product2 = null;
//    }
//
//     @Test//Passes the test
//     public void testFindAllProducts() {
//         assertEquals(allProducts, service.findAllProducts());
//     }
//      @Test//Passes the test
//     public void testFindProduct() {
//        assertEquals(product, service.findProduct(product2.getId()));
//     }
//     
//     @Test//Passes the test
//     public void testAddProduct() {
//         assertEquals("Product with the provided ID already exists", service.addProduct(product));
//     }
//     @Test//Passes the test
//     public void testDeleteProduct() {
//         assertEquals("Successfully deleted product", service.deleteProduct("2"));
//     }
//     
//      @Test//Passes the test
//     public void testUpdateProductPrice() {
//         Map<String,Double> newPrice = new HashMap();
//         newPrice.put(product.getId(),20.2);
//         assertEquals("Successfully updated product price", service.putProductOnSale(newPrice));
//     }
//     
//      @Test//Passed the test
//     public void testSerachForItem() {
//         assertEquals(allProducts, service.SerachForItem(sCats));
//     }
//     
//      @Test//Passed the test
//     public void testFindAllCategories() {
//         assertEquals(cats, service.findAllCategories());
//     }
//     
//      @Test//Passed the test
//     public void testFindCategory() {
//         assertEquals(new Category("2", "Shirts"), service.findCategory("2"));
//     }
//     
//      @Test//passed the test
//     public void testAddCategory() {
//         assertEquals("Successfully added category", service.addCategory(new Category("2","Shirts")));
//     }
//     
//       @Test//Test passed
//     public void testDeleteCategory() {
//         assertEquals("Successfully deleted category", service.deleteCategory("1"));
//     }
//     
//       @Test//Test passed
//     public void testLogStock() {
//         assertEquals("Stock loaded", service.logStock(new NewProduct(stockEntry, product)));
//     }
//            
//        @Test//(Laurence)Returns boutique,size id, think it would be better to retrun names 
//     public void testFindStockOfProduct() {//boutique,size
//         assertEquals("Stock loaded",service.findStockOfProduct(product2.getId()));
//     }
}
