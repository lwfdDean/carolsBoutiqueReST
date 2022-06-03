package co.za.carolsBoutique.boutique.repository;

import co.za.carolsBoutique.boutique.model.Boutique;
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

    @Override //willem
    public Boutique findBoutique(String boutiqueId) {
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
            }
        }
        return boutique;
    }

    @Override
    public boolean addBoutique(Boutique boutique) {
        if (con != null) {
            try {
                ps = con.prepareStatement("Insert Into boutique(id, location, dailytarget, password) values(?, ?, ?, ?)");
                ps.setString(1, boutique.getId());
                ps.setString(2, boutique.getLocation());
                ps.setDouble(3, boutique.getDailyTarget());
                ps.setString(4, boutique.getPassword());
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
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean updateBoutique(String boutiqueId, Double dailyTarget) {
        if (con != null) {
            try {
                ps = con.prepareStatement("Update boutique Set dailytarget = ? Where id = ?");
                ps.setDouble(1, dailyTarget);
                ps.setString(2, boutiqueId);

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
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean updateBoutique(String boutiqueid, String password) {
        if (con != null) {
            try {
                ps = con.prepareStatement("Update boutique Set password = ? Where id = ?");
                ps.setString(1, password);
                ps.setString(2, boutiqueid);

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
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean deleteBoutique(String boutiqueId) {
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
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public List<Boutique> findAllBoutiques() {
        List<Boutique> boutiques = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("Select * from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {
                    boutiques.add(new Boutique(rs.getString("id"),
                            rs.getString("location"),
                            rs.getDouble("dailytarget"),
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
            }
        }
        return boutiques;
    }

}
