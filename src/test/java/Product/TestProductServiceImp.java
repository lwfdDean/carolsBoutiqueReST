package Product;

import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
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
    List<String> sCats;
    List<Category> cats;
    List sizes;
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
        service = new ProductServiceImp(dao, gen);
        sCats = new ArrayList<String>();
        sCats.add("Accessories");
        cats = new ArrayList<Category>();
        cats.add(new Category("1", "Accessories"));
        sizes = new ArrayList<String>();
        sizes.add("Universal");
        product = new Product("123", "Hat", "Red Hat", sizes, "Red", 19.99,sCats);
        product2 = new Product("123", "Hat", "Red Hat", sizes, "Red", 19.99,sCats);
        allProducts.add(product);
    }
    
    @After
    public void tearDown() {
        service = null;
        product = null;
        product2 = null;
    }

     @Test//private methods return id's instaid of names(size and cat)
     public void testFindAllProducts() {
         assertEquals(allProducts, service.findAllProducts());
     }
      @Test//private methods return id's instaid of names(size and cat)
     public void testFindProduct() {
        assertEquals(product, service.findProduct(product.getId()));
     }
     
      @Test//Passes the test
     public void testAddProduct() {
         assertEquals("Product with the provided ID already exists", service.addProduct(product));
     }
     
      @Test//Passes the test
     public void testUpdateProductPrice() {
         Map<String,Double> newPrice = new HashMap();
         newPrice.put(product.getId(),20.2);
         assertEquals("Successfully updated product price", service.updateProductPrice(newPrice));
     }
     
      @Test//Passed the test
     public void testSerachForItem() {
         assertEquals(allProducts, service.SerachForItem(sCats));
     }
     
      @Test//Passed the test
     public void testFindAllCategories() {
         assertEquals(cats, service.findAllCategories());
     }
     
      @Test//Passed the test
     public void testFindCategory() {
         assertEquals(new Category("1", "Accessories"), service.findCategory("1"));
     }
     
      @Test//passed the test
     public void testAddCategory() {
         assertEquals("Successfully added category", service.addCategory(new Category("2","Shirts")));
     }
     
       @Test//error message: Cannot delete or update a parent row: a foreign key constraint fails (`carolsboutique`.`product_category`, CONSTRAINT `product_category_ibfk_2` FOREIGN KEY (`category`) REFERENCES `category` (`id`))
     public void testDeleteCategory() {
         assertEquals("Successfully deleted category", service.deleteCategory("1"));
     }
}
