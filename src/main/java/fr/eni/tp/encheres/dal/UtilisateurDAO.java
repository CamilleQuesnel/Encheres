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
    boolean isUserPseudoUnique (Utilisateur utilisateur);
    boolean isUserEmailUnique (Utilisateur utilisateur);
    List<Utilisateur> findUsers();
    List<Utilisateur> findUsersBuyers();


}
