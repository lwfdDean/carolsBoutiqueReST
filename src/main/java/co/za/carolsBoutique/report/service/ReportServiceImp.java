package co.za.carolsBoutique.report.service;

import co.za.carolsBoutique.report.model.Report;
import co.za.carolsBoutique.report.model.ReportCriteria;
import co.za.carolsBoutique.report.repository.IReportRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportServiceImp implements IServiceReport{
    private IReportRepository dao;

    public ReportServiceImp(IReportRepository dao) {
        this.dao = dao;
    }

    @Override
    public List<Report> findTopStoresInTermsOfSales(ReportCriteria rc) {
        List<Report> reports = dao.findTopStoresInTermsOfSales(rc.getMonth());
        Collections.sort(reports);
        List<Report> results = new ArrayList<>();
        for (int i = reports.size()-1; i > reports.size()-rc.getResults()-1 ; i--) {
            results.add(reports.get(i));
        }
        return results;
    }
    
    @Override
    public List<Report> findHighestRatedStores(ReportCriteria rc) {
        List<Report> reports = dao.findHighestRatedStores(rc.getMonth());
        System.out.println(reports.size()+" $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        for (int i = 0; i < reports.size()-1; i++) {
            Report r = reports.get(i);
            if (r == null) {
                continue;
            }
            if (r.getRating()>reports.get(i+1).getRating()) {
                Report r2 = reports.get(i+1);
                reports.set(i+1, r);
                reports.set(i, r2);
                i = 0;
            }
        }
        List<Report> results = new ArrayList<>();
        for (int i = 0; i < reports.size() ; i++) {
            if (i>=reports.size()-rc.getResults()) {
                results.add(reports.get(i));//nothing is added
            }
        }
        return results;
    }

    @Override
    public List<Report> findStoreMonthlySales(ReportCriteria rc) {
        return dao.findStoreMonthlySales(rc.getBoutique(), rc.getMonth());
    }

    @Override
    public List<Report> findTopSellingEmployees(ReportCriteria rc) {
        List<Report> reports = (rc.getBoutique()==null || rc.getBoutique().isEmpty())?
                dao.findTopSellingEmployees(rc.getMonth()):dao.findTopSellingEmployees(rc.getBoutique(), rc.getMonth());
        Collections.sort(reports);
        List<Report> results = new ArrayList<>();
        for (int i = reports.size()-1; i > reports.size()-rc.getResults()-1 ; i--) {
            results.add(reports.get(i));
        }
        return results;
    }

    @Override
    public List<Report> findStoreThatAchievedMonthlyTarget(ReportCriteria rc) {
        List<Report> reports = dao.findStoreThatAchievedMonthlyTarget(rc.getMonth());
        Collections.sort(reports);
        List<Report> results = new ArrayList<>();
        for (int i = reports.size()-1; i > reports.size()-rc.getResults()-1 ; i--) {
            results.add(reports.get(i));
        }
        return results;
    }

    @Override
    public List<Report> findTop40Products() {
        List<Report> reports = dao.findTop40Products();
        List<String> products = new ArrayList<>();
        List<Report> results = new ArrayList<>();
        reports.forEach(report -> products.add(report.getId()));
        for (int i = 0; i < products.size(); i++) {
            Report report1 = null;
            for (int j = 0; j < reports.size()-1; j++) {
                if (report1==null||(reports.get(j).getAmount()>report1.getAmount() && reports.get(j).getId().equals(products.get(i)))) {
                    report1 = reports.get(j);
                }
            }
            results.add(report1);
        }
        for (int i = 0; i < reports.size()-1; i++) {
            Report r = reports.get(i);
            if (r.getAmount()>reports.get(i+1).getAmount()) {
                Report r2 = reports.get(i+1);
                reports.set(i+1, r);
                reports.set(i, r2);
                i = 0;
            }
        }
        int amount = (results.size()<40)?0:results.size();
        for (int i = results.size()-1; i > results.size()-amount-1 ; i--) {
            results.add(reports.get(i));
        }
        return results;
    }

    @Override
    public List<Report> findUnderPerformingStores(ReportCriteria rc) {
        List<Report> reports = dao.findUnderPerformingStores(rc.getMonth());
        Collections.sort(reports);
        List<Report> results = new ArrayList<>();
        for (int i = 0; i < rc.getResults() ; i++) {    //this line breaks code, decrementing 0 results in nullpointer
            results.add(reports.get(i));
        }
        return results;
    }

    @Override
    public Report findTopSalepersonForAProduct(ReportCriteria rc) {
        return dao.findTopSalepersonForAProduct(rc.getProduct());
    }

    @Override
    public Report findCurrentDailySales(ReportCriteria rc) {
        return dao.findCurrentDailySales(rc.getBoutique());
    }

}
