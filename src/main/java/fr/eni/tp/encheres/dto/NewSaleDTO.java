package fr.eni.tp.encheres.dto;

import java.time.LocalDateTime;

public class NewSaleDTO {
    // objet article
    private int no_utlisateur;
    private String article;
    private int categorie;
    private String description;
    private String photo;
    private int prix;
    private LocalDateTime debut;
    private LocalDateTime fin;
    // objet categorie
    private String rue;
    private String codePostal;
    private String ville;

    public NewSaleDTO() {
    }

    public NewSaleDTO(int no_utlisateur, String article, int categorie, String description, String photo, int prix, LocalDateTime debut, LocalDateTime fin, String rue, String codePostal, String ville) {
        this.no_utlisateur = no_utlisateur;
        this.article = article;
        this.categorie = categorie;
        this.description = description;
        this.photo = photo;
        this.prix = prix;
        this.debut = debut;
        this.fin = fin;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public int getNo_utlisateur() {
        return no_utlisateur;
    }

    public void setNo_utlisateur(int no_utlisateur) {
        this.no_utlisateur = no_utlisateur;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public void setDebut(LocalDateTime debut) {
        this.debut = debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
