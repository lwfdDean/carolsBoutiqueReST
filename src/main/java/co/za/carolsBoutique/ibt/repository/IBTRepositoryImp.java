package co.za.carolsBoutique.ibt.repository;

import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.ibt.model.IBT;
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
                ps = con.prepareStatement("insert into ibt(id,customerEmail,approved,productcode,requestingBoutique,approvingBoutique) values(?,?,?,?,?,?)");
                ps.setString(1, ibt.getId());
                ps.setString(2, ibt.getCustomerEmail());
                ps.setBoolean(3, ibt.getApproved());
                ps.setString(4, ibt.getProductCode());
                ps.setString(5, ibt.getRequestingBoutique());
                ps.setString(6, ibt.getApprovingBoutique());
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
                    ibt = new IBT(rs.getString("id"),
                            rs.getString("customerEmail"),
                            rs.getBoolean("approved"),
                            rs.getString("productCode"),
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
                    ibtRequests.add(new IBT(rs.getString("id"),
                            rs.getString("customerEmail"),
                            rs.getBoolean("approved"),
                            rs.getString("productcode"),
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

    @Override
    public List<IBT> findStoreIBTRequests(String storeId) {
        List<IBT> ibtRequested = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from ibt where requestingBoutique = ?");
                ps.setString(1, storeId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    ibtRequested.add(new IBT(rs.getString("id"),
                            rs.getString("customerEmail"),
                            rs.getBoolean("approved"),
                            rs.getString("productCode"),
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

    @Override
    public String getManagerEmail(String boutiqueId) {
        String email = new String();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select emailAddress from employee where role = \"man101\" and boutique = ?");
                ps.setString(1, boutiqueId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    email = rs.getString("emailAddress");
                }
            } catch (SQLException ex) {
                Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return email;
    }

}
