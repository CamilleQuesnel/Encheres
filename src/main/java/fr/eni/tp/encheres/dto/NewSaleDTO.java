package fr.eni.tp.encheres.dto;

public class NewSaleDTO {
    // objet article
    private String article;
    private String categorie;
    private String description;
    private String photo;
    private String prix;
    private String debut;
    private String fin;
    // objet categorie
    private String rue;
    private String codePostal;
    private String ville;

    public NewSaleDTO() {
    }

    public NewSaleDTO(String article, String categorie, String description, String photo, String prix, String debut, String fin, String rue, String codePostal, String ville) {
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


    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
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

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("NewSaleDTO{");
        sb.append("article='").append(article).append('\'');
        sb.append(", categorie='").append(categorie).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", photo='").append(photo).append('\'');
        sb.append(", prix='").append(prix).append('\'');
        sb.append(", debut='").append(debut).append('\'');
        sb.append(", fin='").append(fin).append('\'');
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
