package co.za.carolsBoutique.ReserveProduct.repository;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.Size;
import co.za.carolsBoutique.product.repository.ProductRepositoryImp;
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
import java.util.stream.Collectors;

public class ReservedproductRepositoryImp implements IReservedproductRepository {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public ReservedproductRepositoryImp() {
        String url = "jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, "root", "Root");
        } catch (SQLException ex) {
            Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String findReserveProduct(String customerEmail) {
        String id = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select stock from reservedproduct where customerEmail=?");
                ps.setString(1, customerEmail);
                rs = ps.executeQuery();
                if (rs.next()) {
                    id = rs.getString("stock");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return id;
    }

    @Override
    public boolean addReserveProduct(Reservedproduct reserveProduct, String id, int quantity) {
        boolean success = false;
        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("insert into reservedproduct(customerEmail,stock,collected) values(?,?,?)");
                ps.setString(1, reserveProduct.getCustomerEmail());
                ps.setString(2, id);
                ps.setBoolean(3, false);
                rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    if (updateQuantity(id ,quantity-1)) {
                        con.commit();
                        success = true;
                    }else{
                        con.rollback();
                    }
                }else{
                    con.rollback();
                }
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return success;
    }

    @Override
    public boolean deleteReserveProduct(String email) {
        String stockId = findReserveProduct(email);
        addStock(stockId);
        if (con != null) {
            try {
                ps = con.prepareStatement("delete from reservedproduct where customerEmail=?");
                ps.setString(1, email);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
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
    public Map<String, String> addStock(String stockId) {
        Map<String, String> productInfo = new HashMap();
        if (con!=null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("select product,size,quantity from stock where id =?");
                ps.setString(1, stockId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    String product = rs.getString("product");
                    String size = rs.getString("size");
                    if (updateQuantity(stockId,quantity+1)) {
                        con.commit();
                        productInfo.put(product, size);
                    }else{
                        con.rollback();
                    }
                }
                
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
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
                System.out.println("program not broken yet");//last line that prints out
            }//throws a null pointer here, after executing both the closes successfully, at the end of the finally block
        }
        return productInfo;
    }

    private boolean updateQuantity(String stockId, int quantity) {
        PreparedStatement ps1 = null;
        int rows = 0;
        if (con!=null) {
            try {
                ps1 = con.prepareStatement("update stock set quantity = ? where id = ?");
                ps1.setInt(1, quantity);
                ps1.setString(2, stockId);
                rows = ps1.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
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

    @Override
    public Product findProductByProductCode(String productId, String size) {
        Product product = null;
        if (con!=null) {
            try {
                ps = con.prepareStatement("select * from product inner join product_size on product_size.product = product.id"
                        + " where product.id = ? and product_size.size = ?");   
                ps.setString(1, productId);
                ps.setString(2, size);
                rs = ps.executeQuery();
                if (rs.next()) {
                    List<Size> prodSize = getProductSizes(productId).stream()
                            .filter(s -> s.getId().equalsIgnoreCase(size))
                            .collect(Collectors.toList());
                    product = new Product(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            prodSize,
                            rs.getString("color"),
                            rs.getDouble("price"),
                            rs.getDouble("discountedprice"),
                            new ArrayList<>()
                    );
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
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
        return product;
    }

     private List<Size> getProductSizes(String productId) {
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        List<Size> sizes = new ArrayList<>();
        if (con != null) {
            try {
                ps1 = con.prepareStatement("SELECT id,name FROM size INNER JOIN product_size ON product_size.size = size.id WHERE product_size.product=?");
                ps1.setString(1, productId);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    sizes.add(new Size(rs1.getString("id"),rs1.getString("name")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (rs1 != null) {
                    try {
                        rs1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sizes;
    }
    
    @Override
    public List<Reservedproduct> getAllReservedproducts() {
        Connection con1 = null;
        String url = "jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false";
        List<Reservedproduct> prods = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con1 = DriverManager.getConnection(url, "root", "Root");
        } catch (SQLException ex) {
            Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        if (con1!=null) {
            try {
                ps1 = con1.prepareStatement("select customeraddress, date from reservedproduct");
                rs1 = ps1.executeQuery();
                while (rs1.next()) {        
                    Reservedproduct r = new Reservedproduct();
                    r.setDate(rs1.getTimestamp("date").toLocalDateTime());
                    r.setCustomerEmail(rs1.getString("customerEmail"));
                    prods.add(r);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs1!=null) {
                    try {
                        rs1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return prods;
    }

    @Override
    public boolean collectReserveProduct(String email) {
         boolean success = false;
        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("update reservedproduct set collected = 1 where customerEmail = ?");
                ps.setString(1, email);
                rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    con.commit();
                    success = true;
                } else {
                    con.rollback();
                }
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReservedproductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return success;
    }
    
    
}
