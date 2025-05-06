package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.UserService;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dto.RegisterDTO;
import fr.eni.tp.encheres.exception.BusinessCode;
import fr.eni.tp.encheres.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import fr.eni.tp.encheres.controller.FieldErrorMapper;

import java.security.Principal;
import java.util.Map;

@Controller
@SessionAttributes("membreSession")
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String displayEncheres(){
        return "redirect:/encheres";
    }
    @GetMapping("/encheres")
    public String afficherListeEncheres() {
        return "index";
    }



    /**
     * Page de login
     */
    @GetMapping("/login")
    public String displayLogin() {
        return "login";
    }

    /**
     * Redirection vers la page de profil après login réussi
     */
//    @GetMapping("/login_success")
//    public String loginSuccess(
//            @ModelAttribute("membreSession") Utilisateur membreSession,
//            Principal principal
//    ) {
//        String pseudo = principal.getName(); // pseudo car utilisé comme identifiant
//        Utilisateur utilisateur = userService.getUtilisateurByPseudo(pseudo);
//
//        membreSession.setNoUtilisateur(utilisateur.getNoUtilisateur());
//        membreSession.setPseudo(utilisateur.getPseudo());
//        membreSession.setNom(utilisateur.getNom());
//        membreSession.setPrenom(utilisateur.getPrenom());
//        membreSession.setEmail(utilisateur.getEmail());
//        membreSession.setAdministrateur(utilisateur.isAdministrateur());
//        membreSession.setActif(utilisateur.isActif());
//
//        return "redirect:/";
//    }

    @GetMapping("/login_success")
    public String loginSuccess(
            @ModelAttribute("membreSession") Utilisateur membreSession,
            Principal principal,
            Model model
    ) {
        try {
            String pseudo = principal.getName(); // pseudo car utilisé comme identifiant
            Utilisateur utilisateur = userService.getUtilisateurByPseudo(pseudo);

            if (utilisateur == null) {
                model.addAttribute("loginError", "Utilisateur introuvable.");
                return "redirect:/login?error"; // ou une page personnalisée
            }

            membreSession.setNoUtilisateur(utilisateur.getNoUtilisateur());
            membreSession.setPseudo(utilisateur.getPseudo());
            membreSession.setNom(utilisateur.getNom());
            membreSession.setPrenom(utilisateur.getPrenom());
            membreSession.setEmail(utilisateur.getEmail());
            membreSession.setAdministrateur(utilisateur.isAdministrateur());
            membreSession.setActif(utilisateur.isActif());

            return "redirect:/encheres";
        } catch (Exception e) {
            e.printStackTrace(); // pour le log, tu peux aussi utiliser un vrai logger
            model.addAttribute("loginError", "Une erreur est survenue lors de la connexion.");
            return "redirect:/login?error"; // tu peux aussi rediriger vers une page d’erreur
        }
    }


    /**
     * Formulaire d'inscription
     */
    @GetMapping("/register")
    public String afficherFormulaireInscription(Model model) {
        if (!model.containsAttribute("registerDTO")) {// Cela empêche de remplacer registerDTO s'il existe déjà (donc s'il vient d’un POST avec des erreurs).
            model.addAttribute("registerDTO", new RegisterDTO());
        }
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
        System.out.println("début du cuicui");

        try {
            userService.createUtilisateur(dto);
            System.out.println("pas cuicui");
        } catch (BusinessException exception) {
            exception.getKeys().forEach(key -> {
                System.out.println(key);
                String field = FieldErrorMapper.getFieldName(key);
                bindingResult.addError( new FieldError("registerDTO", field,null,false, new String[]{key}, null, null));

            });
            System.out.println("cuicui register");
        }

        // ✅ Toujours revenir au formulaire si des erreurs sont présentes
        if (bindingResult.hasErrors()) {
            return "register";
        }

        return "redirect:/login";
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


