package edu.metrostate.booknow.Models;


public class User {

    private int userId;
    private String username;
    private String password;
    private String role;

    public User(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (role.equals("customer") || role.equals("restaurant")) {
            this.role = role;
        } else {
            throw new IllegalArgumentException("Invalid role: Must be 'customer' or 'restaurant'");
        }
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", role=" + role + "]";
    }
}
