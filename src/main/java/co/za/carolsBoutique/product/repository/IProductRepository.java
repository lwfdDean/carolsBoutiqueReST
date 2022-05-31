package co.za.carolsBoutique.product.repository;

import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
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
    boolean addReservedItem(String productId, String customerEmial);
    
}
