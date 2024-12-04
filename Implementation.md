# Implementation Guide

## Phase 1

### Last Commit 12/04/24

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

# Login Process Description

When a user arrives at the Login Screen, they enter their username and password into the designated fields. They click the Login button. This action sets off a chain of events, starting with the `LoginController`.

The `LoginController` first reacts to the button press by invoking the method `onLoginButtonAction`. This method gathers the entered information from the `usernameField.getText()` and `passwordField.getText()` fields. It now has the user’s username and password and is ready to validate them.

Instead of directly handling the validation, the `LoginController` delegates this responsibility to the `AuthenticationService` by calling its method `login(username, password)`. The `AuthenticationService` is designed to handle this exact task. It now steps into action, prepared to confirm whether these credentials are genuine.

To check the credentials, the `AuthenticationService` calls `UserDAO.login(username, password)`. The `UserDAO` is the bridge to the database. Within this method, the `UserDAO` crafts a SQL query:

```sql
SELECT * FROM users WHERE username = ? AND password = ?
```

The placeholders (`?`) are populated with the user-provided username and password. To execute this query, the `UserDAO` relies on the `DBConnection` utility. It calls `DBConnection.getConnection()` to establish a connection to the database.

Once the connection is secured, the `UserDAO` prepares a `PreparedStatement` using its `prepareStatement` helper method, ensuring the parameters are securely injected into the query. The query is executed, and the `ResultSet` is analyzed to see if any matching user exists in the database.

- If the `ResultSet.next()` method returns true, it means a match was found—signaling a valid login. The `UserDAO.login()` method then returns `true` to the `AuthenticationService`.
- If no match is found, the method returns `false`.

The `AuthenticationService` receives this result and sends it back to the `LoginController`, which now knows whether the login was successful.

In the `LoginController`, depending on the result:

- **If Successful:**
  - The username is stored globally in the application by assigning it to `UIUtil.USER`.
  - A success alert is displayed using `UIUtil.displayAlert("Success", "Login successful")`.
  - The user is redirected to the main application view by calling `UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event)`.

- **If Failed:**
  - An error alert is shown via `UIUtil.displayAlert("Error", "Invalid username or password")`.

Meanwhile, if a database error occurs at any stage (e.g., during the connection or query execution), the catch block in the `onLoginButtonAction` method handles it. The user is informed of the issue using `UIUtil.displayAlert("Error", "Database error: " + e.getMessage())`.

## Testing

### Test Case: Successful Login
- **Steps:**
    1. Open the application and navigate to the login page.
    2. Enter valid credentials for an existing user.
    3. Click the "Login" button.
- **Expected Results:**
    - A success alert appears: "Login successful."
    - The user is redirected to the `BookNowView`.

### Test Case: Invalid Credentials
- **Steps:**
    1. Open the application and navigate to the login page.
    2. Enter incorrect credentials or leave blank.
    3. Click the "Login" button.
- **Expected Results:**
    - An error alert is displayed: "Invalid username or password."
    - The user remains on the login page.

---
## Implementation

# Account Creation

The account creation process begins when a user decides to join the BookNow platform. They are directed to the "Create Account" page, where they encounter a user-friendly interface prompting them to fill in three essential fields:

- **Username**
- **Password**
- **Confirm Password**

The user enters their desired username into the `usernameField`, sets a secure password in the `passwordField`, and retypes the same password in the `confirmPasswordField` to confirm it. They click the "Create Account" button, initiating the process managed by the `CreateNewAccountController`.

When the button is clicked, the `onCreateAccountButtonAction` method in `CreateNewAccountController` is triggered. This method gathers the inputs from the FXML fields:

```java
String username = usernameField.getText();
String password = passwordField.getText();
String confirmPassword = confirmPasswordField.getText();
```

The gathered input is passed to the `validateAndCreateAccount` method in the `AuthenticationService`:

```java
String resultMessage = authenticationService.validateAndCreateAccount(username, password, confirmPassword);
```

The `AuthenticationService` acts as the core business logic handler, ensuring the integrity of the account creation process. Here’s how it works:

### Validation of User Input

The first step in the `validateAndCreateAccount` method is validating the provided data through the `validateAccountDetails` method. This method ensures that the following conditions are met:

- **All Fields Are Filled**: No input field is left empty.
- **Username Length**: The username must have at least 8 characters.
- **Password Length**: The password must also be at least 8 characters long.
- **Passwords Match**: The password and confirm password fields must contain the same value.

If any of these checks fail, an appropriate error message is returned, such as "All fields must be filled!" or "Passwords do not match."

### Checking for Username Availability

If validation passes, the `AuthenticationService` ensures that the username is unique. It calls the `UserDAO` method `login` to query the database and check if the username already exists:

```sql
SELECT * FROM users WHERE username = ? AND password = ?
```

If the query returns a result, it means the username is already taken, and the service returns the message "Username already exists!".

### Creating the Account

If the username is available, the `AuthenticationService` proceeds to create the new account by calling `userDAO.createAccount`:

```java
boolean accountCreated = userDAO.createAccount(username, password);
```

In the `UserDAO`, the `createAccount` method executes the following SQL statement to insert the new user into the database:

```sql
INSERT INTO users (username, password) VALUES (?, ?)
```

If the database insertion is successful, the method returns `true`, indicating that the account has been created.

### Responding to the Outcome

Back in the `CreateNewAccountController`, the result from the `validateAndCreateAccount` method determines the next steps:

- **Success**:
  - The controller updates the application's state with:
    ```java
    UIUtil.USER = username;
    ```
  - It displays a success message to the user:
    ```java
    UIUtil.displayAlert("Success", "Account created successfully");
    ```
  - Finally, it transitions the user to the main application interface:
    ```java
    UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/BookNowView.fxml"), event);
    ```

- **Failure**:
  - If the service returned an error message, the controller displays an alert:
    ```java
    UIUtil.displayAlert("Error", resultMessage);
    ```
    
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

# 2. Restaurant Search Process

## Implementation

### Last Commit 12/04/24

## 1. User Input 

The process begins when the user interacts with the UI (`BookNowView.fxml`) to input their search criteria:

- **Dropdowns**: The user selects options from various dropdowns, including:
  - **City** (`locationComboBox`)
  - **Cuisine Type** (`cb_cuisineType`)
  - **Number of Adults** (`cb_adults`)
  - **Number of Children** (`cb_children`)

- **Date Picker**: The user chooses a reservation date using the `checkInDate` component.

- **Search Button**: Clicking this button triggers the `onSearchButtonClick` event.

## 2. Controller: BookNowController

The `onSearchButtonClick` method in the `BookNowController` manages the user's search request. It follows these steps:

- **Retrieve User Inputs**: Captures the values selected by the user from the UI components.
  ```java
  String selectedCity = locationComboBox.getSelectionModel().getSelectedItem();
  String selectedCuisineType = cb_cuisineType.getSelectionModel().getSelectedItem();
  LocalDate selectedDate = checkInDate.getValue();
  Integer selectedAdults = cb_adults.getSelectionModel().getSelectedItem();
  Integer selectedChildren = cb_children.getSelectionModel().getSelectedItem();
  ```

- **Calculate Total Guests**: Adds adults and children to determine the total number of guests.
  ```java
  int totalGuests = selectedAdults + selectedChildren;
  ```

- **Delegate Search Logic**: Invokes `BookNowFacadeService.searchRestaurants` to handle the search and update the UI.

## 3. Service Layer: BookNowFacadeService

The `searchRestaurants` method in `BookNowFacadeService` serves as the central processing unit for the search functionality.

- **Steps in `searchRestaurants`**:
  - **Validate Inputs**: Ensures inputs are complete and the date is valid (e.g., not in the past).
    ```java
    if (selectedCity == null || selectedCuisineType == null || selectedAdults == null || selectedChildren == null || selectedDate == null || selectedDate.isBefore(LocalDate.now())) {
        UIUtil.displayAlert("Error", "Please fill in all fields correctly.");
        return;
    }
    ```
  - **Query Database**: Fetches filtered restaurants based on the user's selections using `RestaurantService.getAvailableRestaurants`.
    ```java
    List<Restaurant> restaurants = restaurantService.getAvailableRestaurants(selectedCity, selectedCuisineType, totalGuests, selectedDate);
    ```

  - **Update UI**: Passes the retrieved list of restaurants to `RestaurantUIManager.populateRestaurantListVBox` to display the results.

## 4. Service Layer: RestaurantService

The `RestaurantService` is responsible for database interactions via `RestaurantDAO`.

- **Steps in `RestaurantService`**:
  - **Fetch Data**: Calls `RestaurantDAO.getAvailableRestaurants` using the specified criteria.
    ```java
    return restaurantDAO.getAvailableRestaurants(selectedCity, selectedCuisineType, totalGuests, selectedDate);
    ```

## 5. DAO Layer: RestaurantDAO

The `RestaurantDAO` directly interacts with the database to find matching restaurants.

- **Steps in `RestaurantDAO`**:

  - **Execute SQL Query**: Uses an SQL query to find available restaurants.
    ```sql
    SELECT DISTINCT r.*
    FROM restaurants r
    JOIN tables t ON r.restaurant_id = t.restaurant_id
    LEFT JOIN reservations res ON t.table_number = res.table_number
    AND res.reservation_date = ?
    WHERE r.city = ?
    AND r.cuisine_type = ?
    AND t.number_of_seats >= ?
    AND (res.reservation_id IS NULL OR ts.slot_id IS NULL);
    ```

  - **Map Results to Restaurant Objects**: Converts each row in the result set into a `Restaurant` object.
    ```java
    return new Restaurant(
        rs.getInt("restaurant_id"),
        rs.getString("name"),
        rs.getString("city"),
        rs.getString("cuisine_type"),
        rs.getString("description"),
        rs.getString("menu_pdf"),
        rs.getString("image_path"),
        rs.getInt("max_guests")
    );
    ```

## 6. UI Update: RestaurantUIManager

The `RestaurantUIManager.populateRestaurantListVBox` method handles the rendering of search results.

- **Steps in `populateRestaurantListVBox`**:
  - **Clear Existing Results**: Removes any previous results from `restaurantListVBox`.
    ```java
    restaurantListVBox.getChildren().clear();
    ```

  - **Render Results**: Iterates through the `Restaurant` objects list, creating an `HBox` for each restaurant:
    - Displays restaurant details such as name, description, and location.
    - Includes action buttons for viewing reviews, menus, and table availability.

## 7. End of Flow

The user sees the filtered list of restaurants displayed in the UI (`restaurantListVBox`). Each restaurant entry includes buttons for additional actions:

- **View Reviews**: Opens an overlay to show reviews.
- **View Menu**: Displays the restaurant's menu in a PDF viewer.
- **Show Availability**: Shows available tables for reservation.

# Test Scenarios

## Scenario 1: Perform a Successful Search

**Steps:**

1. Open the application and log in successfully.
2. On the main "Book Now" page, locate the search section.
3. Select "Minneapolis" from the "Location" dropdown.
4. Select "Italian" from the "Cuisine Type" dropdown.
5. Pick a valid date (e.g., one week from today) using the date picker.
6. Choose "2 Adults" and "1 Child" from the respective dropdowns.
7. Click the Search button.

**Expected Outcome:**

- A list of restaurants matching the search criteria is displayed in the scrollable area.
- Each restaurant entry includes:
  - **Name**
  - **Location**
  - **Description**
  - **Action Buttons** (e.g., "View Menu," "Read Reviews," "Check Availability").

## Scenario 2: Invalid Inputs

**Steps:**

1. Open the search page without logging in.
2. Attempt to search without filling any input fields.
3. Leave "Location" blank and try searching.
4. Enter a past date in the "Check-in Date" field and try searching.

**Expected Outcome:**

- **For no input**: An error alert stating, "Please fill in all fields."
- **For blank "Location"**: An error alert stating, "Please select a valid location."
- **For a past date**: An error alert stating, "Please select a date that is not in the past."

---

# Phase 2

## 1. Table Availability Check

### Last Commit 12/04/24

**Implementation**  
The Table Availability Check process is initiated when the user selects a restaurant and clicks the "Show Availability" button in the application. This action triggers a series of method calls to fetch and display available tables and time slots for the selected restaurant and reservation details.

### User Action

- **User Interaction**: The user selects a restaurant and clicks the "Show Availability" button.

### Method Chain

1. **`handleShowAvailability(Restaurant restaurant)` in `BookNowController`**

  - **Objective**: Manages the transition to the availability view and initiates data retrieval.
  - **Steps**:
    - **Show Availability View**: Calls `showAvailabilityView()` to update the UI, ensuring that the availability view is displayed.
    - **Prepare Availability Data**: Invokes the `prepareAvailabilityView` method in `BookNowFacadeService`, passing the selected restaurant, date, and guest count.

2. **`prepareAvailabilityView` in `BookNowFacadeService`**

  - **Objective**: Centralizes logic for fetching availability data and updating the UI.
  - **Steps**:
    - **Fetch Available Tables**:
      - Calls `TableDAO.getAvailableTables`, passing the restaurant ID, reservation date, and required guest count.
      - Retrieves a list of tables that meet the criteria.
    - **Fetch Available Time Slots**:
      - Calls `TableDAO.getAvailableTimeSlots`, providing the restaurant ID and reservation date.
      - Returns the time slots that are unreserved for the specified date.
    - **UI Update**:
      - Dynamically updates the UI to display the available tables and time slots.
    - **Callbacks**:
      - **Table Selection**: Attaches a callback to invoke `handleReserveTable` when a user selects a table.
      - **Time Slot Selection**: Sets up a callback to invoke `setSelectedTimeSlot` for time slot selection.

3. **Database Queries in `TableDAO`**

  - **`getAvailableTables`**
    - **Purpose**: Executes a SQL query to retrieve tables that:
      - Match the restaurant's ID.
      - Meet the guest count requirement.
      - Are not reserved for the specified date.
    - **Result**: Returns a list of `Table` objects representing the available tables.

  - **`getAvailableTimeSlots`**
    - **Purpose**: Queries the database to identify unreserved time slots for the specified restaurant and date.
    - **Result**: Provides the time slots available for reservation.

### Completion of Table Availability Check

At this stage, the user is presented with a list of available tables and time slots, preparing the system for the next step: reservation booking.

# Test Case: Table Availability Check

## Test Objective

Verify that the application correctly fetches and displays available tables and time slots for a selected restaurant based on the user’s input.

## Test Setup

**Preconditions**:
- Install Mysql, Mysql workbench
- Execute the dbscript
- Log in as a valid user to access the `BookNowController`.

## Steps

1. **Navigate to the Search Page**.
2. **Select**:
  - A valid **Location**
  - A valid **Cuisine Type**
  - At least one **Adult Guest**
  - Select number of **children**
  - A valid **Reservation Date** in the future using the `checkInDate` picker, or present.
3. **Click Search** to populate the list of restaurants.
4. From the restaurant list, select a restaurant and click the **"Show Availability"** button.

## Expected Results

### UI Behavior:

- The application switches to the **Availability View**.
- Tables and time slots are displayed dynamically in the availability section.
- Users can see table numbers, available seats, and time slots.

### Data Accuracy:

- Only tables with sufficient seating capacity are shown.
- Time slots displayed should not overlap with existing reservations.

### Error Handling:

- If no tables or time slots are available, an error message such as "No tables or time slots available for the selected date." is displayed.

## Validation

- Confirm the tables displayed correspond to the data in the `tables` table in the database.
- Verify the time slots match the available entries in the `time_slots` table.
---

# 2. Reservation Booking

## Implementation

Once the user selects a table and time slot, they can proceed to book a reservation. The system processes the booking request and ensures that no conflicts exist before confirming the reservation.

### User Action

- **User Interaction**: The user selects a table and time slot and clicks the "Reserve" button.

### Method Chain

1. **`handleReserveTable(Restaurant restaurant, Table table)` in `BookNowController`**
  - **Objective**: Handles the reservation process by delegating it to the service layer.
  - **Steps**:
    - **Gather Reservation Details**:
      - Collects the necessary details, including:
        - The logged-in user (`UIUtil.USER`).
        - Selected restaurant and table.
        - Reservation date.
        - Chosen time slot.
    - **Initiate Reservation**:
      - Calls `BookNowFacadeService.handleTableReservation` to process the reservation.

2. **`handleTableReservation` in `BookNowFacadeService`**
  - **Objective**: Validates and processes the reservation request.
  - **Steps**:
    - **Check for Existing Reservation**:
      - Calls `TableDAO.isReservationAlreadyExists` to verify that no reservation exists for the same user, restaurant, date, and time slot.
    - **Reserve Table**:
      - If no conflict is detected, calls `TableDAO.reserveTable` to save the reservation details in the database.

3. **Database Queries in `TableDAO`**
  - **`isReservationAlreadyExists`**
    - **Purpose**: Checks the database for conflicting reservations by executing a SQL query that matches:
      - User ID.
      - Restaurant ID.
      - Reservation date.
      - Time slot ID.
    - **Result**: Prevents duplicate reservations by returning true if a conflict exists.

  - **`reserveTable`**
    - **Purpose**: Executes a SQL `INSERT` query to store reservation details in the database, including:
      - User ID.
      - Restaurant ID.
      - Reservation date.
      - Time slot ID.
      - Table number.
    - **Result**: Saves the reservation and returns a status code:
      - > 0: Success.
      - 0: Failure.
      - -1: Conflict.

### Completion of Reservation Booking

- **Result Evaluation**:
  - **Successful Reservation**: Displays a confirmation message: "Reservation Confirmed."
  - **Existing Reservation**: Shows an error: "Reservation already exists for the selected time slot."
  - **Failure**: Notifies the user: "An error occurred while trying to reserve the table."

This step concludes the reservation process, ensuring that the user has successfully booked a table or is informed of any issues.

# Test Cases for Reservation Booking

## 1. Successful Reservation

**Steps**:
1. Log in to the application.
2. Search for a restaurant by selecting location, cuisine, date, and guest count.
3. Click "Show Availability" for a restaurant.
4. Select an available table and time slot.
5. Click the "Reserve" button.
6. Observe the confirmation message displayed on the screen.

**Expected Outcome**:
- A message appears: "Reservation Confirmed."

---

## 2. Duplicate Reservation

**Steps**:
1. Log in to the application.
2. Search for the same restaurant and date as a previous reservation.
3. Click "Show Availability" again.
4. Try reserving the same table and time slot used in the previous reservation.
5. Click the "Reserve" button.

**Expected Outcome**:
- An error message appears: "Reservation already exists for the selected time slot."

---

## 3. Invalid Input Handling

**Steps**:
1. Log in to the application.
2. Search for a restaurant and proceed to the Show Availability page.
3. Attempt to reserve a table without selecting a time slot.
4. Click the "Reserve" button.

**Expected Outcome**:
- An error message appears: "Please select a table and time slot before proceeding."
- The reservation process does not continue.

---

## 4. Database Connection Error

**Steps**:
1. Log in to the application.
2. Simulate a database error (e.g., by disconnecting the database if possible).
3. Search for a restaurant and try to reserve a table.
4. Click the "Reserve" button.

**Expected Outcome**:
- An error message appears: "An error occurred while trying to reserve the table."
- The booking does not proceed.

---

These test cases cover scenarios related to reservation booking, ensuring that the application behaves correctly under various conditions such as successful booking, duplicate entries, invalid input handling, and system errors like a database connection issue.

---

# Phase 3

## 1. View My Reservations

### Implementation

The "View My Reservations" feature allows users to see their active and past reservations in a user-friendly interface, offering management options such as canceling upcoming reservations or leaving reviews for completed ones.

### How it Works

**Flow in the Application**:
1. **BookNowController**: When a user clicks the "View My Reservations" button, it activates `onViewMyReservationsClick`.
2. **Reservations View**: The user is navigated to `ReservationsView.fxml`.
3. **ReservationsController**:
  - Calls `initialize()` to set up table columns and load reservation data.
  - Uses `ReservationUIManager.loadReservations` to fetch reservations for the logged-in user.
  - Retrieves reservation data through `ReservationDAO.getUserReservations`.
  - Displays data in a `TableView`, complete with interactive buttons per reservation (e.g., Cancel, Leave a Review).

### Testing Notes

**Objective**: Ensure users can view and manage their reservations effectively.

**Steps for Testing**:

- **View Active Reservations**:
  1. Log in with an account containing active reservations.
  2. Click "View My Reservations."
  3. Verify display includes restaurant name, reservation date/time, table number, and action buttons.

- **View Past Reservations**:
  1. Log in with an account with past reservations.
  2. Click "View My Reservations."
  3. Ensure past reservations show without "Cancel" buttons, but allow reviews if pending.

- **Cancel an Upcoming Reservation**:
  1. Click "Cancel" on a future reservation.
  2. Confirm cancellation.
  3. Verify removal from the list and database.

- **Database Error Handling**:
  1. Simulate a database disconnection.
  2. Try to view reservations.
  3. Check for error message: "Unable to load your reservations. Please try again later."

---

## 2. Leave a Review

### Implementation

The "Leave a Review" feature enables users to provide feedback and ratings after completing a reservation.

### How it Works

**Flow in the Application**:
1. **Reservations View**: Users select "Leave a Review" for eligible reservations.
2. **Review Page Navigation**: Redirects to `CreateReviewView.fxml`.
3. **CreateReviewController**:
  - Initializes form with `setReservationId`.
  - Users input a rating and feedback.
  - `onSubmitReviewClickAction` validates and submits the review:
    - Validated through `ReviewService.validateAndSubmitReview`.
    - Saved via `ReviewDAO.submitReview`.

### Testing Notes

**Objective**: Confirm users can successfully leave reviews for completed reservations.

**Steps for Testing**:

- **Submit a Valid Review**:
  1. Log in with a valid account.
  2. Navigate to "View My Reservations."
  3. Select "Leave a Review" for a completed reservation.
  4. Fill the form: rating (1-5) and feedback.
  5. Submit and confirm success message: "Review successfully submitted!"

- **Missing Input Validation**:
  1. Leave rating or feedback blank.
  2. Submit and check for error: "Please select a rating and provide feedback."

- **Duplicate Review Prevention**:
  1. Submit a review.
  2. Try another review for the same reservation.
  3. Ensure button changes to "View Your Review."

- **Database Error Handling**:
  1. Simulate database disconnection.
  2. Try submitting a review.
  3. Check for error message: "Failed to save review. Please try again later."