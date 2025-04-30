package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {


    // Créer une enchère
    void save(Enchere enchere) ;

    // Récupérer toutes les enchères d'un utilisateur
    List<Enchere> selectByUtilisateur(int noUtilisateur);

    // Récupérer toutes les enchères pour un article
    List<Enchere> selectByArticle(int noArticle);

    // Obtenir la meilleure enchère pour un article
    Enchere selectBestEnchere(int noArticle);
    // Supprimer toutes les enchères d’un article (par exemple si annulé)
    void deleteByArticle(int noArticle);
    // Vérifier si un utilisateur a déjà enchéri sur un article
    boolean hasUserBid(int noUtilisateur, int noArticle);
}
