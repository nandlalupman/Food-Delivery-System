import dao.UserDAO;
import dao.FoodItemDAO;
import dao.OrderDAO;
import db.DBConnection;
import model.FoodItem;
import model.Order;
import model.User;

import java.util.List;
import java.util.Scanner;

/**
 * Main class - Entry point of the Food Delivery System.
 * Uses Scanner for console-based input/output.
 */
public class Main {

    // DAO objects for database operations
    static UserDAO userDAO = new UserDAO();
    static FoodItemDAO foodItemDAO = new FoodItemDAO();
    static OrderDAO orderDAO = new OrderDAO();
    static Scanner scanner = new Scanner(System.in);

    // Currently logged-in user (null = not logged in)
    static User loggedInUser = null;

    public static void main(String[] args) {

        // Step 1: Initialize database (create tables if not exist)
        DBConnection.initializeDatabase();

        System.out.println("========================================");
        System.out.println("   Welcome to Food Delivery System");
        System.out.println("========================================");

        // Step 2: Show main menu in a loop
        while (true) {

            if (loggedInUser == null) {
                // --- Not logged in: show login/register menu ---
                showLoginMenu();
            } else if (loggedInUser.getName().equals("admin")) {
                // --- Admin menu ---
                showAdminMenu();
            } else {
                // --- Regular user menu ---
                showUserMenu();
            }
        }
    }

    // ==========================================
    //           LOGIN / REGISTER MENU
    // ==========================================
    static void showLoginMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Thank you! Goodbye.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice! Try again.");
        }
    }

    // ==========================================
    //            USER REGISTRATION
    // ==========================================
    static void register() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        userDAO.registerUser(name, password);
    }

    // ==========================================
    //              USER LOGIN
    // ==========================================
    static void login() {
        System.out.print("Enter username: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userDAO.loginUser(name, password);

        if (user != null) {
            loggedInUser = user;
            System.out.println("Login successful! Welcome, " + user.getName() + "!");
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    // ==========================================
    //          REGULAR USER MENU
    // ==========================================
    static void showUserMenu() {
        System.out.println("\n--- User Menu ---");
        System.out.println("1. View Food Menu");
        System.out.println("2. Place Order");
        System.out.println("3. View My Orders");
        System.out.println("4. Logout");
        System.out.print("Enter choice: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                viewMenu();
                break;
            case 2:
                placeOrder();
                break;
            case 3:
                viewMyOrders();
                break;
            case 4:
                loggedInUser = null;
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice! Try again.");
        }
    }

    // ==========================================
    //             VIEW FOOD MENU
    // ==========================================
    static void viewMenu() {
        List<FoodItem> items = foodItemDAO.getAllFoodItems();

        if (items.isEmpty()) {
            System.out.println("No food items available.");
            return;
        }

        System.out.println("\n--- Food Menu ---");
        System.out.printf("%-5s %-20s %-10s%n", "ID", "Name", "Price");
        System.out.println("-------------------------------------");

        for (FoodItem item : items) {
            System.out.printf("%-5d %-20s Rs.%-10.2f%n",
                    item.getId(), item.getName(), item.getPrice());
        }
    }

    // ==========================================
    //             PLACE ORDER
    // ==========================================
    static void placeOrder() {
        viewMenu();

        List<FoodItem> items = foodItemDAO.getAllFoodItems();
        if (items.isEmpty()) return;

        double total = 0;
        System.out.println("\nEnter food item IDs to order (type 0 to finish):");

        while (true) {
            System.out.print("Item ID: ");
            int itemId = readInt();

            if (itemId == 0) break;

            FoodItem item = foodItemDAO.getFoodItemById(itemId);
            if (item != null) {
                total += item.getPrice();
                System.out.println("Added: " + item.getName() + " (Rs." + item.getPrice() + ")");
            } else {
                System.out.println("Invalid item ID! Try again.");
            }
        }

        if (total > 0) {
            orderDAO.placeOrder(loggedInUser.getId(), total);
        } else {
            System.out.println("No items selected. Order cancelled.");
        }
    }

    // ==========================================
    //           VIEW MY ORDERS
    // ==========================================
    static void viewMyOrders() {
        List<Order> orders = orderDAO.getOrdersByUserId(loggedInUser.getId());

        if (orders.isEmpty()) {
            System.out.println("You have no orders yet.");
            return;
        }

        System.out.println("\n--- My Orders ---");
        System.out.printf("%-10s %-15s%n", "Order ID", "Total Price");
        System.out.println("---------------------------");

        for (Order order : orders) {
            System.out.printf("%-10d Rs.%-15.2f%n", order.getId(), order.getTotalPrice());
        }
    }

    // ==========================================
    //             ADMIN MENU
    // ==========================================
    static void showAdminMenu() {
        System.out.println("\n--- Admin Menu ---");
        System.out.println("1. View Food Menu");
        System.out.println("2. Add Food Item");
        System.out.println("3. Logout");
        System.out.print("Enter choice: ");

        int choice = readInt();

        switch (choice) {
            case 1:
                viewMenu();
                break;
            case 2:
                addFoodItem();
                break;
            case 3:
                loggedInUser = null;
                System.out.println("Admin logged out!");
                break;
            default:
                System.out.println("Invalid choice! Try again.");
        }
    }

    // ==========================================
    //         ADD FOOD ITEM (ADMIN)
    // ==========================================
    static void addFoodItem() {
        System.out.print("Enter food name: ");
        String name = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = readDouble();

        foodItemDAO.addFoodItem(name, price);
    }

    // ==========================================
    //          HELPER: Read integer safely
    // ==========================================
    static int readInt() {
        try {
            int value = Integer.parseInt(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            return -1; // invalid input
        }
    }

    // ==========================================
    //          HELPER: Read double safely
    // ==========================================
    static double readDouble() {
        try {
            double value = Double.parseDouble(scanner.nextLine().trim());
            return value;
        } catch (NumberFormatException e) {
            return -1; // invalid input
        }
    }
}
