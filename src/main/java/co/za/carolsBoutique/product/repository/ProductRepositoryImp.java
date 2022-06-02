package co.za.carolsBoutique.product.repository;

import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.StockEntry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductRepositoryImp implements IProductRepository {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public ProductRepositoryImp() {

        String url = "jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, "root", "Root");
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean addProduct(Product product) {
        boolean success = false;
        if (con != null) {
            try {/*TODO: add a category/product*/
                con.setAutoCommit(false);
                ps = con.prepareStatement("INSERT INTO product(id,name,description,color,price) VALUES(?,?,?,?,?);");
                ps.setString(1, product.getId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getDescription());
                ps.setString(4, product.getColor());
                ps.setDouble(5, product.getPrice());
                rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    if (addProductCategory(product) && addProductSizes(product)) {
                        con.commit();
                        success = true;
                        con.setAutoCommit(true);
                    } else {
                        con.rollback();
                        con.setAutoCommit(true);
                    }
                }
            } catch (SQLException se) {
                se.printStackTrace();
                return false;
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return success;
    }

    private boolean addProductSizes(Product product) {
        PreparedStatement ps1 = null;
        int rows = 0;
        if (con != null) {
            try {
                List<String> sizes = product.getSizes();
                for (int i = 0; i < sizes.size(); i++) {
                    ps1 = con.prepareStatement("INSERT INTO product_size(product,size) VALUES(?,?)");
                    ps1.setString(1, product.getId());
                    ps1.setString(2, sizes.get(i));
                    rows += ps1.executeUpdate();
                    ps1.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rows == product.getSizes().size();
    }

    private boolean addProductCategory(Product product) {
        PreparedStatement ps1 = null;
        int roles = 0;
        if (con != null) {
            try {
                List<String> catagories = product.getCategories();
                for (int i = 0; i < catagories.size(); i++) {
                    ps1 = con.prepareStatement("INSERT INTO product_category(product,category) VALUES(?,?)");
                    ps1.setString(1, product.getId());
                    ps1.setString(2, catagories.get(i));
                    roles += ps1.executeUpdate();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return roles == product.getCategories().size();
    }

    @Override
    public boolean addStockEntry(StockEntry stockEntry, List<String> stockId, Product product) {
        if (con != null) {
            try {
                for (int i = 0; i < product.getSizes().size(); i++) {
                    ps = con.prepareStatement("insert into stock(id,product,boutique,quantity,size) values(?,?,?,?,?)");
                    ps.setString(1, stockId.get(i));
                    ps.setString(2, product.getId());
                    ps.setString(3, stockEntry.getBoutiqueId());
                    ps.setInt(4, stockEntry.getQuantity());
                    ps.setString(5, product.getSizes().get(i));
                    rowsAffected += ps.executeUpdate();
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == product.getSizes().size();
    }

    @Override //laurence
    public Product findProduct(String productId) {
        Product product = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from product where id = ?");
                ps.setString(1, productId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    product = new Product(
                            productId,
                            rs.getString("name"),
                            rs.getString("description"),
                            getProductSizes(productId),
                            rs.getString("color"),
                            rs.getDouble("price"),
                            findProductCategories(productId));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return product;
    }

    private List<String> getProductSizes(String productId) {//(Laurence) changed quarie(from id to name)
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        List<String> sizes = new ArrayList<>();
        if (con != null) {
            try {
                ps1 = con.prepareStatement("select name from size inner join product_size on product_size.size = size.id where product_size.product=?");
                ps1.setString(1, productId);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    sizes.add(rs1.getString("name"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs1 != null) {
                    try {
                        rs1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return sizes;
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<>();
        if (con != null) {
            try {                         //(Laurence) fixed statement (products to product)(removed size)
                ps = con.prepareStatement("select id,name,description,color,price from product");
                rs = ps.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("id");
                    products.add(
                            new Product(
                                    productId,
                                    rs.getString("name"),
                                    rs.getString("description"),
                                    getProductSizes(productId),
                                    rs.getString("color"),
                                    rs.getDouble("price"),
                                    findProductCategories(productId)));
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return products;
    }

    private List<String> findProductCategories(String productId) {
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        List<String> categories = new ArrayList<>();
        List<String> categoriesName = new ArrayList<>();
        if (con != null) {
            try {
                ps1 = con.prepareStatement("select category from product_category where product=?");
                ps1.setString(1, productId);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    categories.add(rs1.getString("category"));
                }
                rs1.close();
                ps1.close();
                for (int i = 0; i < categories.size(); i++) {
                    ps1 = con.prepareStatement("select name from category where id=?");
                    ps1.setString(1, categories.get(i));
                    rs1 = ps1.executeQuery();
                    rs1.next();
                    categoriesName.add(rs1.getString("name"));
                    rs1.close();
                    ps1.close();
                
                }
                return categoriesName;
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (rs1 != null) {
                    try {
                        rs1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean deleteProduct(String productId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("DELETE FROM product WHERE  name=?");
                ps.setString(1, productId);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean updateProduct(Map<String, Double> newPrice) {
        String productId = "";
        Double price = 0.0;
        for (Map.Entry<String, Double> entry : newPrice.entrySet()) {
            productId = entry.getKey();
            price = entry.getValue();
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE product SET price = ? WHERE id = ?");
                ps.setDouble(1, price);
                ps.setString(2, productId);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public Category findCategory(String categoryId) {
        Category category = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from category where id = ?");
                ps.setString(1, categoryId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    category = new Category(rs.getString("id"),
                            rs.getString("name"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return category;
    }

    @Override
    public List<Category> findAllCategories() {
        List<Category> categories = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("select id,name from category");
                rs = ps.executeQuery();
                while (rs.next()) {

                    categories.add(new Category(rs.getString("id"), rs.getString("name")));
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return categories;
    }

    @Override
    public boolean addCategory(Category category) {
        if (con != null) {
            try {
                ps = con.prepareStatement("INSERT INTO category(id,name) VALUES(?,?);");
                ps.setString(1, category.getId());
                ps.setString(2, category.getName());
                rowsAffected = ps.executeUpdate();
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override//(Laurence) had to change statement completely, had to delete dependency first
    public boolean deleteCategory(String categoryId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("DELETE FROM product_category WHERE category = ?");
                ps.setString(1, categoryId);
                rowsAffected = ps.executeUpdate();
                if(rowsAffected==1){
                    ps = con.prepareStatement("DELETE FROM category WHERE id = ?");
                    ps.setString(1, categoryId);
                    rowsAffected = ps.executeUpdate();
                }else{
                    return false;
                }
               
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public List<Product> findProductsByCategories(List<String> categoriesId) {
        List<String> productIds = new ArrayList();
        List<Product> products = new ArrayList();
        if (con != null) {
            try {
                for (int i = 0; i < categoriesId.size(); i++) {
                    ps = con.prepareStatement("select product from product_category where catogory = ?");
                    ps.setString(1, categoriesId.get(i));
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        String product = rs.getString("product");
                        if (!productIds.contains(product)) {
                            productIds.add(product);
                            products.add(findProduct(product));
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        return products;
    }

    @Override
    public Product findProductBySize(String productId, String size) {
        Product product = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from product inner join product_size on product_size.product = product.id where product.id = ?"
                        + " and product_size.size = ?");
                ps.setString(1, productId);
                ps.setString(2, size);
                rs = ps.executeQuery();
                if (rs.next()) {
                    List<String> sizes = new ArrayList<>();
                    sizes.add(size);
                    product = new Product(
                            productId,
                            rs.getString("name"),
                            rs.getString("description"),
                            sizes,
                            rs.getString("color"),
                            rs.getDouble("price"),
                            findProductCategories(productId)
                    );
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return product;
    }

    @Override
    public Map<String, Integer> findStockEntry(String productId, String boutiqueId, String size) {
        Map<String, Integer> stockEntry = new HashMap<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("select id,quantity from stock where product=? and boutique = ? and size = ?");
                ps.setString(1, productId);
                ps.setString(2, boutiqueId);
                ps.setString(3, size);
                rs = ps.executeQuery();
                if (rs.next()) {
                    stockEntry.put(rs.getString("id"), rs.getInt("quantity"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return stockEntry;
    }

    @Override
    public boolean addNewStockLog(String employeeId, int quantityBefore, int quantityAdded, String stockId) {
        boolean success = false;
        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("insert into stocklog(quantityAdded,quantityBefore,employee,stock) values (?,?,?,?)");
                ps.setInt(1, quantityAdded);
                ps.setInt(2, quantityBefore);
                ps.setString(3, employeeId);
                ps.setString(4, stockId);
                rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    if (addStock(stockId, (quantityAdded + quantityBefore))) {
                        con.commit();
                        con.setAutoCommit(true);
                        success = true;
                    }
                } else {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return success;
    }

    private boolean addStock(String stockId, int newQuantity) {
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        int rows = 0;
        if (con != null) {
            try {
                ps1 = con.prepareStatement("update stock set quantity = ? where id = ?");
                ps1.setInt(1, newQuantity);
                ps1.setString(2, stockId);
                rows = ps1.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rows == 1;
    }

    @Override//(Laurence)method retruns ID of boutique,size
    public Map<String, String> findAvailabeStock(String productId) {
        Map<String, String> available = new HashMap<>();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select boutique,size from stock where product = ?");
                ps.setString(1, productId);
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    available.put(rs.getString("boutique"),rs.getString("size"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return available;
    }

    

}
