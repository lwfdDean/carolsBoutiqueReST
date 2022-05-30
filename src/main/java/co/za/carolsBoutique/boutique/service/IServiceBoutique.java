package co.za.carolsBoutique.boutique.service;

import co.za.carolsBoutique.boutique.model.Boutique;
import java.util.List;
import java.util.Map;

public interface IServiceBoutique {
    List<Boutique> getAllBoutiques();
    String registerNewBoutique(Boutique boutique);
    Boutique login(Map<String,String> loginDetails);
    String changePassword(Map<String, String> paswordDetails);
    String changeDailyTarget(Map<String,Double> newTarget);
}
