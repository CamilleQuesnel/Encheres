package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TestArticlevenduDAO {

    @Autowired
    private ArticleVenduDAO articleVenduDAO;

    @Test
    void test_findAll() {
        System.out.println(articleVenduDAO.findAll());
    }

    @Test
    void test_findById() {
        System.out.println(articleVenduDAO.findById(1));
    }

    @Test void insert() {
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNomArticle("Test article");
        articleVendu.setDescription("Description test");
        articleVendu.setDateDebutEncheres(LocalDate.now());
        articleVendu.setDateFinEncheres(LocalDate.now().plusDays(7));
        articleVendu.setMiseAPrix(100);
        articleVendu.setEtatVente("en cours");

        Utilisateur u = new Utilisateur();
        u.setNoUtilisateur(1); // supposé exister
        articleVendu.setUtilisateur(u);

        Categorie c = new Categorie();
        c.setNoCategorie(1); // supposé exister
        articleVendu.setCategorieArticle(c);

        articleVenduDAO.insert(articleVendu);
        System.out.println("Article inséré avec ID : " + articleVendu.getNoArticle());
    }

//        mapSqlParameterSource.addValue("nom_article", article.getNomArticle());
//        mapSqlParameterSource.addValue("description", article.getDescription());
//        mapSqlParameterSource.addValue("date_debut_encheres",article.getDateDebutEncheres());
//        mapSqlParameterSource.addValue("date_fin_encheres", article.getDateFinEncheres());
//        mapSqlParameterSource.addValue("prix_initial",article.getMiseAPrix());
//        mapSqlParameterSource.addValue("no_utilisateur", article);
//        mapSqlParameterSource.addValue("no_categorie",article);
}
