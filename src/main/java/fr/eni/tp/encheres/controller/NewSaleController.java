package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.bll.ItemService;
import fr.eni.tp.encheres.bll.UserService;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dto.NewSaleDTO;
import fr.eni.tp.encheres.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.sql.init.SqlDataSourceScriptDatabaseInitializer;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("membreSession")
public class NewSaleController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer;
    private ItemService itemService;
    private UserService userService;

    public NewSaleController(ItemService itemService, UserService userService, SqlDataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer) {
        this.itemService = itemService;
        this.userService = userService;
        this.dataSourceScriptDatabaseInitializer = dataSourceScriptDatabaseInitializer;
    }

    //********************* PAGE DE CREATION NOUVEAU ARTICLE ********************************//

    @GetMapping("new_sale")
    public String afficherNewSale(
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

    @PostMapping("new_sale")
    @Transactional
    public String CreateNewSale(
            @ModelAttribute("NewSaleDTO") NewSaleDTO newSaleDTO,
            @RequestParam("fichierPhoto") MultipartFile fichierPhoto,
            RedirectAttributes redirectAttributes,
            Authentication authentication,
            BindingResult bindingResult,
            Model model
    ) {
        if (!fichierPhoto.isEmpty()) {
            try {
                // Créer le dossier si il n'existe pas
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();  // Créer le dossier uploads s'il n'existe pas
                }
                // Générer un nom unique pour la photo (pour éviter les conflits de noms)
                String photoName = authentication.getName() + "_" + System.currentTimeMillis() + "_" + fichierPhoto.getOriginalFilename();
                // Créer un chemin pour le fichier
                Path path = Paths.get(uploadDir + File.separator + photoName);

                // Mettre à jour l'objet NewSaleDTO avec le chemin de la photo
                newSaleDTO.setPhoto("/images/" + photoName);

                itemService.createArticle(newSaleDTO);

                // Sauvegarder le fichier dans le dossier
                //TRY pour fermeture auto du stream
                try (InputStream inputStream = fichierPhoto.getInputStream()) {
                    Files.copy(inputStream, path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("****************************************************************************************************************************");
                System.out.println("NewSaleController");
                System.out.println(newSaleDTO.getArticle());
                System.out.println("****************************************************************************************************************************");
                return "redirect:/"; //ATTENTION A REDIRIGER LA OU IL DOIT ALLER :)

            } catch (BusinessException exception) {
                exception.getKeys().forEach(key -> {


//                    System.out.println("****************************************************************************************************************************");
//                    System.out.println("ENTRE CATCH CREATION ARTICLE A VENDRE ");
//                    System.out.println(key);
//                    System.out.println("****************************************************************************************************************************");
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                    List<Categorie> categories = itemService.readCategories();
                    Utilisateur utilisateur = userService.getUtilisateurByPseudo(authentication.getName());
                    model.addAttribute("NewSaleDTO", newSaleDTO);
                    model.addAttribute("utilisateur",utilisateur);
                    model.addAttribute("categories", categories);

                });
            }
        }

                return "new_sale";
    }


}












