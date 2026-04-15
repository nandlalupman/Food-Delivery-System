# Food Delivery System
### Console-Based Application using Core Java + JDBC + SQLite

---

## 📁 Project Structure

```
FoodDeliverySystem/
├── schema.sql              ← SQL queries for table creation
├── compile_and_run.bat     ← One-click compile & run script
├── README.md               ← This file
├── lib/
│   └── sqlite-jdbc-3.46.1.3.jar  ← SQLite JDBC driver (download needed)
└── src/
    ├── Main.java            ← Entry point (console menus)
    ├── model/
    │   ├── User.java        ← User POJO class
    │   ├── FoodItem.java    ← FoodItem POJO class
    │   └── Order.java       ← Order POJO class
    ├── db/
    │   └── DBConnection.java ← JDBC connection + table init
    └── dao/
        ├── UserDAO.java     ← User register/login operations
        ├── FoodItemDAO.java ← Add/view food items
        └── OrderDAO.java   ← Place/view orders
```

---

## 🗄️ Database Tables

### 1. Users Table
| Column   | Type    | Description          |
|----------|---------|----------------------|
| id       | INTEGER | Primary Key (Auto)   |
| name     | TEXT    | Username (Unique)    |
| password | TEXT    | Password             |

### 2. FoodItems Table
| Column | Type    | Description           |
|--------|---------|-----------------------|
| id     | INTEGER | Primary Key (Auto)    |
| name   | TEXT    | Food item name        |
| price  | REAL    | Price in Rupees       |

### 3. Orders Table
| Column      | Type    | Description                |
|-------------|---------|----------------------------|
| id          | INTEGER | Primary Key (Auto)         |
| user_id     | INTEGER | Foreign Key → Users(id)    |
| total_price | REAL    | Total order amount         |

---

## 🚀 Step-by-Step Instructions to Run

### Prerequisites
- **Java JDK 8+** installed ([Download](https://www.oracle.com/java/technologies/javase-downloads.html))
- Set `JAVA_HOME` and add `java`/`javac` to your system PATH

### Step 1: Download SQLite JDBC Driver

Download the SQLite JDBC JAR file from:
- **URL:** https://github.com/xerial/sqlite-jdbc/releases
- **Direct:** Download `sqlite-jdbc-3.46.1.3.jar` (or latest version)

Place the downloaded `.jar` file into the `lib/` folder:
```
FoodDeliverySystem/
└── lib/
    └── sqlite-jdbc-3.46.1.3.jar
```

### Step 2: Compile the Java Files

Open **Command Prompt** (cmd) and navigate to the project folder:

```cmd
cd C:\Users\Loq\.gemini\antigravity\scratch\FoodDeliverySystem
```

Compile all Java files:

```cmd
javac -cp "lib/*" -d out src/model/*.java src/db/*.java src/dao/*.java src/Main.java
```

This creates compiled `.class` files in the `out/` folder.

### Step 3: Run the Application

```cmd
java -cp "out;lib/*" Main
```

> **Note:** On Linux/Mac, use `:` instead of `;`:
> ```bash
> java -cp "out:lib/*" Main
> ```

### Step 4 (Alternative): Use the Batch Script

Simply double-click `compile_and_run.bat` or run:
```cmd
compile_and_run.bat
```

---

## 🎮 How to Use

### Default Admin Login
```
Username: admin
Password: admin123
```

### Flow:
1. **Start** → Main Menu appears (Register / Login / Exit)
2. **Register** → Create a new user account
3. **Login** → Enter credentials
   - If **admin** → Admin Menu (View Menu, Add Food Item)
   - If **regular user** → User Menu (View Menu, Place Order, View Orders)
4. **Place Order** → Select food items by ID, type `0` to finish
5. **Logout** → Returns to main menu

### Sample Session:
```
========================================
   Welcome to Food Delivery System
========================================
Database initialized successfully!

--- Main Menu ---
1. Register
2. Login
3. Exit
Enter choice: 2
Enter username: admin
Enter password: admin123
Login successful! Welcome, admin!

--- Admin Menu ---
1. View Food Menu
2. Add Food Item
3. Logout
Enter choice: 2
Enter food name: Biryani
Enter price: 200
Food item added successfully!
```

---

## 🔑 OOP Concepts Used (for Viva)

| Concept           | Where Used                                      |
|-------------------|-------------------------------------------------|
| **Classes**       | User, FoodItem, Order, UserDAO, FoodItemDAO, etc.|
| **Objects**       | Created using `new User()`, `new FoodItem()`, etc.|
| **Encapsulation** | Private fields + public getters/setters          |
| **Constructors**  | Default + parameterized constructors in models   |
| **Packages**      | `model`, `dao`, `db` packages for organization   |
| **Static Methods**| `DBConnection.getConnection()`, `Main` methods   |
| **Collections**   | `ArrayList<FoodItem>`, `List<Order>`              |

---

## 🔑 JDBC Concepts Used (for Viva)

| Concept              | Where Used                              |
|----------------------|-----------------------------------------|
| **DriverManager**    | `DBConnection.getConnection()`          |
| **Connection**       | Every DAO method                        |
| **Statement**        | Table creation in `DBConnection`        |
| **PreparedStatement**| All DAO methods (prevents SQL injection)|
| **ResultSet**        | Reading query results in DAOs           |
| **try-with-resources**| Auto-closing connections               |

---

## ❓ Common Viva Questions & Answers

**Q: What is JDBC?**
A: JDBC (Java Database Connectivity) is an API that allows Java programs to interact with databases using SQL queries.

**Q: Why use PreparedStatement instead of Statement?**
A: PreparedStatement prevents SQL injection attacks and is faster for repeated queries because it pre-compiles the SQL.

**Q: What is a DAO?**
A: DAO (Data Access Object) is a design pattern that separates database operations from business logic. Each table has its own DAO class.

**Q: Why SQLite instead of MySQL?**
A: SQLite is a file-based database that requires no server installation. For a simple project, it's easier to set up. The JDBC code is almost identical for MySQL — only the connection URL and driver change.

**Q: What is try-with-resources?**
A: It's a Java feature (`try (resource) { }`) that automatically closes resources like Connection, Statement, and ResultSet after use, preventing memory leaks.

---

## 🔄 Switching to MySQL (Optional)

If your college requires MySQL, change `DBConnection.java`:

```java
// Replace this:
private static final String URL = "jdbc:sqlite:food_delivery.db";

// With this:
private static final String URL = "jdbc:mysql://localhost:3306/food_delivery";
private static final String USER = "root";
private static final String PASS = "";

public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
}
```

Also download the MySQL JDBC driver (`mysql-connector-j-8.x.jar`) and change `AUTOINCREMENT` to `AUTO_INCREMENT` in the SQL, and `INTEGER` to `INT`.
