package co.za.carolsBoutique.boutique.controller;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.model.Review;
import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.boutique.service.BoutiqueIdGenerator;
import co.za.carolsBoutique.boutique.service.BoutiqueServiceImp;
import co.za.carolsBoutique.boutique.service.IServiceBoutique;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/boutique")
public class BoutiqueResource {
    private IServiceBoutique service;

    public BoutiqueResource() {
        service = new BoutiqueServiceImp(new BoutiqueRepositoryImp(),new BoutiqueIdGenerator());
    }
    
    @POST
    @Path("/register")/////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Boutique boutique){
        return Response.status(Response.Status.OK).entity(service.registerNewBoutique(boutique)).build();
    }

    @POST
    @Path("/updateBoutique")/////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBoutique(Boutique boutique){
        return Response.status(Response.Status.OK).entity(service.updateBoutique(boutique)).build();
    }
    
    @GET
    @Path("/getAllBoutiques")/////
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBoutiques(){
        return Response.status(Response.Status.OK).entity(service.getAllBoutiques()).build();
    }

    @Path("/rateUs")////
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rateTheBoutique(Review review){
        return Response.status(Response.Status.OK).entity(service.rateTheBoutique(review)).build();
    }
    
    @GET
    @Path("/findBoutique/{boutiqueId}")/////
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBoutique(@PathParam("boutiqueId")String boutiqueId){
        return Response.status(Response.Status.OK).entity(service.findBoutique(boutiqueId)).build();
    }
}
