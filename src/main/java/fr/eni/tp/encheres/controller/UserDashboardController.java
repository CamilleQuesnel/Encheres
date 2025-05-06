package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/encheres/user")
@SessionAttributes({"membreSession"})

public class UserDashboardController {

    private final static Logger logger = LoggerFactory.getLogger(UserDashboardController.class);
    private final ArticleVenduDAO articleVenduDAO;
    private final ItemService itemService;

    @Autowired
    public UserDashboardController(ArticleVenduDAO articleVenduDAO, ItemService itemService) {
        this.articleVenduDAO = articleVenduDAO;
        this.itemService = itemService;
    }

    /**
     * Handles the user dashboard page.
     * Redirects to login if the user is not authenticated.
     */
    @GetMapping("/dashboard")
    public String showUserDashboard(
            Model model,
            Principal principal
    ) {
        if (principal != null) {
            Utilisateur utilisateur = new Utilisateur();
            // Get the user ID from the Utilisateur object
            Integer userId = utilisateur.getNoUtilisateur();

            List<ArticleVendu> ventesEncours = articleVenduDAO.findByUserByEtat("en cours",userId );
            List<ArticleVendu> ventesNonDebutess = articleVenduDAO.findByUserByEtat("n.c", userId);
            List<ArticleVendu> ventesTerminees = articleVenduDAO.findByUserByEtat("vendu", userId);

            // Add data to the model for Thymeleaf rendering
            model.addAttribute("ventesEncours", ventesEncours);
            model.addAttribute("ventesNonDebutess", ventesNonDebutess);
            model.addAttribute("ventesTerminees", ventesTerminees);

            return "user/dashboard";
            // Ensure dashboard.html exists in the templates folder
        } else {
            // Si l'utilisateur n'est pas connecté ou pas autorisé
            logger.warn("Utilisateur non administrateur");

            // Redirige vers la liste des films
            return "redirect:/";
        }
    }

}
