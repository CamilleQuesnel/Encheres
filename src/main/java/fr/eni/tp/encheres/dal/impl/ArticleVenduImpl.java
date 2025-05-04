package fr.eni.tp.encheres.dal.impl;


import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.ArticleVenduDAO;
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
import java.util.Optional;

@Repository
public class ArticleVenduImpl implements ArticleVenduDAO {
    //*****************  QUERIES  ***********************
    private static final String SELECT_BY_ETAT = "SELECT no_article,nom_article,description,date_debut_encheres," +
            "date_fin_encheres,prix_initial,prix_vente,no_categorie,etat_vente,url_image\n" +
            "FROM ARTICLES_VENDUS WHERE etat_vente = :etat;";
    private static final String SELECT_BY_ETAT_BY_USER = "SELECT no_article,nom_article,description," +
            "date_debut_encheres,date_fin_encheres,prix_initial,prix_vente," +
            "no_utilisateur,no_categorie,etat_vente,url_image FROM ARTICLES_VENDUS" +
            " WHERE etat_vente = :etat AND no_utilisateur = :idUser;";
    //*****************  QUERIES  ***********************

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public ArticleVenduImpl(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    //finfById
    private static final String SELECT_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article = :id";


    //findAll
    private static final String SELECT_ALL = "SELECT * FROM ARTICLES_VENDUS";
    //Insert
    private static final String INSERT = "INSERT INTO ARTICLES_VENDUS " +
            "(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, etat_vente, no_utilisateur, no_categorie) " +
            "VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :etat_vente, :no_utilisateur, :no_categorie)";


    //Update
    private static final String UPDATE =
            "UPDATE ARTICLES_VENDUS SET " +
                    "nom_article = :nom_article, " +
                    "description = :description, " +
                    "date_debut_encheres = :date_debut_encheres, " +
                    "date_fin_encheres = :date_fin_encheres, " +
                    "prix_initial = :prix_initial, " +
                    "prix_vente = :prix_vente, " +
                    "etat_vente = :etat_vente, " +
                    "no_utilisateur = :no_utilisateur, " +
                    "no_categorie = :no_categorie " +
                    "WHERE no_article = :no_article";


    //Delete
    private static final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = :id";



    @Override
    public ArticleVendu findById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        return namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new ArticleVenduRowMapper()
        );
    }



    @Override
    public List<ArticleVendu> findAll() {
        MapSqlParameterSource  mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", 0);

        return namedParameterJdbcTemplate.query(
                SELECT_ALL,
                mapSqlParameterSource,
                new ArticleVenduRowMapper()

        );
    }

    /**
     *
     * @param idCategorie
     * @return
     */


    @Override
    public List<ArticleVendu> findByCategorie(int idCategorie) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_categorie", idCategorie);

        String SELECT_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_categorie = :no_categorie";

        return namedParameterJdbcTemplate.query(
                SELECT_BY_CATEGORIE,
                mapSqlParameterSource,
                new ArticleVenduRowMapper()
        );

    }


    @Override
    public void insert(ArticleVendu article) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("nom_article", article.getNomArticle());
        mapSqlParameterSource.addValue("description", article.getDescription());
        mapSqlParameterSource.addValue("date_debut_encheres",article.getDateDebutEncheres());
        mapSqlParameterSource.addValue("date_fin_encheres", article.getDateFinEncheres());
        mapSqlParameterSource.addValue("prix_initial",article.getMiseAPrix());
        // Récupération des identifiants de l'utilisateur et de la catégorie
        if (article.getUtilisateur() != null) {
            mapSqlParameterSource.addValue("no_utilisateur", article.getUtilisateur().getNoUtilisateur());
        } else {
            throw new IllegalArgumentException("Utilisateur ne peut pas être null");
        }


        if (article.getCategorieArticle() != null) {
            mapSqlParameterSource.addValue("no_categorie", article.getCategorieArticle().getNoCategorie());
        } else {
            throw new IllegalArgumentException("CategorieArticle ne peut pas être null");
        }


        mapSqlParameterSource.addValue("etat_vente", article.getEtatVente());


        // Génération de la clé
        KeyHolder keyHolder = new GeneratedKeyHolder();


        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"id"}
        );


        // Mise à jour de l'identifiant de l'article
        article.setNoArticle(keyHolder.getKey().intValue());
    }


    @Override
    public void update(ArticleVendu article) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_article", article.getNoArticle());
        mapSqlParameterSource.addValue("nom_article", article.getNomArticle());
        mapSqlParameterSource.addValue("description", article.getDescription());
        mapSqlParameterSource.addValue("date_debut_encheres", article.getDateDebutEncheres());
        mapSqlParameterSource.addValue("date_fin_encheres", article.getDateFinEncheres());
        mapSqlParameterSource.addValue("prix_initial", article.getMiseAPrix());
        mapSqlParameterSource.addValue("prix_vente", article.getPrixVente());

        if (article.getUtilisateur() != null) {
            mapSqlParameterSource.addValue("no_utilisateur", article.getUtilisateur().getNoUtilisateur());
        } else {
            throw new IllegalArgumentException("Utilisateur ne peut pas être null");
        }

        if (article.getCategorieArticle() != null) {
            mapSqlParameterSource.addValue("no_categorie", article.getCategorieArticle().getNoCategorie());
        } else {
            throw new IllegalArgumentException("CategorieArticle ne peut pas être null");
        }

        mapSqlParameterSource.addValue("etat_vente", article.getEtatVente());

        namedParameterJdbcTemplate.update(
                UPDATE,
                mapSqlParameterSource
        );
    }

    @Override
    public void delete(int id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", id);
        namedParameterJdbcTemplate.update(DELETE, parameters);
    }

    /**
     *
     * @param etat ('n.c','en cours','vendu','annulé')
     * @param idUser peut etre mis a null = dans ce cas liste de l'etat
     * @return
     */
    @Override // TEST OK
    public List<ArticleVendu> findByUserByEtat(String etat, Integer idUser) {
        List<String> etatsValid = List.of("n.c", "en cours", "vendu", "annulé");

        boolean idUserOk = idUser != null;
        boolean etatOk = etat != null && etatsValid.contains(etat);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        List<ArticleVendu> articleVendus = null;


        if (!idUserOk && etatOk){ // LIST GENERIQUE
            mapSqlParameterSource.addValue("etat",etat);
            try {
                articleVendus = namedParameterJdbcTemplate.query(
                        SELECT_BY_ETAT,
                        mapSqlParameterSource,
                        new ArticleVenduRowMapper()
                );
            } catch (EmptyResultDataAccessException e) {
                throw e;
            }catch(Exception e){
                e.printStackTrace();
                articleVendus = null;
            }

        }else if(idUserOk && etatOk){ // LISTE PAR UTILISATEUR
            mapSqlParameterSource.addValue("idUser",idUser);
            mapSqlParameterSource.addValue("etat",etat);
            try {
                articleVendus = namedParameterJdbcTemplate.query(
                        SELECT_BY_ETAT_BY_USER,
                        mapSqlParameterSource,
                        new ArticleVenduRowMapper()
                );
            } catch (EmptyResultDataAccessException e) {
                throw e;
            }catch(Exception e){
                e.printStackTrace();
                articleVendus = null;
            }

        }
        return articleVendus;
    }


    class ArticleVenduRowMapper implements RowMapper<ArticleVendu> {

        @Override
        public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleVendu article = new ArticleVendu();

            article.setNoArticle(rs.getInt("no_article"));
            article.setNomArticle(rs.getString("nom_article"));
            article.setDescription(rs.getString("description"));

            if (rs.getDate("date_debut_encheres") != null) {
                article.setDateDebutEncheres(rs.getDate("date_debut_encheres").toLocalDate());
            }

            if (rs.getDate("date_fin_encheres") != null) {
                article.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            }

            article.setMiseAPrix(rs.getInt("prix_initial"));
            article.setPrixVente(rs.getInt("prix_vente"));
            article.setEtatVente(rs.getString("etat_vente"));

            // Mapping minimal de l'utilisateur et de la catégorie si présents
            try {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
                article.setUtilisateur(utilisateur);
            } catch (SQLException ignored) {}

            try {
                Categorie categorie = new Categorie();
                categorie.setNoCategorie(rs.getInt("no_categorie"));
                article.setCategorieArticle(categorie);
            } catch (SQLException ignored) {}

            return article;
        }
    }
}





