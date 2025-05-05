package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewSaleController {

    private ItemService itemService;

    public NewSaleController(ItemService itemService) {
        this.itemService = itemService;
    }

    //********************* PAGE DE CREATION NOUVEAU ARTICLE ********************************//

    @GetMapping("new_sale")
    public String afficherNewSale() {
        return "new_sale";
    }





}
