/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.employee.model.Employee;

/**
 *
 * @author 27609
 */
public interface IServiceSale {
    String createNewSale(Sale sale);
	Sale findSale(String saleId);
	String removeSaleLineItem(String saleId, String productId, Employee manager, String managerUniqueCode);
	String updateTotalSalePrice(String saleId, Double totalPrice);
	String updateSaleLineItem(String oldProductId, String newProductId, String saleId);
}
