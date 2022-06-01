package co.za.carolsBoutique.product.service;

import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.repository.IProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductServiceImp implements IServiceProduct{
    private IProductRepository dao;
    private CodeGenerator gen;
    public ProductServiceImp(IProductRepository dao,CodeGenerator gen){
        this.dao = dao;
        this.gen = gen;
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
    public String updateProductPrice(Map<String, Double> newPrice) {
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
}
