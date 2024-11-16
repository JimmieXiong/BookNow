# Features

## 1. User Authentication

### Account Creation
- **Functionality**:
   - Allows new users to register by entering a unique username, password, and password confirmation.
   - Passwords must match and meet a minimum length of 8 characters.
- **Error Handling**:
   - Displays specific error messages for mismatched passwords, short usernames, and duplicate usernames using `UIUtil.displayAlert()`.

### Login
- **Functionality**:
   - Allows existing users to log in using valid credentials, verified against stored data in the database.
- **Error Handling**:
   - Displays error messages for invalid login attempts using `UIUtil.displayAlert()`.

## 2. Restaurant Search Functionality

### Comprehensive Filters
- **Functionality**:
   - Users can filter by location, cuisine type, reservation date, and guest count (adults and children).
   - Date Validation ensures the reservation date cannot be in the past.
   - Guest Selection provides dropdowns for selecting adult and child guest counts.
- **Dynamic Search**:
   - Updates and displays search results in real-time, based on selected filters.
- **No Results Alert**:
   - Shows a message if no restaurants match the selected criteria.

## 3. Reservation System

### Table Availability Check
- **Functionality**:
   - Users can view available tables and time slots for selected restaurants, date, and guest count.
- **Reservation Details**:
   - Allows users to choose a time slot and table with visual indicators for available options.
- **Confirmation**:
   - Notifies users immediately upon successful reservation.
- **Backend Integration**:
   - Saves reservation data directly into the database for easy retrieval.
- **Limitations**:
   - Only allows reservations for valid dates and available slots.

## 4. Review System

### Leave a Review
- **Functionality**:
   - Allows users to leave a review with a rating and feedback after a completed reservation.
   - Validation ensures the review is only available for past reservations that have not been reviewed yet.

### View Customer Reviews
- **Functionality**:
   - Displays existing reviews for each restaurant, including rating, feedback, and date of experience.
   - Allows users to view a list of their previously submitted reviews.

### Review Error Handling
- **Functionality**:
   - Provides error messages if required fields (like rating or feedback) are missing.

## 5. Reservation Management

### Cancel Reservation
- **Functionality**:
   - Users can cancel upcoming reservations directly from the reservation list.
   - Eligibility Check verifies if a reservation is within the eligible window for cancellation.
   - Displays an alert if the reservation cannot be canceled within the restricted time frame.

### Confirmation on Cancellation
- **Functionality**:
   - Notifies users once a reservation is successfully canceled and updates the displayed list.

## 6. Manual Restaurant Data Management

### Database-Managed Restaurant Profiles
- **Functionality**:
   - All restaurant information (location, cuisine, availability, and menus) is stored and managed in the database by administrators.
   - Restaurants do not have access to modify their profiles directly on the platform.

### Data Consistency
- **Functionality**:
   - Ensures that all displayed restaurant data is accurate and consistently retrieved from the database.

## 7. Enhanced Error Handling

### Detailed Error Messages
- **Functionality**:
   - Provides specific messages for validation failures (e.g., missing fields in the search form, invalid dates).
   - Notifies users of database connection issues using error handling in `DBConnection`.

### User-Friendly Error Messages
- **Functionality**:
   - Provides comprehensive error handling for invalid inputs or failed processes.

## 8. Booking Dashboard

### Dashboard for Active Reservations
- **Functionality**:
   - Allows users to view current reservations and manage them through a user-friendly interface.

### Past Reservations View
- **Functionality**:
   - Displays completed reservations, allowing users to leave reviews on eligible reservations.

### Interactive Elements
- **Functionality**:
   - Each reservation has options for “Cancel Reservation” (if eligible) or “Leave a Review” (for completed reservations).

## 9. Restaurant Details Page

### View Restaurant Information
- **Functionality**:
   - Displays detailed restaurant information when a user selects a restaurant from the search results.

### View Menu
- **Functionality**:
   - Opens a PDF menu associated with the restaurant, if available.

### Read Customer Reviews
- **Functionality**:
   - Shows customer reviews with ratings for the selected restaurant.

### Error Handling for Missing Information
- **Functionality**:
   - Notifies users if specific information, like a menu or reviews, is unavailable for the selected restaurant.

## 10. Consistent UI Navigation

### Smooth Scene Transitions
- **Functionality**:
   - Utilizes `UIUtil.displayScene()` and `displaySceneWithController()` to enable seamless transitions between pages.

### User Feedback
- **Functionality**:
   - Provides visual cues, such as alerts and status updates, to guide users through the reservation, search, and review processes.

### Centralized Error Alerts
- **Functionality**:
   - Uses `UIUtil.displayAlert()` for consistent error and success message displays across different user actions.expect.