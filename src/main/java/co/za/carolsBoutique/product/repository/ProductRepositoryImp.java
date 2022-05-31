    package co.za.carolsBoutique.product.repository;

import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductRepositoryImp implements IProductRepository{
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public ProductRepositoryImp() {
        
        String url = "jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, "root", "Root");
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override //laurence
    public Product findProduct(String productId) {
        Product product = null;
        try{
            ps = con.prepareStatement("select * from product where id = ?");
            ps.setString(1, productId);
            rs = ps.executeQuery();
            rs.next();
            
            product = new Product(
                        productId, 
                        rs.getString("name"), 
                        rs.getString("description"), 
                        rs.getString("size"),
                        rs.getString("color"),
                        rs.getDouble("price"),
                        findProductCategories(productId));
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return product;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<Product>();
        try{
            ps = con.prepareStatement("select id,name,description,size,color,price from products");
            rs = ps.executeQuery();
            while(rs.next()){
                String productId = rs.getString("id");
                products.add(
                    new Product(
                        productId,
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("size"),
                        rs.getString("color"),
                        rs.getDouble("price"),
                        findProductCategories(productId)));
            }
            return products;
        }catch(SQLException se){
            se.printStackTrace();
            return null;
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private List<String> findProductCategories(String productId){
        List<String> categories = new ArrayList<String>();
		try{
                    ps = con.prepareStatement("select category from product_category where product = ?");
                    ps.setString(1, productId);
                    rs = ps.executeQuery();
                    while(rs.next()){
                            categories.add(rs.getString("category"));
                    }
                    return categories;
		}catch(SQLException se){
			se.printStackTrace();
		}finally{
                    if(rs!=null){
                        try {
                            rs.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if(ps!=null){
                        try {
                            ps.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
		}
		return null;
    }
    
    @Override
    public boolean addProduct(Product product) {
        try {/*TODO: add a category/product*/
            con.setAutoCommit(false);
            ps = con.prepareStatement("INSERT INTO product(id,name,description,size,color,price) VALUES(?,?,?,?,?,?);");
            ps.setString(1, product.getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setString(4, product.getSize());
            ps.setString(5, product.getColor());
            ps.setDouble(6, product.getPrice());
            rowsAffected = ps.executeUpdate();
            if(addProductCategory(product)){
                con.commit();
                
            }else{
                con.rollback();
                return false;
            }
        }catch(SQLException se){
            se.printStackTrace();
            return false;
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }try {
                con.setAutoCommit(true);
            }catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
          }
            
        }
         return rowsAffected ==1;
    }

    @Override
    public boolean deleteProduct(String productId) {
        try {
            ps = con.prepareStatement("DELETE FROM product WHERE  name=?");
            ps.setString(1, productId);
            if(ps.executeUpdate() == 0){
                    return false;
            }
            return true;

        }catch(SQLException se) {
            se.printStackTrace();
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public boolean updateProduct(Map<String, Double> newPrice) {
        String productId = "";
        Double price = 0.0;
        for (Map.Entry<String, Double> entry : newPrice.entrySet()) {
            productId = entry.getKey();
            price = entry.getValue();
        }
        try {
            ps = con.prepareStatement("UPDATE product SET price = ? WHERE id = ?");
            ps.setDouble(1, price);
            ps.setString(2, productId);
            int holder = ps.executeUpdate();
            if(holder<1) {
                    return false;
            }
            return true;
       }catch(SQLException se){
            se.printStackTrace();
            return false;
       }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public Category findCategory(String categoryId) {
        Category category = null;
        try{
            ps = con.prepareStatement("select * from category where id = ?");
            ps.setString(1, categoryId);
            rs = ps.executeQuery();
            rs.next();
            category = new Category(    rs.getString("id"), 
                                        rs.getString("name"));
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return category;
    }

    @Override
    public List<Category> findAllCategories() {
        List<Category> categories = new ArrayList<Category>();
        try{
            ps = con.prepareStatement("select id,name from category");
            rs = ps.executeQuery();
            while(rs.next()){

                    categories.add(new Category(rs.getString("id"), rs.getString("name")));
            }
            return categories;
        }catch(SQLException se){
            se.printStackTrace();
            return null;
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public boolean addCategory(Category category) {
        try {
            ps = con.prepareStatement("INSERT INTO category(id,name) VALUES(?,?);");
            ps.setString(1, category.getId());
            ps.setString(2, category.getName());
            ps.executeUpdate();
            return true;
        }catch(SQLException se){
            se.printStackTrace();
            return false;
        }finally{
           if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public boolean deleteCategory(String categoryId) {
        try {
            ps = con.prepareStatement("DELETE FROM category WHERE  name=?;");
            ps.setString(1, categoryId);
            if(ps.executeUpdate() == 0){
                return false;
            }
            return true;
        }catch(SQLException se) {
            se.printStackTrace();
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public List<Product> findProductsByCategories(List<String> categoriesId) {
        List<String>productIds = new ArrayList();
        List<Product>products = new ArrayList();
        try{
            for (int i = 0; i < categoriesId.size(); i++) {
                ps = con.prepareStatement("select product from product_category where catogory = ?");
                ps.setString(1, categoriesId.get(i));
                rs = ps.executeQuery();
                while(rs.next()){
                    String product = rs.getString("product");
                    if(!productIds.contains(product)){
                        productIds.add(product);
                        products.add(findProduct(product));   
                    }    
                }
                    rs.close();
                    ps.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return products;
    }
    
    private boolean addProductCategory(Product product) {
        int roles = 0;
        try {
            List<String> catagories = product.getCategories();
            for (int i = 0; i < catagories.size(); i++) {
                    ps = con.prepareStatement("INSERT INTO product_category(product,category) VALUES(?,?);");
                    ps.setString(1, product.getId());
                    ps.setString(2, catagories.get(i));
                    roles = ps.executeUpdate();
            }
        }catch(SQLException se){
                se.printStackTrace();
                return false;
        }finally{
            if(ps!=null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return roles == 1;
    }

    @Override
    public boolean addReservedItem(String productId, String customerEmial) {
//          try {
//            ps = con.prepareStatement("INSERT INTO reservedproduct(id,customerEmail,stock) VALUES(?,?,?);");
//            ps.setString(1, productId);
//            ps.setString(2, customerEmial);
//            ps.executeUpdate();
//            return true;
//        }catch(SQLException se){
//            se.printStackTrace();
//            return false;
//        }finally{
//           if(ps!=null){
//                try {
//                    ps.close();
//                } catch (SQLException ex) {
//                    Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
        return false;

    }
        
    
    
}
