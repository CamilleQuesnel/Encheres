package fr.eni.tp.encheres.dal;



import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Retrait;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface RetraitDAO {

    void create(Retrait retrait);
    List<Retrait> selectAllRetrait();
    Retrait findRetraitById( int id );
    List<Retrait> findRetraitByCodePostal (String CP);
    List<Retrait> findRetraitByVille (String ville);
    boolean isIfRetraitExists(int id);
    void updateRetrait(Retrait retrait);


}

