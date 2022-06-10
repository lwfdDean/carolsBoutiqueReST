package co.za.carolsBoutique.ReserveProduct.controller;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.ReserveProduct.repository.ReservedproductRepositoryImp;
import co.za.carolsBoutique.ReserveProduct.service.IServiceReservedproduct;
import co.za.carolsBoutique.ReserveProduct.service.ReservedproductIdGenerator;
import co.za.carolsBoutique.ReserveProduct.service.ReservedproductServiceImp;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/KeepAside")
public class ReservedproductResource {
//     Reservedproduct findReserveProduct(String reserveProductid);
//    String makeReserveProduct(Reservedproduct reserveProduct);
//    String removeReserveProduct(String reserveProductid);
     private IServiceReservedproduct service;

    public ReservedproductResource() {
        service = new ReservedproductServiceImp(new ReservedproductRepositoryImp(),new ReservedproductIdGenerator());
    }
    
    @DELETE
    @Path("/deleteProduct/{email}")////
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReservedproduct(@PathParam("email") String email){
        return Response.status(Response.Status.OK).entity(service.removeReserveProduct(email)).build();
    }
    
    @POST
    @Path("/addKeepAside")////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Reservedproduct reservedproduct){
        return Response.status(Response.Status.OK).entity(service.makeReserveProduct(reservedproduct)).build();
    }
    
    @GET
    @Path("/findKeepAside/{email}")///
    @Produces(MediaType.APPLICATION_JSON)
    public Response findKeepAside(@PathParam("email")String customerEmail){
        return Response.status(Response.Status.OK).entity(service.collectKeepAside(customerEmail)).build();
    }
}
