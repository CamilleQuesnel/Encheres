package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {
    void createUser (Utilisateur utilisateur);
    Utilisateur readId (int no_utilisateur);
    Utilisateur readPseudo (String pseudo);
    Utilisateur readEmail (String email);
    void updateUser (Utilisateur utilisateur);
    void deleteUser (int noUtilisateur);
    boolean isUserPseudoUnique (String pseudo);
    boolean isUserEmailUnique (String email);
    boolean isUserPseudoUniqueForUpdate(String pseudo, int id);
    boolean isUserEmailUniqueForUpdate(String email, int id);
    boolean isUserPseudoUniqueExceptCurrent(String pseudo, int id);
    List<Utilisateur> searchByPseudoOrEmail(String query);

    List<Utilisateur> findUsers();
    List<Utilisateur> findUsersBuyers();
    void deleteCredit(int noUtilisateur, int credit);
    void updateCredit(int noUtilisateur, int credit);

}
