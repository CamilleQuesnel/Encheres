package fr.eni.tp.encheres.dal.impl;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.EnchereDAO;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnchereImpl implements EnchereDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    /**
     *
     * Les requêtes SQL
     *
     */
    private static final String INSERT_ENCHERE_INTO="INSERT INTO ENCHERES (no_utilisateur, no_article,date_enchere, montant_enchere) VALUES (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)";
    private static final String UPDATE_ENCHERE="UPDATE ENCHERES SET ENCHERES.montant_enchere = :montant_enchere, ENCHERES.date_enchere = :date_enchere, ENCHERES.etat_achat = :etat_achat WHERE ENCHERES.no_article =:no_article;";
    private static final String DELETE_ENCHERE="DELETE FROM ENCHERES WHERE no_utilisateur = :no_utilisateur AND no_article = :no_article;";
    private static final String SELECT_BY_NO_UTILISATEUR="SELECT * FROM ENCHERES WHERE ENCHERES.no_utilisateur = :no_utilisateur;";
    private static final String SELECT_BY_ARTICLE ="SELECT * FROM ENCHERES WHERE ENCHERES.no_article = :no_article;";
    private static final String SELECT_BEST_ENCHERE="SELECT TOP 1 no_utilisateur, no_article, date_enchere, montant_enchere, etat_achat FROM ENCHERES\n" +
            "ORDER BY montant_enchere DESC;";

    /**
     *
     * @param jdbcTemplate
     * @param namedParameterJdbcTemplate
     */
    public EnchereImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void save(Enchere enchere) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", enchere.getUtilisateur().getNoUtilisateur());
        mapSqlParameterSource.addValue("no_article", enchere.getArticleVendu().getNoArticle());
        mapSqlParameterSource.addValue("date_enchere", enchere.getDateEnchere());
        mapSqlParameterSource.addValue("montant_enchere", enchere.getMontant_enchere());


        namedParameterJdbcTemplate.update(
                INSERT_ENCHERE_INTO,
                mapSqlParameterSource
        );
    }

    @Override
    public List<Enchere> selectByUtilisateur(int noUtilisateur) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
        return namedParameterJdbcTemplate.query(
                SELECT_BY_NO_UTILISATEUR,
                mapSqlParameterSource,
                new EnchereRowMapper()
        );
    }

    @Override
    public List<Enchere> selectByArticle(int noArticle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", noArticle);
        return namedParameterJdbcTemplate.query(
                SELECT_BY_ARTICLE,
                mapSqlParameterSource,
                new EnchereRowMapper()
        );
    }

    @Override
    public void updateEnchere(Enchere enchere) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", enchere.getUtilisateur().getNoUtilisateur());
        mapSqlParameterSource.addValue("no_article", enchere.getArticleVendu().getNoArticle());
        mapSqlParameterSource.addValue("date_enchere", enchere.getDateEnchere());
        mapSqlParameterSource.addValue("montant_enchere", enchere.getMontant_enchere());
        mapSqlParameterSource.addValue("etat_achat", enchere.getEtat_achat());

        namedParameterJdbcTemplate.update(
                UPDATE_ENCHERE,
                mapSqlParameterSource
        );
    }

    @Override
    public Enchere selectBestEnchere(int noArticle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", noArticle);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BEST_ENCHERE,
                mapSqlParameterSource,
                new EnchereRowMapper()
        );

    }

    @Override
    public void deleteByArticle(int noArticle) {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("no_article", noArticle);

    namedParameterJdbcTemplate.update(
            DELETE_ENCHERE,
            mapSqlParameterSource
    );
    }


}

class EnchereRowMapper implements RowMapper <Enchere> {

    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
        Enchere enchere = new Enchere();
        enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime().toLocalDate());
        enchere.setMontant_enchere(rs.getInt("montant_enchere"));
        enchere.setEtat_achat(rs.getString("etat_achat"));

        ArticleVendu articleVendu = new ArticleVendu();
        articleVendu.setNoArticle(rs.getInt("no_article"));

        enchere.setArticleVendu(articleVendu);

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
        enchere.setUtilisateur(utilisateur);

        return enchere;
    }
}