package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles database connection using JDBC with SQLite.
 * Creates tables automatically if they don't exist.
 */
public class DBConnection {

    // SQLite database file will be created in the project folder
    private static final String URL = "jdbc:sqlite:food_delivery.db";

    /**
     * Returns a connection to the SQLite database.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Creates all required tables if they don't already exist.
     * Also inserts a default admin user.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Create Users table
            stmt.execute("CREATE TABLE IF NOT EXISTS Users ("
                    + "id       INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name     TEXT    NOT NULL UNIQUE, "
                    + "password TEXT    NOT NULL)");

            // Create FoodItems table
            stmt.execute("CREATE TABLE IF NOT EXISTS FoodItems ("
                    + "id    INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name  TEXT    NOT NULL, "
                    + "price REAL    NOT NULL)");

            // Create Orders table
            stmt.execute("CREATE TABLE IF NOT EXISTS Orders ("
                    + "id          INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "user_id     INTEGER NOT NULL, "
                    + "total_price REAL    NOT NULL, "
                    + "FOREIGN KEY (user_id) REFERENCES Users(id))");

            // Insert default admin user (ignore if already exists)
            stmt.execute("INSERT OR IGNORE INTO Users (name, password) "
                    + "VALUES ('admin', 'admin123')");

            System.out.println("Database initialized successfully!");

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}
