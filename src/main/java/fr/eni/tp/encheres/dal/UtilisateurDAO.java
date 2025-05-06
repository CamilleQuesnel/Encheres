package fr.eni.tp.encheres.dal;

import fr.eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDAO {
    void createUser (Utilisateur utilisateur);
    Utilisateur readId (int id);
    Utilisateur readPseudo (String pseudo);
    Utilisateur readEmail (String email);
    void updateUser (Utilisateur utilisateur);
    void deleteUser (int noUtilisateur);
    boolean isUserPseudoUnique (String pseudo);
    boolean isUserEmailUnique (String email);
    List<Utilisateur> findUsers();
    List<Utilisateur> findUsersBuyers();
    void deleteCredit(int noUtilisateur, int credit);
    void updateCredit(int noUtilisateur, int credit);

}
