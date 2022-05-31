package co.za.carolsBoutique.product.controller;


import co.za.carolsBoutique.product.model.Category;
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
import javax.ws.rs.PathParam;
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
    @Path("/findProduct/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findProduct(@PathParam("productId")String productId){
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
    @Path("/deleteProduct/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("productId") String productId){
        return Response.status(Response.Status.OK).entity(service.deleteProduct(productId)).build();
    }
    
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
    
    @GET
    @Path("/findCategory/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCategory(@PathParam("categoryId")String categoryId){
        return Response.status(Response.Status.OK).entity(service.findCategory(categoryId)).build();
   }
   
    @POST
    @Path("/addCategory")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(Category category){
        return Response.status(Response.Status.OK).entity(service.addCategory(category)).build();
    }
    
    //deleteCategory
    @POST
    @Path("/deleteCategory/{categoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("categoryId")String categoryId){
        return Response.status(Response.Status.OK).entity(service.deleteCategory(categoryId)).build();
   }
}
