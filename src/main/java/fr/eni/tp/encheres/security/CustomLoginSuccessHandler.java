package fr.eni.tp.encheres.security;

import fr.eni.tp.encheres.bll.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@Component // ⚠️ c’est cette annotation qui permet à Spring de l’injecter
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public CustomLoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String pseudo = authentication.getName();
        var utilisateur = userService.getUtilisateurByPseudo(pseudo);

        HttpSession session = request.getSession();
        session.setAttribute("membreSession", utilisateur); // nom cohérent avec @SessionAttributes

        response.sendRedirect("/encheres");
    }
}

