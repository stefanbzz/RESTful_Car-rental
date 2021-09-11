package ch.bzz.carRental.model;

/**
 * Klasse für ein User
 *
 * @author Stefan Thomsen
 * @since 31.3.2021
 * @version 1.0
 *
 * inspiriert durch Marcel Suter (Ghwalin)
 */
public class User {
    private String userUUID;
    private String username;
    private String password;
    private String role;

    /**
     * Leerer Konstruktor eines User
     */
    public User(){
        setRole("guest");
    }

    /**
     * getter für eine userUUID
     *
     * @return die userUUID
     */
    public String getUserUUID() {
        return userUUID;
    }

    /**
     * setter für eine UserUUID
     *
     * @param userUUID des Users
     */
    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    /**
     * getter für einen username
     *
     * @return den usernamen
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter für eine Usernamen
     *
     * @param username des User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter für ein Passwort
     *
     * @return user passwort
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter für ein Passwort
     *
     * @param password des User
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter für eine Rolle
     * Bsp. Admin oder guest
     *
     * @return rolle des Users
     */
    public String getRole() {
        return role;
    }

    /**
     * setter für eine Rolle
     * Bsp. Admin oder guest
     *
     * @param role des Users
     */
    public void setRole(String role) {
        this.role = role;
    }

}
