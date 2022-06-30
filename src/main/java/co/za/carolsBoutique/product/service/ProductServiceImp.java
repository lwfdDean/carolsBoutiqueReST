package co.za.carolsBoutique.product.service;

import co.za.carolsBoutique.EmailTemplate.EmailReader;
import co.za.carolsBoutique.mailService.MailService;
import co.za.carolsBoutique.messageService.MessageService;
import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.NewProduct;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.PromoCode;
import co.za.carolsBoutique.product.model.Size;
import co.za.carolsBoutique.product.model.StockEntry;
import co.za.carolsBoutique.product.repository.IProductRepository;
import jakarta.mail.MessagingException;
import java.io.IOException;
import static java.lang.Math.random;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductServiceImp implements IServiceProduct{
    private EmailReader er = new EmailReader();
    private IProductRepository dao;
    public ProductServiceImp(IProductRepository dao){
        this.dao = dao;
    }

    @Override
    public List<Product> findAllProducts() {
        return dao.findAllProducts();
    }

    @Override
    public Product findProduct(String productCode) {
        return dao.findProduct(productCode.substring(0, 10));
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
        String [] prodInfo = productId.split(" ");
        Product product = dao.findProduct(prodInfo[0]);
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
        String[] expiry = promoCode.getDate().split("-");
        LocalDate dt = LocalDate.of(
                Integer.parseInt(expiry[0]), 
                Integer.parseInt(expiry[1]), 
                Integer.parseInt(expiry[2]));
        if(dao.addPromo(promoCode,dt)){
            promoCode.setCategory(dao.findCategory(promoCode.getCategory()).getName());
            List<String> contactInfo = dao.findContactInfo();
            for (int i = 0; i < contactInfo.size(); i++) {
                if (contactInfo.get(i).contains("@")) {
                    try {
                        new MailService(contactInfo.get(i),"Promotion",er.readInEmail("Promotion", promoCode)).sendMail();
                        return "Promo added";
                    } catch (MessagingException ex) {
                        Logger.getLogger(ProductServiceImp.class.getName()).log(Level.SEVERE, null, ex);
                        return "failed to add promo";
                    } catch (IOException ex) {
                        Logger.getLogger(ProductServiceImp.class.getName()).log(Level.SEVERE, null, ex);
                        return "failed to add promo";
                    }
                }else{
                new MessageService(contactInfo.get(i), "New Promotion!\n"+
                                                        promoCode.getCategory()+" is on promotion for"+
                                                        promoCode.getDiscount()+"\n"+
                                                        "PromoCode: "+promoCode.getCode());
                return "Promo added";
                }
            }
        }
        return "failed to add promo";
    }
    @Override
    public PromoCode findPromoCode(String promoCode) {
        PromoCode pc = dao.findPromo(promoCode);
        if (pc == null) {
            return null;
        }
        System.out.println(pc.getDate());
        String[] expiry = pc.getDate().split("-");
        LocalDate dt = LocalDate.of(
                Integer.parseInt(expiry[0]), 
                Integer.parseInt(expiry[1]), 
                Integer.parseInt(expiry[2]));
        if (LocalDate.now().isBefore(dt) || LocalDate.now().isEqual(dt)) {
            return pc;
        }
        return null;
    }   

    @Override
    public List<Size> findAllSizes() {
        return dao.findAllSizes();
    }

    @Override
    public Product findProduct2(String productId) {
        return dao.findProduct(productId);
    }
}