package co.za.carolsBoutique.product.service;

import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IServiceProduct {
    List<Product> findAllProducts();
    Product findProduct(String productId);
    String addProduct(Product product);
    String deleteProduct(String productId);
    String updateProductPrice(Map<String,Double>newPrice);
    
    List<Product> SerachForItem(List<String> categoriesId);
    
    List<Category> findAllCategories();
    Category findCategory(String categoryId);
    String addCategory(Category category);
    String deleteCategory(String categoryId);
}
