package co.za.carolsBoutique.boutique.service;

import co.za.carolsBoutique.boutique.model.Boutique;
import co.za.carolsBoutique.boutique.model.Review;
import java.util.List;

public interface IServiceBoutique {
    List<Boutique> getAllBoutiques();
    String registerNewBoutique(Boutique boutique);
    String updateBoutique(Boutique boutique);
    Boutique findBoutique(String boutiqueId);
    String rateTheBoutique(Review review);
}
