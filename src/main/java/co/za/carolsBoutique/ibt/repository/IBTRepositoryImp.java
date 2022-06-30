package co.za.carolsBoutique.ibt.repository;

import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.databaseManager.DBManager;
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
        
    }

    @Override
    public boolean addIBT(IBT ibt) {
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if(con!=null){
                    try {
                        con.close();
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
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if(con!=null){
                    try {
                        con.close();
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
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if(con!=null){
                    try {
                        con.close();
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
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if(con!=null){
                    try {
                        con.close();
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
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if(con!=null){
                    try {
                        con.close();
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
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if(con!=null){
                    try {
                        con.close();
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
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if(con!=null){
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(IBTRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return email;
    }

}
