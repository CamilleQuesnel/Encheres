package fr.eni.tp.encheres.dto;

/**
 * Sert pour que l'utilisateur puisse modifier les champs
 */
public class UpdateDTO {
    private int no_utilisateur;
    private String lastname;
    private String firstname;
    private String username;
    private String email;
    private String street;
    private String city;
    private String postalCode;
    private String phone;
    private String password;
    private String passwordConfirm;

    public UpdateDTO() {
    }

    /**
     * Pas d'id dedans, il ne sert qu'à rechercher l'id utilisateur, il n'y a pas de setter pour l'id non plus
     * @param passwordConfirm
     * @param password
     * @param phone
     * @param postalCode
     * @param city
     * @param street
     * @param email
     * @param username
     * @param firstname
     * @param lastname
     */
    public UpdateDTO(String passwordConfirm, String password, String phone, String postalCode, String city, String street, String email, String username, String firstname, String lastname) {
        this.passwordConfirm = passwordConfirm;
        this.password = password;
        this.phone = phone;
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
        this.email = email;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getNo_utilisateur() {
        return no_utilisateur;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String toString() {
        return "UpdateDTO{" +
                "lastname='" + lastname + '\'' +
                ", firstname='" + firstname + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                '}';
    }
}
