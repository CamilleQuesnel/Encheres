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

    public UpdateDTO(String lastname, String firstname, String username, String email, String street, String city, String postalCode, String phone, String password, String passwordConfirm) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.email = email;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public UpdateDTO(int no_utilisateur, String lastname, String firstname, String username, String email, String street, String city, String postalCode, String phone, String password, String passwordConfirm) {
        this.no_utilisateur = no_utilisateur;
        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;
        this.email = email;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
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

    public void setNo_utilisateur(int no_utilisateur) {
        this.no_utilisateur = no_utilisateur;
    }
}
