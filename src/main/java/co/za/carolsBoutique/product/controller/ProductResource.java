package co.za.carolsBoutique.product.controller;


import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.StockEntry;
import co.za.carolsBoutique.product.repository.ProductRepositoryImp;
import co.za.carolsBoutique.product.service.IServiceProduct;
import co.za.carolsBoutique.product.service.ProductIdGenerator;
import co.za.carolsBoutique.product.service.ProductServiceImp;
import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/product")
public class ProductResource {
    private IServiceProduct service;
    public ProductResource(){
        this.service = new ProductServiceImp(new ProductRepositoryImp());
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
    @Path("/logStock")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Map<Product,StockEntry> stockInfo){
        return Response.status(Response.Status.OK).entity(service.logStock(stockInfo)).build();
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
    @Path("/SearchForItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response SerachForItem(List<String> categoriesId){
        return Response.status(Response.Status.OK).entity(service.SerachForItem(categoriesId)).build();
    }
    
    @GET
    @Path("/findAvailableStock/{productId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAvailableStock(@PathParam("productId")String productId){
        return Response.status(Response.Status.OK).entity(service.findStockOfProduct(productId)).build();
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
