package co.za.carolsBoutique.boutique.repository;

import co.za.carolsBoutique.boutique.model.Boutique;
import java.util.List;

public interface IBoutiqueRepository {
    Boutique findBoutique(String boutiqueId);
    List<Boutique> findAllBoutiques();
    boolean addBoutique(Boutique boutique);
    boolean deleteBoutique(String boutiqueId);
    boolean updateBoutique(Boutique boutique);
    public boolean subscribeToNewsletter(String contactInfo);

    public boolean addReview(int rating, String comment, String boutique);
}
