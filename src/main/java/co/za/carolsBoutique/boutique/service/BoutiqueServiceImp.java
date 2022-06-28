package co.za.carolsBoutique.boutique.service;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.model.Review;
import co.za.carolsBoutique.boutique.repository.IBoutiqueRepository;
import co.za.carolsBoutique.codeGenerator.CodeGenerator;
import java.util.List;

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

    @Override
    public String rateTheBoutique(Review review) {
        if (review.getContactInfo()!=null) {
            dao.subscribeToNewsletter(review.getContactInfo());
        }
        return dao.addReview(Integer.parseInt(review.getRating()),review.getComment(),review.getBoutique())?
                "Thank you for rating our store":"an error occured";
    }

    @Override
    public Boutique findBoutique(String boutiqueId) {
        return dao.findBoutique(boutiqueId);
    }

    @Override
    public String updateBoutique(Boutique boutique) {
        return dao.updateBoutique(boutique)?"Successfully updated":"could not update boutique";
    }

}