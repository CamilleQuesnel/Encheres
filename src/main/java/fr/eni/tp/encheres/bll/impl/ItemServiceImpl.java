package fr.eni.tp.encheres.bll.impl;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;

import java.util.List;

public class ItemServiceImpl implements ItemService {

    private ArticleVenduDAO articleVenduDAO;

    public ItemServiceImpl(ArticleVenduDAO articleVenduDAO) {
        this.articleVenduDAO = articleVenduDAO;
    }

    @Override
    public List<ArticleVendu> readSales() {// liste des articles vendu
    return null;
    }

    @Override
    public List<ArticleVendu> readPurchases() { //  liste des articles acheté
        return null;
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
