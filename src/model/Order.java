package model;

/**
 * Represents an Order placed by a user.
 */
public class Order {
    private int id;
    private int userId;
    private double totalPrice;

    // Default constructor
    public Order() {}

    // Parameterized constructor
    public Order(int id, int userId, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString() {
        return "Order [id=" + id + ", userId=" + userId + ", totalPrice=Rs." + totalPrice + "]";
    }
}
