/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.ReserveProduct.repository;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.product.repository.ProductRepositoryImp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 27609
 */
public class ReservedproductRepositoryImp implements ReservedproductRepository {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public ReservedproductRepositoryImp() {
        String url = "jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
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
    public boolean deleteReserveProduct(String reserveProductid) {
        if (con != null) {
            try {
                ps = con.prepareStatement("delete from reservedproduct where id=?");
                ps.setString(1, reserveProductid);
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
                ps = con.prepareStatement("select id,quantity from stock where product=? and boutique = ? amd size = ?");
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
        Map<String, String> productInfo = null;
        if (con!=null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("select product,szie,quantity from stock where id =?");
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
            }
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
}
