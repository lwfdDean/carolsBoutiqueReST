/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.ReserveProduct.repository;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 27609
 */
public class ReservedproductRepositoryImp implements ReservedproductRepository{
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
    public Reservedproduct findReserveProduct(String reserveProductid) {
    Reservedproduct reserveProduct = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from reservedproduct where id=?");
                ps.setString(1, reserveProductid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    reserveProduct = new Reservedproduct(rs.getString("id"), rs.getString("customerEmail"));
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
        return reserveProduct;
    }

    @Override
    public boolean addReserveProduct(Reservedproduct reserveProduct) {
  if (con != null) {
            try {
                ps = con.prepareStatement("insert into reservedproduct(id,customerEmail,collected) values(?,?,?)");
                ps.setString(1, reserveProduct.getProducId());
                ps.setString(2, reserveProduct.getCustomerEmail());
                ps.setBoolean(3, false);
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
        return rowsAffected == 1;    }
 }
