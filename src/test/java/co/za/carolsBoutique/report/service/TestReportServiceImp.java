/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package co.za.carolsBoutique.report.service;

import co.za.carolsBoutique.report.model.Report;
import co.za.carolsBoutique.report.model.ReportCriteria;
import co.za.carolsBoutique.report.repository.ReportRepositoryImp;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wille
 */
public class TestReportServiceImp {
    ReportServiceImp service;
    ReportRepositoryImp dao;
    public TestReportServiceImp() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        dao = new ReportRepositoryImp();
        service = new  ReportServiceImp(dao);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findTopStoresInTermsOfSales method, of class ReportServiceImp.
     */
    @Test
    public void testFindTopStoresInTermsOfSales() {
        System.out.println("findTopStoresInTermsOfSales");
        ReportCriteria rc = null;
        ReportServiceImp instance = null;
        List<Report> expResult = null;
        List<Report> result = instance.findTopStoresInTermsOfSales(rc);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findHighestRatedStores method, of class ReportServiceImp.
     */
    @Test
    public void testFindHighestRatedStores() {
        System.out.println("findHighestRatedStores");
        ReportCriteria rc = new ReportCriteria(null, null, 6, 2);
        ReportServiceImp instance = null;
        List<Report> expResult = new ArrayList<>();
        expResult.add(new Report("1", 6));
        //List<Report> result = instance.findHighestRatedStores(rc);
        assertEquals(expResult, service.findHighestRatedStores(rc));
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findStoreMonthlySales method, of class ReportServiceImp.
     */
    @Test
    public void testFindStoreMonthlySales() {
        System.out.println("findStoreMonthlySales");
        ReportCriteria rc = null;
        ReportServiceImp instance = null;
        List<Report> expResult = null;
        List<Report> result = instance.findStoreMonthlySales(rc);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findTopSellingEmployees method, of class ReportServiceImp.
     */
    @Test
    public void testFindTopSellingEmployees() {
        System.out.println("findTopSellingEmployees");
        ReportCriteria rc = new ReportCriteria("ao8154bb", null, 6, null);
        ReportServiceImp instance = null;
        List<Report> topEmp = new ArrayList<>();
        topEmp.add(new Report("12werw23", "ao8154bb"));
        List<Report> expResult = topEmp;
        List<Report> result = service.findTopSellingEmployees(rc);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findStoreThatAchievedMonthlyTarget method, of class ReportServiceImp.
     */
    @Test
    public void testFindStoreThatAchievedMonthlyTarget() {
        System.out.println("findStoreThatAchievedMonthlyTarget");
        ReportCriteria rc = new ReportCriteria(null, null, 6, null);
        ReportServiceImp instance = null;
        List<Report> expResult = new ArrayList<>();
        expResult.add(new Report("ao8154bb", 279.86));
        List<Report> result = service.findStoreThatAchievedMonthlyTarget(rc);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findTop40Products method, of class ReportServiceImp.
     */
    @Test
    public void testFindTop40Products() {
        System.out.println("findTop40Products");
        ReportServiceImp instance = null;
        List<Report> expResult = new ArrayList<>();
        //List<Report> result = instance.findTop40Products();
        assertEquals(expResult, service.findTop40Products());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findUnderPerformingStores method, of class ReportServiceImp.
     */
    @Test
    public void testFindUnderPerformingStores() {
        System.out.println("findUnderPerformingStores");
        ReportCriteria rc = new ReportCriteria(null, null, 3, 2);
        ReportServiceImp instance = null;
        List<Report> expResult = new ArrayList<>();
        expResult.add(new Report("1",20.00));
        //List<Report> result = instance.findUnderPerformingStores(rc);
        assertEquals(expResult,service.findUnderPerformingStores(rc));
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findTopSalepersonForAProduct method, of class ReportServiceImp.
     */
    @Test
    public void testFindTopSalepersonForAProduct() {
        System.out.println("findTopSalepersonForAProduct");
        ReportCriteria rc = new ReportCriteria(null, "1234567891", null, null);
        Report expResult = new Report("12werw23", 10);
        assertEquals(expResult, service.findTopSalepersonForAProduct(rc));
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findCurrentDailySales method, of class ReportServiceImp.
     */
    @Test
    public void testFindCurrentDailySales() {
        System.out.println("findCurrentDailySales");
        ReportCriteria rc = new ReportCriteria("ao8154bb", null, null, null);
        Report expResult = new Report("ao8154bb",199.9 );
        //Report result = instance.findCurrentDailySales(rc);
        assertEquals(expResult, service.findCurrentDailySales(rc));
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
