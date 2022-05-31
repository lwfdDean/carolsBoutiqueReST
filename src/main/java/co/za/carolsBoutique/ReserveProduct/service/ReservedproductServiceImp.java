/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.ReserveProduct.service;

import co.za.carolsBoutique.ReserveProduct.model.Reservedproduct;
import co.za.carolsBoutique.ReserveProduct.repository.ReservedproductRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import co.za.carolsBoutique.employee.repository.IEmployeeRepository;

/**
 *
 * @author 27609
 */
public class ReservedproductServiceImp implements IServiceReservedproduct{
     private ReservedproductRepository dao;
    private CodeGenerator gen;

    public ReservedproductServiceImp(ReservedproductRepository dao, CodeGenerator gen) {
        this.dao = dao;
        this.gen = gen;
    }
    @Override
    public Reservedproduct findReserveProduct(String reserveProductid) {
        return dao.findReserveProduct(reserveProductid);
    }

    @Override
    public String makeReserveProduct(Reservedproduct reserveProduct) {
        return dao.addReserveProduct(reserveProduct)?"Reserved item successfully":"Reservation failed";
    }

    @Override
    public String removeReserveProduct(String reserveProductid) {
        return dao.deleteReserveProduct(reserveProductid)?"Deteting item successful":"Deletion failed";
    }
    
}
