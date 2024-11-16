# Entities

## User

- **Purpose**: Represents a user of the application, typically a customer.
- **Fields**: `userId`, `username`, `password`
- **Description**:
  - This entity holds information about each registered user, including their unique ID, username, and password.
  - It is used primarily for authentication and authorization when users log in or register within the system.

## Restaurant

- **Purpose**: Represents a restaurant listed in the application for search and booking.
- **Fields**: `restaurantId`, `name`, `city`, `cuisineType`, `description`, `imagePath`, `menuPdf`
- **Description**:
  - This entity holds key details about a restaurant, such as its name, location, type of cuisine, and a path to its menu and images.
  - It is central to the search and booking functionality, where users search for and view restaurants based on filters.

## Reservation

- **Purpose**: Represents a reservation made by a user for a specific restaurant.
- **Fields**: `reservationId`, `userId`, `restaurantId`, `reservationDate`, `timeSlot`, `tableNumber`
- **Description**:
  - This entity captures all details related to a booking, including the user, restaurant, date, time slot, and table number.
  - It is used to manage the reservation process, including booking, viewing, and cancellation of reservations.

## Review

- **Purpose**: Represents a review left by a user for a restaurant they have visited.
- **Fields**: `reviewId`, `userId`, `restaurantId`, `rating`, `feedback`, `dateOfExperience`
- **Description**:
  - This entity stores user feedback in the form of ratings and comments for a restaurant.
  - It is essential for the review system, allowing users to leave feedback and see reviews left by others.

## Table

- **Purpose**: Represents a table within a restaurant that can be reserved.
- **Fields**: `tableId`, `restaurantId`, `tableNumber`, `numberOfSeats`, `bookingFee`
- **Description**:
  - This entity manages the details of individual tables in each restaurant, including the number of seats and a booking fee.
  - It is used when checking for available tables and making reservations.

## TimeSlot

- **Purpose**: Represents a specific time slot available for booking at a restaurant.
- **Fields**: `timeSlotId`, `restaurantId`, `date`, `time`, `availability`
- **Description**:
  - This entity helps in managing availability for each restaurant by date and time.
  - Allows users to select a suitable reservation time and prevents double-booking by checking slot availability.