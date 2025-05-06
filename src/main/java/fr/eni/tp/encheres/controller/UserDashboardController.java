package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
@SessionAttributes("membreSession")
public class UserDashboardController {



    private final ArticleVenduDAO articleVenduDAO;


    private final ItemService itemService;

    @Autowired
    public UserDashboardController(ArticleVenduDAO articleVenduDAO, ItemService itemService) {
        this.articleVenduDAO = articleVenduDAO;
        this.itemService = itemService;
    }

    @GetMapping("/dashbord")
    public String showUserDashboard(
            @SessionAttribute
                    ("membreSession")
            Utilisateur utilisateur,
            Model model) {
        // Get the user ID from the Utilisateur object
        Integer userId = utilisateur.getNoUtilisateur();
        // Get all articles for the user by different states
        List<ArticleVendu> ventesEncours = articleVenduDAO.findByUserByEtat("en cours", userId);
        List<ArticleVendu> ventesNonDebutess = articleVenduDAO.findByUserByEtat("n.c", userId);
        List<ArticleVendu> ventesTerminees = articleVenduDAO.findByUserByEtat("vendu", userId);

        //add to model
        model.addAttribute("ventesEncours", ventesEncours);
        model.addAttribute("ventesNonDebutess", ventesNonDebutess);
        model.addAttribute("ventesTerminees", ventesTerminees);

        return "user/dashbord";
    }



}
