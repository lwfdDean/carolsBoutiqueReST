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
import java.util.Map;
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
            Class.forName("com.mysql.cj.jdbc.Driver");
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
					employee = new Employee(
							employeeId,
							rs.getString("name"),
							rs.getString("surname"),
							rs.getString("emailAddress"),
							rs.getString("password"),
							rs.getString("managerUniqueCode"),
							getEmployeeRole(employeeId),
							rs.getString("boutique"));
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
	public List<Employee> findAllEmployees(String boutiqueId) {
		List<Employee> employees = new ArrayList<>();
		if (con != null) {
			try {
				ps = con.prepareStatement("select id,name,surname,pasword,managerUniqueCode,role,boutique from employee where boutique = ?");
				ps.setString(1, boutiqueId);
				rs = ps.executeQuery();
				while (rs.next()) {
					String empId = rs.getString("id");
					employees.add(new Employee(
							empId,
							rs.getString("name"),
							rs.getString("surname"),
							rs.getString("emailAddress"),
							rs.getString("password"),
							rs.getString("managerUniqueCode"),
							getEmployeeRole(empId),
							boutiqueId));
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
				ps = con.prepareStatement("insert into employee(id,name,surname,emailAddress,password,managerUniqueCode,role,boutique) values(?,?,?,?,?,?,?,?)");
				ps.setString(1, employee.getId());
				ps.setString(2, employee.getName());
				ps.setString(3, employee.getSurname());
				ps.setString(4, employee.getEmailAddress());
				ps.setString(5, employee.getPassword());
				ps.setString(6, employee.getManagerCode());
				ps.setString(7, employee.getRole().getId());
				ps.setString(8, employee.getBoutique());
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
	public boolean updateToTeller(String employeeId, String password, String roleId) {
		if (con != null) {
			try {
				ps = con.prepareStatement("update employee set password=?, role= 'tel101' where id=?");
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

	@Override
	public boolean updateToManager(String employeeId, String managerUniqueCode, String roleId) {
		if (con != null) {
			try {
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

    private Role getEmployeeRole(String employeeId) {
        Role role = null;
        PreparedStatement ps1 = null;
        ResultSet rs1 = null;
        if (con != null) {
            try {
                ps1 = con.prepareStatement("select * from role inner join employee on employee.role = role.id where employee.id = ?");
                ps1.setString(1, employeeId);
                rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    role = new Role(rs1.getString("id"), rs1.getString("name"), rs1.getInt("authorizationlvl"));
                    System.out.println(role.getName());
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps1 != null) {
                    try {
                        ps1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs1 != null) {
                    try {
                        rs1.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return role;
    }

	@Override
	public List<Employee> findAllByRole(String roleId, String boutiqueId) {
		List<Employee> employees = new ArrayList<>();
		if (con != null) {
			try {
				ps = con.prepareStatement("select * from employee where role = ? and boutique = ?");
				ps.setString(1, roleId);
				rs = ps.executeQuery();
				while (rs.next()) {
					String empId = rs.getString("id");
					employees.add(new Employee(
							empId,
							rs.getString("name"),
							rs.getString("surname"),
							rs.getString("emailAddress"),
							rs.getString("password"),
							rs.getString("managerUniqueCode"),
							getEmployeeRole(empId),
							boutiqueId
					));
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

	@Override//////////////Dont test yet
	public boolean verifyManagerCode(Map<String, String> managerInformation) {
		String boutiqueId = managerInformation.keySet().iterator().next();
		if(con != null){
			try {
				ps = con.prepareStatement("SELECT id FROM employee WHERE boutique = ? AND managerUniqueCode = ?");
				ps.setString(1, boutiqueId);
				ps.setString(2, managerInformation.get(boutiqueId));
				
			} catch (SQLException ex) {
				Logger.getLogger(EmployeeRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return true;
	}

}
