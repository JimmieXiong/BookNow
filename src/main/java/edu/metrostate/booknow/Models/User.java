package edu.metrostate.booknow.Models;

public class User {

    private final int userId;
    private String usernName;
    private String passWord;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.usernName = username;
        this.passWord = password;
    }

    public String getUserName() {
        return usernName;
    }
}
