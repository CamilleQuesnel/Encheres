package fr.eni.tp.encheres.exception;

public class BusinessCode {
    //UTILISATEUR
    public static final String UTILISATEUR_INEXISTANT ="utilisateur.inexistant";
        //NOM UTILISATEUR
    public static final String VALID_UTILISATEUR_LASTNAME = "validation.utilisateur.lastname.blank";
    public static final String VALID_UTILISATEUR_LASTNAME_LENGHT_MAX = "validation.utilisateur.lastname.length.max";
        //PRENOM UTILISATEUR
    public static final String VALID_UTILISATEUR_FIRSTNAME = "validation.utilisateur.firstname.blank";
    public static final String VALID_UTILISATEUR_FIRSTNAME_LENGHT_MAX = "validation.utilisateur.firstname.length.max";
        //PSEUDO UTILISATEUR
    public static final String VALID_UTILISATEUR_PSEUDO = "validation.utilisateur.pseudo.blank";
    public static final String VALID_UTILISATEUR_PSEUDO_LENGHT_MAX = "validation.utilisateur.pseudo.length.max";
    public static final String VALID_UTILISATEUR_PSEUDO_ALREADY_EXISTS = "validation.utilisateur.pseudo.already.exists";
        //TELEPHONE UTILISATEUR
    public static final String VALID_UTILISATEUR_TELEPHONE_REGEX = "validation.utilisateur.telephone.regex";//on considère qu'il n'y aura que des num FR
    public static final String VALID_UTILISATEUR_TELEPHONE ="validation.utilisateur.telephone";
    public static final String VALID_UTILISATEUR_TELEPHONE_LENGHT_MAX = "validation.utilisateur.telephone.length.max";
        //RUE UTILISATEUR
    public static final String VALID_UTILISATEUR_RUE = "validation.utilisateur.rue.blank";
    public static final String VALID_UTILISATEUR_RUE_LENGHT_MAX = "validation.utilisateur.rue.length.max";
        //CODE POSTAL UTILISATEUR
    public static final String VALID_UTILISATEUR_CODE_POSTAL = "validation.utilisateur.code.postal";
    public static final String VALID_UTILISATEUR_CODE_POSTAL_REGEX ="validation.utilisateur.code.postal.regex";
        //VILLE UTILISATEUR
    public static final String VALID_UTILISATEUR_VILLE = "validation.utilisateur.ville.blank";
    public static final String VALID_UTILISATEUR_VILLE_LENGHT_MAX = "validation.utilisateur.ville.length.max";
        //EMAIL UTILISATEUR
    public static final String VALID_UTILISATEUR_EMAIL_REGEX = "validation.utilisateur.email.regex";
    public static final String VALID_UTILISATEUR_EMAIL_ALREADY_EXISTS = "validation.utilisateur.email.already.exists";
    public static final String VALID_UTILISATEUR_EMAIL = "validation.utilisateur.email.blank";
    public static final String VALID_UTILISATEUR_EMAIL_LENGHT_MAX = "validation.utilisateur.email.length.max";
        //MOT DE PASSE UTILISATEUR
    public static final String VALID_UTILISATEUR_PASSWORD = "validation.utilisateur.password.blank";
    public static final String VALID_UTILISATEUR_PASSWORD_REGEX = "validation.utilisateur.password.regex";
    public static final String VALID_UTILISATEUR_PASSWORD_LENGHT_MAX = "validation.utilisateur.password.length.max";
    public static final String VALID_UTILISATEUR_PASSWORD_CONFIRM = "validation.utilisateur.password.confirm";
        //CONNEXION UTILISATEUR
    public static final String CONNEXION_ERROR = "connexion.error";
}
