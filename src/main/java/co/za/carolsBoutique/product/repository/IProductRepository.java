package co.za.carolsBoutique.product.repository;

import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.PromoCode;
import co.za.carolsBoutique.product.model.Size;
import co.za.carolsBoutique.product.model.StockEntry;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IProductRepository {
    Product findProduct(String productId);
    Product findProductBySize(String productId, String size);
    List<Product> findAllProducts();
    boolean addProduct(Product product);
    boolean deleteProduct(String productId);
    boolean updateProduct(Map<String, Double> newPrice);
    List<Product> findProductsByCategories(List<String> categoriesId);
    
    Category findCategory(String categoryId);
    List<Category> findAllCategories();
    boolean addCategory(Category category);
    boolean deleteCategory(String categoryId);
    
    Map<String,Integer> findStockEntry(String productId,String boutiqueId,String size);
    boolean addStockEntry(StockEntry stockEntry,List<String> stockId,Product product);
    boolean addNewStockLog(String employeeId,int quantityBefore,int quantityAdded,String stockId);
    Map<String,String> findAvailabeStock(String productId);
    
    PromoCode findPromo(String code);
    boolean addPromo(PromoCode promoCode, LocalDate dt);
    List<Size> findAllSizes();
}
