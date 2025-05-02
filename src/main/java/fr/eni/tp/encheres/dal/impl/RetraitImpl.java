package fr.eni.tp.encheres.dal.impl;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Retrait;
import fr.eni.tp.encheres.dal.RetraitDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RetraitImpl implements RetraitDAO {

    //*****************  QUERIES  ***********************
    private static final String SELECT_ALL="SELECT no_article,rue,code_postal,ville FROM RETRAITS";

    private static final String SELECT_BY_ID="SELECT no_categorie, libelle FROM CATEGORIES WHERE no_categorie = :id;";
    private static final String SELECT_BY_LIBELLE="SELECT no_categorie, libelle FROM CATEGORIES WHERE libelle = :libelle;";
    private static final String INSERT="INSERT INTO CATEGORIES (libelle) VALUES (:libelle);";
    private static final String UPDATE="UPDATE CATEGORIES SET libelle = :libelle WHERE no_categorie = :no_categorie;";
    // Pour delete une categorie
    private static final String UPDATE_ARTICLES_WITH_NEW_LIBELLE = "UPDATE ARTICLES_VENDUS set no_categorie = 1 WHERE no_categorie = :id_delete;";
    private static final String DELETE_CATEGORIE_WITH_NO_CAEGORIE = "DELETE FROM CATEGORIES WHERE no_categorie = :id_delete;";

    //*****************  QUERIES  ***********************

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RetraitImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void create(Retrait retrait) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article",retrait.getArticleVendu().getNoArticle());
        mapSqlParameterSource.addValue(("rue"),retrait.getRue());

        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource
        );
    }

    @Override
    public List<Retrait> selectAllRetrait()
    {
        List<Retrait> retraits = jdbcTemplate.query(
                SELECT_ALL,
                new RetraitRowMapper()
        );

        return retraits;
    }

    @Override
    public Retrait findRetraitById(int id) {
        return null;
    }

    @Override
    public List<Retrait> findRetraitByCodePostal(String CP) {
        return List.of();
    }

    @Override
    public List<Retrait> findRetraitByVille(String ville) {
        return List.of();
    }

    @Override
    public boolean isIfRetraitExists(String id) {
        return false;
    }

    @Override
    public void updateRetrait(Retrait retrait) {

    }
}
class RetraitRowMapper implements RowMapper<Retrait> {
    @Override
    public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
        Retrait retrait = new Retrait();
        retrait.setRue(rs.getString("rue"));
        retrait.setVille(rs.getString("ville"));
        retrait.setCode_postal(rs.getString("code_postal"));


        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNoArticle(rs.getInt("no_article"));

        retrait.setArticleVendu(articleVendu);


        return retrait;
    }
}
