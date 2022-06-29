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
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        allProducts = new ArrayList();
        dao = new ProductRepositoryImp();
        gen = new ProductIdGenerator();
        service = new ProductServiceImp(dao);
        sCats = new ArrayList<>();
        sCats.add("");
        cats = new ArrayList<>();
        cats.add(new Category("2", "Shirts"));
        sizes = new ArrayList<>();
        sizes.add(new Size());
        product = new Product("123", "Hat", "Red Hat", sizes, "Red", 20.2,2.00,cats);
        product2 = new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99,15.00,cats);
        sizes = new ArrayList<>();
        sizes.add(new Size("1", "XXXL"));
        sizes.add(new Size("10", "3"));
        sizes.add(new Size("11", "4"));
        sizes.add(new Size("12", "5"));
        sizes.add(new Size("13", "6"));
        sizes.add(new Size("14", "7"));
        sizes.add(new Size("15", "8"));
        product = new Product("thuyr7895321", "Denim", "Denim Pants", sizes, "Blue", 100.90,0.00,cats);
        product2 = new Product("1234567891", "Hat", "Red Hat", sizes, "Red", 19.99,15.00,cats);
        allProducts.add(product);
        stockEntry =new StockEntry("blden1212 32", "ao8154bb", 10, "Dl3983nl");
        //10 diget id and space and 2 diget code for size
    }
    
    @After
    public void tearDown() {
        service = null;
        product = null;
        product2 = null;
    }

     @Test//Passes the test
     public void testFindAllProducts() {
         assertEquals(dao.findAllProducts(), service.findAllProducts());
     }
      @Test//Passes the test
     public void testFindProduct() {
         product.setSizes(new ArrayList());
         product.setCategories(new ArrayList());
        assertEquals(product, service.findProduct(product.getId()));
     }
     
     @Test//Passes the test
     public void testAddProduct() {
         assertEquals("Successfully added product", service.addProduct(product));
     }
     @Test//Passes the test
     public void testDeleteProduct() {
         assertEquals("Successfully deleted product", service.deleteProduct("thuyr45321"));
     }
     
      @Test//Passes the test
     public void testUpdateProductPrice() {
         Map<String,Double> newPrice = new HashMap();
         newPrice.put(product2.getId(),20.2);
         assertEquals("Successfully updated product price", service.putProductOnSale(newPrice));
     }
     
      @Test//Passed the test
     public void testSerachForItem() {
         assertEquals(dao.findProductsByCategories(sCats), service.SerachForItem(sCats));
     }
     
      @Test//Passed the test
     public void testFindAllCategories() {
         assertEquals(dao.findAllCategories(), service.findAllCategories());
     }
     
      @Test//Passed the test
     public void testFindCategory() {
         assertEquals(new Category("1", "Mens"), service.findCategory("1"));
     }
     
      @Test//passed the test method will return false if its run a second time, so commented out
     public void testAddCategory() {
         assertEquals("Successfully added category", service.addCategory(new Category("233","Shirtsss")));
     }
     
       @Test//Test passed
     public void testDeleteCategory() {
         assertEquals("Successfully deleted category", service.deleteCategory("233"));
     }
     
       @Test//Dean please help
     public void testLogStock() {
         assertEquals("Stock loaded", service.logStock(new NewProduct(stockEntry, product,true)));
     }
            
        @Test//(Laurence)Returns boutique,size id, think it would be better to retrun names 
     public void testFindStockOfProduct() {//boutique,size
         Map map = new HashMap();
         map.put("ao8154bb", "1");
         assertEquals( map,service.findStockOfProduct(product2.getId()));
     }
}