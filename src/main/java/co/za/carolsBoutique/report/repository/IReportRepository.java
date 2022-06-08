package co.za.carolsBoutique.report.repository;

import co.za.carolsBoutique.report.model.Report;
import java.util.List;
import java.util.Map;

public interface IReportRepository {
    
    List<Report> findTopStoresInTermsOfSales(int month); 
    List<Report> findHighestRatedStores(int month);
    List<Report> findStoreMonthlySales(String store, int month);
    List<Report> findTopSellingEmployees(String store, int month);
    List<Report> findTopSellingEmployees(int month);
    List<Report> findStoreThatAchievedMonthlyTarget(int month);
    List<Report> findTop40Products();
    List<Report> findUnderPerformingStores(int month);
    Report findTopSalepersonForAProduct(String product);
    Report findCurrentDailySales(String store);

}