package fr.eni.tp.encheres.dal;


import fr.eni.tp.encheres.bo.Retrait;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestRetrait {

    @Autowired
    private RetraitDAO retraitDAO;

    @Test
    void test_selectAllRetrait() {
        List<Retrait> retraits = retraitDAO.selectAllRetrait();
        System.out.println(retraits);
    }



}
