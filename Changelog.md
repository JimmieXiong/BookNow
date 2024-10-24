# ChangeLog


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