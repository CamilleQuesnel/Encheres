package fr.eni.tp.encheres.bll.impl;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
import fr.eni.tp.encheres.dal.EnchereDAO;

import java.util.List;

public class ItemServiceImpl implements ItemService {

    private ArticleVenduDAO articleVenduDAO;
    private EnchereDAO enchereDAO;

    public ItemServiceImpl(ArticleVenduDAO articleVenduDAO, EnchereDAO enchereDAO) {
        this.articleVenduDAO = articleVenduDAO;
        this.enchereDAO = enchereDAO;
    }

    @Override
    public List<ArticleVendu> readSales() {// liste des articles vendu dans ARTICLES_VENDUS ('n.c','en cours','vendu','annulé')
    return this.articleVenduDAO.findByUserByEtat("vendu",null);
    }

    @Override
    public List<Enchere> readPurchases() { //  liste des articles acheté dans encheres ('remportée','perdue','en cours','annulée'))
        // TODO A mettre dans encheres ?
        return this.enchereDAO.findByUserByEtat("vendu",null);
    }


    public List<ArticleVendu> readSalesOrPurchasesByUser(int idUser, String SalesOrPurchases) { // liste des vente ou acaht de l'utilisateur
        return List.of();
    }

    @Override
    public ArticleVendu readArticle() { // un article
        return null;
    }

    @Override
    public ArticleVendu createArticle(ArticleVendu articleVendu) { // creer
        return null;
    }

    @Override
    public ArticleVendu updateArticle(ArticleVendu articleVendu) {
        return null;
    }

    @Override
    public ArticleVendu deleteArticle(ArticleVendu articleVendu) {
        return null;
    }

    @Override
    public List<Categorie> readCategories() { // retourne la liste des categories
        return List.of();
    }
}
