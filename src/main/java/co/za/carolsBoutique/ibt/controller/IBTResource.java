package co.za.carolsBoutique.ibt.controller;

import co.za.carolsBoutique.ibt.model.IBT;
import co.za.carolsBoutique.ibt.repository.IBTRepositoryImp;
import co.za.carolsBoutique.ibt.service.IBTIdGenerator;
import co.za.carolsBoutique.ibt.service.IBTServiceImp;
import co.za.carolsBoutique.ibt.service.IServiceIBT;
import java.util.Map;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ibt")
public class IBTResource {
    private IServiceIBT serviceIBT;
    
    
    public IBTResource() {
        serviceIBT = new IBTServiceImp(new IBTRepositoryImp(),new IBTIdGenerator());
    }
    
    @Path("/requestIBT")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response requestIBT(IBT ibt){
        return Response.status(Response.Status.OK).entity(serviceIBT.requestIBT(ibt)).build();
    }
    
    @Path("/getIBT/{ibtId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIBT(@PathParam("ibtId") String ibtId){
        return Response.status(Response.Status.OK).entity(serviceIBT.getIBT(ibtId)).build();
    }
    
    @Path("/findStoreIBTS/{storeId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findStoreIBTS(@PathParam("storeId") String storeId){
        return Response.status(Response.Status.OK).entity(serviceIBT.findStoreIBTS(storeId)).build();
    }
    
    @Path("/approveIBT")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response approveIBT(Map<String,Boolean> approvedIBT){
        return Response.status(Response.Status.OK).entity(serviceIBT.approveIBT(approvedIBT)).build();
    }
    
}
