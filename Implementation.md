# Implementation Guide

## Phase 1

### Instructions for Running BookNow Application

#### 1. Switch to JavaFX 21 and Java 21 (if needed):
- Ensure you have **JavaFX 21** installed.
- Update to **Java 21** if required to ensure compatibility with the project environment.
- You might need to manually add javafx 21.0.4 lib if so download it. If 21.0.4 is no longer available I believe any version of 21 will work (correct me if im wrong), 
will have to add VM options in edit configurations and fill out the necessary 
- Vm option ex: --module-path C:\Javafx21.0.4\javafx-sdk-21.0.4\lib --add-modules javafx.controls,javafx.fxml

#### 2. Set Up MySQL Workbench:
- Download and install **MySQL-8.4.2**, and **MySQL Workbench**. 
- Use the following default settings for the database connection:
  - **User**: `root`
  - **Password**: `root`
  - **Database Name**: `booknow`

#### 3. Database Setup:
- Copy and paste the provided **DBScript** into MySQL Workbench and execute it.
- This script will create the necessary tables for the application to function correctly.

---

### Issues and Future Enhancements

- Any issues encountered during setup should be addressed. MacOS users may face errors that require manual troubleshooting, while the setup has been tested and works fine on Windows.
- If mac gives an error, related so mac internal when building then create a virtual windows machine and run code from there. 
- Future updates will include an executable JAR file, eliminating the need for manually building the program. However, database setup will still be required.
- The feature called **Manual Restaurant Data Management** involves managing restaurant data using an SQL script. Once all related functionality—such as adding, updating, and 
retrieving restaurant information—has been fully implemented, this feature will be considered complete.
- If you encounter an error related to some configurations, it may be due to settings specific to my environment that i'm not aware of. You will need to manually adjust these settings. If you happen to find any please let me know.

### Faced Issues
- Tried setting up project, DB, etc. on a linux environment and proven to be unnecessarily complex because it involves multiple manual configurations, including installing IntelliJ IDEA, setting up JavaFX, and configuring the database. These steps, especially in the terminal, difficult and 
time-consuming. The BookNow database is configured to run locally at 127.0.0.1:3306, which means the project depends on the database being set up on the same machine where the project is running. Since the project is tightly coupled with this locally hosted database, running the project 
without manually setting up the database makes it non-functional. Due to the local database dependency, the previously defined non-functional requirement for 'portability' no longer makes sense. Portability refers to the ability 
to easily move the application between different environments, but since the project requires a local database configuration for each environment, it lacks true portability.  If the database were hosted online or on a remote server, the setup would be much simpler and more flexible across different environments. We will remove 'portability' as 
a non-functional requirement and replace it with a more relevant requirement, such as 'Maintainability' this new focus will ensure that the system is easy to maintain, with clear documentation and an understandable system structure, making future updates and fixes simpler to implement.

---
### Error Handling (Work in Progress)

- Several areas of the application still require better error handling. If you prefer strict error checks, note that this aspect is currently under development.
- Planned improvements for error handling will be implemented in the near future. For now, the focus is on testing the core functionality of the program: user creation, login, and restaurant search.

---

## Features Overview

### User Authentication

This feature allows users to create new accounts and log in to the system using a unique username and password.

#### Implementation:
- Users can create an account with a unique username and password through the `CreateNewAccountController`.
  - Input validation ensures that the username is at least 8 characters long, and the password is validated for length and matching confirmation.
- The `LoginController` manages the login process, ensuring that both fields (username and password) are filled before verifying the credentials through `UserServices`.
- Upon successful login, the user is redirected to the **BookNowView** (main page).
- Error handling for incorrect credentials and existing usernames is implemented, providing specific alerts via `AlertUtil`.
- Password validation includes checks for empty fields, matching passwords, and password length (at least 8 characters).
- A logout feature is not yet implemented but is planned for future updates.

#### Testing:
1. Create a new account by entering a unique username, password, and confirming the password.
  - Ensure that the username is at least 8 characters long, and the passwords match.
2. Log in using valid credentials, which should redirect you to the **BookNowView**.
3. Attempt to log in with incorrect credentials to trigger an error message.
  - Verify that appropriate validation messages are displayed for missing or invalid inputs.

---

### Search Restaurants

This feature allows users to search for restaurants based on location, cuisine type, date, and number of guests.
Make sure that you're selecting dates that match those in the DBScript, such as (1, '2024-11-01') or (2, '2024-11-02'). If you choose dates outside of those in the script, no restaurants will appear.

Reason: This ensures that the booking system aligns with the actual data in the database, providing users with a realistic and accurate view of restaurant availability based on the specific reservation dates stored in the system. Since there isn't a dedicated restaurant view, all restaurant-related data and functionality must be handled directly in the database.

#### Implementation:
- The `BookNowController` handles restaurant search functionality.
  - Users can filter restaurants by **location** and **cuisine type** using dropdown menus populated by `RestaurantServices`.
  - The user must select a **date** from the date picker, and validation ensures that the selected date is not in the past. Any invalid date selection triggers an error message using `AlertUtil`.
  - Users can specify the number of **guests** (adults and children) through dropdowns, which are populated using `IntStream`.
- Upon clicking the "Search" button, the search criteria are validated using `RestaurantServices.isSearchCriteriaValid()`, and matching restaurants are fetched from the database via `RestaurantServices.findAvailableRestaurants()`.
- The matching restaurants are dynamically displayed in the **BookNowView** using JavaFX `VBox` components.

#### Testing:
1. Select a **location** and **cuisine type** from the dropdown menus.
2. Choose a **date** from the date picker. Ensure that selecting a past date triggers an error.
3. Specify the number of **guests** (adults and children) using the dropdowns.
4. Click the "Search" button and verify that matching restaurants are displayed.
  - Ensure that no restaurants are displayed if no matches are found, and an appropriate message is shown.
  - Test validation for missing or invalid input (e.g., no location or date selected).

#### Note:
- Advanced filtering based on the number of guests (e.g., checking table availability for a specific number of guests) is not yet implemented.
- This feature will be added in the upcoming implementation of the **Restaurant Detail Page** and **Reservation** system, where the total number of guests will be cross-checked against the restaurant's available capacity.
- Aside from this, the search functionality, login, and create account is fully operational.
---
