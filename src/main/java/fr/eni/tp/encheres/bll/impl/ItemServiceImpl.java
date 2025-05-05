package fr.eni.tp.encheres.bll.impl;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
import fr.eni.tp.encheres.dal.CategorieDAO;
import fr.eni.tp.encheres.dal.EnchereDAO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {

    private ArticleVenduDAO articleVenduDAO;
    private EnchereDAO enchereDAO;
    private CategorieDAO categorieDAO;

    public ItemServiceImpl(ArticleVenduDAO articleVenduDAO, EnchereDAO enchereDAO, CategorieDAO categorieDAO) {
        this.articleVenduDAO = articleVenduDAO;
        this.enchereDAO = enchereDAO;
        this.categorieDAO = categorieDAO;
    }

    @Override
    public List<ArticleVendu> readSales() {// liste des articles vendu dans ARTICLES_VENDUS ('n.c','en cours','vendu','annulé')
        return this.articleVenduDAO.findByUserByEtat("vendu", null);
    }

    @Override
    public List<Enchere> readPurchases() { //  liste des articles acheté dans encheres ('remportée','perdue','en cours','annulée'))
        return this.enchereDAO.findByUserByEtat("vendu", null);
    }


    @Override
    public ArticleVendu readArticle(int id) { // un article
        return articleVenduDAO.findById(id);
    }

    @Override
    public List<Categorie> readCategories() { // retourne la liste des categories
        return categorieDAO.selectAllCategorie();
    }

    @Override
    public void createArticle(ArticleVendu articleVendu) { // creer

        // TEST TODO


        articleVenduDAO.insert(articleVendu);
    }

    @Override
    public void updateArticle(ArticleVendu articleVendu) {

        // TEST TODO
        articleVenduDAO.update(articleVendu);
    }


    @Override
    public void deleteArticle(int id) {

        //TEST TODO
        articleVenduDAO.delete(id);
    }
}
