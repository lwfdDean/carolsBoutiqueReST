package co.za.carolsBoutique.report.controller;

import co.za.carolsBoutique.report.model.ReportCriteria;
import co.za.carolsBoutique.report.repository.ReportRepositoryImp;
import co.za.carolsBoutique.report.service.IServiceReport;
import co.za.carolsBoutique.report.service.ReportPdfBuilder;
import co.za.carolsBoutique.report.service.ReportServiceImp;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/report")
public class ReportResource {
    private IServiceReport service;
    private ReportPdfBuilder pdfBuilder;

    public ReportResource() {
        this.service = new ReportServiceImp(new ReportRepositoryImp());
        pdfBuilder = new ReportPdfBuilder();
    }
    
    @POST
    @Path("/findTopStoresInTermsOfSales")////////////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTopStoresInTermsOfSales(ReportCriteria rc){
//        pdfBuilder.findTopStoresInTermsOfSales(service.findTopStoresInTermsOfSales(rc));
        return Response.status(Response.Status.OK).entity(service.findTopStoresInTermsOfSales(rc)).build();
    }
    
    @POST
    @Path("/findHighestRatedStores")//////////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findHighestRatedStores(ReportCriteria rc){
//        pdfBuilder.findHighestRatedStores(service.findHighestRatedStores(rc));
        return Response.status(Response.Status.OK).entity(service.findHighestRatedStores(rc)).build();
    }
    
    @POST
    @Path("/findStoreMonthlySales")////////////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findStoreMonthlySales(ReportCriteria rc){
//        pdfBuilder.findStoreMonthlySales(service.findStoreMonthlySales(rc));
        return Response.status(Response.Status.OK).entity(service.findStoreMonthlySales(rc)).build();
    }
    
    @POST
    @Path("/findTopSellingEmployees")////////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTopSellingEmployees(ReportCriteria rc){
//        pdfBuilder.findTopSellingEmployees(service.findTopSellingEmployees(rc));
        return Response.status(Response.Status.OK).entity(service.findTopSellingEmployees(rc)).build();
    }
    
    @POST
    @Path("/findStoreThatAchievedMonthlyTarget")////////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findStoreThatAchievedMonthlyTarget(ReportCriteria rc){
//        pdfBuilder.findStoreThatAchievedMonthlyTarget(service.findStoreThatAchievedMonthlyTarget(rc));
        return Response.status(Response.Status.OK).entity(service.findStoreThatAchievedMonthlyTarget(rc)).build();
    }
    
    @GET
    @Path("/findTop40Products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTop40Products(){
//        pdfBuilder.findTop40Products(service.findTop40Products());
        return Response.status(Response.Status.OK).entity(service.findTop40Products()).build();
    }
    
    @POST
    @Path("/findUnderPerformingStores")//////////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUnderPerformingStores(ReportCriteria rc){
//        pdfBuilder.findUnderPerformingStores(service.findUnderPerformingStores(rc));
        return Response.status(Response.Status.OK).entity(service.findUnderPerformingStores(rc)).build();
    }
    
    @POST////////////////////////
    @Path("/findTopSalepersonForAProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findTopSalepersonForAProduct(ReportCriteria rc){
//        pdfBuilder.findTopSalepersonForAProduct(service.findTopSalepersonForAProduct(rc));
        return Response.status(Response.Status.OK).entity(service.findTopSalepersonForAProduct(rc)).build();
    }
    
    @POST
    @Path("/findCurrentDailySales")/////////////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCurrentDailySales(ReportCriteria rc){
//        pdfBuilder.findCurrentDailySales(service.findCurrentDailySales(rc));
        return Response.status(Response.Status.OK).entity(service.findCurrentDailySales(rc)).build();
    }
}
