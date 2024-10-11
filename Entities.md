# Entities.md

---

## **User**

- **Description**:
  The `User` entity represents individuals interacting with the system. These users can be customers making reservations or reviewing restaurants.

- **Attributes**:
    - `userId`: Unique identifier for the user.
    - `username`: A unique string representing the user's login name.
    - `password`: A hashed password for secure login.
    - `role`: The role of the user, either "customer" or "restaurant".

- **Responsibilities**:
    - Create accounts, log in, and authenticate themselves.
    - Manage personal information such as passwords.
    - Make reservations and leave reviews.

---

## **Restaurant**

- **Description**:
  The `Restaurant` entity represents restaurants available for booking in the system. It includes relevant details such as location, cuisine type, and maximum guest capacity.

- **Attributes**:
    - `restaurantId`: Unique identifier for the restaurant.
    - `name`: Name of the restaurant.
    - `city`: Location of the restaurant.
    - `cuisineType`: Type of food served (e.g., Italian, Mexican).
    - `description`: A brief description of the restaurant.
    - `menuPdf`: A file path to the restaurant’s menu in PDF format.
    - `imagePath`: A file path to an image representing the restaurant.
    - `maxGuests`: The maximum number of guests the restaurant can accommodate.

- **Responsibilities**:
    - Provide restaurant details such as menus and location.
    - Display available reservation slots.
    - Receive and display customer reviews.

---

## **Reservation**

- **Description**:
  The `Reservation` entity keeps track of table bookings made by users at restaurants. It connects users, restaurants, and specific time slots.

- **Attributes**:
    - `reservationId`: Unique identifier for the reservation.
    - `restaurantId`: The ID of the restaurant where the reservation is made.
    - `restaurantName`: The name of the restaurant.
    - `reservationDate`: The date the reservation was made.
    - `timeSlot`: The reserved time slot.
    - `tableNumber`: The number of the reserved table.
    - `user`: The user who made the reservation.
    - `restaurant`: The restaurant associated with the reservation.
    - `timeSlotReserved`: The time slot reserved.
    - `table`: The table reserved.

- **Responsibilities**:
    - Store information about reservations made by users.
    - Link restaurants and available tables to specific reservation times.

---

## **Review**

- **Description**:
  The `Review` entity represents customer feedback provided after visiting a restaurant. This entity stores the review rating, feedback text, and related reservation.

- **Attributes**:
    - `reviewId`: Unique identifier for the review.
    - `username`: The name of the user who left the review.
    - `restaurantName`: The name of the restaurant being reviewed.
    - `rating`: Numeric rating given by the user (e.g., 1-5).
    - `feedback`: Written feedback from the user.
    - `dateOfExperience`: The date the user visited the restaurant.
    - `user`: The user who left the review.
    - `restaurant`: The restaurant being reviewed.
    - `reservation`: The reservation associated with the review.

- **Responsibilities**:
    - Store customer feedback and rating information.
    - Display reviews for each restaurant based on customer experiences.

---

## **Table**

- **Description**:
  The `Table` entity represents individual tables in a restaurant that are available for booking. Each table has a number of seats and an associated booking fee.

- **Attributes**:
    - `tableId`: Unique identifier for the table.
    - `restaurantId`: The ID of the restaurant the table belongs to.
    - `tableNumber`: The table's assigned number.
    - `numberOfSeats`: The number of seats available at the table.
    - `bookingFee`: The fee required to reserve the table.
    - `isAvailable`: Boolean flag indicating if the table is currently available for booking.

- **Responsibilities**:
    - Provide details about available tables for customers to reserve.
    - Track seating capacity and availability.

---

## **TimeSlot**

- **Description**:
  The `TimeSlot` entity represents specific time slots available for restaurant reservations. Time slots are critical for managing when a table is available for booking.

- **Attributes**:
    - `slotId`: Unique identifier for the time slot.
    - `slotLabel`: The label for the time slot (e.g., "6:00 PM - 7:00 PM").

- **Responsibilities**:
    - Provide available time slots for customers to select during reservations.
    - Ensure that each table is only booked for one reservation per time slot.
