package fr.eni.tp.encheres.bo;

import java.util.List;
import java.util.Objects;

public class Categorie {
    private int noCategorie;
    private String libelle;
    private List<ArticleVendu> articleVendus;

    public Categorie() {
    }

    public Categorie(int noCategorie, String libelle, List<ArticleVendu> articleVendus) {
        this.noCategorie = noCategorie;
        this.libelle = libelle;
        this.articleVendus = articleVendus;
    }

    public int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<ArticleVendu> getArticleVendus() {
        return articleVendus;
    }

    public void setArticleVendus(List<ArticleVendu> articleVendus) {
        this.articleVendus = articleVendus;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "noCategorie=" + noCategorie +
                ", libelle='" + libelle + '\'' +
                ", articleVendus=" + articleVendus +
                '}';
    }

    @Override
    public boolean equals(Object o) {


        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return noCategorie == categorie.noCategorie && Objects.equals(libelle, categorie.libelle) && Objects.equals(articleVendus, categorie.articleVendus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noCategorie, libelle, articleVendus);
    }
}
