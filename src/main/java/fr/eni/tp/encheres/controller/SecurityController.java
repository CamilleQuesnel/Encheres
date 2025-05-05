package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.UserService;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dto.RegisterDTO;
import fr.eni.tp.encheres.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@SessionAttributes("membreSession")
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Page de login
     */
    @GetMapping("/login")
    public String afficherLogin() {
        return "login";
    }

    /**
     * Redirection vers la page de profil après login réussi
     */
    @GetMapping("/login_success")
    public String loginSuccess(
            @ModelAttribute("membreSession") Utilisateur membreSession,
            Principal principal
    ) {
        String pseudo = principal.getName(); // pseudo car utilisé comme identifiant
        Utilisateur utilisateur = userService.getUtilisateurByPseudo(pseudo);

        membreSession.setNoUtilisateur(utilisateur.getNoUtilisateur());
        membreSession.setPseudo(utilisateur.getPseudo());
        membreSession.setNom(utilisateur.getNom());
        membreSession.setPrenom(utilisateur.getPrenom());
        membreSession.setEmail(utilisateur.getEmail());
        membreSession.setAdministrateur(utilisateur.isAdministrateur());
        membreSession.setActif(utilisateur.isActif());

        return "redirect:/";
    }

    /**
     * Formulaire d'inscription
     */
    @GetMapping("/register")
    public String afficherFormulaireInscription(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    /**
     * Traitement de l'inscription
     */
    @PostMapping("/register")
    public String traiterInscription(
            @ModelAttribute("registerDTO") RegisterDTO dto,
            BindingResult bindingResult,
            Model model
    ) {
        try {
            userService.createUtilisateur(dto);
            return "redirect:/login";
        } catch (BusinessException exception) {
            exception.getKeys().forEach(System.out::println);

            exception.getKeys().forEach(key -> {
                ObjectError error = new ObjectError("globalError", key);
                bindingResult.addError(error);
            });
            return "register";
        }
    }

    /**
     *
     * Page profile
     */
    @GetMapping("/profile")
    public String afficherProfile() {
        return "profile";
    }


    /**
     * Création de l'attribut de session membreSession  pour stocker l'utilisateur connecté
     */
    @ModelAttribute("membreSession")
    public Utilisateur membreSession() {
        return new Utilisateur();
    }
}
