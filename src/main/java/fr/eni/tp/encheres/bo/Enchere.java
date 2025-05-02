package fr.eni.tp.encheres.bo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Enchere {
    private LocalDate dateEnchere;
    private int montant_enchere;
    private Utilisateur utilisateur;
    private ArticleVendu articleVendu;


    public Enchere() {
    }

    public Enchere(LocalDate dateEnchere, int montant_enchere, Utilisateur utilisateur, ArticleVendu articleVendu) {
        this.dateEnchere = dateEnchere;
        this.montant_enchere = montant_enchere;
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
    public String toString() {
        final StringBuffer sb = new StringBuffer("Enchere{");
        sb.append("dateEnchere=").append(dateEnchere);
        sb.append(", montant_enchere=").append(montant_enchere);
        sb.append(", utilisateur=").append(utilisateur);
        sb.append(", articleVendu=").append(articleVendu);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enchere enchere = (Enchere) o;
        return montant_enchere == enchere.montant_enchere && Objects.equals(dateEnchere, enchere.dateEnchere) && Objects.equals(utilisateur, enchere.utilisateur) && Objects.equals(articleVendu, enchere.articleVendu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateEnchere, montant_enchere, utilisateur, articleVendu);
    }
}
