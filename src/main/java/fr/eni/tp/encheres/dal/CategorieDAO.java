package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.Categorie;

import java.util.List;

public interface CategorieDAO {
    void create(String libelle);
    List<Categorie> selectAllCategorie();
    Categorie findCategorieById( int id );
    Categorie findCategorieByLibelle (String libelle);
    boolean isIfCategorieExists(String libelle);
    void updateCategorie(Categorie categorie );
    void deleteCategorie(int id);


}
