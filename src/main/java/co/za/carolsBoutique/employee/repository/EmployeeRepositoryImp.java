package co.za.carolsBoutique.employee.repository;

import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.employee.model.Role;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeRepositoryImp implements IEmployeeRepository {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public EmployeeRepositoryImp() {
        String url = "jdbc:mysql://localhost:3306/carolsboutique?autoReconnect=true&useSSL=false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            con = DriverManager.getConnection(url, "root", "Root");
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Employee findEmployee(String employeeId) {
        Employee employee = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from employee where id=?");
                ps.setString(1, employeeId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    employee = new Employee(rs.getString("id"), rs.getString("name"), rs.getString("surname"),
                            rs.getString("password"), rs.getString("managerUniqueCode"), rs.getString("role"), rs.getString("boutique"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return employee;
    }

    @Override
    public List<Employee> findAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("select id,name,surname,pasword,managerUniqueCode,role,boutique from employee");
                rs = ps.executeQuery();
                while (rs.next()) {
                    employees.add(new Employee(rs.getString("id"), rs.getString("name"), rs.getString("surname"),
                            rs.getString("password"), rs.getString("managerUniqueCode"), rs.getString("role"),
                            rs.getString("boutique")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return employees;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        if (con != null) {
            try {
                ps = con.prepareStatement("insert into employee(id,name,surname,password,managerUniqueCode,role,boutique) values(?,?,?,?,?,?,?)");
                ps.setString(1, employee.getId());
                ps.setString(2, employee.getName());
                ps.setString(3, employee.getSurname());
                ps.setString(4, employee.getPassword());
                ps.setString(5, employee.getManagerCode());
                ps.setString(6, employee.getRole());
                ps.setString(7, employee.getRole());
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean deleteEmployee(String employeeId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("delete from employee where id=?");
                ps.setString(1, employeeId);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean updateToTeller(String employeeId, String password) {
        if (con != null) {
            try {
                String roleId = getRoleId("Teller");
                ps = con.prepareStatement("update employee set password=?, role=? where id=?");
                ps.setString(1, password);
                ps.setString(2, roleId);
                ps.setString(3, employeeId);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }

    private String getRoleId(String roleName) throws SQLException {
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        String roleId = "";
        if (con != null) {
            ps1 = con.prepareStatement("select id from role where name = ?");
            ps1.setString(1, roleName);
            rs1 = ps1.executeQuery();
            if (rs1.next()) {
                roleId = rs1.getString("id");
            }
            if (ps1 != null) {
                ps1.close();
            }
            if (rs1 !=null) {
                rs1.close();
            }
        }
        return roleId;
    }

    @Override
    public boolean updatToeManager(String employeeId, String managerUniqueCode) {
        if (con != null) {
            try {
                String roleId = getRoleId("Manager");
                ps = con.prepareStatement("update employee set managerUniqueCode=?, role=? where id=?");
                ps.setString(1, managerUniqueCode);
                ps.setString(2, roleId);
                ps.setString(3, employeeId);
                rowsAffected = ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return rowsAffected == 1;
    }
    
    //freddy
    @Override 
    
    public Role findRole(String roleId) {
         Role role = null;
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from role where id=?");
                ps.setString(1, roleId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    role = new Role(rs.getString("id"), rs.getString("name"), rs.getInt(" authorizationlvl"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (ps != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return role;
    }

    @Override
    public List<Role> findAllRoles() {  
       List<Role> role = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("Select * from role");
                rs = ps.executeQuery();
                while (rs.next()) {
                    role.add(new Role(rs.getString("id"),
                            rs.getString("name"),
                            rs.getInt("authorizationlvl")));
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
        return role; 
       
    }

    @Override
    public boolean addRole(Role role) {
        if (con != null) {
            try {
                ps = con.prepareStatement("Insert Into role(id,name,authorizationlvl) values(?,?,?)");
                ps.setString(1, role.getId());
                ps.setString(2, role.getName());
                ps.setInt(3, role.getAuthorizationLevel());
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
    public boolean deleteRole(String roleId) {
        if (con != null) {
            try {
                ps = con.prepareStatement("DELETE FROM role Where id= ?");
                ps.setString(1, roleId);

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

}
