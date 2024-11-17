# ChangeLog

## [1.2.6] - 2024-11-16 - Jimmie Xiong

### Updated Architecture.md
- Improved ComboBox Initialization: Updated `BookNowController` to correctly display "Adults" and "Children" as prompt text for `cb_adults` and `cb_children` ComboBoxes, ensuring prompt text visibility without pre-selected values.

### Resolved Type Mismatch in Reservation Flow
- Updated `reserveTable` methods across `TableService`, `TableDAO`, and `BookNowFacadeService` to use `int` consistently as the return type, allowing for detailed reservation status feedback:
  - **-1** for duplicate reservations
  - **1** for successful reservations
  - **0** for failures
- Adjusted `BookNowController` logic to display user-friendly alerts based on `reserveTable` outcomes.

### Improvements
- **Enhanced Reservation Handling**: Ensured users receive specific messages when a table is already reserved, confirmed, or if an error occurs, improving user experience and debugging clarity.

Removed User Roles: Dropped unnecessary role column from users table, simplifying user management.

## [1.2.5] - 2024-11-15 - Jimmie Xiong
- Updated features.md, requriements.md, entities.md to reflect current code.
- 
## [1.2.4] - 2024-11-14 - Jimmie Xiong
- Refactored `ReservationDAO`, `ReservationService`, `RestaurantDAO`, `ReviewDAO`, `UserDAO`.
- Refactored/debugged models `User`, `Reservation`, `Review`, `Table`. Removed unused fields/methods/constructors.
- Code looks cleaner now.

## [1.2.3] - 2024-11-14 - Hao Tran
- Implemented validation: password must be 8 characters or longer, username must be 8 characters or longer.

## [1.2.2] - 11/13/24 # Jimmie Xiong and Hao Tran
## Fixed
- **Reservation Cancellation UI**: Now updates immediately without requiring navigation.
- **Account Creation and Validation**: Moved business logic from the controller to the service layer for better separation of concerns.
- **Review Validation**: Moved business logic for review validation from the controller to the service layer for better separation of concerns.

## Updated
- **ReservationUIManager**: Now directly modifies the `ObservableList` bound to `TableView`.
- **UserService**:
  - Added the `validateAndCreateAccount` method to handle:
    - Validation of input parameters (empty fields, password confirmation).
    - Checking if the username already exists.
    - Creating a new account if validations pass.
    - Returning a message indicating success or failure.
- **ReviewService**:
  - Added the `validateAndSubmitReview` method to handle:
    - Validation of input parameters (rating selection and feedback).
    - Submitting the review if validations pass.
    - Returning a message indicating success or failure.

## Improved
- **Action Buttons**: Now remove canceled reservations from the list, ensuring instant feedback in the UI.
- **CreateNewAcccountController**:
  - Refactored the `onCreateAccountButtonAction` method to:
    - Call `userService.validateAndCreateAccount` for account creation logic.
    - Manage UI updates based on the result from `validateAndCreateAccount`.
- **CreateReviewController**:
  - Refactored the `onSubmitReviewClick` method to:
    - Call `reviewService.validateAndSubmitReview` for validation and submission.
    - Manage UI updates based on the result from `validateAndSubmitReview`.

## Added
### `BookNowServiceManager`
- **searchRestaurants**: Validates input, calculates guests, fetches restaurants, and populates the UI.
- **showAvailability**: Updates availability UI, including back button, labels, and `TableView`.

### `BookNowController`
- **onSearchButtonClick**: Extracts UI values and uses `searchRestaurants` method.
- **handleShowAvailability**: Extracts date and guests, delegates to `showAvailability`.
- **setSelectedTimeSlot**: Sets the selected time slot.

### `validateAndCreateAccount` Method
- **Class**: `UserService`
- **Description**: Created a new method to handle account creation and validation in the service layer.
- **Functionality**:
  - Validates input parameters.
  - Checks if the username already exists.
  - Creates a new account.
  - Returns a message indicating success or failure.

### `validateAndSubmitReview` Method
- **Class**: `ReviewService`
- **Description**: Created a new method to handle review validation and submission in the service layer.
- **Functionality**:
  - Validates input parameters.
  - Submits the review if validations pass.
  - Returns a message indicating success or failure.

- The `BookNowController, all other controllers` is now clean and organized!

## [1.2.1] - 11/13/24
- Refactored code and fixed various errors.
- Suppressed warnings in `createViewTable`.
- Refactored `BookNowView`, `ReservationView`,  `ReviewView` and `CreateReviewView.fxml` to dynamically fill the screen when maximized.
- Reverted to multiple DB instances due to unresolved error with single DB instance approach.
- Moved business logic out of controllers, isolating it within `UIManager` and `ServiceManager` classes; additional cleanup is still in progress.

## [1.1.9] - 11/10/25 - Hao
- Will update `implementation.md`, `architecture.md`, to reflect code.

## [1.1.9] - 11/10/25 - Jimmie Xiong
- Refactored code, fixed errors on review, and show availability.

## [1.1.8] - 11/09/25 - Jimmie Xiong & Hao
- Implemented `dbscript` to work with code.

## [1.1.7] - 11/08/25 - Hao
- Coded `ReservationService`, added new code to `UserService` & `RestaurantService`.

## [1.1.6] - 11/06/25 - Jimmie Xiong
- Implemented `TableDAO` class.

## [1.1.5] - 11/05/25 - Jimmie Xiong
- Refactored code.

## [1.1.4] - 11/02/24 - Hao
- Created `RestaurantDAO`, `ReviewDAO`.

## [1.1.3] - 11/02/24 - Jimmie Xiong
- Implemented `reservationsview.fxml`.

## [1.1.2] - 10/30/24 - Hao
- Implemented `CreateReviewController` and `CreateReviewView`.

## [1.1.1] - 10/29/24 - Jimmie Xiong
- Implemented `ReviewView.fxml`, `ReviewController`.
- Refactored code.

## [1.1.0] - 10/28/24 - Jimmie Xiong
- Deleted `RestaurantBoxController` & FXML.
- Refactored code.

## [1.0.9] - 10/27/24 - Jimmie Xiong
- Renamed `DBConnectionUtil` to `DBConnection`.
- Refactored code.

## [1.0.8] - 10/26/24 - Jimmie Xiong
- Refactored code.

## [1.0.7] - 10/25/24 - Jimmie Xiong
- Implemented a utility class named `UIUtils` to encapsulate common UI-related operations, improving code organization and reuse.
- **Controller Refactoring**: Moved relevant logic from `LoginController`, `CreateNewAccountController`, and `RestaurantBoxController` to `UIUtils` for common tasks, enhancing maintainability and readability.
- Merged `AlertUtil` class functionality into `UIUtils`, as well as `SwitchSceneUtil`.
- Documented some methods.
- Refactored Services, and DAO classes.
- Code looks way cleaner now.

## [1.1.3] - 10/24/24 - Jimmie Xiong
- Updated implementation.md, features.md, requriements.md, Architecture.md

## [1.1.2] - 10/23/24 - Jimmie Xiong
Refactoring and Architecture Improvements:
- Refactored the project to follow the Model-View-Controller (MVC) pattern for better structure and maintainability.
  Introduced DAO and Service Layers:
- Created DAO (Data Access Object) classes to handle database interactions, separating them from business logic.
- Added Service classes, including RestaurantServices and UserServices, to handle business logic, keeping the code modular.
  Controller Refactoring:
- Moved business logic out of controller classes such as BookNowController, LoginController, CreateNewAccountController and RestaurantBoxController
- Controllers now focus solely on handling UI and user input, while delegating data operations to the Services and DAO layers.
  Database Connection Utility:
- Renamed DBConnection to DB_Connection_Util to simplify its role in managing database connections.
- Removed business logic from the connection utility and centralized it in the DAO layer.
  Utility Classes:
- Introduced a new Utils package for reusable utility code:
- AlertUtil: Centralized logic for showing alerts and messages to the user.
- SwitchSceneUtil: Streamlined scene transitions across the app, making navigation cleaner and more reusable.
  Model Optimization:
- Removed unused fields like userId from the User model as they were not necessary for the current functionality, making the model leaner and more efficient.
  DAO Classes Implementation:
- Implemented RestaurantDAO and UserDAO classes to handle all queries related to restaurants and users, improving the separation of concerns.
  Service Classes Implementation:
- Introduced RestaurantServices and UserServices classes to manage all business logic.
  Expanded input validation logic for account creation to handle more specific error messages.
- Validation now checks for:
- Empty username, password, and confirm password fields.
- Username and password length requirements.
- Mismatched password and confirm password fields.
- validation system covers all possible field combinations and ensures appropriate feedback to the user.
  Fixed issues with the database script:
  - Corrected restaurant reservation data for all cities (Minneapolis, Saint Paul, Maple Grove, and others).
  - Added accurate reservation dates to ensure restaurants are properly available in all cities when searched by date.


## [1.1.1] - 10/22/24 - Jimmie Xiong
Refactored BookNowController to follow the MVC pattern took me awhile to realize was not following MVC. Removed UI management for the restaurant box (lower half) from the controller.
Created RestaurantBox.fxml to handle the restaurant display layout.
Added RestaurantBoxController class to manage the logic related to individual restaurant display components (name, rating, image, etc.).

## [1.1.0] - 10/21/24 - Jimmie Xiong
Implemented method for dynamically loading and setting restaurant names.
Created method to dynamically fetch and display restaurant ratings.

## [1.0.9] - 10/20/24 - Jimmie Xiong
Added createRestaurantButtons method to handle the creation of buttons like "Read Reviews", "View Menu", and "Show Availability".
Defined placeholder methods for actions related to the buttons: reviewsButton, menuButton, and availabilityButton.

## [1.0.8] - 10/19/24 - Jimmie Xiong
Added createRestaurantImage method to set and display restaurant images.

## [1.0.7] - 10/17/24 - Jimmie Xiong
Refactored restaurant display logic by separating createRestaurantBox and createRestaurantDetails methods.
Renamed populateRestaurantListVBox method to populateRestaurant for clarity.

## [1.0.6] - 10/15/25 - Jimmie Xiong
- Replaced **Portability** non-functional requirement with **Maintainability**.
- The project relies on a locally hosted database, which makes it non-portable between different environments without manual configuration. This
  dependency rendered the portability requirement irrelevant.
-
## [1.0.5] - 10/15/24 - Jimmie Xiong
- Fixed an issue in module-info.java where the .booknow module was incorrectly pointing to edu.metrostate.booknow.booknow instead of edu.metrostate.booknow
- Added notes about implementation errors in implementation.md

## [1.0.4] - 10/10/24 - Jimmie Xiong
- Updated `Architecture.md`.
- Added final touches to milestone 3 deliverables.

## [1.0.3] - 10/09/24 - Hao Tran
- Updated `Wireframe.md`.
- Contributed updates to `Architecture.md`.
- Made necessary refinements based on feedback.

## [1.0.3] - 10/09/24 - Jimmie Xiong
- Updated `DBConnection`.
- Modified `dbscript` to reflect new changes.

## [1.0.3] - 10/08/24 - Hao Tran
- Implemented `CreateNewAccountController` and `LoginController`.
- Updated `Architecture.md`.
- Added images, menus, and updated diagrams.
- Modified `dbscript` to reflect additional logic.

## [1.0.3] - 10/05/24 - Jimmie Xiong
- Updated wireframes for Login, Create New Account, and BookNow Dashboard.
- Created the initial `dbscript`.
- Implemented `BookNowController` logic.
- Implemented `DBConnection` logic
- Added `Util` class.
- Updated models for enhanced functionality.
## [1.0.1] - 09/29/24 - Jimmie Xiong
### Added
- Implemented the `displayRestaurantDetails(Restaurant restaurant)` method in `BookNowController` for displaying detailed information about selected restaurants.
- Added a new FXML file section for the restaurant details page.
- Created the `RestaurantDetailsView` UI for displaying restaurant details, including menu, operating hours, and customer reviews.
- Added methods to the `BookNowController`

## [1.0.0] - 09/28/24 - Jimmie Xiong
### Added
- Developed models: `Customer`, `Restaurant`, and `Reservation`.
- Developed views: `LoginView`, `CreateAccountView`, and `BookNowView`.
- Developed controllers: `LoginController`, `CreateAccountController`, and `BookNowController`.
- Developed services: `AuthenticationService`, `RestaurantService`, and `ReservationService`.
- Developed Utils `Alerts`, `DatabaseConnection`
- Did Milestone01, 02 again. 