package co.za.carolsBoutique.report.repository;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.databaseManager.DBManager;
import co.za.carolsBoutique.employee.model.Employee;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.report.model.Report;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportRepositoryImp implements IReportRepository {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public ReportRepositoryImp() {
        
    }

    @Override
    public List<Report> findTopStoresInTermsOfSales(int month) {
        List<Report> report = new ArrayList<>();
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Boutique> stores = new ArrayList<>();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select * from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {
                    stores.add(new Boutique(
                            rs.getString("id"),
                            rs.getString("location"),
                            rs.getDouble("dailytarget"),
                            rs.getDouble("monthlytarget"),
                            rs.getString("password")));
                }
                ps.close();
                rs.close();
                for (Boutique store : stores) {
                    Double totalForStore = 0.0;
                    ps = con.prepareStatement("select date,totalPrice from sale where boutique = ?");
                    ps.setString(1, store.getId());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue() == month) {
                            totalForStore += rs.getDouble("totalPrice");
                        }
                    }

                    report.add(new Report(store.getLocation(),totalForStore));

                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return report;
    }

    @Override
    public List<Report> findHighestRatedStores(int month) {
        List<Report> results = new ArrayList<>();
        List<Boutique> stores = new ArrayList<>();
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {
                    stores.add(new Boutique(
                            rs.getString("id"),
                            rs.getString("location"),
                            rs.getDouble("dailytarget"),
                            rs.getDouble("monthlytarget"),
                            rs.getString("password")));
                }
                ps.close();
                rs.close();
                for (Boutique store : stores) {
                    int numberOfRatings = 0;
                    int totalRating = 0;
                    ps = con.prepareStatement("select rating,date from review where boutique = ?");
                    ps.setString(1, store.getId());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue() == month) {
                            numberOfRatings++;
                            totalRating += rs.getDouble("rating");
                        }
                    }
                    if (numberOfRatings != 0) {
                        results.add(new Report(store.getLocation(), totalRating / numberOfRatings));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public List<Report> findStoreMonthlySales(String store, int month) {
        List<Report> results = new ArrayList<>();
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                String location = null;
                ps = con.prepareStatement("select location from boutique where id = ?");
                ps.setString(1, store);
                rs = ps.executeQuery();
                while (rs.next()) {
                    location = rs.getString("location");
                }
                ps.close();
                rs.close();
                ps = con.prepareStatement("select date,totalPrice from sale where boutique = ?");
                ps.setString(1, store);
                rs = ps.executeQuery();
                double total = 0.0;
                while (rs.next()) {
                    if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue() == month) {
                        total += rs.getDouble("totalPrice");
                    }
                }
                results.add(new Report(location, total));
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public List<Report> findTopSellingEmployees(String store, int month) {
        List<Employee> employees = new ArrayList<>();
        List<Report> results = new ArrayList<>();
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from employee where boutique = ?");
                ps.setString(1, store);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setId(rs.getString("id"));
                    e.setName(rs.getString("name"));
                    e.setSurname(rs.getString("surname"));
                    employees.add(e);
                }
                ps.close();
                rs.close();

                for (int i = 0; i < employees.size(); i++) {
                    ps = con.prepareStatement("select totalPrice, date from sale where employee = ?");
                    ps.setString(1, employees.get(i).getId());
                    rs = ps.executeQuery();
                    double totalSales = 0.0;
                    while (rs.next()) {
                        if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue() == month) {
                            totalSales += rs.getDouble("totalPrice");
                        }
                    }
                    results.add(new Report(employees.get(i).getName() + " " + employees.get(i).getSurname(), totalSales));
                    rs.close();
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public List<Report> findTopSellingEmployees(int month) {
        List<Employee> employees = new ArrayList<>();
        List<Report> results = new ArrayList<>();
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from employee");
                rs = ps.executeQuery();
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setId(rs.getString("id"));
                    e.setName(rs.getString("name"));
                    e.setSurname(rs.getString("surname"));
                    employees.add(e);
                }
                ps.close();
                rs.close();
                for (int i = 0; i < employees.size(); i++) {
                    ps = con.prepareStatement("select totalPrice, date from sale where employee = ?");
                    ps.setString(1, employees.get(i).getId());
                    rs = ps.executeQuery();
                    double totalSales = 0.0;
                    while (rs.next()) {
                        if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue() == month) {
                            totalSales += rs.getDouble("totalPrice");
                        }
                    }
                    results.add(new Report(employees.get(i).getName() + " " + employees.get(i).getSurname(), totalSales));
                    rs.close();
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
        return results;
    }

    @Override
    public List<Report> findStoreThatAchievedMonthlyTarget(int month) {
        Map<Boutique, Double> stores = new HashMap<>();
        List<Report> results = new ArrayList<>();
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("select id,monthlyTarget,location from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {
                    Boutique b = new Boutique();
                    b.setId(rs.getString("id"));
                    b.setLocation(rs.getString("location"));
                    stores.put(b, rs.getDouble("monthlyTarget"));
                }
                ps.close();
                rs.close();
                for (Iterator<Boutique> it = stores.keySet().iterator(); it.hasNext();) {
                    Boutique boutique = it.next();
                    ps = con.prepareStatement("select date,totalPrice from sale where boutique = ?");
                    ps.setString(1, boutique.getId());
                    rs = ps.executeQuery();
                    double total = 0.0;
                    while (rs.next()) {
                        if (true) {
                            if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue() == month) {
                                total += rs.getDouble("totalPrice");
                            }
                        }
                    }
                    if (total >= stores.get(boutique)) {
                        results.add(new Report(boutique.getLocation(), total));
                    }
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public List<Report> findTop40Products() {
        List<Report> results = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Boutique> boutiques = new ArrayList<>();
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("select * from product");
                rs = ps.executeQuery();
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getString("id"));
                    p.setName(rs.getString("name"));
                    products.add(p);
                }
                ps.close();
                rs.close();
                ps = con.prepareStatement("select * from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {
                    Boutique b = new Boutique();
                    b.setId(rs.getString("id"));
                    b.setLocation(rs.getString("location"));
                    boutiques.add(b);
                }
                ps.close();
                rs.close();
                for (Boutique boutique : boutiques) {
                    for (Product product : products) {
                        int totalNumberOfSales = 0;
                        ps = con.prepareStatement("select count(sale_line_item.sale) AS total from sale_line_item inner join sale on sale.id = sale_line_item.sale"
                                + " where sale.boutique = ? and sale_line_item.product = ?");
                        ps.setString(1, boutique.getId());
                        ps.setString(2, product.getId());
                        rs = ps.executeQuery();
                        if (rs.next()) {
                            totalNumberOfSales = rs.getInt("total");
                        }
                        results.add(new Report(product.getName(), boutique.getLocation(), totalNumberOfSales));
                    }
                    rs.close();
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public List<Report> findUnderPerformingStores(int interval) {
        List<Report> results = new ArrayList<>();
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                List<Boutique> boutiques = new ArrayList<>();
                ps = con.prepareStatement("select * from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {
                    Boutique b = new Boutique();
                    b.setId(rs.getString("id"));
                    b.setLocation(rs.getString("location"));
                    boutiques.add(b);
                }
                ps.close();
                rs.close();
                for (Boutique store : boutiques) {
                    Double totalForStore = 0.0;
                    ps = con.prepareStatement("select date,totalPrice from sale where boutique = ?");
                    ps.setString(1, store.getId());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int month = rs.getTimestamp("date").toLocalDateTime().getMonth().getValue();
                        if (month + interval > LocalDateTime.now().getMonth().getValue()) {
                            totalForStore += rs.getDouble("totalPrice");
                        }
                    }
                    results.add(new Report(store.getLocation(), totalForStore));
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return results;
    }

    @Override
    public Report findTopSalepersonForAProduct(String product) {
        Report report = null;
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                List<Employee> employees = new ArrayList<>();
                ps = con.prepareStatement("select * from employee");
                rs = ps.executeQuery();
                while (rs.next()) {
                    Employee e = new Employee();
                    e.setId(rs.getString("id"));
                    e.setName(rs.getString("id"));
                    e.setSurname(rs.getString("id"));
                    employees.add(e);
                }
                rs.close();
                ps.close();
                int totalSold = 0;
                int empTotal = 0;
                String topEmp = null;
                for (Employee employee : employees) {
                    ps = con.prepareStatement("select count(sale.id) AS total from sale inner join sale_line_item on sale.id = sale_line_item.sale"
                            + " where sale_line_item.product = ? and sale.employee = ?");
                    ps.setString(1, product);
                    ps.setString(2, employee.getId());
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt("total");
                        if (count > empTotal) {
                            empTotal = count;
                            topEmp = employee.getId();
                        }
                        totalSold += count;
                    }
                    ps.close();
                    rs.close();
                }
                for (Employee employee : employees) {
                    if (employee.getId().equals(topEmp)) {
                        topEmp = employee.getName() + " " + employee.getSurname();
                    }
                }
                report = new Report(topEmp, totalSold);
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return report;
    }

    @Override
    public Report findCurrentDailySales(String store) {
        Report report = null;
        try {
            con = DBManager.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con != null) {
            try {
                ps = con.prepareStatement("select totalPrice,date from sale where boutique = ?");
                ps.setString(1, store);
                rs = ps.executeQuery();
                double todaysSale = 0.0;
                int i = 0;
                while (rs.next()) {
                    if (rs.getTimestamp("date").toLocalDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
                        double holder = rs.getDouble("totalPrice");
                        todaysSale += holder;
                    }
                }
                ps.close();
                rs.close();
                String local = "";
                ps = con.prepareStatement("select location,dailytarget from sale where boutique = ?");
                ps.setString(1, store);
                rs = ps.executeQuery();
                int pAway = 0;
                if(rs.next()){
                    local = rs.getString("location");
                    double target = rs.getDouble("dailytarget");
                    pAway = (int)(todaysSale/target)*100;
                }
                report = new Report(local, todaysSale);
                report.setAmount(pAway);
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return report;
    }

}
