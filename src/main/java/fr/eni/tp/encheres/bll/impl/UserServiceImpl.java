package fr.eni.tp.encheres.bll.impl;

import fr.eni.tp.encheres.bll.UserService;
import fr.eni.tp.encheres.bo.Utilisateur;
import fr.eni.tp.encheres.dal.UtilisateurDAO;
import fr.eni.tp.encheres.dto.RegisterDTO;
import fr.eni.tp.encheres.dto.UpdateDTO;
import fr.eni.tp.encheres.exception.BusinessCode;
import fr.eni.tp.encheres.exception.BusinessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private UtilisateurDAO utilisateurDAO;

    //Injection de dépendance
    public UserServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public Utilisateur getUtilisateurById(int no_utilisateur) {
        return utilisateurDAO.readId(no_utilisateur);
    }

    @Override
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurDAO.readEmail(email);
    }

    @Override
    public Utilisateur getUtilisateurByPseudo(String pseudo) {
        return utilisateurDAO.readPseudo(pseudo);
    }

    @Override
    public List<Utilisateur> readUsers() {
        return utilisateurDAO.findUsers();
    }


    @Override
    public void createUtilisateur(RegisterDTO registerDTO) {
        boolean isValid =true;
        BusinessException businessException = new BusinessException();

        isValid = isLastnameValid(registerDTO.getLastname(), businessException);
        isValid &= isFirstnameValid(registerDTO.getFirstname(), businessException);
        isValid &= isPseudoValid(registerDTO.getUsername(), businessException);
        isValid &= isEmailValid(registerDTO.getEmail(), businessException);
        isValid &= isPasswordValid(registerDTO.getPassword(), registerDTO.getPasswordConfirm(), businessException);
        isValid &= isStreetValid(registerDTO.getStreet(), businessException);
        isValid &= isCityValid(registerDTO.getCity(), businessException);
        isValid &= isCodePostalValid(registerDTO.getPostalCode(), businessException);
        isValid &= isPhoneValid(registerDTO.getPhone(), businessException);

        if (isValid) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(registerDTO.getPassword());

            Utilisateur utilisateur = new Utilisateur();

            utilisateur.setMotDePasse("{bcrypt}" + hashedPassword);
            utilisateur.setNom(registerDTO.getLastname());
            utilisateur.setPrenom(registerDTO.getFirstname());
            utilisateur.setPseudo(registerDTO.getUsername());
            utilisateur.setEmail(registerDTO.getEmail());
            utilisateur.setRue(registerDTO.getStreet());
            utilisateur.setCodePostal(registerDTO.getPostalCode());
            utilisateur.setVille(registerDTO.getCity());
            utilisateur.setTelephone(registerDTO.getPhone());
            utilisateur.setActif(true);
            utilisateur.setAdministrateur(false);

            utilisateurDAO.createUser(utilisateur);

        } else {
            throw businessException;
        }
    }

    @Override
    public void updateUtilisateur(UpdateDTO updateDTO) {
        boolean isValid = true;
        BusinessException businessException = new BusinessException();

        // Charger l'utilisateur existant
        Utilisateur utilisateur = utilisateurDAO.readId(updateDTO.getNo_utilisateur());
        if (utilisateur == null) {
            businessException.addKey(BusinessCode.UTILISATEUR_INEXISTANT);
            throw businessException;
        }

        // Validations classiques
        isValid &= isLastnameValid(updateDTO.getLastname(), businessException);
        isValid &= isFirstnameValid(updateDTO.getFirstname(), businessException);
        isValid &= isStreetValid(updateDTO.getStreet(), businessException);
        isValid &= isCityValid(updateDTO.getCity(), businessException);
        isValid &= isCodePostalValid(updateDTO.getPostalCode(), businessException);
        isValid &= isPhoneValid(updateDTO.getPhone(), businessException);

        // Vérifier l’unicité du pseudo seulement s’il change
        if (!updateDTO.getUsername().equals(utilisateur.getPseudo())) {
            if (!utilisateurDAO.isUserPseudoUnique(updateDTO.getUsername())) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_UTILISATEUR_PSEUDO_ALREADY_EXISTS);
            }
        }

        // Vérifier l’unicité de l’email seulement s’il change
        if (!updateDTO.getEmail().equals(utilisateur.getEmail())) {
            if (!utilisateurDAO.isUserEmailUnique(updateDTO.getEmail())) {
                isValid = false;
                businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL_ALREADY_EXISTS);
            }
        }

        // Vérifications de format pour email et pseudo
        isValid &= isPseudoValidForUpdate(updateDTO.getUsername(), updateDTO.getNo_utilisateur(), businessException);
        isValid &= isEmailValidForUpdate(updateDTO.getEmail(), updateDTO.getNo_utilisateur(), businessException);

        if (!isValid) {
            System.out.println("Erreurs de validation détectées :");
            businessException.getKeys().forEach(System.out::println);
            throw businessException;
        }


        // Mise à jour des données
        utilisateur.setNom(updateDTO.getLastname());
        utilisateur.setPrenom(updateDTO.getFirstname());
        utilisateur.setPseudo(updateDTO.getUsername());
        utilisateur.setEmail(updateDTO.getEmail());
        utilisateur.setRue(updateDTO.getStreet());
        utilisateur.setCodePostal(updateDTO.getPostalCode());
        utilisateur.setVille(updateDTO.getCity());
        utilisateur.setTelephone(updateDTO.getPhone());
        utilisateur.setActif(true); // facultatif si tu veux toujours forcer à true
        utilisateur.setAdministrateur(false); // idem

        utilisateurDAO.updateUser(utilisateur);

        System.out.println("Utilisateur mis à jour avec succès");
    }


    @Override
    public void desactivateUtilisateur(int no_utilisateur) {

    }

    @Override
    public void deleteUtilisateur(int no_utilisateur) {
        Utilisateur utilisateur = utilisateurDAO.readId(no_utilisateur);

        if (utilisateur == null) {
            BusinessException businessException = new BusinessException();
            businessException.addKey(BusinessCode.UTILISATEUR_INEXISTANT);
            throw businessException;
        }
        utilisateurDAO.deleteUser(no_utilisateur);
    }


    /**
     *
     * Les fonctions pour valider la création d'un utilisateur, elle servent aussi pour l'update
     *
     */

    private boolean isLastnameValid(String lastname, BusinessException businessException) {
        boolean isValid = true;
        if (lastname == null || lastname.isBlank()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_LASTNAME);
            System.out.println("lastname is empty youpi");
        }
        if (lastname.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_LASTNAME_LENGHT_MAX);
        }
        return isValid;
    }

    private boolean isFirstnameValid(String firstname, BusinessException businessException) {
        boolean isValid = true;
        if (firstname == null || firstname.isEmpty()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_FIRSTNAME);
        }
        if (firstname.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_FIRSTNAME_LENGHT_MAX);
        }
        return isValid;
    }

    private boolean isPseudoValid(String pseudo, BusinessException businessException) {
        boolean isValid = true;
        Utilisateur pseudoUtilisateur = utilisateurDAO.readPseudo(pseudo);
        if (pseudo == null || pseudo.isEmpty()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PSEUDO);
        }
        if (pseudo.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PSEUDO_LENGHT_MAX);
        }
        if (!utilisateurDAO.isUserPseudoUnique(pseudo)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PSEUDO_ALREADY_EXISTS);
        }
        return isValid;
    }

    private boolean isEmailValid(String email, BusinessException businessException) {
        boolean isValid = true;
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);//compile la regex en objet
        Matcher matcher = pattern.matcher(email);//teste la regex compilée
        Utilisateur emailUtilisateur = utilisateurDAO.readEmail(email);
        if (email == null || email.isEmpty()) {//même si l'IDE dit qu'il ne sera jamais nul ça n'empêche pas de le vérifier
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL);
        }
        if (!matcher.matches()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL_REGEX);
        }
        if (email.length() > 50) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL_LENGHT_MAX);
        }
        if (!utilisateurDAO.isUserEmailUnique(email)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL_ALREADY_EXISTS);
        }

        return isValid;
    }

    private boolean isPasswordValid(String password, String passwordConfirm, BusinessException businessException) {
        boolean isValid = true;
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        if (password == null || password.isEmpty()) {//idem que pour l'email
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PASSWORD);
        }
        if (!matcher.matches()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PASSWORD_REGEX);
        }
        if (password.length() > 68) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PASSWORD_LENGHT_MAX);
        }
        if (!password.equals(passwordConfirm)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PASSWORD_CONFIRM);
        }

        return isValid;
    }

    private boolean isStreetValid(String street, BusinessException businessException) {
        boolean isValid = true;

        if (street == null || street.isEmpty()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_RUE);
        }
        if (street.length() > 50) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_RUE_LENGHT_MAX);
        }
        return isValid;
    }

    private boolean isCityValid(String city, BusinessException businessException) {
        boolean isValid = true;
        if (city == null || city.isEmpty()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_VILLE);
        }
        if (city.length() > 50) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_VILLE_LENGHT_MAX);
        }
        return isValid;
    }

    private boolean isCodePostalValid(String postalCode, BusinessException businessException) {
        boolean isValid = true;
        String postalRegex = "^(0[1-9]|[1-8][0-9]|9[0-8])[0-9]{3}$";
        Pattern pattern = Pattern.compile(postalRegex);
        Matcher matcher = pattern.matcher(postalCode);
        if (postalCode == null || postalCode.isEmpty()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_CODE_POSTAL);
        }
        if (!matcher.matches()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_CODE_POSTAL_REGEX);
        }
        return isValid;
    }

    private boolean isPhoneValid(String phone, BusinessException businessException) {
        boolean isValid = true;

        if (phone == null || phone.isEmpty()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_TELEPHONE);
            return false; // Inutile de continuer si déjà vide
        }

        // Nettoyage : on enlève espaces, points et tirets
        String cleanedPhone = phone.replaceAll("[ .-]", "");

        // Validation du format : commence par 0 + 9 chiffres (ex: 0601020304)
        Pattern pattern = Pattern.compile("^0[1-9][0-9]{8}$");
        Matcher matcher = pattern.matcher(cleanedPhone);

        if (!matcher.matches()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_TELEPHONE_REGEX);
        }

        // Longueur stricte (après nettoyage)
        if (cleanedPhone.length() > 10) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_TELEPHONE_LENGHT_MAX);
        }

        return isValid;
    }

    private boolean isPasswordChanged(String password, String passwordConfirm, BusinessException businessException) {
        boolean isValid = true;

        // Si les deux champs sont vides, pas de validation à faire (pas de changement demandé)
        if ((password == null || password.isBlank()) && (passwordConfirm == null || passwordConfirm.isBlank())) {
            return true;
        }

        // Si l’un des deux est vide mais pas l’autre
        if ((password == null || password.isBlank()) || (passwordConfirm == null || passwordConfirm.isBlank())) {
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PASSWORD);
            return false;
        }

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PASSWORD_REGEX);
        }
        if (password.length() > 68) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PASSWORD_LENGHT_MAX);
        }
        if (!password.equals(passwordConfirm)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PASSWORD_CONFIRM);
        }

        return isValid;
    }

    private boolean isPseudoValidForUpdate(String pseudo, int id, BusinessException businessException) {
        boolean isValid = true;

        if (pseudo == null || pseudo.isEmpty()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PSEUDO);
        }
        if (pseudo.length() > 30) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PSEUDO_LENGHT_MAX);
        }
        if (!utilisateurDAO.isUserPseudoUniqueExceptCurrent(pseudo, id)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_PSEUDO_ALREADY_EXISTS);
        }

        return isValid;
    }


    private boolean isEmailValidForUpdate(String email, int id, BusinessException businessException) {
        boolean isValid = true;
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if (email == null || email.isEmpty()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL);
        }
        if (!matcher.matches()) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL_REGEX);
        }
        if (email.length() > 50) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL_LENGHT_MAX);
        }
        if (!utilisateurDAO.isUserEmailUniqueForUpdate(email, id)) {
            isValid = false;
            businessException.addKey(BusinessCode.VALID_UTILISATEUR_EMAIL_ALREADY_EXISTS);
        }
        return isValid;
    }


}
