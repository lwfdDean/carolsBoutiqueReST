package co.za.carolsBoutique.report.repository;

import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.report.model.Report;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportRepositoryImp implements IReportRepository{
    
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private int rowsAffected;

    public ReportRepositoryImp() {
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
    public List<Report> findTopStoresInTermsOfSales(int month) {
        List<Report> report = new ArrayList<>();
        List<String> stores = new ArrayList<>();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select id from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    stores.add(rs.getString("id"));
                }
                ps.close();
                rs.close();
                for (String store : stores) {
                    Double totalForStore = 0.0;
                    ps = con.prepareStatement("select date,totalPrice from sale where boutique = ?");
                    ps.setString(1, store);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue()==month) {
                            totalForStore += rs.getDouble("totalPrice");
                        }
                    }
                    report.add(new Report(store,totalForStore));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        List<String> stores = new ArrayList<>();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select id from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    stores.add(rs.getString("id"));
                }
                ps.close();
                rs.close();
                for (String store : stores) {
                    int numberOfRatings = 0;
                    int totalRating = 0;
                    ps = con.prepareStatement("select rating,date from review where boutique = ?");
                    ps.setString(1, store);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue()==month) {
                            numberOfRatings++;
                            totalRating += rs.getDouble("rating");
                        }
                    }
                    if (numberOfRatings!=0) {
                        results.add(new Report(store, totalRating/numberOfRatings));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        if (con!=null) {
            try {
                ps = con.prepareStatement("select date,totalPrice from sale where boutique = ?");
                ps.setString(1, store);
                rs = ps.executeQuery();
                double total = 0.0;
                while (rs.next()) {
                    if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue()==month) {
                        total += rs.getDouble("totalPrice");
                    }
                }
                results.add(new Report(store, total));
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        List<String> employees = new ArrayList<>();
        List<Report> results = new ArrayList<>();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select id from employee where boutique = ?");
                ps.setString(1, store);
                rs = ps.executeQuery();
                while (rs.next()) {
                    employees.add(rs.getString("id"));
                }
                ps.close();
                rs.close();
                
                for (int i = 0; i < employees.size(); i++) {
                    ps = con.prepareStatement("select totalPrice, date from sale where employee = ?");
                    ps.setString(1, employees.get(i));
                    rs = ps.executeQuery();
                    double totalSales = 0.0;
                    while (rs.next()) {                        
                        if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue()==month) {
                            totalSales += rs.getDouble("totalPrice");
                        }
                    }
                    results.add(new Report(employees.get(i), totalSales));
                    rs.close();
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        List<String> employees = new ArrayList<>();
        List<Report> results = new ArrayList<>();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select id from employee");
                rs = ps.executeQuery();
                while (rs.next()) {
                    employees.add(rs.getString("id"));
                }
                ps.close();
                rs.close();
                for (int i = 0; i < employees.size(); i++) {
                    ps = con.prepareStatement("select totalPrice, date fom sale where employee = ?");
                    ps.setString(1, employees.get(i));
                    rs = ps.executeQuery();
                    double totalSales = 0.0;
                    while (rs.next()) {                        
                        if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue()==month) {
                            totalSales += rs.getDouble("totalPrice");
                        }
                    }
                    results.add(new Report(employees.get(i), totalSales));
                    rs.close();
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        Map<String, Double> stores = new TreeMap<>();
        List<Report> results = new ArrayList<>();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select id,monthlyTarget from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {
                    stores.put(rs.getString("id"), rs.getDouble("monthlyTarget"));
                }
                ps.close();
                rs.close();
                for (Iterator<String> it = stores.keySet().iterator(); it.hasNext();) {
                    String boutique = it.next();
                    ps = con.prepareStatement("select date,totalPrice from sale where boutique = ?");
                    ps.setString(1, boutique);
                    rs = ps.executeQuery();
                    double total = 0.0;
                    while (rs.next()) {                        
                        if (true) {
                            if (rs.getTimestamp("date").toLocalDateTime().getMonth().getValue()==month) {
                                total += rs.getDouble("totalPrice");
                            }
                        }
                    }
                    if (total>=stores.get(boutique)) {
                        results.add(new Report(boutique,total));
                    }
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        List<String> products = new ArrayList<>();
        List<String> boutiques = new ArrayList<>();
        if (con!=null) {
            try {
                ps = con.prepareStatement("select id from product");
                rs = ps.executeQuery();
                while(rs.next()){
                    products.add(rs.getString("id"));
                }
                ps.close();
                rs.close();
                ps = con.prepareStatement("select id from boutique");
                rs = ps.executeQuery();
                while(rs.next()){
                    boutiques.add(rs.getString("id"));
                }
                ps.close();
                rs.close();
                for (String boutique : boutiques) {
                    for (String product : products) {
                        int totalNumberOfSales = 0;
                        ps = con.prepareStatement("select count(sale_line_item.sale) AS total from sale_line_item inner join sale on sale.id = sale_line_item.sale"
                                + " where sale.boutique = ? and sale_line_item.product = ?");
                        ps.setString(1, boutique);
                        ps.setString(2, product);
                        rs = ps.executeQuery();
                        if (rs.next()) {                            
                            totalNumberOfSales = rs.getInt("total");
                        }
                        results.add(new Report(product,boutique,totalNumberOfSales));
                    }
                    rs.close();
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        if (con!=null) {
            try {
                List<String> boutiques = new ArrayList<>();
                ps = con.prepareStatement("select id from boutique");
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    boutiques.add(rs.getString("id"));
                }
                ps.close();
                rs.close();
                for (String store : boutiques) {
                    Double totalForStore = 0.0;
                    ps = con.prepareStatement("select date,totalPrice from sale where boutique = ?");
                    ps.setString(1, store);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        int month = rs.getTimestamp("date").toLocalDateTime().getMonth().getValue();
                        if (month+interval>LocalDateTime.now().getMonth().getValue()) {
                            totalForStore += rs.getDouble("totalPrice");
                        }
                    }
                    results.add(new Report(store,totalForStore));
                    ps.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        if (con!=null) {
            try {
                List<String> employees = new ArrayList<>();
                ps = con.prepareStatement("select id from employee");
                rs = ps.executeQuery();
                while (rs.next()) {                    
                    employees.add(rs.getString("id"));
                }
                rs.close();
                ps.close();
                int totalSold = 0;
                int empTotal = 0;
                String topEmp = null;
                for (String employee : employees) {
                    ps = con.prepareStatement("select count(sale.id) AS total from sale inner join sale_line_item on sale.id = sale_line_item.sale"
                            + " where sale_line_item.product = ? and sale.employee = ?");
                    ps.setString(1, product);
                    ps.setString(2, employee);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int count = rs.getInt("total");
                        if (count>empTotal) {
                            empTotal = count;
                            topEmp = employee;
                        }
                        totalSold += count;
                    }
                    ps.close();
                    rs.close();
                }
                report = new Report(topEmp,totalSold);
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
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
        if (con!=null) {
            try {
                ps = con.prepareStatement("select totalPrice,date from sale where boutique = ?");
                ps.setString(1, store);
                rs =ps.executeQuery();
                double todaysSale = 0.0;
                int i = 0;
                while (rs.next()) {                    
                    if (rs.getTimestamp("date").toLocalDateTime().getDayOfYear()==LocalDateTime.now().getDayOfYear()) {
                        double holder = rs.getDouble("totalPrice");
                        System.out.println(i +"th" + holder);
                        todaysSale += holder;
                    }
                }
                report = new Report(store, todaysSale);
            } catch (SQLException ex) {
                Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
            } finally{
                if (ps!=null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (rs!=null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportRepositoryImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return report;
    }

}
