# Implementation Guide

## Phase 1

### Instructions for Running BookNow Application

#### 1. Switch to JavaFX 21 and Java 21 (if needed):
- Ensure you have **JavaFX 21** installed.
- Update to **Java 21** if required to ensure compatibility with the project environment.
- Add VM options in edit configurations and fill out the necessary parameters (e.g., to run the main method).
  - Example VM options:
    ```
    --module-path C:\Javafx21.0.4\javafx-sdk-21.0.4\lib --add-modules javafx.controls,javafx.fxml
    ```

#### 2. Set Up MySQL Workbench:
- Download and install **MySQL 8.4.2** and **MySQL Workbench**.
- Use the following default settings for the database connection:
  - **User**: `root`
  - **Password**: `password`
  - **Database Name**: `booknow`

#### 3. Database Setup:
- Copy and paste the provided **DBScript** into MySQL Workbench and execute it.
- This script will create the necessary tables for the application to function correctly.

---

### Issues and Future Enhancements

- Any issues encountered during setup should be addressed. MacOS users may face errors that require manual troubleshooting, while the setup has been tested and works fine on Windows.
- If Mac gives an error related to internal configurations when building, create a virtual Windows machine and run the code from there.
- If you encounter an error related to some configurations, it may be due to settings specific to my environment. You will need to manually adjust these settings. If you find any, please let me know.

#### Faced Issues:
- Setting up the project and database on a Linux environment proved unnecessarily complex due to manual configurations like installing IntelliJ IDEA, setting up JavaFX, and configuring the database.
- The **BookNow** database is configured to run locally at `127.0.0.1:3306`, meaning the project depends on the database being set up on the same machine.
- Due to this dependency, the previously defined non-functional requirement for 'portability' no longer applies. Instead, the focus will shift to **Maintainability**, ensuring the system is easy to document, update, and debug.

---

# 1. User Authentication Feature

The **User Authentication** feature encompasses two essential functionalities: **Login** and **Account Creation**. Below is a detailed breakdown of how these components are implemented and executed.

## Overview

The User Authentication feature aims to securely handle user access to the application by enabling users to log in with existing account credentials or create a new account.

### Functionalities

1. **Login**:
    - Allows existing users to access their accounts by validating their credentials.

2. **Account Creation**:
    - Enables new users to register by creating an account with a unique username and a password.


## Implementation

# Login

### User Input:
- The user enters their username and password in the `LoginView` interface, which is directly linked to the `LoginController`.

### Controller Layer:
- The `LoginController` retrieves the username and password fields using `.getText()` and sends these values to the `AuthenticationService` for validation.

### Service Layer:
- The `AuthenticationService` is responsible for:
    - **Input Validation**: Ensures that both fields (username and password) are filled out.
    - **Database Interaction**: Delegates the task of checking the credentials to `UserDAO`.

### Database Layer:
- The `UserDAO` prepares and executes a `SELECT` query:
  ```sql
  SELECT * FROM users WHERE username = ? AND password = ?
  ```
    - It securely binds the username and password to the query placeholders using the `prepareStatement` method to prevent SQL injection. If a matching record exists, it returns `true`; otherwise, it returns `false`.

### UI Feedback:
- **If the credentials are valid**:
    - Displays a success alert: "Login successful."
    - Sets the global variable `UIUtil.USER` to the username and redirects the user to the `BookNowView` (main page).
- **If the credentials are invalid**:
    - Displays an error alert: "Invalid username or password."
- **If a database error occurs during the process**:
    - Displays an error alert with the specific error message.

## Testing

### Test Case: Successful Login
- **Steps:**
    1. Open the application and navigate to the login page.
    2. Enter valid credentials for an existing user (e.g., `testUser` / `TestPass123`).
    3. Click the "Login" button.
- **Expected Results:**
    - A success alert appears: "Login successful."
    - The user is redirected to the `BookNowView`.

### Test Case: Invalid Credentials
- **Steps:**
    1. Open the application and navigate to the login page.
    2. Enter incorrect credentials (e.g., `invalidUser` / `wrongPass`).
    3. Click the "Login" button.
- **Expected Results:**
    - An error alert is displayed: "Invalid username or password."
    - The user remains on the login page.

### Test Case: Database Error
- **Steps:**
    1. Simulate a database issue by stopping the database service.
    2. Open the application and navigate to the login page.
    3. Enter any credentials and click the "Login" button.
- **Expected Results:**
    - An error alert is displayed: "Database error: ."
    - The user remains on the login page.

---

# Account Creation

### User Input:
- Users provide a username, password, and confirm password in the `CreateAccountView` interface, which is managed by the `CreateNewAccountController`.

### Controller Layer:
- The `CreateNewAccountController` retrieves the user's input and triggers the `onCreateAccountButtonAction` method when the "Create Account" button is clicked.
- This method sends the input data (username, password, and confirm password) to the `AuthenticationService` for further processing.

### Service Layer:
- The `AuthenticationService` is responsible for the following tasks:
    - **Validation**:
        - Ensures all fields are filled.
        - Verifies that the username is at least 8 characters long.
        - Checks that the password is at least 8 characters long.
        - Ensures the password and confirm password fields match.
        - If any validation fails, an appropriate error message is returned (e.g., "Passwords do not match").
    - **Duplicate Check**:
        - Uses the `UserDAO.login` method to check if the username already exists in the database.
        - If the username exists, it returns an error message like "Username already exists!"
    - **Account Creation**:
        - Calls `UserDAO.createAccount` to insert the new account into the database if the username is unique and all validations pass.

### Database Layer:
- The `UserDAO` interacts directly with the database:
    - It checks for existing usernames using a `SELECT` query.
    - It inserts the new account into the `users` table using an `INSERT` query if the username is unique.

### UI Feedback:
- **If the account creation is successful**:
    - A success alert is displayed: "Account created successfully."
    - The user is redirected back to the Login Page (`LoginView.fxml`) using the `displayScene` method.
- **If there is an error during validation or database interaction**:
    - An appropriate error alert is displayed (e.g., "Passwords do not match" or "Username already exists!").

## Testing

### Test Case: Successful Account Creation
- **Steps**:
    1. Navigate to the Create Account Page.
    2. Enter a unique username and a valid password that's at least 8 characters.
    3. Re-enter the same password in the "Confirm Password" field.
    4. Click the "Create Account" button.
- **Expected Results**:
    - A success alert is displayed: "Account created successfully."
    - The user is redirected to the Login Page.

### Test Case: Missing Input
- **Steps**:
    1. Leave one or more fields empty (e.g., only fill in the username).
    2. Click the "Create Account" button.
- **Expected Results**:
    - An error alert is displayed: "All fields must be filled!"

### Test Case: Invalid Username Length
- **Steps**:
    1. Enter a username shorter than 8 characters.
    2. Enter a valid password and confirm password.
    3. Click the "Create Account" button.
- **Expected Results**:
    - An error alert is displayed: "Username must be at least 8 characters."

### Test Case: Duplicate Username
- **Steps**:
    1. Enter a username that already exists in the database.
    2. Enter valid passwords and confirm the password.
    3. Click the "Create Account" button.
- **Expected Results**:
    - An error alert is displayed: "Username already exists!"

### Test Case: Password Mismatch
- **Steps**:
    1. Enter a unique username.
    2. Enter a valid password.
    3. Enter a different password in the "Confirm Password" field.
    4. Click the "Create Account" button.
- **Expected Results**:
    - An error alert is displayed: "Passwords do not match!"
  
---
