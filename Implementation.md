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

# 1. User Authentication and Account Creation

The **User Authentication and Account Creation** feature encompasses two essential functionalities: **Login** and **Account Creation**. 

### Functionalities

1. **Login**:
    - Allows existing users to access their accounts by validating their credentials.

2. **Account Creation**:
    - Enables new users to register by creating an account with a unique username and a password.


## Implementation

# Login

## Controller Initialization
When the `LoginController` is instantiated, the constructor initializes the following components:
- **`DBConnection`**: Manages database access.
- **`UserDAO`**: Handles user-related database operations.
- **`AuthenticationService`**: Manages authentication logic.

This initialization ensures that all required dependencies are prepared, enabling the application to handle user authentication seamlessly when interacting with the login screen.

## Scene Creation
- A scene is created using the layout from `LoginView.fxml`.
- The scene is configured with:
  - **Width**: 1400
  - **Height**: 800
- The scene is attached to the `primaryStage`, which represents the application's main window.
- Additional configurations include:
  - Setting the window title to **“BookNow”**.
  - Making the `primaryStage` visible to the user.

Once initialized, the application displays the login screen and waits for user interaction, such as clicking the **Login** button or the **Create Account** button.

## Login Button Functionality

### Event Trigger
When the **Login** button is clicked, it triggers the `onLoginButtonAction(ActionEvent event)` method, which performs the following tasks:

1. **Retrieve User Input**
  - Retrieves the text from `usernameField` and `passwordField`.

2. **Authenticate User**
  - Calls `authenticationService.login(username, password)`.
  - This method delegates the authentication process to the `UserDAO.login()` method.

3. **Database Query**
  - The `UserDAO.login()` method:
    - Prepares a SQL query to check if the credentials exist in the `users` table.
    - Uses placeholders to prevent SQL injection.
    - Establishes a database connection, executes the query, and checks the result set.

4. **Handle Results**
  - **Success**:
    - If credentials are valid:
      - Returns `true`.
      - Stores the username in `UIUtil.User.USER`.
      - Displays a success alert.
      - Switches the scene to `BookNowView.fxml`.
  - **Failure**:
    - If credentials are invalid:
      - Displays an error alert with the message: **“Invalid username or password.”**
  - **Database Error**:
    - In case of an `SQLException`:
      - Displays an error alert with the message: **“Database error”**, along with exception details.

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

# Create New Account 

## Implementation

## Scene and Controller Initialization
- When `CreateAccountView.fxml` loads, it links the following UI elements to the corresponding fields in the `CreateAccountController`:
  - `usernameField`
  - `passwordField`
  - `confirmPasswordField`
- The `CreateAccountController` constructor initializes the following components:
  - **`DBConnection`**: Manages database access.
  - **`UserDAO`**: Handles user-related database operations.
  - **`AuthenticationService`**: Manages account creation logic.

At this point, the **Create New Account** button waits for user input, while the **Login** button acts as a back button, switching back to the `LoginView`.

## Create New Account Button Functionality

### Event Trigger
When the **Create New Account** button is clicked, it triggers the `onCreateAccountButtonAction(ActionEvent event)` method, which performs the following steps:

1. **Retrieve User Input**
  - Retrieves the text from:
    - `usernameField`
    - `passwordField`
    - `confirmPasswordField`

2. **Validate and Create Account**
  - Calls `authenticationService.validateAndCreateAccount(username, password, confirmPassword)` to handle account creation.

3. **Validation Logic**
  - The `validateAccountDetails` method checks:
    - All fields are filled.
    - `username` and `password` meet the minimum length requirement (8 characters).
    - `password` and `confirmPassword` match.
  - If validation fails:
    - An error message is returned (e.g., **“Passwords do not match.”**).

4. **Check Username Availability**
  - If validation passes:
    - Calls `userDAO.login(username, password)` to check if the username already exists.
    - If the username is taken:
      - Returns the message: **“Username already exists!”**

5. **Create Account**
  - If the username is available:
    - Calls `userDAO.createAccount(username, password)`:
      - Prepares and executes an `INSERT` query using placeholders for `username` and `password` to prevent SQL injection.
    - If the query executes successfully:
      - Sets `UIUtil.USER` to the `username`.
      - Displays a success alert: **“Account created successfully”**.
      - Switches the scene to `BookNowView.fxml`.

6. **Handle Errors**
  - If the account creation is unsuccessful:
    - Displays an error alert with the relevant message.
  - If an `SQLException` occurs:
    - Catches the exception and displays an alert with the error details.

    
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

# Restaurant Discovery and Interaction

## Implementation

## Features
The Restaurant Discovery and Interaction module includes the following functionality:
- **Restaurant Search**: Allows users to filter restaurants by location, cuisine, date, and guest count.
- **View Menus**: Provides PDF-based menu viewing for selected restaurants.
- **Read Reviews**: Displays reviews left by other users for a given restaurant.
- **Table Availability**: Shows available tables for reservation, allowing users to choose a specific time slot and guest count.
- **Reserve a Table**: Enables users to book a table with confirmation and error handling.

## Initialization
The process begins when `BookNowView.fxml` is loaded using `UIUtil.displayScene`, linking UI elements to fields in the `BookNowController`. During initialization:
- A `BookNowFacadeService` is instantiated to manage database interactions through `DBConnection`.
- The `BookNowFacadeService` initializes service classes:
  - `RestaurantService`, `ReviewService`, and `TableService`, each relying on their corresponding DAOs (`RestaurantDAO`, `ReviewDAO`, `TableDAO`) for database operations.
- A `RestaurantUIManager` is created to handle UI interactions, such as displaying reviews, table availability, and reservation data.

Once `BookNowView.fxml` is fully loaded, the `initialize()` method:
1. Displays a personalized welcome message using `UIUtil.USER`.
2. Populates combo boxes for location, cuisine, and guest counts using data from `BookNowFacadeService`.
3. Sets up numeric ranges for adults (1–10) and children (0–10).
4. Awaits user input for location, cuisine type, date, and guest count selection.

## Restaurant Search
When the **Search** button is clicked, the `onSearchButtonClick(ActionEvent event)` method is triggered. This method:
1. Calls `showRestaurantListView()` to display the restaurant list and retrieve user input.
2. Delegates the search operation to `BookNowFacadeService.searchRestaurants`, which:
  - Validates user inputs using `validateSearchInputs`. Alerts are shown for invalid inputs.
  - Calculates the total guest count.
  - Calls `getAvailableRestaurants` to fetch restaurants matching the filters via `restaurantService` and `restaurantDAO`.
  - Maps the query results to `Restaurant` objects using `mapResultSetToRestaurant`.

3. Passes the list of `Restaurant` objects to `restaurantUIManager.populateRestaurantListVBox`, which:
  - Clears previous content.
  - Iterates over the restaurant list, creating an `HBox` for each using `createRestaurantBox`.

### Restaurant Box Creation
- **ImageView**: Created by `createRestaurantImageView` using the restaurant's image path or a placeholder.
- **Details VBox**: Created by `createRestaurantDetails` with the restaurant’s name, location, and description.
- **Buttons VBox**: Includes:
  - Buttons for reviews, menus, and availability using consumer callbacks (`onReadReviews`, `onViewMenu`, `onShowAvailability`).
  - Reviews and rating display using `createReviewsAndRatingBox`.

## Read Reviews
When the **Read Reviews** button is clicked:
1. `handleReadReviews(Restaurant restaurant)` is triggered, making the `reviewsOverlay` visible.
2. It calls `BookNowFacadeService.showRestaurantReviews`:
  - Retrieves reviews using `reviewService.getReviewsByRestaurantId` via `reviewDAO`.
  - Processes the `ResultSet` to extract review details and maps them to `Review` objects.

3. `restaurantUIManager.displayReviews` populates the `reviewsOverlay`:
  - Clears existing content.
  - Calls `createEmptyReviewBox` if no reviews are available.
  - Otherwise, iterates through reviews, generating styled `VBox` components with:
    - Partially masked usernames.
    - Star ratings (★ for filled stars, ☆ for empty).
    - Feedback text.
    - Formatted dates.

Each review includes a close button to dynamically remove it from the overlay.

## View Menus
When the **View Menu** button is clicked:
1. `handleViewMenu(Restaurant restaurant)` is triggered.
2. It calls `RestaurantUIManager.viewMenu(restaurant)`:
  - Loads the menu PDF using the `loadPDF` method.
  - Opens the file in the system's default PDF viewer if the `Desktop` class is supported.
  - Displays an alert if the menu is unavailable or an error occurs.

## Show Availability
When the **Show Availability** button is clicked:
1. `handleShowAvailability(Restaurant restaurant)` is triggered, which:
  - Hides the restaurant list and displays the availability view.
  - Calls `BookNowFacadeService.prepareAvailabilityView`, passing details like restaurant, date, guest count, and `availabilityVBox`.

2. `prepareAvailabilityView`:
  - Clears the `availabilityVBox`.
  - Adds a `TableView` created by `restaurantUIManager.createTableView`, showing:
    - Table details (number, seats, price).
    - Interactive elements for selecting time slots and reserving tables.

### Table Reservation
If the user reserves a table:
1. `handleReserveTable(Restaurant restaurant, Table table)` is triggered.
2. It calls `BookNowFacadeService.handleTableReservation`, which:
  - Verifies if a reservation already exists via helper methods:
    - `getTimeSlotIdByTimeSlot`
    - `getUserIdByUserName`
    - `isReservationAlreadyExists`
  - If a reservation exists:
    - Returns `-1` and displays a "Reservation Failed" alert.
  - Otherwise:
    - Creates the reservation using `tableService.reserveTable` via `tableDAO`.

Finally, the `availabilityVBox` is updated with the latest `TableView`, showing table details and user interactions.


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

## commit 12/4/24

# Reservation and Review Management

---

# Functions
- **View Reservations**: Displays all reservations made by the user, including restaurant name, reservation date, time slot, and table number.
- **Cancel Reservation**: Enables users to cancel upcoming reservations, removing them from the database.
- **Leave a Review**: Allows users to submit reviews for completed reservations.
- **View Your Reviews**: Displays user-submitted reviews in a structured table format.

---

## Implementation

### Step 1: Viewing Reservations

The user initiates the process by clicking the **View My Reservations** button in the `BookNowView` interface. This triggers the `onViewMyReservationsClick()` method:

```java
public void onViewMyReservationsClick(ActionEvent event) {
    UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/ReservationsView.fxml"), event);
}
```

This switches the scene to `ReservationsView.fxml` and links it to the `ReservationsController`.

### Step 2: Initializing Reservations Controller

The `ReservationsController` initializes the `ReservationUIManager`, which handles the UI interactions and data operations for reservations:

```java
public ReservationsController() {
    this.reservationUIManager = new ReservationUIManager();
}
```

The `ReservationUIManager` is initialized with dependencies for data retrieval and management:

```java
public ReservationUIManager() {
    this.reservationService = new ReservationService(new ReservationDAO(new DBConnection()));
}
```

### Step 3: Setting Up the Reservations View

Once `ReservationsView.fxml` is loaded, the `initialize()` method is called. This method performs the following tasks:

- Displays a personalized welcome message.
- Sets up `TableView` columns for reservation details.
- Loads reservations using the `loadReservations()` method.

```java
public void initialize() {
    lbl_welcome.setText("Welcome, " + UIUtil.USER);
    reservationUIManager.setUpTableColumns(restaurantNameColumn, reservationDateColumn, timeSlotColumn, tableNumberColumn, actionColumn);
    loadReservations();
}
```

#### Setting Up Table Columns

The `setUpTableColumns()` method maps the `TableView` columns to the corresponding fields in the `Reservation` model:

```java
public void setUpTableColumns(TableColumn<Reservation, String> restaurantNameColumn,
                              TableColumn<Reservation, String> reservationDateColumn,
                              TableColumn<Reservation, String> timeSlotColumn,
                              TableColumn<Reservation, String> tableNumberColumn,
                              TableColumn<Reservation, Button> actionColumn) {
    restaurantNameColumn.setCellValueFactory(new PropertyValueFactory<>("restaurantName"));
    reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
    timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
    tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
    actionColumn.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
}
```

### Step 4: Loading Reservations

The `loadReservations()` method populates reservations into the `TableView` and assigns action buttons:

```java
private void loadReservations() {
    this.reservationUIManager.loadReservations(UIUtil.USER, reservationsTable.getItems());
}

public void loadReservations(String username, ObservableList<Reservation> reservationsList) {
    reservationsList.clear();
    reservationsList.addAll(reservationService.getUserReservations(username));
    for (Reservation reservation : reservationsList) {
        reservation.setActionButton(createActionButton(reservation, reservationsList));
    }
}
```

### Step 5: Adding Action Buttons

The `createActionButton()` method generates buttons for three key actions based on the reservation's status:

- **Cancel Reservation**: Available if the reservation time has not passed.
- **View Your Review**: Enabled if a review exists for the reservation.
- **Leave a Review**: Available for completed reservations without a review.

```java
private Button createActionButton(Reservation reservation, ObservableList<Reservation> reservationsList) {
    Button actionButton;
    if (!reservation.checkReservationDateTimePassed()) {
        actionButton = new Button("Cancel Reservation");
        actionButton.setOnAction(e -> cancelReservation(reservation.getReservationId(), reservationsList));
    } else if (reservationService.checkReservationHasReview(reservation.getReservationId())) {
        actionButton = new Button("View Your Review");
        actionButton.setOnAction(e -> UIUtil.displayScene(getClass().getResource("/edu/metrostate/booknow/ReviewView.fxml"), e));
    } else {
        actionButton = new Button("Leave a Review");
        actionButton.setOnAction(e -> handleLeaveReviewAction(e, reservation.getReservationId()));
    }
    return actionButton;
}
```

### Step 6: View Your Review

If the user clicks **View Your Review**, the application switches to `ReviewView.fxml`. This process mirrors the View My Review functionality, which displays user-submitted reviews in a structured `TableView`.

### Step 7: Leaving a Review

Clicking the **Leave a Review** button triggers the following method:

```java
private void handleLeaveReviewAction(ActionEvent event, int reservationId) {
    URL url = getClass().getResource("/edu/metrostate/booknow/CreateReviewView.fxml");
    UIUtil.displaySceneWithController(url, event, controller -> {
        ((CreateReviewController) controller).setReservationId(reservationId);
    });
}
```

This switches the scene to `CreateReviewView.fxml`, where the `CreateReviewController` is initialized with the reservation ID and details.

#### Submitting the Review

When the user submits the review, the `onSubmitReviewClickAction()` method validates and saves the review:

```java
public void onSubmitReviewClickAction(ActionEvent event) {
    Integer rating = combo_rating.getValue();
    String feedback = txt_reviewComment.getText();

    try {
        String resultMessage = reviewService.validateAndSubmitReview(UIUtil.USER, reservation.getRestaurantId(), reservation.getReservationId(), rating, feedback, reservation.getReservationDate());
        if (resultMessage.equals("Success")) {
            UIUtil.displayAlert("Success", "Review successfully submitted!");
        } else {
            UIUtil.displayAlert("Error", resultMessage);
        }
    } catch (Exception e) {
        UIUtil.displayAlert("Error", e.getMessage());
    }
}
```

### Step 8: Canceling a Reservation

If the user clicks **Cancel Reservation**, the reservation is removed from the database. The following code handles the process:

#### UI Manager Layer

```java
public void cancelReservation(int reservationId, ObservableList<Reservation> reservationsList) {
    reservationService.cancelReservation(reservationId);
    reservationsList.removeIf(reservation -> reservation.getReservationId() == reservationId);
}
```

#### Service Layer

```java
public void cancelReservation(int reservationId) {
    reservationDAO.cancelReservation(reservationId);
}
```

#### DAO Layer

```java
public void cancelReservation(int reservationId) {
    try (Connection connection = dbConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
        preparedStatement.setInt(1, reservationId);
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

# Test Cases for Reservation and Review Management

## Test Case 1: View Reservations


**Steps:**
1. Log in to the application with a valid user account.
2. Make a reservation
2. Click the **View My Reservations** button on the main screen.

**Expected Outcome:**
- The `ReservationsView.fxml` scene is displayed.
- The `TableView` is populated with reservations, displaying:
    - Restaurant name
    - Reservation date
    - Time slot
    - Table number
    - Action buttons (Cancel Reservation, View Your Review, Leave a Review).

---

## Test Case 2: Cancel Reservation


**Steps:**
1. Log in and make sure to have a reservation then hit the `View My Reservation" button.
2. Identify a reservation with the **Cancel Reservation** button enabled.
3. Click the **Cancel Reservation** button.

**Expected Outcome:**
- The reservation is removed from the `TableView`.
- The reservation is deleted from the database.
- A confirmation alert is displayed: "Reservation canceled successfully."

---

## Test Case 3: Leave a Review

**Objective:** Ensure users can submit reviews for completed reservations.

**Steps:**
1. Log in and make sure to have a reservation then hit the `View My Reservation" button.
2. Identify a completed reservation this is based on your current time make sure you reserve a time before current so leave a review button will be enabled.
3. Click the **Leave a Review** button.
4. Enter a valid rating and feedback in the review form.
5. Click the **Submit Review** button.

**Expected Outcome:**
- A success alert is displayed: "Review successfully submitted!"
- The review is saved in the database.
- The reservation’s action button changes to **View My Review**.

---

## Test Case 4: View Your Review


**Steps:**
1. Log in, make a reservation, and hit the `View My Reviews` button.
2. Verify data

**Expected Outcome:**
- The `ReviewView.fxml` scene is displayed.
- The `TableView` shows:
    - Restaurant name
    - Rating
    - Feedback
    - Date of experience.
  
---

## Phase 3

## Commit 12/4/25

# Application Infrastructure and User Experience Framework

The **Application Infrastructure and User Experience Framework** is designed to streamline application functionality while maintaining a 
seamless user experience. This feature combines essential components such as **Scene Management**, **Alerts and Notifications**, **UI Element Management**, and 
**Database Schema and Seed Data Initialization** to create a robust foundation for the application.

---

## Functions

### **Scene Management**
- Handles seamless transitions between views.
- Maintains consistent window dimensions and maximization state.
- Supports custom controller initialization during scene transitions.

### **Alerts and Notifications**
- Displays error and success alerts to guide users during operations.

### **UI Element Management**
- Dynamically manages UI components such as `ComboBox`, ensuring proper population and default selections.

### **Database Schema and Seed Data Initialization**
- Establishes the structural framework of the application.
- Defines core entities, relationships, and initial data for development and testing.

---

## Code Implementation

### **Scene Management**

Scene management begins with the `UIUtil` utility class, which provides methods for transitioning between scenes while preserving the application window's state.

#### **Key Methods**

##### **`setScene`**
Maintains consistent window dimensions and maximization state during scene transitions.

```java
private static void setScene(Stage window, Parent parent) {
    boolean isMaximized = window.isMaximized();
    double currentWidth = window.getWidth();
    double currentHeight = window.getHeight();
    Scene scene = new Scene(parent, currentWidth, currentHeight);
    window.setScene(scene);
    if (isMaximized) {
        window.setMaximized(true);
    } else {
        window.setWidth(currentWidth);
        window.setHeight(currentHeight);
    }
    window.show();
}
```

##### **`displayScene`**
Switches scenes by loading a new FXML layout.

```java
public static void displayScene(URL url, ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setScene(window, parent);
    } catch (IOException e) {
        displayAlert("Scene Switch Error", "Failed to switch scenes.");
    }
}
```

##### **`displaySceneWithController`**
Extends scene-switching functionality by initializing controllers with custom logic before displaying the scene.

```java
public static <T> void displaySceneWithController(URL url, ActionEvent event, Consumer<T> controllerConsumer) {
    try {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        T controller = loader.getController();
        controllerConsumer.accept(controller);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setScene(window, parent);
    } catch (IOException e) {
        displayAlert("Scene Switch Error", "Failed to switch scenes.");
    }
}
```

### **Alerts and Notifications**
This feature ensures effective user communication through information dialogs for errors and successes.

##### **`displayAlert`**
Displays a modal alert dialog with a title and message.

```java
public static void displayAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
```

### **UI Element Management**

Dynamic UI elements like `ComboBox` are managed with methods such as `populateComboBox`, ensuring they are properly populated and default selections are applied.

##### **`populateComboBox`**
Populates a `ComboBox` with a list of items and selects the first item if available.

```java
public static <T> void populateComboBox(ComboBox<T> comboBox, List<T> items) {
    comboBox.setItems(FXCollections.observableArrayList(items));
    if (!items.isEmpty()) {
        comboBox.getSelectionModel().selectFirst();
    }
}
```

# Database Schema and Seed Data Initialization

**Purpose:**  
This script establishes the structural foundation and initial dataset for the application, enabling features like user management, restaurant discovery, table reservations, and customer reviews.

---

## Schema Overview

### Users Table

**Purpose:**  
Stores user credentials for authentication.

**Fields:**
- `user_id`: Unique identifier for each user (Primary Key).
- `username`: User's unique login name.
- `password`: User's hashed password.

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);
```

### Restaurants Table

**Purpose:**  
Contains restaurant details such as name, city, cuisine type, and menu information.

**Fields:**
- `restaurant_id`: Unique identifier (Primary Key).
- `name`: Restaurant name.
- `city`: Location of the restaurant.
- `cuisine_type`: Type of cuisine offered (e.g., Fast Food, BBQ, Vegan).
- `description`: Brief description of the restaurant.
- `menu_pdf`: Path to the restaurant's menu file.
- `image_path`: Path to the restaurant's image file.
- `max_guests`: Maximum seating capacity.

```sql
CREATE TABLE restaurants (
    restaurant_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    cuisine_type VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    menu_pdf VARCHAR(255),
    image_path VARCHAR(255),
    max_guests INT
);
```

### Tables Table

**Purpose:**  
Tracks table details for each restaurant, including seating capacity and booking fees.

**Fields:**
- `table_id`: Unique identifier (Primary Key).
- `restaurant_id`: Associated restaurant (Foreign Key).
- `table_number`: Identifier for the table.
- `number_of_seats`: Seating capacity of the table.
- `booking_fee`: Cost to reserve the table.

```sql
CREATE TABLE tables (
    table_id INT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id INT NOT NULL,
    table_number VARCHAR(50) NOT NULL,
    number_of_seats INT NOT NULL,
    booking_fee DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id)
);
```

### Time Slots Table

**Purpose:**  
Stores predefined time slots for restaurant reservations.

**Fields:**
- `slot_id`: Unique identifier (Primary Key).
- `slot_label`: Descriptive time slot (e.g., "9:00 AM - 11:00 AM").

```sql
CREATE TABLE time_slots (
    slot_id INT AUTO_INCREMENT PRIMARY KEY,
    slot_label VARCHAR(50) NOT NULL
);
```

### Reservations Table

**Purpose:**  
Tracks reservation details, linking users, restaurants, and time slots.

**Fields:**
- `reservation_id`: Unique identifier (Primary Key).
- `user_id`: Associated user (Foreign Key).
- `restaurant_id`: Associated restaurant (Foreign Key).
- `booking_time`: Timestamp for reservation creation.
- `reservation_date`: Date of the reservation.
- `time_slot_id`: Associated time slot (Foreign Key).
- `table_number`: Reserved table identifier.

```sql
CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    restaurant_id INT,
    booking_time DATETIME NOT NULL,
    reservation_date DATE NOT NULL,
    time_slot_id INT,
    table_number VARCHAR(20),
    FOREIGN KEY (time_slot_id) REFERENCES time_slots(slot_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id)
);
```

### Reviews Table

**Purpose:**  
Stores user reviews for restaurants.

**Fields:**
- `review_id`: Unique identifier (Primary Key).
- `user_id`: Associated user (Foreign Key).
- `restaurant_id`: Associated restaurant (Foreign Key).
- `reservation_id`: Associated reservation (Foreign Key).
- `rating`: User's rating (1 to 5).
- `feedback`: Text feedback.
- `date_of_experience`: Date of the experience.

```sql
CREATE TABLE reviews (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    restaurant_id INT,
    reservation_id INT,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    feedback TEXT,
    date_of_experience DATE NOT NULL,
    FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id)
);
```

---

## Seed Data Initialization

### Predefined Entries

- **Users**: Includes a default admin user.
- **Restaurants**: Categorized by city and cuisine.
- **Tables**: Each restaurant has defined tables with seating capacity and booking fees.
- **Time Slots**: Predefined time slots for consistent scheduling.
- **Reservations**: Sample data to validate schema relationships.

#### Example for Time Slots:

```sql
INSERT INTO time_slots (slot_label) VALUES
('9:00 AM - 11:00 AM'),
('11:00 AM - 1:00 PM'),
('1:00 PM - 3:00 PM'),
('3:00 PM - 5:00 PM'),
('5:00 PM - 7:00 PM'),
('7:00 PM - 9:00 PM'),
('9:00 PM - 11:00 PM');
```

#### Example for Restaurants in Minneapolis:

```sql
INSERT INTO restaurants (name, city, cuisine_type, description, menu_pdf, image_path, max_guests)
VALUES
('Burger God', 'Minneapolis', 'Fast Food', 'Serving fast, delicious food with a variety of quick meal options for people on the go.', 'fastfood_menu.pdf', 'images/BurgerGod.png', 10),
('Charcoal Flame', 'Minneapolis', 'BBQ', 'Experience perfectly grilled meats and smoky flavors from the finest barbeque in town.', 'bbq_menu.pdf', 'images/bbq1.png', 10);
```

#### Example for Tables:

```sql
INSERT INTO tables (restaurant_id, table_number, number_of_seats, booking_fee)
VALUES
(1, 'T1', 4, 40),
(1, 'T2', 6, 90),
(1, 'T3', 2, 20);
```

#### Example for Reservations:

```sql
INSERT INTO reservations
(reservation_id, user_id, restaurant_id, booking_time, reservation_date, time_slot_id, table_number)
VALUES
(1, 1, 1, '2024-10-08 01:05:29', '2024-10-08', 1, 'T1');
```

# Test Cases

## Scene Management
- **Test Objective**: Verify that scene transitions work correctly.
- **Test Steps**:
    1. Navigate through various views in the application (e.g., Login to Dashboard, Dashboard to Reservations).
    2. Observe the scene transitions.
- **Expected Results**:
    - If the scene switches as intended, the functionality is working.

---

## Alerts and Notifications
- **Test Objective**: Validate that alerts (success and error) are displayed correctly.
- **Test Steps**:
    1. Perform an action that triggers an alert (e.g., login with incorrect credentials or successfully create a reservation).
    2. Observe if the alert appears.
- **Expected Results**:
    - If alerts are popping up as expected, the functionality is working.

---

## UI Element Management
- **Test Objective**: Ensure that UI elements, such as `ComboBox`, are properly populated.
- **Test Steps**:
    1. Navigate to a dropdown selection (e.g., the Adults and Children dropdown in the reservation form).
    2. Observe the displayed text in the dropdown menu.
- **Expected Results**:
    - If the text "Adults" and "Children" appears on the dropdown, the functionality is working.

---

## Database Schema and Seed Data Initialization
- **Test Objective**: Validate that the database schema and seed data enable all application features.
- **Test Steps**:
    1. Launch the application and execute all its main features (e.g., login, search restaurants, make reservations, leave reviews).
    2. Observe if the features run without errors.
- **Expected Results**:
    - If you are able to use all the features of BookNow without issues, the functionality is working.

    