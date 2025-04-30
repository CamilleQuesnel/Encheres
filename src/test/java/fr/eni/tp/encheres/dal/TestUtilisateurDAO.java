package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestUtilisateurDAO {
@Autowired
    private UtilisateurDAO utilisateurDAO;

@Test
    void test_findUsers() {//ça fonctionne
    List<Utilisateur> users = utilisateurDAO.findUsers();
    System.out.println(users);
}

@Test
    void test_findUsersBuyers() {//ça fonctionne
    List<Utilisateur> buyers = utilisateurDAO.findUsersBuyers();
    System.out.println(buyers);
}

@Test
void test_createUser() {//ça fonctionne
    Utilisateur utilisateur = new Utilisateur();
    utilisateur.setPseudo("Doudou");
    utilisateur.setNom("Dodo");
    utilisateur.setPrenom("Kouros");
    utilisateur.setMotDePasse("pa$$w0rd");
    utilisateur.setRue("8, rue de l'île de Batz 29000 Quimper");
    utilisateur.setCodePostal("29000");
    utilisateur.setVille("Quimper");
    utilisateur.setTelephone("0621166808");
    utilisateur.setEmail("kdoudou@email.com");

    utilisateurDAO.createUser(utilisateur);
}

@Test
    void test_updateUser(){//ça fonctionne
    Utilisateur utilisateur = new Utilisateur();
    utilisateur.setNoUtilisateur(1);
    utilisateur.setActif(false);
    utilisateur.setAdministrateur(true);
    utilisateur.setPseudo("Bouli");
    utilisateur.setMotDePasse("Pa$$w0rd2");
    utilisateur.setCredit(150);
    utilisateur.setEmail("bouli@email.fr");
    utilisateur.setTelephone("0621166808");
    utilisateur.setVille("Herm");
    utilisateur.setCodePostal("40990");
    utilisateur.setRue("180, rue des Bruyères");
    utilisateur.setPrenom("Bouli");
    utilisateur.setNom("Bis");

    utilisateurDAO.updateUser(utilisateur);
}

    @Test
    void test_delete() {//ça fonctionne
        int no_utilisateur = 1;
        utilisateurDAO.deleteUser(no_utilisateur);
    }
}
