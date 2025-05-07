package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.UserService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
import fr.eni.tp.encheres.dto.NewSaleDTO;
import fr.eni.tp.encheres.dto.RegisterDTO;
import fr.eni.tp.encheres.dto.UpdateDTO;
import fr.eni.tp.encheres.exception.BusinessCode;
import fr.eni.tp.encheres.exception.BusinessException;
import jakarta.servlet.http.HttpSession;
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
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("membreSession")
public class SecurityController {

    private final UserService userService;
    private final ArticleVenduDAO articleVenduDAO;

    public SecurityController(UserService userService, ArticleVenduDAO articleVenduDAO) {
        this.userService = userService;
        this.articleVenduDAO = articleVenduDAO;
    }

    @GetMapping("/")
    public String displayEncheres(){
        return "redirect:/encheres";
    }
    @GetMapping("/encheres")
    public String afficherListeEncheres(Model model) {
        List<ArticleVendu> articlesEnCours = articleVenduDAO.findByUserByEtat("en cours", null);
        model.addAttribute("articles", articlesEnCours);
        return "index";
    }

    @GetMapping("/login")
    public String displayLogin(Model model, @RequestParam(required = false) String error,
                               @RequestParam(required = false) String logout) {
        if (error != null) {
            model.addAttribute("loginError", "Identifiant ou mot de passe incorrect, ou compte désactivé.");
        }
        if (logout != null) {
            model.addAttribute("logoutSuccess", "Vous avez été déconnecté(e).");
        }
        return "login";
    }

    @GetMapping("/login_success")
    public String loginSuccess(
            @ModelAttribute("membreSession") Utilisateur membreSession,
            Principal principal,
            Model model
    ) {
        try {
            String pseudo = principal.getName();
            Utilisateur utilisateur = userService.getUtilisateurByPseudo(pseudo);

            if (utilisateur == null) {
                model.addAttribute("loginError", "Utilisateur introuvable.");
                return "redirect:/login?error";
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
            e.printStackTrace();
            model.addAttribute("loginError", "Une erreur est survenue lors de la connexion.");
            return "redirect:/login?error";
        }
    }

    @GetMapping("/register")
    public String afficherFormulaireInscription(Model model) {
        if (!model.containsAttribute("registerDTO")) {
            model.addAttribute("registerDTO", new RegisterDTO());
        }
        return "register";
    }

    @PostMapping("/register")
    public String traiterInscription(
            @ModelAttribute("registerDTO") RegisterDTO dto,
            BindingResult bindingResult,
            Model model
    ) {
        try {
            userService.createUtilisateur(dto);
        } catch (BusinessException exception) {
            exception.getKeys().forEach(key -> {
                String field = FieldErrorMapper.getFieldName(key);
                bindingResult.addError(new FieldError("registerDTO", field, null, false, new String[]{key}, null, null));
            });
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String afficherProfile(@ModelAttribute("membreSession") Utilisateur membreSession, Model model) {
        Utilisateur utilisateur = userService.getUtilisateurByPseudo(membreSession.getPseudo());

        membreSession.setNom(utilisateur.getNom());
        membreSession.setPrenom(utilisateur.getPrenom());
        membreSession.setEmail(utilisateur.getEmail());
        membreSession.setRue(utilisateur.getRue());
        membreSession.setCodePostal(utilisateur.getCodePostal());
        membreSession.setVille(utilisateur.getVille());
        membreSession.setTelephone(utilisateur.getTelephone());

        UpdateDTO dto = new UpdateDTO();
        dto.setNo_utilisateur(utilisateur.getNoUtilisateur());
        dto.setLastname(utilisateur.getNom());
        dto.setFirstname(utilisateur.getPrenom());
        dto.setUsername(utilisateur.getPseudo());
        dto.setEmail(utilisateur.getEmail());
        dto.setStreet(utilisateur.getRue());
        dto.setPostalCode(utilisateur.getCodePostal());
        dto.setCity(utilisateur.getVille());
        dto.setPhone(utilisateur.getTelephone());
        dto.setPassword("");
        dto.setPasswordConfirm("");

        model.addAttribute("UpdateDTO", dto);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @ModelAttribute("membreSession") Utilisateur membreSession,
            @ModelAttribute("UpdateDTO") UpdateDTO updateDTO,
            BindingResult bindingResult,
            Model model
    ) {
        try {
            updateDTO.setUsername(membreSession.getPseudo());
            updateDTO.setNo_utilisateur(membreSession.getNoUtilisateur());

            userService.updateUtilisateur(updateDTO);

            membreSession.setNom(updateDTO.getLastname());
            membreSession.setPrenom(updateDTO.getFirstname());
            membreSession.setEmail(updateDTO.getEmail());
            membreSession.setRue(updateDTO.getStreet());
            membreSession.setCodePostal(updateDTO.getPostalCode());
            membreSession.setVille(updateDTO.getCity());
            membreSession.setTelephone(updateDTO.getPhone());

            model.addAttribute("successMessage", "Profil mis à jour avec succès.");

        } catch (BusinessException exception) {
            System.out.println("Erreurs de validation dans updateUtilisateur :");
            System.out.println("Contenu de UpdateDTO : " + updateDTO);
            exception.printStackTrace();
            exception.getKeys().forEach(key -> {
                String field = FieldErrorMapper.getFieldName(key);
                bindingResult.addError(new FieldError("UpdateDTO", field, null, false, new String[]{key}, null, null));
            });
            model.addAttribute("errorMessage", "Erreur lors de la mise à jour du profil.");
            return "profile";
        }

        return "redirect:/profile";
    }

    @ModelAttribute("membreSession")
    public Utilisateur membreSession() {
        return new Utilisateur();
    }
}
