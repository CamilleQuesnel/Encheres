package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.dal.EnchereDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    }
}


