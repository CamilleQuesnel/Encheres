package fr.eni.tp.encheres.dto;

/**
 * l'admin peut faire une recherche par id, et peut modifier si l'utilisateur est actif
 * ou non c'est tout donc j'ai désactivé le set id
 */
public class UpdateAdminDTO {
    private int no_utilisateur;
    private boolean actif;

    public UpdateAdminDTO() {
    }

    public UpdateAdminDTO(boolean actif) {
        this.actif = actif;
    }

    public int getNo_utilisateur() {
        return no_utilisateur;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public String toString() {
        return "UpdateAdminDTO{" +
                "no_utilisateur=" + no_utilisateur +
                ", actif=" + actif +
                '}';
    }
}
