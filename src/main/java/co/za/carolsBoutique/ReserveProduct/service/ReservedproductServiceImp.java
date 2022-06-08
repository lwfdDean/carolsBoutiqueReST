/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.ReserveProduct.service;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.product.model.Product;
import java.util.Map;
import co.za.carolsBoutique.ReserveProduct.repository.IReservedproductRepository;

public class ReservedproductServiceImp implements IServiceReservedproduct{
     private IReservedproductRepository dao;
    private CodeGenerator gen;

    public ReservedproductServiceImp(IReservedproductRepository dao, CodeGenerator gen) {
        this.dao = dao;
        this.gen = gen;
    }

    @Override
    public String makeReserveProduct(Reservedproduct reserveProduct) {
        String[] productInfo = reserveProduct.getProductCode().split(" ");
        Map<String,Integer> entry = dao.findStockEntry(productInfo[0], reserveProduct.getBoutiqueId(), productInfo[1]);
        String id = entry.keySet().iterator().next();
        return dao.addReserveProduct(reserveProduct,id,entry.get(id))?"product reserved":"error occured";
    }

    @Override
    public String removeReserveProduct(String reserveProductid) {
        return dao.deleteReserveProduct(reserveProductid)?"Deteting item successful":"Deletion failed";
    }
    
    @Override
    public Product collectKeepAside(String customerEmail) {
        System.out.println("beginning of service");
        String stockId = dao.findReserveProduct(customerEmail);
        Map<String,String> productInfo = dao.addStock(stockId); //this line returns null
        String productId = productInfo.keySet().iterator().next();
        String size = productInfo.get(productId);
        Product test = dao.findProductByProductCode(productId, size);
        System.out.println(test.getCategories().get(0));
        return dao.findProductByProductCode(productId, size);
    }//index out of bounds thrown
    
    
}
