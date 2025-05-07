package fr.eni.tp.encheres.dal.impl;

import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.UtilisateurDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class UtilisateurDAOImpl implements UtilisateurDAO {


        private JdbcTemplate jdbcTemplate;
        private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

        /**
         *
         * Les requêtes SQL
         *
         */
        private static final String SELECT_BY_MAIL = "SELECT no_utilisateur, UTILISATEURS.pseudo, UTILISATEURS.nom, UTILISATEURS.prenom, UTILISATEURS.administrateur, UTILISATEURS.rue, UTILISATEURS.code_postal, UTILISATEURS.ville, UTILISATEURS.email, UTILISATEURS.telephone, UTILISATEURS.credit, UTILISATEURS.actif FROM UTILISATEURS WHERE UTILISATEURS.email = :email;";
        private static final String SELECT_BY_ID="SELECT no_utilisateur, UTILISATEURS.pseudo, UTILISATEURS.nom, UTILISATEURS.prenom, UTILISATEURS.administrateur, UTILISATEURS.rue, UTILISATEURS.code_postal, UTILISATEURS.ville, UTILISATEURS.email, UTILISATEURS.telephone, UTILISATEURS.credit, UTILISATEURS.actif FROM UTILISATEURS WHERE UTILISATEURS.no_utilisateur = :no_utilisateur;";
        private static final String SELECT_BY_PSEUDO="SELECT no_utilisateur, pseudo, nom, prenom, administrateur, rue, code_postal, ville, email, telephone, credit, actif " +
            "FROM UTILISATEURS WHERE pseudo = :pseudo;";
        private static final String SELECT_ALL_USERS="SELECT no_utilisateur, UTILISATEURS.pseudo, UTILISATEURS.nom, UTILISATEURS.prenom, UTILISATEURS.administrateur, UTILISATEURS.rue, UTILISATEURS.code_postal, UTILISATEURS.ville, UTILISATEURS.email, UTILISATEURS.telephone, UTILISATEURS.credit, UTILISATEURS.actif FROM UTILISATEURS";
        private static final String SELECT_USERS_BUYERS="SELECT no_utilisateur, UTILISATEURS.pseudo, UTILISATEURS.nom, UTILISATEURS.prenom, UTILISATEURS.administrateur, UTILISATEURS.rue, UTILISATEURS.code_postal, UTILISATEURS.ville, UTILISATEURS.email, UTILISATEURS.telephone, UTILISATEURS.credit, UTILISATEURS.actif FROM UTILISATEURS INNER JOIN ROLES ON ROLES.no_utilisateur = UTILISATEURS.no_utilisateur WHERE ROLES.role_utilisateur='acheteur';";
        private static final String INSERT_USER_INTO = "INSERT INTO UTILISATEURS (pseudo, nom,mot_de_passe, prenom, rue, code_postal, ville, email, telephone) VALUES (:pseudo, :nom, :mot_de_passe,:prenom, :rue, :code_postal, :ville, :email, :telephone);";
    private static final String UPDATE_USER_INTO =
            "UPDATE UTILISATEURS SET " +
                    "pseudo = :pseudo, " +
                    "nom = :nom, " +
                    "prenom = :prenom, " +
                    "administrateur = :administrateur, " +
//                    "mot_de_passe = :mot_de_passe, " +
                    "rue = :rue, " +
                    "code_postal = :code_postal, " +
                    "ville = :ville, " +
                    "email = :email, " +
                    "telephone = :telephone, " +
                    "credit = :credit, " +
                    "actif = :actif " +
                    "WHERE no_utilisateur = :no_utilisateur";

    private static final String DELETE_USER_BY_ID="DELETE FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur;";
    private static final String DELETE_SOME_CREDIT="UPDATE UTILISATEUR SET credit = credit - :montant_enchere WHERE no_utilisateur = :no_utilisateur;";//faudra implémenter montant_enchere dans le service enchere pour le récupérer
    private static final String ADD_SOME_CREDIT="UPDATE UTILISATEUR SET credit = credit + :montant_enchere WHERE no_utilisateur = :no_utilisateur;";//idem faudra implétmenter montant_enchere dans le service enchere


        /**
         *
         * @param jdbcTemplate
         * @param namedParameterJdbcTemplate
         */
        public UtilisateurDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        }



    @Override
    public void createUser(Utilisateur utilisateur) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", utilisateur.getPseudo());
        mapSqlParameterSource.addValue("nom", utilisateur.getNom());
        mapSqlParameterSource.addValue("mot_de_passe", utilisateur.getMotDePasse());
        mapSqlParameterSource.addValue("prenom", utilisateur.getPrenom());
        mapSqlParameterSource.addValue("rue", utilisateur.getRue());
        mapSqlParameterSource.addValue("code_postal", utilisateur.getCodePostal());
        mapSqlParameterSource.addValue("ville", utilisateur.getVille());
        mapSqlParameterSource.addValue("email", utilisateur.getEmail());
        mapSqlParameterSource.addValue("telephone", utilisateur.getTelephone());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = namedParameterJdbcTemplate.update(
                INSERT_USER_INTO,
                mapSqlParameterSource,
                keyHolder,
                new String[]{"no_utilisateur"}
        );

        if (rowsAffected > 0 && keyHolder.getKey() != null) {
            utilisateur.setNoUtilisateur(keyHolder.getKey().intValue());
        } else {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur : aucun ID généré.");
        }
    }

    @Override
    public Utilisateur readId(int no_utilisateur) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", no_utilisateur);

        try {
            return namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_ID,
                    mapSqlParameterSource,
                    new UtilisateurRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // Aucun utilisateur trouvé avec cet ID
        }
    }

    @Override
    public Utilisateur readPseudo(String pseudo) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", pseudo);

        try {
            return namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_PSEUDO,
                    mapSqlParameterSource,
                    new UtilisateurRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // Aucun utilisateur trouvé → pseudo libre
        }
    }


    @Override
    public Utilisateur readEmail(String email) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", email);

        try {
            return namedParameterJdbcTemplate.queryForObject(
                    SELECT_BY_MAIL,
                    mapSqlParameterSource,
                    new UtilisateurRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null; // Aucun utilisateur trouvé avec cet email
        }
    }


    @Override
    public void updateUser(Utilisateur utilisateur) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", utilisateur.getNoUtilisateur());
        mapSqlParameterSource.addValue("pseudo", utilisateur.getPseudo());
        mapSqlParameterSource.addValue("nom", utilisateur.getNom());
        mapSqlParameterSource.addValue("prenom", utilisateur.getPrenom());
        mapSqlParameterSource.addValue("administrateur", utilisateur.isAdministrateur());//boolean
        mapSqlParameterSource.addValue("mot_de_passe", utilisateur.getMotDePasse());
        mapSqlParameterSource.addValue("rue", utilisateur.getRue());
        mapSqlParameterSource.addValue("code_postal", utilisateur.getCodePostal());
        mapSqlParameterSource.addValue("ville", utilisateur.getVille());
        mapSqlParameterSource.addValue("email", utilisateur.getEmail());
        mapSqlParameterSource.addValue("telephone", utilisateur.getTelephone());
        mapSqlParameterSource.addValue("credit", utilisateur.getCredit());
        mapSqlParameterSource.addValue("actif", utilisateur.isActif());//boolean

        //pas de keyHolder car on màj un user qui est déjà présent en BDD

        namedParameterJdbcTemplate.update(
                UPDATE_USER_INTO,
                mapSqlParameterSource
        );

    }

    @Override
    public void deleteUser(int noUtilisateur) {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
            mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
        namedParameterJdbcTemplate.update(
                DELETE_USER_BY_ID,
                mapSqlParameterSource
        );
    }

    @Override
    public boolean isUserPseudoUnique(String pseudo) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("pseudo", pseudo);
//        mapSqlParameterSource.addValue("no_utilisateur", utilisateur.getNoUtilisateur());

        List <Utilisateur> users = namedParameterJdbcTemplate.query(
                SELECT_BY_PSEUDO,
                mapSqlParameterSource,
                new BeanPropertyRowMapper<>(Utilisateur.class)
        );
        return users.isEmpty();
    }

    @Override
    public boolean isUserEmailUnique(String email) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", email);

        List <Utilisateur> users = namedParameterJdbcTemplate.query(
                SELECT_BY_MAIL,
                mapSqlParameterSource,
                new BeanPropertyRowMapper<>(Utilisateur.class)
        );
        return users.isEmpty();
    }

    @Override
    public boolean isUserPseudoUniqueForUpdate(String pseudo, int id) {
        String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = :pseudo AND no_utilisateur != :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("pseudo", pseudo)
                .addValue("id", id);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count == 0;
    }

    public boolean isUserPseudoUniqueExceptCurrent(String pseudo, int id) {
        String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = :pseudo AND no_utilisateur != :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("pseudo", pseudo);
        params.addValue("id", id);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count == 0;
    }

    @Override
    public List<Utilisateur> searchByPseudoOrEmail(String query) {
        String sql = "SELECT * FROM UTILISATEURS WHERE pseudo LIKE :q OR email LIKE :q";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("q", "%" + query + "%");

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Utilisateur.class));
    }



    @Override
    public boolean isUserEmailUniqueForUpdate(String email, int id) {
        String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = :email AND no_utilisateur != :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("id", id);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
        return count == 0;
    }


    @Override
    public List<Utilisateur> findUsers() {
        return namedParameterJdbcTemplate.query(
                SELECT_ALL_USERS,
                new BeanPropertyRowMapper<>(Utilisateur.class)
        );
    }

    @Override
    public List<Utilisateur> findUsersBuyers() {
        return namedParameterJdbcTemplate.query(
                SELECT_USERS_BUYERS,
                new BeanPropertyRowMapper<>(Utilisateur.class)
        );
    }

    /**
     * TODO faire le test voir si ça fonctionne
     * @param noUtilisateur
     * @param credit
     */
    @Override
    public void deleteCredit(int noUtilisateur, int credit) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
        mapSqlParameterSource.addValue("credit", credit);
        namedParameterJdbcTemplate.update(
                DELETE_SOME_CREDIT,
                mapSqlParameterSource
        );
    }

    /**
     * TODO faire le test voir si ça fonctionne
     * @param noUtilisateur
     * @param credit
     */
    @Override
    public void updateCredit(int noUtilisateur, int credit) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("no_utilisateur", noUtilisateur);
        mapSqlParameterSource.addValue("credit", credit);
        namedParameterJdbcTemplate.update(
                ADD_SOME_CREDIT,
                mapSqlParameterSource
        );
    }


}

class UtilisateurRowMapper implements RowMapper<Utilisateur> {

    @Override
    public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
        utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
        utilisateur.setPseudo(rs.getString("pseudo"));
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setRue(rs.getString("rue"));
        utilisateur.setCodePostal(rs.getString("code_postal"));
        utilisateur.setVille(rs.getString("ville"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setTelephone(rs.getString("telephone"));
        utilisateur.setCredit(rs.getInt("credit"));
        utilisateur.setActif(rs.getBoolean("actif"));

        return utilisateur;

    }

}

