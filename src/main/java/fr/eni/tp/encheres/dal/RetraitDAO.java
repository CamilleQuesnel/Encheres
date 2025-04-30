package fr.eni.tp.encheres.dal;



import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Retrait;

import java.util.List;

public interface RetraitDAO {

    void create(String libelle);
    List<Retrait> selectAllRetrait();
    Retrait findRetraitById( int id );
    List<Retrait> findRetraitByCodePostal (String CP);
    List<Retrait> findRetraitByVille (String ville);
    boolean isIfRetraitExists(String id);
    void updateRetrait(Retrait retrait);


}
