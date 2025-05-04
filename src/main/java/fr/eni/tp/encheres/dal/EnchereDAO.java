package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {


    // Créer une enchère
    void save(Enchere enchere) ;

    // Récupérer toutes les enchères d'un utilisateur
    List<Enchere> selectByUtilisateur(int noUtilisateur);

    // Récupérer toutes les enchères pour un article
    List<Enchere> selectByArticle(int noArticle);

    // Met à jour les données de l'enchères pour un article
    void updateEnchere(Enchere enchere);

    // Obtenir la meilleure enchère pour un article
    Enchere selectBestEnchere(int noArticle);

    // Supprimer toutes les enchères d’un article (par exemple si annulé)
    void deleteEnchere(int noArticle, int noUtilisateur, int montant_enchere);

    // Récupérer tous les articles achetés par un utilisateur ou liste complete
    public List<Enchere> findByUserByEtat(String etat_achat, Integer idUser);

}
