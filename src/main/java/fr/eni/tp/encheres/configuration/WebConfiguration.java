package fr.eni.tp.encheres.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfiguration  implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//         Cette ligne expose le dossier "uploads" pour qu'il soit accessible via HTTP
        registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:///" + uploadDir)
                    .addResourceLocations("classpath:/static/images/") // Garder l'accès aux ressources statiques
                    .resourceChain(true);

    }
}
