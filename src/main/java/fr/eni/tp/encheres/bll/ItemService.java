package fr.eni.tp.encheres.bll;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface ItemService{
    public List <ArticleVendu> readSales();
    public List <ArticleVendu> readPurchases();
//    List<ArticleVendu> readSalesOrPurchasesByUser(int idUser,String SalesOrPurchases);
//    List<ArticleVendu> readPurchasesByUser(int idUser); // A voir si readSalesOrPurchasesByUser et une bonne idée
    public ArticleVendu readArticle();
    public ArticleVendu createArticle(ArticleVendu articleVendu);
    public ArticleVendu updateArticle(ArticleVendu articleVendu);
    public ArticleVendu deleteArticle(ArticleVendu articleVendu);
    List <Categorie> readCategories();


}
