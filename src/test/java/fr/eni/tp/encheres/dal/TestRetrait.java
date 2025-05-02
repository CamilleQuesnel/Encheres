package fr.eni.tp.encheres.dal;


import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Retrait;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestRetrait {

    @Autowired
    private RetraitDAO retraitDAO;
    @Autowired
    private ArticleVenduDAO articleVenduDAO;

    @Test
    void test_selectAllRetrait() {
        List<Retrait> retraits = retraitDAO.selectAllRetrait();
        System.out.println(retraits);
    }
    @Test
    void test_create(){
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu = articleVenduDAO.findById(5);
        articleVendu.setNoArticle(20);
        Retrait retrait = new Retrait("2 rue de la ville","99999","TEST VILLE",articleVendu);
        retraitDAO.create(retrait);
    }
    @Test
    void test_findRetraitById(){
        Retrait retrait = retraitDAO.findRetraitById(1);
        System.out.println(retrait);
    }
    @Test
    void test_findRetraitByCodePostal(){
        List<Retrait> retraits  = retraitDAO.findRetraitByCodePostal("54000");
        System.out.println(retraits);
    }
    @Test
    void test_findRetraitByCodeville(){
        List<Retrait> retraits  = retraitDAO.findRetraitByVille("Nancy");
        System.out.println(retraits);
    }

    @Test
    void test_isIfRetraitExists() {
        boolean isFind = retraitDAO.isIfRetraitExists(1);
        Assertions.assertTrue(isFind);
        boolean isNotFind = retraitDAO.isIfRetraitExists(100);
        Assertions.assertFalse(isNotFind);
    }
    @Test
    void test_updateRetraits() {
        Retrait retrait = retraitDAO.findRetraitById(2);//5 avenue Jean Jaurès
        retrait.setRue("TEST");
        retraitDAO.updateRetrait(retrait);
        Assertions.assertEquals("TEST", retraitDAO.findRetraitById(2).getRue());
        retrait.setRue("5 avenue Jean Jaurès"); // Remise en etats de la BDD
        retraitDAO.updateRetrait(retrait);
        Assertions.assertEquals("5 avenue Jean Jaurès", retraitDAO.findRetraitById(2).getRue());
    }

}
