/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.Sale.service;

import co.za.carolsBoutique.paymentGateway.PaymentGateway;
import co.za.carolsBoutique.Sale.model.Sale;
import co.za.carolsBoutique.Sale.repository.ISaleRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.employee.model.Employee;

/**
 *
 * @author wille
 */
public class SaleServiceImp implements IServiceSale{
	private ISaleRepository dao;
	private CodeGenerator gen;
        private PaymentGateway pg;

	public SaleServiceImp(ISaleRepository dao, CodeGenerator gen, PaymentGateway pg) {
		this.dao = dao;
		this.gen = gen;
                this.pg = pg;
	}
	
	@Override//generating ID
	public String checkout(Sale sale) {
            sale.setId(gen.generateId(sale.getBoutique(), true));
            sale.setApproved(pg.makePayment(sale));
            return dao.addSale(sale)?"accepted":"declined";
	}

	@Override
	public Sale findSale(String saleId) {
		return dao.findSale(saleId);
	}
	
	private boolean verfyManagersUniqueCode(Employee manager, String managerUniqueCode){
		if(manager.getManagerCode().equals(managerUniqueCode)){
			return true;
		}
		return false;
	}
	
	@Override
	public String updateTotalSalePrice(String saleId, Double totalPrice) {
		return dao.updateSale(saleId, totalPrice)?"Sale total price has been updated":"Sale total price could not be updated";
	}

	@Override
	public String updateSaleLineItem(String oldProductId, String newProductId, String saleId) {
		return dao.updateSaleLineItem(oldProductId, newProductId, saleId)?"The sale line item has been updated":"The sale line item could not be updated";
	}
	
}
