package fr.eni.tp.encheres.controller;


import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;


@Controller
@RequestMapping("/")
@SessionAttributes("membreSession")
public class ArticleController {


    private final ArticleVenduDAO articleVenduDAO;


    private final ItemService itemService;


    @Autowired
    public ArticleController(ArticleVenduDAO articleVenduDAO, ItemService itemService) {
        this.articleVenduDAO = articleVenduDAO;
        this.itemService = itemService;
    }





    @GetMapping("/articles/{id}")
    public String getArticleById(@PathVariable int id, Model model) {
        ArticleVendu article = itemService.readArticle(id);
        if (article == null) {
            throw new IllegalArgumentException("Invalid article Id: " + id);
        }
        model.addAttribute("article", article);
        return "articles/detail";
    }




}

