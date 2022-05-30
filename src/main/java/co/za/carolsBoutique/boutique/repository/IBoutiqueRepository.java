package co.za.carolsBoutique.boutique.repository;

import co.za.carolsBoutique.boutique.model.Boutique;
import java.util.ArrayList;

public interface IBoutiqueRepository {
    Boutique findBoutique(String boutiqueId);
    ArrayList<Boutique> findAllBoutiques();
    boolean addBoutique(Boutique boutique);
    boolean updateBoutique(String boutiqueId, Double dailyTarget);
    boolean updateBoutique(String boutiqueid, String password);
    boolean deleteBoutique(String boutiqueId);
}
