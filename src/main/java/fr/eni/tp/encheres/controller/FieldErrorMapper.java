package fr.eni.tp.encheres.controller;

import fr.eni.tp.encheres.exception.BusinessCode;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class FieldErrorMapper  {

    public static final Map<String, String> FIELD_MAPPING = Map.ofEntries(
            Map.entry(BusinessCode.VALID_UTILISATEUR_LASTNAME, "lastname"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_LASTNAME_LENGHT_MAX, "lastname"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_EMAIL, "email"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_EMAIL_ALREADY_EXISTS, "email"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_EMAIL_REGEX, "email"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_EMAIL_LENGHT_MAX, "email"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_PASSWORD, "password"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_PASSWORD_CONFIRM, "passwordConfirm"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_PASSWORD_LENGHT_MAX, "password"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_PASSWORD_REGEX, "password"),
//            Map.entry(BusinessCode.VALID_UTILISATEUR_PASSWORD_CONFIRM_REQUIRED, "passwordConfirm"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_VILLE_LENGHT_MAX, "city"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_VILLE, "city"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_CODE_POSTAL_REGEX, "postalCode"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_CODE_POSTAL, "postalCode"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_RUE_LENGHT_MAX, "street"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_RUE, "street"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_TELEPHONE_LENGHT_MAX, "phone"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_TELEPHONE, "phone"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_TELEPHONE_REGEX, "phone"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_PSEUDO_ALREADY_EXISTS, "username"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_PSEUDO_LENGHT_MAX, "username"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_PSEUDO, "username"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_FIRSTNAME_LENGHT_MAX, "firstname"),
            Map.entry(BusinessCode.VALID_UTILISATEUR_FIRSTNAME, "firstname")

    );

    public static String getFieldName(String errorCode) {
        return FIELD_MAPPING.get(errorCode);
    }
}
