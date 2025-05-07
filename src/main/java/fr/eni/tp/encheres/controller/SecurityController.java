package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.UserService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
import fr.eni.tp.encheres.dal.UtilisateurDAO;
import fr.eni.tp.encheres.dto.RegisterDTO;
import fr.eni.tp.encheres.dto.UpdateDTO;
import fr.eni.tp.encheres.exception.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@SessionAttributes("membreSession")
public class SecurityController {

    private final UserService userService;
    private final ArticleVenduDAO articleVenduDAO;
    private final UtilisateurDAO utilisateurDAO;

    public SecurityController(UserService userService, ArticleVenduDAO articleVenduDAO, UtilisateurDAO utilisateurDAO) {
        this.userService = userService;
        this.articleVenduDAO = articleVenduDAO;
        this.utilisateurDAO = utilisateurDAO;
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
            model.addAttribute("loginError", "⚠ Identifiant ou mot de passe incorrect, ou compte désactivé.");
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
        Utilisateur utilisateur = userService.getUtilisateurById(membreSession.getNoUtilisateur());

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


        model.addAttribute("UpdateDTO", dto);
        return "profile";
    }

@PostMapping("/profile")
public String updateProfile(
        @ModelAttribute("membreSession") Utilisateur membreSession,
        @ModelAttribute("UpdateDTO") UpdateDTO updateDTO,
        BindingResult bindingResult,
        Model model,
        SessionStatus sessionStatus
) {
    try {
        System.out.println("ID utilisateur en session : " + membreSession.getNoUtilisateur());

        membreSession = utilisateurDAO.readId(membreSession.getNoUtilisateur());

        boolean aChanger = false;

        if (!Objects.equals(membreSession.getPseudo(), updateDTO.getUsername())) {
            aChanger = true;
        }
        if (!Objects.equals(membreSession.getPrenom(), updateDTO.getFirstname())) {
            aChanger = true;
        }
        if (!Objects.equals(membreSession.getNom(), updateDTO.getLastname())) {
            aChanger = true;
        }
        if (!Objects.equals(membreSession.getEmail(), updateDTO.getEmail())) {
            aChanger = true;
        }
        if (!Objects.equals(membreSession.getRue(), updateDTO.getStreet())) {
            aChanger = true;
        }
        if (!Objects.equals(membreSession.getCodePostal(), updateDTO.getPostalCode())) {
            aChanger = true;
        }
        if (!Objects.equals(membreSession.getVille(), updateDTO.getCity())) {
            aChanger = true;
        }
        if (!Objects.equals(membreSession.getTelephone(), updateDTO.getPhone())) {
            aChanger = true;
        }

        if (aChanger) {
            userService.updateUtilisateur(updateDTO);
            membreSession.setPseudo(updateDTO.getUsername());
            model.addAttribute("membreSession", membreSession);
            System.out.println("le pseudo est mis à jour");
        } else {
            System.out.println("Aucune modification détectée.");
        }

    } catch (BusinessException exception) {
        exception.printStackTrace();
        exception.getKeys().forEach(key -> {
            String field = FieldErrorMapper.getFieldName(key);
            bindingResult.addError(new FieldError("UpdateDTO", field, null, false, new String[]{key}, null, null));
        });
    }

    return "redirect:/profile";
}

    @GetMapping("/admin")
    public String interfaceAdministrateur(
            Model model
    ){
        return "/admin";
    }

    @GetMapping("/admin/categories_gestion")
    public String interfaceAdministrateurCategories(
            Model model
    ){
        return "admin/categories_gestion";
    }

    @GetMapping("/admin/utilisateur_gestion")
    public String interfaceAdministrateurUtilisateur(
            @RequestParam(name = "query", required = false) String query,
            Model model
    ) {
        List<Utilisateur> utilisateurs;

        if (query != null && !query.isBlank()) {
            utilisateurs = utilisateurDAO.searchByPseudoOrEmail(query.trim());
        } else {
            utilisateurs = utilisateurDAO.findUsers(); // méthode pour tous les utilisateurs
        }

        model.addAttribute("utilisateurs", utilisateurs);
        return "admin/utilisateur_gestion";
    }

    @GetMapping("/admin/toggle_utilisateur")
    public String toggleUtilisateur(@RequestParam("id") int id) {
        Utilisateur utilisateur = utilisateurDAO.readId(id);
        utilisateur.setActif(!utilisateur.isActif()); // inverse l'état
        utilisateurDAO.updateUser(utilisateur); // met à jour dans la BDD
        return "redirect:/admin/utilisateur_gestion";
    }


    @ModelAttribute("membreSession")
    public Utilisateur membreSession() {
        return new Utilisateur();
    }
}
