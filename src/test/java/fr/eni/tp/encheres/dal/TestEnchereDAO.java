package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.EnchereDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class TestEnchereDAO {

    @Autowired
    private EnchereDAO enchereDAO;

    @Test
        void test_findEncheresByUserId() {
        List<Enchere> encheres = enchereDAO.selectByUtilisateur(1);

        System.out.println(encheres);
    }

    @Test
        void test_findEncheresByArticleId() {
        List<Enchere> encheres = enchereDAO.selectByArticle(19);
        System.out.println(encheres);

    }

    @Test
        void testCreateEnchere(){
            Enchere enchere = new Enchere();
            ArticleVendu article = new ArticleVendu();
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(1);
            enchere.setUtilisateur(utilisateur);
            article.setNoArticle(19);
            enchere.setArticleVendu(article);
            enchere.setMontant_enchere(298);
            enchere.setDateEnchere(LocalDateTime.now());

            enchereDAO.save(enchere);
    }

    @Test
    void testUpdateEnchere(){
        Enchere enchere = new Enchere();
        Utilisateur utilisateur = new Utilisateur();
        ArticleVendu article = new ArticleVendu();
        utilisateur.setNoUtilisateur(1);
        enchere.setUtilisateur(utilisateur);
        article.setNoArticle(19);
        enchere.setArticleVendu(article);
        enchere.setDateEnchere(LocalDateTime.of(2025, 5, 2, 14, 53, 52, 404018900));
        enchere.setMontant_enchere(298);
        enchere.setEtat_achat("remportée");

        enchereDAO.updateEnchere(enchere);
    }

    @Test
    void testSelectBestEnchere() {
        ArticleVendu article = new ArticleVendu();
        Enchere enchere = enchereDAO.selectBestEnchere(article.getNoArticle());
        System.out.println(enchere);
    }

    @Test
    void testDeleteEnchere() {
        Utilisateur utilisateur = new Utilisateur();
        ArticleVendu article = new ArticleVendu();
        Enchere enchere = new Enchere();
        utilisateur.setNoUtilisateur(1);
        article.setNoArticle(19);
        enchere.setMontant_enchere(298);
        enchereDAO.deleteEnchere(utilisateur.getNoUtilisateur(),
                article.getNoArticle(), enchere.getMontant_enchere());
    }
}


