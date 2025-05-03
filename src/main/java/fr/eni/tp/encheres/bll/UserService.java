package fr.eni.tp.encheres.bll;

import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dto.RegisterDTO;
import fr.eni.tp.encheres.dto.UpdateDTO;
import fr.eni.tp.encheres.exception.BusinessException;

import java.util.List;

public interface UserService {

    public Utilisateur getUtilisateurById(int id);
    public Utilisateur getUtilisateurByEmail(String email);
    public Utilisateur getUtilisateurByPseudo(String pseudo);
    public void createUtilisateur(RegisterDTO registerDTO);
    public void updateUtilisateur(UpdateDTO updateDTO);
    public void desactivateUtilisateur(int no_utilisateur);
    public void deleteUtilisateur(int no_utilisateur);
    public List<Utilisateur> readUsers();


}
