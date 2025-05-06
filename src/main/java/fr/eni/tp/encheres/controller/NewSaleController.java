package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bll.UserService;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("membreSession")
public class NewSaleController {

    private ItemService itemService;
    private UserService userService;

    public NewSaleController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    //********************* PAGE DE CREATION NOUVEAU ARTICLE ********************************//

    @GetMapping("new_sale")
    public String afficherNewSale(
//            @ModelAttribute("membreSession") Utilisateur membreSession,
            Authentication authentication,
            Model model
    ) {
                List<Categorie> categories = itemService.readCategories();
                Utilisateur utilisateur = userService.getUtilisateurByPseudo(authentication.getName());
//        System.out.println("****************************************************************************************************************************");
//        System.out.println(utilisateur);
//        System.out.println("****************************************************************************************************************************");
                model.addAttribute("categories", categories);
                model.addAttribute("utilisateur",utilisateur);
        return "new_sale";
    }







}
