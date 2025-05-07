package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.ArticleVendu;

import java.util.List;

public interface ArticleVenduDAO {
    ArticleVendu findById(int id);
    List<ArticleVendu> findAll();
    List<ArticleVendu> findByCategorie(int idCategorie);
    ArticleVendu insert(ArticleVendu article);
    void update(ArticleVendu article);
    void delete(int id);

    /*AJOUT SEB A VALIDER PAR LA CHEF */
    List<ArticleVendu> findByUserByEtat(String etat , Integer idUser); // Integer permet un null donc posibilité de rien envoyer
    /*FIN AJOUT SEB A VALIDER PAR LA CHEF */






}
