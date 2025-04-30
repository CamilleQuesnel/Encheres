package fr.eni.tp.encheres.dal.impl;

import fr.eni.tp.encheres.bo.ArticleVendu;
import fr.eni.tp.encheres.bo.Categorie;
import fr.eni.tp.encheres.dal.CategorieDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



@Repository
public class CategorieImpl implements CategorieDAO {
    //*****************  QUERIES  ***********************
    private static final String SELECT_ALL="SELECT no_categorie, libelle FROM CATEGORIES;";
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

    public CategorieImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     *
     * @param libelle
     */
    @Override // tester OK
    public void create(String libelle){
        Categorie categorie = new Categorie(libelle);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
//        mapSqlParameterSource.addValue("no_categorie", categorie.getNoCategorie());
        mapSqlParameterSource.addValue("libelle", categorie.getLibelle());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(
                INSERT,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"no_categorie"}
        );

        categorie.setNoCategorie(keyHolder.getKey().intValue());


    }

    /**
     *
     * @return Catagorie
     */
    @Override // TESTER OK
    public List<Categorie> selectAllCategorie() {
        List<Categorie> categories = jdbcTemplate.query(
                SELECT_ALL,
                new CategorieRowMapper()
        );
        return categories;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override // TESTER OK
    public Categorie findCategorieById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        Categorie categorie = namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_ID,
                mapSqlParameterSource,
                new CategorieRowMapper()
        );
        return categorie;
    }
    @Override // TEST OK
    public Categorie findCategorieByLibelle(String libelle) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("libelle", libelle);

        Categorie categorie = namedParameterJdbcTemplate.queryForObject(
                SELECT_BY_LIBELLE,
                mapSqlParameterSource,
                new CategorieRowMapper()
        );


        return categorie;
    }

    /**
     *
     * @param libelle
     * @return boolean
     */
    @Override // TEST Ok
    public boolean isIfCategorieExists(String libelle) {
        boolean isExits = true;
        try{
        Categorie categorie = this.findCategorieByLibelle(libelle);
        }catch(Exception e){
//            System.out.println("PAS DE CATEGORIE TROUVER !!!!!!");
            isExits = false;
        }
        return isExits;
    }

    @Override //TEST OK
    public void updateCategorie(Categorie categorie) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("libelle", categorie.getLibelle());
        mapSqlParameterSource.addValue("no_categorie", categorie.getNoCategorie());

        namedParameterJdbcTemplate.update(
                UPDATE,
                mapSqlParameterSource
        );


    }

    @Override
    @Transactional
    public void deleteCategorie(int id) {
        MapSqlParameterSource mapSqlParameterSourceUpdate = new MapSqlParameterSource();
        mapSqlParameterSourceUpdate.addValue("id_delete", id);

        namedParameterJdbcTemplate.update(
                UPDATE_ARTICLES_WITH_NEW_LIBELLE,
                mapSqlParameterSourceUpdate
        );
        namedParameterJdbcTemplate.update(
                DELETE_CATEGORIE_WITH_NO_CAEGORIE,
                mapSqlParameterSourceUpdate
        );



        // 1 Recherche L'id categorie a supprimer dans la table articles_vendus
        // 2 Update articles vendu avec no_cat divers .
        // 3 delete categorie


    }
}
class CategorieRowMapper implements RowMapper<Categorie> {
    @Override
    public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Categorie categorie = new Categorie();
        categorie.setNoCategorie(rs.getInt("no_categorie"));
        categorie.setLibelle(rs.getString("libelle"));

        return categorie;
    }

}