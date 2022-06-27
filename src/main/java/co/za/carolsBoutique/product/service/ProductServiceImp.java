package co.za.carolsBoutique.product.service;

import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.NewProduct;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.PromoCode;
import co.za.carolsBoutique.product.model.Size;
import co.za.carolsBoutique.product.model.StockEntry;
import co.za.carolsBoutique.product.repository.IProductRepository;
import static java.lang.Math.random;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductServiceImp implements IServiceProduct{
    private IProductRepository dao;
    public ProductServiceImp(IProductRepository dao){
        this.dao = dao;
    }

    @Override
    public List<Product> findAllProducts() {
        return dao.findAllProducts();
    }

    @Override
    public Product findProduct(String productId) {
        return dao.findProduct(productId);
    }

    @Override
    public String addProduct(Product product) {
        if(dao.findProduct(product.getId())==null){
            return dao.addProduct(product)?"Successfully added product":"Adding product failed"; 
        }
        return "Product with the provided ID already exists";
    }

    @Override
    public String deleteProduct(String productId) {
        return dao.deleteProduct(productId)?"Successfully deleted product":"Deleting product failed";
    }

    @Override 
    public String putProductOnSale(Map<String, Double> newPrice) {
        return dao.updateProduct(newPrice)?"Successfully updated product price":"Updateding product price failed";
    }
    
    //Changes made(Laurence) changed ArrayList to List(param), including interface
    @Override
    public List<Product> SerachForItem(List<String> categoriesId) {
        return  dao.findProductsByCategories(categoriesId);
    }

    @Override
    public List<Category> findAllCategories() {
       return dao.findAllCategories();}

    @Override
    public Category findCategory(String categoryId) {
        return dao.findCategory(categoryId); }

    @Override
    public String addCategory(Category category) {
        if(dao.findCategory(category.getId())==null){
           return dao.addCategory(category)?"Successfully added category":"Adding category failed";
        }
        return "Cagegory alrady exsists";
    }
    //(Laurence, think we should use cat.name, we dont need the ID outside the database)
    @Override
    public String deleteCategory(String categoryId) {
        return dao.deleteCategory(categoryId)?"Successfully deleted category":"Deleting category failed";

    }

    @Override//(changes needed to fulfil http://localhost:8080/carolsBoutiqueRest/CarolsBoutique/product/logStock)
    public String logStock(NewProduct newProduct) {
        StockEntry stockEntry = newProduct.getStockEntry();
        if (newProduct.getNewProduct()) {
            Product product = newProduct.getProduct();
            dao.addProduct(product);
            dao.addStockEntry(stockEntry, generateStockIds(product), product);
        }
        String[] productEntry =stockEntry.getProductCode().split(" ");
        Map<String,Integer> entry = dao.findStockEntry(productEntry[0], stockEntry.getBoutiqueId(), productEntry[1]);
        String stockId = entry.keySet().iterator().next();
        return dao.addNewStockLog(stockEntry.getEmployeeId(),stockEntry.getQuantity(),entry.get(stockId),stockId)?
                "Stock loaded":
                "stock could not be loaded";
    }
   
    private List<String> generateStockIds(Product product) {
        List<String> stockIds = new ArrayList<>();
        for (int i = 0; i < product.getSizes().size(); i++) {
            StringBuilder sb = new StringBuilder();
            String cat = product.getCategories().get((int)(Math.random()*product.getCategories().size())).getId();
            sb.append((int)(random()*100000+1000));
            sb.append(product.getSizes().get((int)(Math.random()*product.getSizes().size())).getId());
            sb.append(cat.charAt((int)(Math.random()*cat.length())));
            stockIds.add(sb.toString());
        }
        return stockIds;
    }

    @Override
    public Map<String, String> findStockOfProduct(String productId) {
        
        Product product = dao.findProduct(productId);
        return dao.findAvailabeStock(product.getId());
    }

    @Override
    public Product findProductBySize(String productCode) {
        String[] prodInfo = productCode.split(" ");
        return dao.findProductBySize(prodInfo[0], prodInfo[1]);
    }

    @Override
    public String addNewPromoCode(PromoCode promoCode) {
        if (promoCode.getCode() == null || promoCode.getCode().isEmpty() || promoCode.getCode().length()<6) {
            return "The promocode provided is invalid";
        }
        return dao.addPromo(promoCode)?"Promo added":"failed to add promo";
    }

    @Override
    public PromoCode findPromoCode(String promoCode) {
        PromoCode pc = dao.findPromo(promoCode);
        if (LocalDate.now().isBefore(pc.getDate()) || LocalDate.now().isEqual(pc.getDate())) {
            return pc;
        }
        return null;
    }   

    @Override
    public List<Size> findAllSizes() {
        return dao.findAllSizes();
    }
}