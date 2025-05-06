package fr.eni.tp.encheres.bll.impl;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
import fr.eni.tp.encheres.dal.CategorieDAO;
import fr.eni.tp.encheres.dal.EnchereDAO;
import fr.eni.tp.encheres.dal.UtilisateurDAO;
import fr.eni.tp.encheres.dto.NewSaleDTO;
import fr.eni.tp.encheres.exception.BusinessCode;
import fr.eni.tp.encheres.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {

    private ArticleVenduDAO articleVenduDAO;
    private EnchereDAO enchereDAO;
    private CategorieDAO categorieDAO;
    private UtilisateurDAO utilisateurDAO;

    public ItemServiceImpl(ArticleVenduDAO articleVenduDAO, EnchereDAO enchereDAO, CategorieDAO categorieDAO, UtilisateurDAO utilisateurDAO) {
        this.articleVenduDAO = articleVenduDAO;
        this.enchereDAO = enchereDAO;
        this.categorieDAO = categorieDAO;
        this.utilisateurDAO = utilisateurDAO;
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
    public void createArticle(NewSaleDTO newSaleDTO) { // creer
        boolean isValid = true;
        BusinessException businessException = new BusinessException();
    //VALIDATION ARTICLE
        /**
         * CREATE TABLE ARTICLES_VENDUS (
         *     no_article                    INT IDENTITY(1,1) NOT NULL,
         *     nom_article                   VARCHAR(30) NOT NULL,
         *     description                   VARCHAR(300) NOT NULL,
         * 	date_debut_encheres           DATE NOT NULL,
         *     date_fin_encheres             DATE NOT NULL,
         *     prix_initial                  INT,
         *     prix_vente                    INT,
         *     no_utilisateur                INT NOT NULL,
         *     no_categorie                  INT NOT NULL ,
         * 	etat_vente					  VARCHAR (20) CHECK (etat_vente IN ('n.c','en cours','vendu','annulé')),
         * 	url_image
         */

        isValid  = isNomArticleValid(newSaleDTO.getArticle(),businessException);
        isValid &= isDescritionValid(newSaleDTO.getDescription(),businessException);
        isValid &= isDateEnchereValid(newSaleDTO.getDebut(), newSaleDTO.getFin(),businessException);
        isValid &= isPrixValid(newSaleDTO.getPrix(),businessException);
        isValid &= isNoCategorieValid(newSaleDTO.getCategorie(),businessException);

        if(isValid){
            // TODO CREATION ARTICLES
//            ArticleVendu articleVendu = new ArticleVendu();
//            articleVendu.setNomArticle();
        }else{

        }



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
    /*****
     * FONCTION DE VALIDATION CREATION ARTICLE
     */
    public boolean isNomArticleValid(String nomArticle ,BusinessException businessException){
        boolean isValid = true;
        if(nomArticle == null || nomArticle.isBlank() ) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_NOM_ARTICLE_BLANK);
        }else if (nomArticle.length() >30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_NOM_ARTICLE_LENGTH);
        }

        return  isValid;
    }
    public boolean isDescritionValid(String description ,BusinessException businessException){
        boolean isValid = true;
        if(description == null || description.isBlank() ) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_DESCRIPTION_BLANK);
        }else if (description.length() >300) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_DESCRIPTION_LENGTH);
        }

        return  isValid;
    }
    public boolean isDateEnchereValid(String debut, String fin,BusinessException businessException){
        boolean isValid = true;
        if (debut == null || debut.isBlank()) {
            businessException.addKey(BusinessCode.VALID_DATE_DEBUT_BLANK);
            isValid = false;
        }
        if (fin == null || fin.isBlank()) {
            businessException.addKey(BusinessCode.VALID_DATE_FIN_BLANK);
            isValid = false;
        }

        try {
            LocalDateTime dateDebut = LocalDateTime.parse(debut);
            LocalDateTime dateFin = LocalDateTime.parse(fin);
            LocalDateTime now = LocalDateTime.now();

            if (dateDebut.isBefore(now)) {
                businessException.addKey(BusinessCode.VALID_DATE_DEBUT_SUP_NOW);
                isValid = false;
            }

            if (!dateFin.isAfter(dateDebut)) {
                businessException.addKey(BusinessCode.VALID_DATE_FIN_SUP_DATE_DEBUT);
                isValid = false;
            }

        } catch (DateTimeParseException e) {
            businessException.addKey(BusinessCode.VALID_DATE_FORMAT_INVALID);
            isValid = false;
        }

        return  isValid;
    }
    public boolean isPrixValid(String prix,BusinessException businessException){
        boolean isValid = true;
        if(prix == null || prix.isBlank() ) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_PRIX_BLANK);
        }else if (Integer.parseInt(prix) <0) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_PRIX_SUP_ZERO);
        }

        return  isValid;
    }
    public boolean isNoCategorieValid( String categorie,BusinessException businessException){
        boolean isValid = true;
        if(categorie == null || categorie.isBlank() ) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_NOM_CATEGORIE_BLANK);
        }
        if(categorieDAO.findCategorieById(Integer.parseInt(categorie))==null){
            isValid = false;
            businessException.addKey(BusinessCode.VALID_NOM_CATEGORIE_NOT_EXIST);

        }

        return  isValid;
    }

    ;


}
