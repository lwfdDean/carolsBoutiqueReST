package co.za.carolsBoutique.product.repository;

import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import java.util.ArrayList;

public interface IProductRepository {
    Product findPProduct(String productId);
    ArrayList<Product> findAllProducts();
    boolean addProduct(Product product);
    boolean deleteProduct(String productId);
    boolean updateProduct(String productId, Double newPrice);
    
    Category findCategory(String categoryId);
    ArrayList<Category> findAllCategories();
    boolean addCategory(Category category);
    boolean deleteCategory(String categoryId);
}
