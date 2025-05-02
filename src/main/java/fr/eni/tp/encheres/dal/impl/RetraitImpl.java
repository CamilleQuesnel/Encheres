package fr.eni.tp.encheres.dal.impl;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Retrait;
import fr.eni.tp.encheres.dal.RetraitDAO;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private static final String INSERT="INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (:no_article,:rue,:code_postal, :ville);";
    private static final String SELECT_BY_ID="SELECT no_article,rue,code_postal,ville FROM RETRAITS WHERE no_article = :no_article;";
    private static final String SELECT_BY_CODE_POSTAL="SELECT no_article,rue,code_postal,ville FROM RETRAITS WHERE code_postal = :code_postal;";
    private static final String SELECT_BY_CODE_VILLE="SELECT no_article,rue,code_postal,ville FROM RETRAITS WHERE ville = :ville;";
    private static final String UPDATE="UPDATE RETRAITS SET rue = :rue,code_postal = :code_postal,ville = :ville WHERE no_article = :no_article;";

    //*****************  QUERIES  ***********************

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RetraitImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override // TEST OK
    public void create(Retrait retrait) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article",retrait.getArticleVendu().getNoArticle());
        mapSqlParameterSource.addValue(("rue"),retrait.getRue());
        mapSqlParameterSource.addValue(("code_postal"),retrait.getCode_postal());
        mapSqlParameterSource.addValue(("ville"),retrait.getVille());
        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource
        );
    }
    @Override // TEST OK
    public List<Retrait> selectAllRetrait()
    {
        List<Retrait> retraits = jdbcTemplate.query(
                SELECT_ALL,
                new RetraitRowMapper()
        );

        return retraits;
    }

    @Override //TEST OK
    public Retrait findRetraitById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article",id);

        Retrait retrait;
        try {
            retrait = namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_ID,
                    mapSqlParameterSource,
                    new RetraitRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            throw e;
        }catch(Exception e){
            e.printStackTrace();
            retrait = null;
        }
        return retrait;
    }

    @Override //TEST OK
    public List<Retrait> findRetraitByCodePostal(String CP) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("code_postal",CP);
        List<Retrait> retraits = namedParameterJdbcTemplate.query(
                    SELECT_BY_CODE_POSTAL,
                    mapSqlParameterSource,
                    new RetraitRowMapper()
            );
        return  retraits;
    }

    @Override//TEST OK
    public List<Retrait> findRetraitByVille(String ville) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("ville",ville);
        List<Retrait> retraits = namedParameterJdbcTemplate.query(
                SELECT_BY_CODE_VILLE,
                mapSqlParameterSource,
                new RetraitRowMapper()
            );
            return  retraits;
    }

    @Override //TEST OK
    public boolean isIfRetraitExists(int id) {
        boolean isExits = true;
        try{
            Retrait retrait = this.findRetraitById(id);
        }catch(Exception e){
            isExits = false;
        }
        return isExits;
    }

    @Override//TEST OK
    public void updateRetrait(Retrait retrait) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article",retrait.getArticleVendu().getNoArticle());
        mapSqlParameterSource.addValue(("rue"),retrait.getRue());
        mapSqlParameterSource.addValue(("code_postal"),retrait.getCode_postal());
        mapSqlParameterSource.addValue(("ville"),retrait.getVille());
        namedParameterJdbcTemplate.update(
                UPDATE,
                mapSqlParameterSource
        );
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
