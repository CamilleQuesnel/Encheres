package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TestArticlevenduDAO {

    @Autowired
    private ArticleVenduDAO articleVenduDAO;

    @Test
    void test_findAll() {

        List<ArticleVendu> articleVendus = articleVenduDAO.findAll();

        assertThat(articleVendus).isNotNull();

        System.out.println("Tous les articles : ");
        articleVendus.forEach(article -> {
            System.out.println("Article : " + article.getNomArticle() + ", Categorie ID : " + article.getCategorieArticle().getNoCategorie());
        });
    }

    @Test
    void test_findById() {

        Optional<ArticleVendu> articleVendu = articleVenduDAO.findById(1);

        assertThat(articleVendu).isPresent();

        ArticleVendu article = articleVendu.get();

        System.out.println("Article avec ID 1 : ");
        System.out.println("Nom : " + article.getNomArticle());
        System.out.println("Description : " + article.getDescription());
        System.out.println("Prix : " + article.getMiseAPrix());
        System.out.println("Etat de vente : " + article.getEtatVente());
        System.out.println("Categorie ID : " + article.getCategorieArticle().getNoCategorie());

    }

    @Test

    void test_findByCategorie() {

        //L'id qu'on veut tester

        int idCategorie = 6;

        //Appeler la méthod findByCateorie pour récupérer les articles de la catégorie donnée

        List<ArticleVendu> articleVendus = articleVenduDAO.findByCategorie(idCategorie);

        //Vérifier que la liste d'articles n'est pas vide

        assertThat(articleVendus).isNotNull();
        // Vérifier que chaque article récupéré appartient bien à la catégorie donnée
        for (ArticleVendu article : articleVendus) {
            assertThat(article.getCategorieArticle().getNoCategorie()).isEqualTo(idCategorie);
        }

        // Afficher les articles pour déboguer ou inspecter les résultats
        System.out.println("Articles trouvés pour la catégorie " + idCategorie + ":");
        articleVendus.forEach(article -> System.out.println(article.getNomArticle()));




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

    @Test
    void test_update() {
        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNoArticle(1);

        articleVendu.setNomArticle("Nouvel Article");
        articleVendu.setDescription("Description mise à jour");
        articleVendu.setDateDebutEncheres(LocalDate.of(2025, 1, 1));
        articleVendu.setDateFinEncheres(LocalDate.of(2025, 12, 31));
        articleVendu.setMiseAPrix(100);
        articleVendu.setPrixVente(150);
        articleVendu.setEtatVente("n.c");

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNoUtilisateur(4);
        articleVendu.setUtilisateur(utilisateur);

        Categorie categorie = new Categorie();
        categorie.setNoCategorie(1);
        articleVendu.setCategorieArticle(categorie);

        // call update
        articleVenduDAO.update(articleVendu);

        // verify the update
        ArticleVendu updated = articleVenduDAO.findById(1).orElseThrow();


        assertThat(updated.getNomArticle()).isEqualTo("Nouvel Article");
        assertThat(updated.getDescription()).isEqualTo("Description mise à jour");
        assertThat(updated.getDateDebutEncheres()).isEqualTo(LocalDate.of(2025, 1, 1));
        assertThat(updated.getDateFinEncheres()).isEqualTo(LocalDate.of(2025, 12, 31));
        assertThat(updated.getMiseAPrix()).isEqualTo(100);
        assertThat(updated.getPrixVente()).isEqualTo(150);
        assertThat(updated.getEtatVente()).isEqualTo("n.c");
        assertThat(updated.getNoUtilisateur()).isEqualTo(4);
        assertThat(updated.getNoCategorie()).isEqualTo(1);
    }


    @Test
    void test_delete() {
        int noArticle = 52;
        articleVenduDAO.delete(noArticle);
    }
}
