/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.za.carolsBoutique.employee.service;

import co.za.carolsBoutique.codeGenerator.CodeGenerator;

public class EmployeeIdGenerator implements CodeGenerator{

    @Override
    public String generateId(String mainSource, String subSource, boolean alphaNumeric) {
        return "";
    }

    @Override
    public String generateId(String source, boolean alphaNumeric) {
        return  "a";
    }
     
}
