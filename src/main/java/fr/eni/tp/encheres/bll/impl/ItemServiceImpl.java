package fr.eni.tp.encheres.bll.impl;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.bo.Retrait;
import fr.eni.tp.encheres.dal.*;
import fr.eni.tp.encheres.dto.NewSaleDTO;
import fr.eni.tp.encheres.exception.BusinessCode;
import fr.eni.tp.encheres.exception.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private RetraitDAO retraitDAO;

    public ItemServiceImpl(ArticleVenduDAO articleVenduDAO, EnchereDAO enchereDAO, CategorieDAO categorieDAO, UtilisateurDAO utilisateurDAO, RetraitDAO retraitDAO) {
        this.articleVenduDAO = articleVenduDAO;
        this.enchereDAO = enchereDAO;
        this.categorieDAO = categorieDAO;
        this.utilisateurDAO = utilisateurDAO;
        this.retraitDAO = retraitDAO;
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
        boolean isArticleValid = true;
        boolean isRetraitValid = true;
        boolean dateSupNow = true;
        BusinessException businessException = new BusinessException();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
    //VALIDATION ARTICLE

        isArticleValid  = isNomArticleValid(newSaleDTO.getArticle(),businessException);
        isArticleValid &= isDescritionValid(newSaleDTO.getDescription(),businessException);
        isArticleValid &= isDateEnchereValid(newSaleDTO.getDebut(), newSaleDTO.getFin(),dateSupNow,businessException);
        isArticleValid &= isPrixValid(newSaleDTO.getPrix(),businessException);
        isArticleValid &= isNoCategorieValid(newSaleDTO.getCategorie(),businessException);

        if(isArticleValid){

            ArticleVendu articleVendu = new ArticleVendu();
            articleVendu.setCategorie(categorieDAO.findCategorieById(newSaleDTO.getCategorie()));
            articleVendu.setNomArticle(newSaleDTO.getArticle());
            articleVendu.setDescription(newSaleDTO.getDescription());
            articleVendu.setDateDebutEncheres(newSaleDTO.getDebut());
            articleVendu.setDateFinEncheres(newSaleDTO.getFin());
            articleVendu.setMiseAPrix(newSaleDTO.getPrix());
            articleVendu.setPrixVente(newSaleDTO.getPrix());

            if (newSaleDTO.getDebut().isAfter(LocalDateTime.now())) {
                articleVendu.setEtatVente("n.c");
            }else{
                articleVendu.setEtatVente("en cours");
            }
            articleVendu.setUtilisateur(utilisateurDAO.readPseudo(username));
            articleVendu.setUrlImage(newSaleDTO.getPhoto());

            ArticleVendu newArticle = articleVenduDAO.insert(articleVendu);

            // CREATION LIEU RETRAIT
            isRetraitValid = isNoArcticleValid(newArticle.getNoArticle(),businessException);
            if(isRetraitValid){
                Retrait retrait = new Retrait();
                retrait.setArticleVendu(newArticle.getNoArticle());
                retrait.setRue(newSaleDTO.getRue());
                retrait.setVille(newSaleDTO.getVille());
                retrait.setCode_postal(newSaleDTO.getCodePostal());
                retraitDAO.create(retrait);
            }
        }else{
            throw businessException;
        }

    }

    private boolean isNoArcticleValid(int noArticle, BusinessException businessException) {
        return  retraitDAO.findRetraitById(noArticle) == null;
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
    public boolean isDateEnchereValid(LocalDateTime debut, LocalDateTime fin,boolean dateSupNow,BusinessException businessException){
        boolean isValid = true;
        if (debut == null ) {
            businessException.addKey(BusinessCode.VALID_DATE_DEBUT_BLANK);
            isValid = false;
        }
        if (fin == null ) {
            businessException.addKey(BusinessCode.VALID_DATE_FIN_BLANK);
            isValid = false;
        }

        try {

            if (debut.isBefore(LocalDate.now().atStartOfDay())) {
                businessException.addKey(BusinessCode.VALID_DATE_DEBUT_SUP_NOW);
                isValid = false;
            }

            if (!fin.isAfter(debut)) {
                businessException.addKey(BusinessCode.VALID_DATE_FIN_SUP_DATE_DEBUT);
                isValid = false;
            }

        } catch (DateTimeParseException e) {
            businessException.addKey(BusinessCode.VALID_DATE_FORMAT_INVALID);
            isValid = false;
        }
        return  isValid;
    }
    public boolean isPrixValid(int prix,BusinessException businessException){
        boolean isValid = true;

            if (prix <0) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_PRIX_SUP_ZERO);
        }
        return  isValid;
    }
    public boolean isNoCategorieValid( int categorie,BusinessException businessException){
        System.out.println(" no categorie isVAlid ? ");
        System.out.println(categorie);
        boolean isValid = true;

        if(categorieDAO.findCategorieById(categorie)==null){
            isValid = false;
            businessException.addKey(BusinessCode.VALID_NOM_CATEGORIE_NOT_EXIST);

        }
        return  isValid;
    };


}
