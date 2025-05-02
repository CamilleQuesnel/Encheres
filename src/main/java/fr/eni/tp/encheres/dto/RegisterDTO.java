package fr.eni.tp.encheres.dto;

public class RegisterDTO {
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

    public RegisterDTO() {
    }

    public RegisterDTO(String lastname, String firstname, String username, String email, String street, String city, String postalCode, String phone, String password, String passwordConfirm) {
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
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
