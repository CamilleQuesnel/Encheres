package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.ArticleVendu;

import java.util.List;
import java.util.Optional;

public interface ArticleVenduDAO {
    Optional<ArticleVendu> findById(int id);
    List<ArticleVendu> findAll();
    List<ArticleVendu> findByCategorie(int idCategorie);
    void insert(ArticleVendu article);
    void update(ArticleVendu article);
    void delete(int id);

}
