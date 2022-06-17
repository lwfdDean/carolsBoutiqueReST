package co.za.carolsBoutique.product.service;

import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.NewProduct;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.PromoCode;
import java.util.List;
import java.util.Map;

public interface IServiceProduct {
    List<Product> findAllProducts();
    Product findProduct(String productId);
    Product findProductBySize(String productCode);
    String addProduct(Product product);
    String deleteProduct(String productId);
    String putProductOnSale(Map<String,Double>newPrice);
    
    Map<String,String> findStockOfProduct(String productId);
    
    List<Product> SerachForItem(List<String> categoriesId);
    
    List<Category> findAllCategories();
    Category findCategory(String categoryId);
    String addCategory(Category category);
    String deleteCategory(String categoryId);
    
    String logStock(NewProduct newProduct);
    
    String addNewPromoCode(PromoCode promoCode);
    PromoCode findPromoCode(String promoCode);
    
}
