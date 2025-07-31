package fr.eni.tp.encheres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EncheresApplication {

    public static void main(String[] args) {
        // Charger .env en premier
        Dotenv dotenv = Dotenv.configure().load();

        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("UPLOAD_DIR", dotenv.get("UPLOAD_DIR"));

        // Puis démarrer Spring
        SpringApplication.run(EncheresApplication.class, args);
    }
}
