package co.za.carolsBoutique.Sale.controller;

import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.repository.SaleRepositoryImp;
import co.za.carolsBoutique.Sale.service.IServiceSale;
import co.za.carolsBoutique.Sale.service.SaleIdGenerator;
import co.za.carolsBoutique.Sale.service.SaleServiceImp;
import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/sale")
public class SaleResource {
    private IServiceSale service;

    public SaleResource() {
        service = new SaleServiceImp(new SaleRepositoryImp(), new SaleIdGenerator(), new PaymentGateway());
    }
     
    @Path("/checkout")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkout(Sale sale){
        return Response.status(Response.Status.OK).entity(service.checkout(sale)).build();
    }
    
    @Path("/findSale/{saleId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkout(@PathParam("saleId")String saleId){
        return Response.status(Response.Status.OK).entity(service.findSale(saleId)).build();
    }
    
    @Path("/refund")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response refund(Map<String,String> refundProduct){
        return Response.status(Response.Status.OK).entity(service.refund(refundProduct)).build();
    }
    
    @Path("/exchange")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response exchange(List<String> exchangeInfo){
        return Response.status(Response.Status.OK).entity(service.exchange(exchangeInfo)).build();
    }
}
