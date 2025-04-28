package fr.eni.tp.encheres.bll;

import fr.eni.tp.encheres.bo.Utilisateur;

import java.util.List;

public interface UserService {

    public Utilisateur getUtilisateur();
    public Utilisateur createUtilisateur(Utilisateur utilisateur);
    public Utilisateur updateUtilisateur(Utilisateur utilisateur);
    public Utilisateur deleteUtilisateur(int id);

    public List<Utilisateur> readUsers();
    public List<Utilisateur> updateUsers(Utilisateur utilisateur);
    public List<Utilisateur> deleteUsers(int id);
}
