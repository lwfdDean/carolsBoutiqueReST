/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.ReserveProduct.service;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.ReserveProduct.repository.ReservedproductRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.product.model.Product;
import java.util.Map;

public class ReservedproductServiceImp implements IServiceReservedproduct{
     private ReservedproductRepository dao;
    private CodeGenerator gen;

    public ReservedproductServiceImp(ReservedproductRepository dao, CodeGenerator gen) {
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
////////Complete FindProductByProductCode for this method
    @Override
    public Product collectKeepAside(String customerEmail) {
        String stockId = dao.findReserveProduct(customerEmail);
        Map<String,String> productInfo = dao.addStock(stockId);
        String productCode = productInfo.keySet().iterator().next() + " " + productInfo.get(productInfo.keySet().iterator().next());
        return new Product();
    }
    
    
}
