package model;

import java.io.Serializable;


/**
 * This class is used to store information (id, nickname, email, password) of a
 * user
 */
public class User implements Serializable {
    private String nickname;
    private String email;
    private String password;
    private String code;

    /**
     * This is the constructor of the {@link User} class
     * @param nickname the nickname of the user to be created
     * @param email the email of the user to be created
     * @param password the password of the user to be created
     */
    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
