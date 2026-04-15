package dao;

import db.DBConnection;
import model.FoodItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for FoodItem operations.
 * Handles adding and viewing food items.
 */
public class FoodItemDAO {

    /**
     * Adds a new food item to the menu (Admin feature).
     */
    public void addFoodItem(String name, double price) {
        String sql = "INSERT INTO FoodItems (name, price) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.executeUpdate();

            System.out.println("Food item added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding food item: " + e.getMessage());
        }
    }

    /**
     * Returns a list of all food items from the database.
     */
    public List<FoodItem> getAllFoodItems() {
        List<FoodItem> items = new ArrayList<>();
        String sql = "SELECT * FROM FoodItems";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                FoodItem item = new FoodItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching food items: " + e.getMessage());
        }

        return items;
    }

    /**
     * Returns a single food item by its ID.
     * Returns null if not found.
     */
    public FoodItem getFoodItemById(int id) {
        String sql = "SELECT * FROM FoodItems WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                FoodItem item = new FoodItem();
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getDouble("price"));
                return item;
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}
