package co.za.carolsBoutique.ibt.repository;

import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.repository.ProductRepositoryImp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IBTRepositoryImp implements IIBTRepository {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public IBTRepositoryImp() {
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
    public boolean addIBT(IBT ibt) {
        if (con != null) {
            try {
                ps = con.prepareStatement("insert into ibt(id,customerEmail,approved,product,requestingBoutique,approvingBoutique,size) values(?,?,?,?,?,?,?)");
                ps.setString(1, ibt.getId());
                ps.setString(2, ibt.getCustomerEmail());
                ps.setBoolean(3, ibt.getApproved());
                ps.setString(4, ibt.getProduct().getId());
                ps.setString(5, ibt.getRequestingBoutique());
                ps.setString(6, ibt.getApprovingBoutique());
                ps.setString(7, ibt.getSize());
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public IBT findIBT(String ibtId) {
        IBT ibt = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from ibt where id=?");
                ps.setString(1, ibtId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String productId = rs.getString("product");
                    String size = rs.getString("size");
                    Product product = getIbtProduct(productId, size);
                    ibt = new IBT(rs.getString("id"),
                            rs.getString("customerEmail"),
                            rs.getBoolean("approved"),
                            product,
                            size,
                            rs.getString("approvingBoutique"),
                            rs.getString("requestingBoutique")
                            
                    );
                }
            } catch (SQLException ex) {
                Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return ibt;
    }

    @Override
    public List<IBT> findStoreIBTS(String storeId) {
        List<IBT> ibtRequests = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from ibt where approvingBoutique = ?");
                ps.setString(1, storeId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("product");
                    String size = rs.getString("size");
                    Product product = getIbtProduct(productId, size);
                    ibtRequests.add(new IBT(rs.getString("id"),
                            rs.getString("customerEmail"),
                            rs.getBoolean("approved"),
                            product,
                            size,
                            rs.getString("approvingBoutique"),
                            rs.getString("requestingBoutique")
                    ));
                }
            } catch (SQLException ex) {
                Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return ibtRequests;
    }

    @Override
    public boolean updateIBT(String ibtId, boolean approved) {
        if (con != null) {
            try {
                ps = con.prepareStatement("update ibt set approved = ? where id = ?");
                ps.setBoolean(1, approved);
                ps.setString(2, ibtId);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean deleteIBT(String ibtId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("delete from ibt where id = ?");
                ps.setString(1, ibtId);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    private Product getIbtProduct(String productId, String size) {
        Product product = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        if (con != null) {
            try {
                ps1 = con.prepareStatement("select * from product inner join product_size on product_size.product = product.id"
                        + " where product_size.size = ? and product.id = ?");
                ps1.setString(1, size);
                ps1.setString(2, productId);
                rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    product = new Product(rs1.getString("id"),
                            rs1.getString("name"),
                            rs1.getString("description"),
                            getProductSizes(productId),
                            rs1.getString("color"),
                            rs1.getDouble("price"),
                            rs1.getDouble("discountedprice"),
                            findProductCategories(productId));
                }
            } catch (SQLException ex) {
                Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs1 != null) {
                    try {
                        rs1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return product;
    }

    private List<String> getProductSizes(String productId) {
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        List<String> sizes = new ArrayList<>();
        if (con != null) {
            try {
                ps2 = con.prepareStatement("select id from size inner join product_size on product_size.size = size.id where product_size.product=?");
                ps2.setString(1, productId);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    sizes.add(rs2.getString("id"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs2 != null) {
                    try {
                        rs2.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps2 != null) {
                    try {
                        ps2.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return sizes;
    }

    private List<String> findProductCategories(String productId) {
        List<String> categories = new ArrayList<>();
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        if (con != null) {
            try {
                ps2 = con.prepareStatement("select category from product_category where product = ?");
                ps2.setString(1, productId);
                rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    categories.add(rs2.getString("category"));
                }
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                if (rs2 != null) {
                    try {
                        rs2.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps2 != null) {
                    try {
                        ps2.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ProductRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        return categories;
    }

    @Override
    public List<IBT> findStoreIBTRequests(String storeId) {
        List<IBT> ibtRequested = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from ibt where requestingBoutique = ?");
                ps.setString(1, storeId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("product");
                    String size = rs.getString("size");
                    Product product = getIbtProduct(productId, size);
                    ibtRequested.add(new IBT(rs.getString("id"),
                            rs.getString("customerEmail"),
                            rs.getBoolean("approved"),
                            product,
                            size,
                            rs.getString("requestingBoutique"),
                            rs.getString("approvingBoutique")
                    ));
                }
            } catch (SQLException ex) {
                Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return ibtRequested;
    }

}
