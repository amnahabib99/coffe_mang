# Coffee Shop Management System

Java desktop application for managing daily coffee shop operations. The system supports account access, category and product management, order completion, invoices, user management, and operational reports through a Swing graphical interface connected to a MySQL database.

## Technologies Used

- Java 17
- Maven
- Swing GUI
- MySQL
- JDBC
- Javadoc
- Git/GitHub

## Project Structure

- `com.coffeeshop`: application entry point.
- `com.coffeeshop.abstracts`: shared abstract domain classes.
- `com.coffeeshop.models`: business entities such as users, products, customers, orders, and invoices.
- `com.coffeeshop.interfaces`: behavior contracts used by services and models.
- `com.coffeeshop.enums`: fixed values for roles, statuses, and customer types.
- `com.coffeeshop.repositories`: JDBC database access classes.
- `com.coffeeshop.services`: validation and business logic.
- `com.coffeeshop.database`: MySQL configuration, connections, and schema initialization.
- `com.coffeeshop.gui`: Swing frames, dialogs, panels, and shared UI theme.
- `com.coffeeshop.exceptions`: custom exception types.
- `com.coffeeshop.utils`: constants, validation helpers, session storage, and password comparison.

## Implemented Use Cases

1. Registration: managers can create user accounts from the user management screen.
2. Login: users sign in through `LoginFrame`.
3. Forgot Password: users reset passwords using their security question and answer.
4. Change Password: logged-in users can update their own password.
5. Change Security Question: logged-in users can update security question details.
6. Home/Main Screen: `MainFrame` shows role-based navigation.
7. Category Management: managers can add, update, delete, and view categories.
8. Product Management: managers can add, update, delete, view, and filter products.
9. Add New Product: implemented inside the product management panel.
10. Complete Order: employees and managers can create orders, add products, calculate totals, and save invoices.
11. User Verification/User Management: managers can activate, deactivate, and update user roles.
12. View Invoice and Order Details: invoices and reports show saved orders and order items.

## MySQL Setup

Database name: `coffee_shop_db`

The application creates the database and tables automatically on startup through `SchemaInitializer`.

Default connection settings are stored in `DatabaseConfig`:

- Host: `localhost`
- Port: `3306`
- Username: `root`
- Database: `coffee_shop_db`

Password configuration options:

- Preferred local option: `.local-db.properties` in the project root with `db.password=your_mysql_password`.
- Environment variable: `COFFEE_DB_PASSWORD`.
- JVM property: `coffee.db.password`.

The local `.local-db.properties` file is ignored by Git and should stay only on the developer machine.

Default admin account:

- Username: `admin`
- Password: `admin123`

## Database Tables

- `users`: application users, roles, account status, and security recovery data.
- `categories`: product categories.
- `products`: coffee shop products linked to categories.
- `customers`: customer records and customer type.
- `orders`: completed order totals and cashier references.
- `order_items`: products included in each completed order.

## How to Run

From the project folder:

```powershell
mvn clean compile
mvn exec:java
```

If Maven is not available as `mvn`, use the Maven executable bundled with NetBeans:

```powershell
& "C:\Program Files\Apache NetBeans\java\maven\bin\mvn.cmd" clean compile
& "C:\Program Files\Apache NetBeans\java\maven\bin\mvn.cmd" exec:java
```

## GUI Screens

- `LoginFrame`: first screen for user login.
- `ForgotPasswordDialog`: password recovery flow.
- `MainFrame`: main application window with right-side navigation.
- `HomePanel`: role-aware home summary.
- `CategoryPanel`: category management.
- `ProductPanel`: product management and product filtering.
- `OrderPanel`: order creation, item list, total calculation, and invoice preview.
- `InvoicePanel`: order and invoice viewing with printing.
- `ReportsPanel`: sales reports based on saved orders with printing.
- `UserManagementPanel`: manager-only user verification and account management.
- `RegisterDialog`: account creation from user management.
- `AccountSettingsPanel`: password and security question settings.
- `ChangePasswordDialog`: password update form.
- `ChangeSecurityQuestionDialog`: security question update form.

## OOP Concepts Used

- Encapsulation: model fields are private and accessed through constructors, getters, and setters in `User`, `Product`, `Category`, `Customer`, `Order`, and related models.
- Abstraction: `Person` is an abstract class that defines common fields and the abstract method `getRoleDescription()`.
- Inheritance: `User` extends `Person`; `Manager` and `Employee` extend `User`; `Customer` extends `Person`; `VIPCustomer` extends `Customer`.
- Interfaces: `Authenticatable`, `Manageable<T>`, `Repository<T>`, `Printable`, `Payable`, and `Discountable`.
- Implements: `AuthService` implements `Authenticatable`; `CategoryService` and `ProductService` implement `Manageable<T>`; repositories implement `Repository<T>`; `Order` implements `Payable` and `Printable`; `VIPCustomer` implements `Discountable`.
- Polymorphism: repositories and services handle `Manager` and `Employee` through `User` references; order totals are calculated through the `Payable` interface.
- Method Overriding: `getRoleDescription()` is overridden in person subclasses; `toString()` is overridden in models; discount behavior is overridden in VIP customer logic.
- Method Overloading: overloaded service and repository methods include product creation and user lookup variations.
- Constructor Overloading: constructors appear in `User`, `Product`, `Order`, and `Customer`.
- Static Members/Methods: `AppConstants.TAX_RATE`, `DatabaseConfig` constants, `ValidationUtils` methods, and `SessionManager` current user storage.
- Inner Class: `Order.OrderItem` represents individual order lines used in totals and persistence.
- Exception Handling: custom exceptions are defined in `com.coffeeshop.exceptions` and handled in service, repository, and GUI layers.

## Sprint 2 Compliance Summary

- Version Control: the project includes `.gitignore` and is structured for Git/GitHub.
- Object-Oriented Design: abstraction, inheritance, encapsulation, polymorphism, interfaces, method overriding, method overloading, constructor overloading, static members, inner class, and exception handling are implemented in the real application classes.
- Javadoc: public classes, interfaces, enums, exceptions, services, repositories, database helpers, and GUI classes include Javadoc comments.
- Database Schema: MySQL database `coffee_shop_db` contains `users`, `categories`, `products`, `customers`, `orders`, and `order_items`.
- GUI: Swing frames, dialogs, panels, JTable views, forms, role-based navigation, and printing actions are implemented.
- Database Connection: JDBC connection logic is separated in `DatabaseConfig`, `DatabaseManager`, `SchemaInitializer`, and repository classes.
- Running System: the application compiles with Maven and runs through `mvn exec:java`.

## Generate Javadoc

```powershell
mvn javadoc:javadoc
```

Generated HTML documentation:

```text
target/site/apidocs/index.html
```

## GitHub Preparation

Suggested commit sequence:

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
12. Add Javadocs and documentation cleanup
