package dao;

import db.DBConnection;
import model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Order operations.
 * Handles placing and viewing orders.
 */
public class OrderDAO {

    /**
     * Places a new order for the given user.
     */
    public void placeOrder(int userId, double totalPrice) {
        String sql = "INSERT INTO Orders (user_id, total_price) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setDouble(2, totalPrice);
            pstmt.executeUpdate();

            System.out.println("Order placed successfully! Total: Rs." + totalPrice);

        } catch (SQLException e) {
            System.out.println("Error placing order: " + e.getMessage());
        }
    }

    /**
     * Returns all orders for a specific user.
     */
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotalPrice(rs.getDouble("total_price"));
                orders.add(order);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }
}
