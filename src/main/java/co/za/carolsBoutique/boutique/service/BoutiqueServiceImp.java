package co.za.carolsBoutique.boutique.service;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.repository.IBoutiqueRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import java.util.List;
import java.util.Map;

public class BoutiqueServiceImp implements IServiceBoutique{
    private IBoutiqueRepository dao;
    private CodeGenerator gen;

    public BoutiqueServiceImp(IBoutiqueRepository dao, CodeGenerator gen) {
        this.dao = dao;
        this.gen = gen;
    }
    
    @Override
    public List<Boutique> getAllBoutiques() {
        return dao.findAllBoutiques();
    }

    @Override
    public String registerNewBoutique(Boutique boutique) {
        boutique.setId(gen.generateId(boutique.getLocation(), true));
        if(dao.findBoutique(boutique.getId())==null){
            if (verifyPassword(boutique.getPassword())) {
                return dao.addBoutique(boutique)?"Boutique added, boutique Id ="+boutique.getId():"Couldnt add boutique";
            }
            return "invalid password";
        }
        return "Boutique already exists";
    }
    
    @Override
    public Boutique login(Map<String, String> loginDetails) {
        String id = loginDetails.keySet().iterator().next();
        Boutique boutique = dao.findBoutique(id);
        if (boutique!=null) {
            if(boutique.getPassword().equals(loginDetails.get(id))){
                return boutique;
            }
        }
        return null;
    }

    @Override
    public String changePassword(Map<String, String> passwordDetails) {
        String id = passwordDetails.keySet().iterator().next();
        if (verifyPassword(passwordDetails.get(id))) {
            return dao.updateBoutique(id,passwordDetails.get(id))?"password Updated":"could not update the password";
        }
        return "invalid password supplied";
    }

    @Override
    public String changeDailyTarget(Map<String, Double> newTarget) {
        String id = newTarget.keySet().iterator().next();
        return dao.updateBoutique(id, newTarget.get(id))?"target updated":"could not update target";
    }
    
    private boolean verifyPassword(String password){
        if (password == null || password.isEmpty() || password.length()!=12) {
            return false;
        }
        int nums = 0;
        int chars = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                nums++;
            }
            if (Character.isLetter(password.charAt(i))) {
                chars++;
            }
        }
        return nums>0 && chars>0;
    }

}