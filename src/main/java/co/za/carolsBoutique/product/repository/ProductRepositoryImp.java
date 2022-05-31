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

public class ProductRepositoryImp implements IProductRepository {

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

    private List<String> getProductSizes(String productId) {
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        List<String> sizes = new ArrayList<>();
        if (con != null) {
            try {
                ps1 = con.prepareStatement("select id from size inner join product_size on product_size.size = size.id where product_size.product=?");
                ps1.setString(1, productId);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    sizes.add(rs1.getString("id"));
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
            try {
                ps = con.prepareStatement("select id,name,description,size,color,price from products");
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
        if (con != null) {
            try {
                ps1 = con.prepareStatement("select category from product_category where product = ?");
                ps1.setString(1, productId);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    categories.add(rs1.getString("category"));
                }
                return categories;
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
    public boolean addProduct(Product product) {
        boolean success = false;
        if (con != null) {
            try {/*TODO: add a category/product*/
                con.setAutoCommit(false);
                ps = con.prepareStatement("INSERT INTO product(id,name,description,color,price) VALUES(?,?,?,?,?);");
                ps.setString(1, product.getId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getDescription());
                ps.setString(5, product.getColor());
                ps.setDouble(6, product.getPrice());
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
        if (con!=null) {
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
        if (con!=null) {
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

    @Override
    public boolean deleteCategory(String categoryId) {
        if (con!=null) {
            try {
            ps = con.prepareStatement("DELETE FROM category WHERE  name=?");
            ps.setString(1, categoryId);
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
    public List<Product> findProductsByCategories(List<String> categoriesId) {
        List<String> productIds = new ArrayList();
        List<Product> products = new ArrayList();
        if (con!=null) {
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
            }
        }
        return product;
    }

}
