package co.za.carolsBoutique.boutique.repository;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.databaseManager.DBManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoutiqueRepositoryImp implements IBoutiqueRepository {//^

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public BoutiqueRepositoryImp() {
        
    }

    @Override //willem
    public Boutique findBoutique(String boutiqueId) {
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Boutique boutique = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("Select * from boutique where id = ?");
                ps.setString(1, boutiqueId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    boutique = new Boutique(rs.getString("id"),
                            rs.getString("location"),
                            rs.getDouble("dailytarget"),
                            rs.getDouble("monthlyTarget"),
                            rs.getString("password"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(con!=null){
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return boutique;
    }

    @Override
    public boolean addBoutique(Boutique boutique) {
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("Insert Into boutique(id, location, dailytarget, monthlyTarget, password) values(?, ?, ?, ?, ?)");
                ps.setString(1, boutique.getId());
                ps.setString(2, boutique.getLocation());
                ps.setDouble(3, boutique.getDailyTarget());
                ps.setDouble(4, boutique.getMonthlyTarget());
                ps.setString(5, boutique.getPassword());
                rowsAffected = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(con!=null){
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean deleteBoutique(String boutiqueId) {
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("DELETE FROM boutique Where id = ?");
                ps.setString(1, boutiqueId);

                rowsAffected = ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(con!=null){
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public List<Boutique> findAllBoutiques() {
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Boutique> boutiques = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("Select * from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {
                    boutiques.add(new Boutique(rs.getString("id"),
                            rs.getString("location"),
                            rs.getDouble("dailytarget"),
                            rs.getDouble("monthlyTarget"),
                            rs.getString("password")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(con!=null){
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return boutiques;
    }

    @Override
    public boolean subscribeToNewsletter(String contactInfo) {
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("insert into subscriberlist(contactInfo) values(?)");
                ps.setString(1, contactInfo);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(con!=null){
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean addReview(int rating, String comment, String boutique) {
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("insert into review(rating, comment, boutique) values(?,?,?)");
                ps.setInt(1, rating);
                ps.setString(2, comment);
                ps.setString(3, boutique);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(con!=null){
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean updateBoutique(Boutique boutique) {
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("update boutique set dailytarget = ?, monthlytarget = ? where id = ?");
                ps.setDouble(1, boutique.getDailyTarget());
                ps.setDouble(2, boutique.getMonthlyTarget());
                ps.setString(3, boutique.getId());
                rowsAffected = ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(con!=null){
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(BoutiqueRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

}
