package co.za.carolsBoutique.product.controller;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.repository.ProductRepositoryImp;
import co.za.carolsBoutique.product.service.IServiceProduct;
import co.za.carolsBoutique.product.service.ProductIdGenerator;
import co.za.carolsBoutique.product.service.ProductServiceImp;
import java.util.ArrayList;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Product")
public class ProductResource {
    private IServiceProduct service;
    public ProductResource(){
        this.service = new ProductServiceImp(new ProductRepositoryImp(),new ProductIdGenerator());
    }
   
    @GET
    @Path("/findAllProducts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllProducts(){
        return Response.status(Response.Status.OK).entity(service.findAllProducts()).build();
    }
    
    @GET
    @Path("/findProduct")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findProduct(String productId){
        return Response.status(Response.Status.OK).entity(service.findProduct(productId)).build();
    }
     //deleteProduct
    @POST
    @Path("/addProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Product newProduct){
        return Response.status(Response.Status.OK).entity(service.addProduct(newProduct)).build();
    }
    
    @DELETE
    @Path("/deleteProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(String productId){
        return Response.status(Response.Status.OK).entity(service.deleteProduct(productId)).build();
    }
    
    //findAllCategories
    @POST
    @Path("/updateProductPrice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProductPrice(Map<String, Double> productNameNewPrice){
        return Response.status(Response.Status.OK).entity(service.updateProductPrice(productNameNewPrice)).build();
    }
    
    @GET
    @Path("/SerachForItem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response SerachForItem(ArrayList<String> categoriesId){
        return Response.status(Response.Status.OK).entity(service.SerachForItem(categoriesId)).build();
    }
    
    @GET
    @Path("/findAllCategories")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllCategories(){
        return Response.status(Response.Status.OK).entity(service.findAllCategories()).build();
    }
}
