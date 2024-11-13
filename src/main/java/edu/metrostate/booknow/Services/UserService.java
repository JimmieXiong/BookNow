package edu.metrostate.booknow.Services;

import edu.metrostate.booknow.DAO.UserDAO;
import edu.metrostate.booknow.Utils.DBConnection;

import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    // Default constructor initializes UserDAO with a DBConnection
    public UserService() {
        this.userDAO = new UserDAO(new DBConnection());
    }

    // Constructor for dependency injection
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(String username, String password) throws SQLException {
        return userDAO.login(username, password);
    }

    public boolean createAccount(String username, String password) throws SQLException {
        return userDAO.createAccount(username, password);
    }

}
