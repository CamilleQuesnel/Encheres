package fr.eni.tp.encheres.bo;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ArticleVendu {
    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDateTime dateDebutEncheres;
    private LocalDateTime dateFinEncheres;
    private int miseAPrix;
    private int prixVente;
    private String etatVente;
    private Utilisateur utilisateur;
    private Retrait lieuRetrait;
    private List<Enchere> encheres;
    private Categorie categorieArticle;

    public ArticleVendu() {}

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres, int miseAPrix, int prixVente, String etatVente, Utilisateur utilisateur, Retrait lieuRetrait, List<Enchere> encheres, Categorie categorieArticle) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.etatVente = etatVente;
        this.utilisateur = utilisateur;
        this.lieuRetrait = lieuRetrait;
        this.encheres = encheres;
        this.categorieArticle = categorieArticle;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDateTime getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(int miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Retrait getLieuRetrait() {
        return lieuRetrait;
    }

    public void setLieuRetrait(Retrait lieuRetrait) {
        this.lieuRetrait = lieuRetrait;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }

    public Categorie getCategorieArticle() {
        return categorieArticle;
    }

    public void setCategorieArticle(Categorie categorieArticle) {
        this.categorieArticle = categorieArticle;
    }

    // Ajout des getters utilitaires demandés
    public int getNoUtilisateur() {
        return utilisateur != null ? utilisateur.getNoUtilisateur() : 0;
    }

    public int getNoCategorie() {
        return categorieArticle != null ? categorieArticle.getNoCategorie() : 0;
    }

    @Override
    public String toString() {
        return "ArticleVendu{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", miseAPrix=" + miseAPrix +
                ", prixVente=" + prixVente +
                ", etatVente='" + etatVente + '\'' +
                ", utilisateur=" + utilisateur +
                ", lieuRetrait=" + lieuRetrait +
                ", encheres=" + encheres +
                ", categorieArticle=" + categorieArticle +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleVendu)) return false;
        ArticleVendu that = (ArticleVendu) o;
        return noArticle == that.noArticle &&
                miseAPrix == that.miseAPrix &&
                prixVente == that.prixVente &&
                Objects.equals(nomArticle, that.nomArticle) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dateDebutEncheres, that.dateDebutEncheres) &&
                Objects.equals(dateFinEncheres, that.dateFinEncheres) &&
                Objects.equals(etatVente, that.etatVente) &&
                Objects.equals(utilisateur, that.utilisateur) &&
                Objects.equals(lieuRetrait, that.lieuRetrait) &&
                Objects.equals(encheres, that.encheres) &&
                Objects.equals(categorieArticle, that.categorieArticle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noArticle, nomArticle, description, dateDebutEncheres, dateFinEncheres, miseAPrix, prixVente, etatVente, utilisateur, lieuRetrait, encheres, categorieArticle);
    }
}
