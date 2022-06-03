package co.za.carolsBoutique.ReserveProduct.controller;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.ReserveProduct.repository.ReservedproductRepositoryImp;
import co.za.carolsBoutique.ReserveProduct.service.IServiceReservedproduct;
import co.za.carolsBoutique.ReserveProduct.service.ReservedproductIdGenerator;
import co.za.carolsBoutique.ReserveProduct.service.ReservedproductServiceImp;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    @Path("/deleteProduct/{reservedproductId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReservedproduct(@PathParam("reservedproductId") String reservedproductId){
        return Response.status(Response.Status.OK).entity(service.removeReserveProduct(reservedproductId)).build();
    }
    
    @POST
    @Path("/addKeepAside")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Reservedproduct reservedproduct){
        return Response.status(Response.Status.OK).entity(service.makeReserveProduct(reservedproduct)).build();
    }
    
    @GET
    @Path("/findKeepAside/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findKeepAside(@PathParam("email")String customerEmail){
        return Response.status(Response.Status.OK).entity(service.collectKeepAside(customerEmail)).build();
    }
}
