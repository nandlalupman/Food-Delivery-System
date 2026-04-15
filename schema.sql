-- =============================================
-- Food Delivery System - Database Schema
-- =============================================

-- Table 1: Users
CREATE TABLE IF NOT EXISTS Users (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    name     TEXT    NOT NULL UNIQUE,
    password TEXT    NOT NULL
);

-- Table 2: FoodItems
CREATE TABLE IF NOT EXISTS FoodItems (
    id    INTEGER PRIMARY KEY AUTOINCREMENT,
    name  TEXT    NOT NULL,
    price REAL    NOT NULL
);

-- Table 3: Orders
CREATE TABLE IF NOT EXISTS Orders (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id     INTEGER NOT NULL,
    total_price REAL    NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Insert a default admin user (password: admin123)
INSERT OR IGNORE INTO Users (name, password) VALUES ('admin', 'admin123');

-- Insert some sample food items
INSERT INTO FoodItems (name, price) VALUES ('Burger',  120.00);
INSERT INTO FoodItems (name, price) VALUES ('Pizza',   250.00);
INSERT INTO FoodItems (name, price) VALUES ('Pasta',   180.00);
INSERT INTO FoodItems (name, price) VALUES ('Sandwich', 90.00);
INSERT INTO FoodItems (name, price) VALUES ('Coffee',   60.00);
