package dao;

import db.DBConnection;
import model.User;
import java.sql.*;

/**
 * Data Access Object for User operations.
 * Handles registration and login using JDBC.
 */
public class UserDAO {

    /**
     * Registers a new user in the database.
     * Returns true if successful, false if username already exists.
     */
    public boolean registerUser(String name, String password) {
        String sql = "INSERT INTO Users (name, password) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            System.out.println("Registration successful!");
            return true;

        } catch (SQLException e) {
            System.out.println("Registration failed! Username may already exist.");
            return false;
        }
    }

    /**
     * Validates user credentials and returns User object if valid.
     * Returns null if login fails.
     */
    public User loginUser(String name, String password) {
        String sql = "SELECT * FROM Users WHERE name = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
        }

        return null; // login failed
    }
}
