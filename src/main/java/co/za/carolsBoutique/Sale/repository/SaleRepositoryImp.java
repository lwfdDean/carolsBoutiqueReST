package co.za.carolsBoutique.Sale.repository;

import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.product.model.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleRepositoryImp implements ISaleRepository {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public SaleRepositoryImp() {
        String url = "jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, "root", "Root");
        } catch (SQLException ex) {
            Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean addSale(Sale sale) {
        boolean success = false;
        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("INSERT INTO sale(id, customerEmail, approved, totalPrice, employee, boutique, cardNumber)"
                        + " VALUES(?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, sale.getId());
                ps.setString(2, sale.getCustomerEmail());
                ps.setBoolean(3, sale.isApproved());
                ps.setDouble(4, sale.getTotalPrice());
                ps.setString(5, sale.getEmployee());
                ps.setString(6, sale.getBoutique());
                rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    removeFromStock(sale);
                    int total = 0;
                    for (Product p : sale.getItems()) {
                        if (addSaleLineItem(sale.getId(), p.getId())) {
                            total++;
                        }
                    }
                    if (total==sale.getItems().size()) {
                        con.commit();
                        success = true;
                    }else{
                        con.rollback();
                    }
                } else {
                    con.rollback();
                }
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return success;
    }

    private boolean removeFromStock(Sale sale) {
        String bId = sale.getBoutique();
        Map<String, String> productCodes = new HashMap<>();
        for (Product product : sale.getItems()) {
            productCodes.put(product.getId(), product.getSizes().get(0));
        }
        int rows = 0;
        PreparedStatement ps1 = null;
        if (con != null) {
            for (Iterator<String> it = productCodes.keySet().iterator(); it.hasNext();) {
                String prodId = it.next();
                try {
                    ps1 = con.prepareStatement("update stock set quantity = quantity - 1 where boutique = ? and product = ? and size = ?");
                    ps1.setString(1, bId);
                    ps1.setString(2, prodId);
                    ps1.setString(2, productCodes.get(prodId));
                    rows += ps1.executeUpdate();
                    ps1.close();
                } catch (SQLException ex) {
                    Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (ps1 != null) {
                        try {
                            ps1.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        return rows == productCodes.size();
    }

    private boolean addSaleLineItem(String saleId, String productId) {
        PreparedStatement ps1 = null;
        int rows = 0;
        if (con != null) {
            try {
                ps1 = con.prepareStatement("insert into sale_line_item(sale, product,returned) values(?,?,?)");
                ps1.setString(1, saleId);
                ps1.setString(2, productId);
                ps1.setBoolean(3, false);
                rows = ps1.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }
        return rows == 1;
    }

    @Override
    public Sale findSale(String saleId) {
        Sale sale = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT * FROM sale WHERE id = ?");
                ps.setString(1, saleId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    sale = new Sale(rs.getString("id"),
                            rs.getString("customerEmail"),
                            rs.getString("employee"),
                            rs.getBoolean("approved"),
                            rs.getDouble("totalPrice"),
                            getSaleLineItems(saleId),
                            rs.getString("boutique"),
                            rs.getString("cardNumber"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return sale;
    }

    private List<Product> getSaleLineItems(String saleId) {
        PreparedStatement ps3 = null;
        ResultSet rs3 = null;
        List<String> categories = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        if (con != null) {
            try {
                ps3 = con.prepareStatement("SELECT * FROM product INNER JOIN sale_line_item ON sale_line_item.product = product.id WHERE sale_line_item.sale = ?");
                ps3.setString(1, saleId);
                rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    products.add(new Product(rs3.getString("id"),
                            rs3.getString("name"),
                            rs3.getString("description"),
                            getProductSizes(rs3.getString("id")),
                            rs3.getString("color"),
                            rs3.getDouble("price"),
                            findProductCategories(rs3.getString("id"))
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps3 != null) {
                    try {
                        ps3.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (rs3 != null) {
                    try {
                        rs3.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return products;
    }

    private List<String> getProductSizes(String productId) {
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        List<String> sizes = new ArrayList<>();
        if (con != null) {
            try {
                ps1 = con.prepareStatement("SELECT id FROM size INNER JOIN product_size ON product_size.size = size.id WHERE product_size.product=?");
                ps1.setString(1, productId);
                rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    sizes.add(rs1.getString("id"));
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

    private List<String> findProductCategories(String productId) {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        List<String> categories = new ArrayList<>();
        if (con != null) {
            try {
                ps2 = con.prepareStatement("SELECT category FROM product_category WHERE product = ?");
                ps2.setString(1, productId);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    categories.add(rs2.getString("category"));
                }
                return categories;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps2 != null) {
                    try {
                        ps2.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (rs2 != null) {
                    try {
                        rs2.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean updateSale(String saleId, Double totalPrice, String productId) {
        boolean success = false;
        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("update sale set totalPrice = ? where id = ?");
                ps.setDouble(1, totalPrice);
                ps.setString(2, saleId);
                rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    if (updateSaleLineItem(saleId, productId)) {
                        con.commit();
                        success = true;
                    }
                } else {
                    con.rollback();
                }
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return success;
    }

    private boolean updateSaleLineItem(String saleId, String productId) {
        PreparedStatement ps1 = null;
        int rows = 0;
        if (con != null) {
            try {
                ps1 = con.prepareStatement("update sale_line_item set returned = true where sale = ? and product =?");
                ps1.setString(1, saleId);
                ps1.setString(2, productId);
                rows = ps1.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return rows == 1;
    }

    @Override
    public Timestamp findSaleDate(String saleId) {
        Timestamp timestamp = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select date from sale where id = ?");
                ps.setString(1, saleId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    timestamp = rs.getTimestamp("date");
                }
            } catch (SQLException ex) {
                Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();

                    } catch (SQLException ex) {
                        Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return timestamp;
    }

    @Override
    public boolean updateSaleLineItem(String saleId, String returnedProductId, String newProductId) {
        boolean success = false;
        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("UPDATE sale_line_item SET returned = true where product = ? and sale = ?");
                ps.setString(1, returnedProductId);
                ps.setString(2, saleId);
                rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1) {
                    if (addSaleLineItem(saleId, newProductId)) {
                        con.commit();
                        success = true;
                    }else{
                        con.rollback();
                    }
                } else {
                    con.rollback();
                }
                con.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(SaleRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }
}
