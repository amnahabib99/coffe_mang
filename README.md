# Coffee Shop Management System

A Java Maven Swing application for managing daily cafe operations: registration, login, password recovery, categories, products, orders, invoices, user verification, and reports.

## Technologies

- Java 17
- Maven
- Swing
- MySQL
- JDBC
- Javadoc

## MySQL Setup

Install and start MySQL locally. The default connection is configured in `DatabaseConfig`:

- Host: `localhost`
- Port: `3306`
- Database: `coffee_shop_db`
- Username: `root`
- Password: empty

Edit `src/main/java/com/coffeeshop/database/DatabaseConfig.java` if your MySQL password is different.

The app creates the database, tables, and seed data automatically.

## Default Login

- Username: `admin`
- Password: `admin123`

## Run in VS Code

Open this folder in VS Code, make sure Java and Maven are available, then run:

```bash
mvn clean compile
mvn exec:java
```

## Generate Javadocs

```bash
mvn javadoc:javadoc
```

Generated docs will be in `target/site/apidocs`.

## Implemented Use Cases

1. Registration
2. Login
3. Forgot password
4. Change password
5. Change security question
6. Role-based home screen
7. Category management
8. Product management
9. Add new product
10. Complete order
11. User verification / management
12. View invoices and order details

## OOP Concepts

- Encapsulation: private fields with getters/setters in all models.
- Abstraction: `Person` abstract class.
- Inheritance: `User`, `Manager`, `Employee`, `Customer`, and `VIPCustomer`.
- Interfaces: `Authenticatable`, `Manageable`, `Repository`, `Printable`, `Payable`, `Discountable`.
- Polymorphism: Reports panel lists different `Person` references and calls overridden methods.
- Overriding: `getRoleDescription`, `toString`, invoice printing, discount calculation.
- Overloading: constructors in `User`, `Product`, `Order`, `Customer`; methods in services/repositories.
- Static: `AppConstants`, `ValidationUtils`, `DatabaseConfig`, `SessionManager`.
- Inner class: `Order.OrderItem` represents order line items.
- Exception handling: custom exceptions handled in services, repositories, and GUI dialogs.

## Suggested Git Commits

1. Initial Maven project structure
2. Add database configuration and schema initializer
3. Add core OOP models
4. Add interfaces and abstract classes
5. Add repositories
6. Add services and validation
7. Add authentication screens
8. Add category and product GUI
9. Add order and invoice GUI
10. Add user management and account settings
11. Add exception handling
12. Add Javadocs and final cleanup
