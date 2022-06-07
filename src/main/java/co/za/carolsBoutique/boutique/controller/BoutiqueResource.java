package co.za.carolsBoutique.boutique.controller;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.model.Review;
import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.boutique.service.BoutiqueIdGenerator;
import co.za.carolsBoutique.boutique.service.BoutiqueServiceImp;
import co.za.carolsBoutique.boutique.service.IServiceBoutique;
import co.za.carolsBoutique.mailService.MailService;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/boutique")
public class BoutiqueResource {
    private IServiceBoutique service;

    public BoutiqueResource() {
        service = new BoutiqueServiceImp(new BoutiqueRepositoryImp(),new BoutiqueIdGenerator());
    }
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Boutique boutique){
        return Response.status(Response.Status.OK).entity(service.registerNewBoutique(boutique)).build();
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> loginDetails){
        return Response.status(Response.Status.OK).entity(service.login(loginDetails)).build();
    }
    
    @GET
    @Path("/getAllBoutiques")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBoutiques(){
		new MailService("stock", "Hello", "willem0402@gmail.com").runMail();
        return Response.status(Response.Status.OK).entity(service.getAllBoutiques()).build();
    }
    
    @POST
    @Path("/changePassword")
    @Consumes(MediaType.APPLICATION_JSON)	
    @Produces(MediaType.APPLICATION_JSON)
    public Response changePassword(Map<String,String> passwordDetails){
        return Response.status(Response.Status.OK).entity(service.changePassword(passwordDetails)).build();
    }
    
    @POST
    @Path("/changeDailyTarget")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeDailyTarget(Map<String,Double> newTaeget){
        return Response.status(Response.Status.OK).entity(service.changeDailyTarget(newTaeget)).build();
    }
    
    @Path("/rateUs")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rateTheBoutique(Review review){
        return Response.status(Response.Status.OK).entity(service.rateTheBoutique(review)).build();
    }
}
