package fr.eni.tp.encheres.bll;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dto.NewSaleDTO;

import java.util.List;

public interface ItemService{
    public List <ArticleVendu> readSales();
    public List <Enchere> readPurchases();
    public ArticleVendu readArticle(int id);
    public void createArticle(NewSaleDTO newSaleDTO);
    public void updateArticle(ArticleVendu articleVendu);
    public void deleteArticle(int id);
    List <Categorie> readCategories();


}
