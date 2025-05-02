package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.Categorie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

@SpringBootTest
public class TestCatagoriesimpl {

    @Autowired
    private CategorieDAO categorieDAO;


    @Test
    void test_selectAllCategorie() {
        List<Categorie> categories = categorieDAO.selectAllCategorie();
        System.out.println(categories);
    }

    @Test
    void test_findCategorieById() {
        Categorie categorie = categorieDAO.findCategorieById(2);//
        Assertions.assertEquals("Jouets", categorie.getLibelle());

    }

    @Test
    void test_findCategorieByLibelle() {
        Categorie categorie = categorieDAO.findCategorieByLibelle("Jouets");//
        Assertions.assertEquals("Jouets", categorie.getLibelle());
    }

    @Test
    void test_create() {
        String libelle = "TEST A EFFACER";
        categorieDAO.create(libelle);
        // test recuperation de l'id
        Categorie categorie = categorieDAO.findCategorieById(18);
        System.out.println("************* test_create ************");
        System.out.println(categorie);
        System.out.println("************* test_create ************");

    }

    @Test
    void test_isIfCategorieExists() {
        boolean isFind = categorieDAO.isIfCategorieExists("Sport");
        Assertions.assertTrue(isFind);
        boolean isNotFind = categorieDAO.isIfCategorieExists("Azerty");
        Assertions.assertFalse(isNotFind);

    }

    @Test
    void test_updateCategorie() {
        Categorie categorie = categorieDAO.findCategorieById(2);//Jouets
        categorie.setLibelle("TEST");
        categorieDAO.updateCategorie(categorie);
        Assertions.assertEquals("TEST", categorieDAO.findCategorieByLibelle("TEST").getLibelle());
        categorie.setLibelle("Jouets"); // Remise en etats de la BDD
        categorieDAO.updateCategorie(categorie);
        Assertions.assertEquals("Jouets", categorieDAO.findCategorieByLibelle("Jouets").getLibelle());

    }

    @Test
    void test_deleteCategorie() {
        categorieDAO.deleteCategorie(2);
        Assertions.assertThrows(
                EmptyResultDataAccessException.class, // Type d'exception attendu
                () -> categorieDAO.findCategorieById(2) // Code qui doit échouer
        );

    }
}
