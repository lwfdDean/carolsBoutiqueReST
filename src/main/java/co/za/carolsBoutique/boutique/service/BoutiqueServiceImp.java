package co.za.carolsBoutique.boutique.service;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.repository.BoutiqueRepositoryImp;
import co.za.carolsBoutique.boutique.repository.IBoutiqueRepository;
import java.util.List;
import java.util.Map;

public class BoutiqueServiceImp implements IServiceBoutique{
    private IBoutiqueRepository dao;

    public BoutiqueServiceImp(IBoutiqueRepository dao) {
        this.dao = new BoutiqueRepositoryImp();
    }
    
    @Override
    public List<Boutique> getAllBoutiques() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String registerNewBoutique(Boutique boutique) {
        return "";
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
    public String changePassword(Map<String, String> paswordDetails) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String changeDailyTarget(Map<String, Double> newTarget) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
