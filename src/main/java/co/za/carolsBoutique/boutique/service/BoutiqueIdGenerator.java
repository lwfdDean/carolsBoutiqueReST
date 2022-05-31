package co.za.carolsBoutique.boutique.service;

import co.za.carolsBoutique.codeGenerator.CodeGenerator;

public class BoutiqueIdGenerator implements CodeGenerator{

    @Override
    public String generateId(String mainSource, String subSource, boolean alphaNumeric) {
        return "";
    }

    @Override
    public String generateId(String source, boolean alphaNumeric) {
        return  "a";
    }
    
}
