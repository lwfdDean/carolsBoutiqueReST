package co.za.carolsBoutique.product.controller;


import co.za.carolsBoutique.product.model.Category;
import co.za.carolsBoutique.product.model.NewProduct;
import co.za.carolsBoutique.product.model.Product;
import co.za.carolsBoutique.product.model.PromoCode;
import co.za.carolsBoutique.product.model.StockEntry;
import co.za.carolsBoutique.product.repository.ProductRepositoryImp;
import co.za.carolsBoutique.product.service.IServiceProduct;
import co.za.carolsBoutique.product.service.ProductServiceImp;
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
    @Path("/findAllProducts")////////////////
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllProducts(){
        return Response.status(Response.Status.OK).entity(service.findAllProducts()).build();
    }
    
    @GET
    @Path("/findProduct/{productId}")//////////////
    @Produces(MediaType.APPLICATION_JSON)
    public Response findProduct(@PathParam("productId")String productId){
        return Response.status(Response.Status.OK).entity(service.findProduct(productId)).build();
    }
     
    @POST
    @Path("/logStock")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response logStock(NewProduct newProduct){
        return Response.status(Response.Status.OK).entity(service.logStock(newProduct)).build();
    }
    
    @DELETE//cant delete the product, the product id will have to be removed from every table.
    @Path("/deleteProduct/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("productId") String productId){
        return Response.status(Response.Status.OK).entity(service.deleteProduct(productId)).build();
    }
    
    @POST
    @Path("/updateProductPrice")///////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putProductOnSale(Map<String, Double> productNameNewPrice){
        return Response.status(Response.Status.OK).entity(service.putProductOnSale(productNameNewPrice)).build();
    }
    
    @POST
    @Path("/SearchForItem")///////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response SerachForItem(List<String> categoriesId){
        return Response.status(Response.Status.OK).entity(service.SerachForItem(categoriesId)).build();
    }
    
    @GET
    @Path("/findAvailableStock/{productId}")//it works but it might change me might need an object, return where stock is, the product, size and store location.
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAvailableStock(@PathParam("productId")String productId){
        return Response.status(Response.Status.OK).entity(service.findStockOfProduct(productId)).build();
    }
    
    @GET
    @Path("/findAllCategories")////////
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllCategories(){
        return Response.status(Response.Status.OK).entity(service.findAllCategories()).build();
    }
    
    @GET
    @Path("/findCategory/{categoryId}")/////////
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCategory(@PathParam("categoryId")String categoryId){
        return Response.status(Response.Status.OK).entity(service.findCategory(categoryId)).build();
   }
   
    @POST
    @Path("/addCategory")////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(Category category){
        return Response.status(Response.Status.OK).entity(service.addCategory(category)).build();
    }
    
    //deleteCategory
    @DELETE
    @Path("/deleteCategory/{categoryId}")//will have to remove all other keys
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("categoryId")String categoryId){
        return Response.status(Response.Status.OK).entity(service.deleteCategory(categoryId)).build();
    }
    
    @POST
    @Path("/addNewPromoCode")//////////
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewPromoCode(PromoCode promocode){
        return Response.status(Response.Status.OK).entity(service.addNewPromoCode(promocode)).build();
    }
    
//    @GET
//    @Path("/findPromoCode/{promocode}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addNewPromoCode(@PathParam("promocode")String promocode){
//        return Response.status(Response.Status.OK).entity(service.findPromoCode(promocode)).build();
//    }
}
