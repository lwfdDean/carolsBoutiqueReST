package co.za.carolsBoutique.product.service;

import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.PromoCode;
import co.za.carolsBoutique.product.model.StockEntry;
import java.util.List;
import java.util.Map;

public interface IServiceProduct {
    List<Product> findAllProducts();
    Product findProduct(String productId);
    Product findProduct(Map<String,String> productInfo);
    String addProduct(Product product);
    String deleteProduct(String productId);
    String updateProductPrice(Map<String,Double>newPrice);
    
    Map<String,String> findStockOfProduct(String productId);
    
    List<Product> SerachForItem(List<String> categoriesId);
    
    List<Category> findAllCategories();
    Category findCategory(String categoryId);
    String addCategory(Category category);
    String deleteCategory(String categoryId);
    
    String logStock(Map<Product,StockEntry> stockInfo);
    
    String addNewPromoCode(PromoCode promoCode);
    PromoCode findPromoCode(String promoCode);
}
