/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.report.service;

import co.za.carolsBoutique.report.model.Report;
import co.za.carolsBoutique.report.model.ReportCriteria;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.util.List;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 27609
 */
public class ReportPdfBuilder {
    String file;
    PdfWriter pdfWriter;
    PdfDocument pdfDoc;
    Document doc; 
    Table table;

    public ReportPdfBuilder(){
        try {
            String file = System.getProperty("user.home")+"\\download.pdf";
            PdfWriter pdfWriter;
            pdfWriter = new PdfWriter(file);
            PdfDocument pdfDoc = new PdfDocument(pdfWriter);
            Document doc = new Document(pdfDoc); 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPdfBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void findTopStoresInTermsOfSales(List<Report> reports){
        float coulmnWidth[] = {100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Boutique"));
        table.addCell(new Cell().add("Ammont of sales"));
        for (int i = 0; i < reports.size(); i++) {
            table.addCell(new Cell().add(reports.get(i).getStore()));
            table.addCell(new Cell().add(reports.get(i).getTotal().toString()));
            
        }
        doc.add(table);
        doc.close();
        System.out.println("Table created successfully..");
    }
    public void findHighestRatedStores(List<Report> reports){
        float coulmnWidth[] = {100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Boutique"));
        table.addCell(new Cell().add("Ragting"));
        for (int i = 0; i < reports.size(); i++) {
            table.addCell(new Cell().add(reports.get(i).getStore()));
            table.addCell(new Cell().add(reports.get(i).getRating().toString()));
            
        }
        doc.add(table);
        doc.close();
        System.out.println("Table created successfully..");
    }
    public void findStoreMonthlySales(List<Report> reports){
        float coulmnWidth[] = {100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Boutique"));
        table.addCell(new Cell().add("Monthly sales"));
        for (int i = 0; i < reports.size(); i++) {
            table.addCell(new Cell().add(reports.get(i).getStore()));
            table.addCell(new Cell().add(reports.get(i).getTotal().toString()));
            
        }
        doc.add(table);
        doc.close();
        System.out.println("Table created successfully..");
    }
    public void findTopSellingEmployees(List<Report> reports){
        float coulmnWidth[] = {100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Employee"));
        table.addCell(new Cell().add("Total sales"));
        for (int i = 0; i < reports.size(); i++) {
            table.addCell(new Cell().add(reports.get(i).getId()));
            table.addCell(new Cell().add(reports.get(i).getTotal().toString()));
            
        }
        doc.add(table);
        doc.close();
        System.out.println("Table created successfully..");
    }
    public void findStoreThatAchievedMonthlyTarget(List<Report> reports){
        float coulmnWidth[] = {100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Boutique"));
        table.addCell(new Cell().add("Total sales"));
        for (int i = 0; i < reports.size(); i++) {
            table.addCell(new Cell().add(reports.get(i).getId()));
            table.addCell(new Cell().add(reports.get(i).getTotal().toString()));
        }
        doc.add(table);
        doc.close();
        System.out.println("Table created successfully..");
    }
    public void findTop40Products(List<Report> reports){
        //new Report(product,boutique,totalNumberOfSales)
        float coulmnWidth[] = {100f, 100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Product"));
        table.addCell(new Cell().add("Boutique"));
        table.addCell(new Cell().add("Total Number Of Sales"));
        for (int i = 0; i < reports.size(); i++) {
            table.addCell(new Cell().add(reports.get(i).getId()));
            table.addCell(new Cell().add(reports.get(i).getStore()));
            table.addCell(new Cell().add(reports.get(i).getAmount().toString()));
        }
        doc.add(table);
        doc.close();
        System.out.println("Table created successfully..");
    }
    public void findUnderPerformingStores(List<Report> reports){
        float coulmnWidth[] = {100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Boutique"));
        table.addCell(new Cell().add("Total sales"));
        for (int i = 0; i < reports.size(); i++) {
            table.addCell(new Cell().add(reports.get(i).getId()));
            table.addCell(new Cell().add(reports.get(i).getTotal().toString()));
        }
        doc.add(table);
        doc.close();
        System.out.println("Table created successfully..");
    }
    public void findTopSalepersonForAProduct(Report report){
        float coulmnWidth[] = {100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Top Employee"));
        table.addCell(new Cell().add("Total sales"));
        table.addCell(new Cell().add(report.getId()));
        table.addCell(new Cell().add(report.getAmount().toString()));
    }
    public void findCurrentDailySales(Report report){
        //new Report(store, todaysSale)
        float coulmnWidth[] = {100f, 100f};
        table = new Table(coulmnWidth);
        table.addCell(new Cell().add("Boutique"));
        table.addCell(new Cell().add("Total sales"));
        table.addCell(new Cell().add(report.getId()));
        table.addCell(new Cell().add(report.getAmount().toString()));
    }
    
}
