package fr.eni.tp.encheres.bo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Enchere {
    private LocalDate dateEnchere;
    private int montant_enchere;
    private String etat_achat;
    private Utilisateur utilisateur;
    private ArticleVendu articleVendu;


    public Enchere() {
    }

    public Enchere(LocalDate dateEnchere, int montant_enchere, String etat_achat, Utilisateur utilisateur, ArticleVendu articleVendu) {
        this.dateEnchere = dateEnchere;
        this.montant_enchere = montant_enchere;
        this.etat_achat = etat_achat;
        this.utilisateur = utilisateur;
        this.articleVendu = articleVendu;
    }

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public int getMontant_enchere() {
        return montant_enchere;
    }

    public void setMontant_enchere(int montant_enchere) {
        this.montant_enchere = montant_enchere;
    }

    public String getEtat_achat() {
        return etat_achat;
    }

    public void setEtat_achat(String etat_achat) {
        this.etat_achat = etat_achat;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ArticleVendu getArticleVendu() {
        return articleVendu;
    }

    public void setArticleVendu(ArticleVendu articleVendu) {
        this.articleVendu = articleVendu;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;
        Enchere enchere = (Enchere) o;
        return montant_enchere == enchere.montant_enchere && Objects.equals(dateEnchere, enchere.dateEnchere) && Objects.equals(etat_achat, enchere.etat_achat) && Objects.equals(utilisateur, enchere.utilisateur) && Objects.equals(articleVendu, enchere.articleVendu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateEnchere, montant_enchere, etat_achat, utilisateur, articleVendu);
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "dateEnchere=" + dateEnchere +
                ", montant_enchere=" + montant_enchere +
                ", etat_achat='" + etat_achat + '\'' +
                ", utilisateur=" + utilisateur +
                ", articleVendu=" + articleVendu +
                '}';
    }
}
