package edu.metrostate.booknow.Utils;

import edu.metrostate.booknow.Models.Restaurant;
import edu.metrostate.booknow.Models.Review;
import edu.metrostate.booknow.Models.Table;
import edu.metrostate.booknow.Services.ReviewService;
import edu.metrostate.booknow.Services.TableService;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;


public class RestaurantUIManager {

    private final ReviewService reviewService;
    private final TableService tableService;

    public RestaurantUIManager(ReviewService reviewService, TableService tableService) {
        this.reviewService = reviewService;
        this.tableService = tableService;
    }

    // Made styling contants to make code somewhat cleaner/can be reused/combined
    private static final String SPACING_STYLE_5 = "-fx-spacing: 5;";
    private static final String FONT_SIZE_12 = "-fx-font-size: 12px;";
    private static final String FONT_SIZE_14 = "-fx-font-size: 14px;";
    private static final String FONT_SIZE_16 = "-fx-font-size: 16px;";
    private static final String FONT_WEIGHT_BOLD = "-fx-font-weight: bold;";
    private static final String TEXT_FILL_BLACK = "-fx-text-fill: black;";
    private static final String TEXT_FILL_GRAY = "-fx-text-fill: gray;";
    private static final String TEXT_FILL_WHITE = "-fx-text-fill: white;";
    private static final String TEXT_FILL_GREEN = "-fx-text-fill: green;";
    private static final String TEXT_FILL_ORANGE = "-fx-text-fill: orange;";
    private static final String TEXT_FILL_RED = "-fx-text-fill: red;";
    private static final String BACKGROUND_COLOR_BLUE = "-fx-background-color: #003580;";

    // Component Specific Styling Constants
    private static final String RESTAURANT_BOX_STYLE = "-fx-spacing: 10; -fx-border-color: lightgray; -fx-padding: 10;";
    private static final String RESTAURANT_DETAILS_STYLE = SPACING_STYLE_5;
    private static final String RESTAURANT_NAME_STYLE = FONT_SIZE_16 + FONT_WEIGHT_BOLD + "-fx-text-fill: #003580;";
    private static final String RESTAURANT_LOCATION_STYLE = TEXT_FILL_GRAY;
    private static final String RESTAURANT_BUTTONS_STYLE = SPACING_STYLE_5;

    private static final String REVIEWS_AND_RATING_STYLE = SPACING_STYLE_5;
    private static final String REVIEW_BUTTON_STYLE = FONT_WEIGHT_BOLD + TEXT_FILL_BLACK;

    private static final String RATING_STYLE = "-fx-padding: 5;" + BACKGROUND_COLOR_BLUE + FONT_WEIGHT_BOLD + FONT_SIZE_14 + TEXT_FILL_WHITE;
    private static final String BUTTON_STYLE = FONT_WEIGHT_BOLD + TEXT_FILL_BLACK;

    private static final String BOOKING_POLICY_STYLE = FONT_SIZE_12 + TEXT_FILL_RED;
    private static final String PRICE_LABEL_STYLE = TEXT_FILL_GREEN + FONT_SIZE_14;

    private static final String RESERVE_BUTTON_STYLE = FONT_WEIGHT_BOLD + BACKGROUND_COLOR_BLUE + TEXT_FILL_WHITE;

    private static final String TABLE_VIEW_STYLE = "-fx-border-color: lightgray; -fx-padding: 10;";

    private static final String REVIEW_BOX_STYLE = "-fx-padding: 10; -fx-spacing: 5; -fx-border-color: lightgray; -fx-border-width: 1;";
    private static final String USERNAME_LABEL_STYLE = FONT_SIZE_14 + TEXT_FILL_BLACK;
    private static final String RATING_LABEL_STYLE = FONT_SIZE_14 + TEXT_FILL_ORANGE;
    private static final String FEEDBACK_LABEL_STYLE = FONT_SIZE_12 + TEXT_FILL_BLACK;
    private static final String DATE_LABEL_STYLE = FONT_SIZE_12 + TEXT_FILL_GRAY;


    /**
     * Populates a given VBox with a list of restaurants.
     *
     * @param restaurantListVBox The VBox container to be populated with restaurant entries.
     * @param restaurants The list of restaurants to display.
     * @param onReadReviews A Consumer function to handle the action of reading reviews for a restaurant.
     * @param onViewMenu A Consumer function to handle the action of viewing the menu of a restaurant.
     * @param onShowAvailability A Consumer function to handle the action of showing availability for a restaurant.
     */
    public void populateRestaurantListVBox(VBox restaurantListVBox, List<Restaurant> restaurants, Consumer<Restaurant> onReadReviews, Consumer<Restaurant> onViewMenu, Consumer<Restaurant> onShowAvailability) {
        restaurantListVBox.getChildren().clear();

        for (Restaurant restaurant : restaurants) {
            HBox restaurantBox = createRestaurantBox(restaurant, onReadReviews, onViewMenu, onShowAvailability);
            restaurantListVBox.getChildren().add(restaurantBox);
        }
    }

    private HBox createRestaurantBox(Restaurant restaurant, Consumer<Restaurant> onReadReviews, Consumer<Restaurant> onViewMenu, Consumer<Restaurant> onShowAvailability) {
        HBox restaurantBox = new HBox();
        // spacing + border padding
        restaurantBox.setStyle(RESTAURANT_BOX_STYLE);

        ImageView restaurantImage = createRestaurantImageView(restaurant.getImagePath());
        VBox restaurantDetails = createRestaurantDetails(restaurant);
        VBox restaurantButtons = createRestaurantButtons(restaurant, onReadReviews, onViewMenu, onShowAvailability);

        restaurantBox.getChildren().addAll(restaurantImage, restaurantDetails, restaurantButtons);

        return restaurantBox;
    }

    private ImageView createRestaurantImageView(String imagePath) {
        ImageView restaurantImage = new ImageView();
        restaurantImage.setFitHeight(150);
        restaurantImage.setFitWidth(200);

        String imageFilePath = "/" + imagePath;
        try {
            restaurantImage.setImage(new Image(getClass().getResource(imageFilePath).toExternalForm()));
        } catch (NullPointerException e) {
            restaurantImage.setImage(new Image(getClass().getResource("/images/default.jpg").toExternalForm()));
        }

        return restaurantImage;
    }

    private VBox createRestaurantDetails(Restaurant restaurant) {
        VBox restaurantDetails = new VBox();
        restaurantDetails.setStyle(RESTAURANT_DETAILS_STYLE);

        Label nameLabel = new Label(restaurant.getName());
        nameLabel.setStyle(RESTAURANT_NAME_STYLE);

        Label locationLabel = new Label(restaurant.getCity());
        locationLabel.setStyle(RESTAURANT_LOCATION_STYLE);

        Label descriptionLabel = new Label(restaurant.getDescription());

        restaurantDetails.getChildren().addAll(nameLabel, locationLabel, descriptionLabel);
        HBox.setHgrow(restaurantDetails, Priority.ALWAYS);

        return restaurantDetails;
    }

    private VBox createRestaurantButtons(Restaurant restaurant, Consumer<Restaurant> onReadReviews, Consumer<Restaurant> onViewMenu, Consumer<Restaurant> onShowAvailability) {
        VBox restaurantButtons = new VBox();
        restaurantButtons.setStyle(RESTAURANT_BUTTONS_STYLE);

        HBox reviewsAndRating = createReviewsAndRatingBox(restaurant, onReadReviews);
        restaurantButtons.getChildren().add(reviewsAndRating);

        Button menuButton = createButton("View Menu", onViewMenu, restaurant);
        Button availabilityButton = createButton("Show Availability", onShowAvailability, restaurant);

        restaurantButtons.getChildren().addAll(menuButton, availabilityButton);

        return restaurantButtons;
    }

    private HBox createReviewsAndRatingBox(Restaurant restaurant, Consumer<Restaurant> onReadReviews) {
        HBox reviewsAndRating = new HBox();
        reviewsAndRating.setStyle(REVIEWS_AND_RATING_STYLE);

        Button reviewsButton = new Button("Read Reviews");
        reviewsButton.setStyle(REVIEW_BUTTON_STYLE);
        reviewsButton.setOnAction(event -> onReadReviews.accept(restaurant));

        String averageRating = reviewService.getAverageRating(restaurant.getRestaurantId());
        Label ratingLabel = new Label(averageRating);
        ratingLabel.setStyle(RATING_STYLE);

        reviewsAndRating.getChildren().addAll(reviewsButton, ratingLabel);

        return reviewsAndRating;
    }

    private Button createButton(String text, Consumer<Restaurant> action, Restaurant restaurant) {
        Button button = new Button(text);
        button.setStyle(BUTTON_STYLE);
        button.setOnAction(event -> action.accept(restaurant));
        return button;
    }

    @SuppressWarnings("unchecked")
    public TableView<Table> createTableView(Restaurant restaurant, LocalDate selectedDate, int selectedGuests, Consumer<Table> onReserveTable, Consumer<String> onTimeSlotSelected) {
        TableView<Table> tableView = new TableView<>();

        TableColumn<Table, String> tableNumberColumn = new TableColumn<>("Table Number");
        TableColumn<Table, Integer> numberOfSeatsColumn = new TableColumn<>("Seats");
        TableColumn<Table, Double> priceColumn = new TableColumn<>("Price");
        TableColumn<Table, String> timeSlotColumn = new TableColumn<>("Available Time Slots");
        TableColumn<Table, Void> reserveColumn = new TableColumn<>("Reserve");

        tableNumberColumn.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        numberOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("bookingFee"));

        priceColumn.setCellFactory(column -> new TableCell<Table, Double>() {
            private final VBox vbox = new VBox();
            private final Label priceLabel = new Label();
            private final Label bookingPolicyLabel = new Label("Note: Booking fee is non-refundable if canceled within 24 hours.");

            {
                bookingPolicyLabel.setStyle(BOOKING_POLICY_STYLE);
                vbox.getChildren().addAll(priceLabel, bookingPolicyLabel);
                vbox.setSpacing(2);
            }

            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    priceLabel.setText(String.format("$%.2f (Booking Fee Included)", item));
                    priceLabel.setStyle(PRICE_LABEL_STYLE);
                    setGraphic(vbox);
                }
            }
        });

        reserveColumn.setCellFactory(col -> new TableCell<Table, Void>() {
            private final Button reserveButton = new Button("Reserve");

            {
                reserveButton.setStyle(RESERVE_BUTTON_STYLE);
                reserveButton.setOnAction(event -> {
                    Table table = getTableView().getItems().get(getIndex());
                    onReserveTable.accept(table);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(reserveButton);
                }
            }
        });

        numberOfSeatsColumn.setCellFactory(col -> new TableCell<Table, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    HBox guestIcons = new HBox();
                    guestIcons.setSpacing(5);
                    for (int i = 0; i < item; i++) {
                        ImageView guestIcon = new ImageView(new Image(getClass().getResource("/images/guest.png").toExternalForm()));
                        guestIcon.setFitHeight(24);
                        guestIcon.setFitWidth(24);
                        guestIcons.getChildren().add(guestIcon);
                    }
                    setGraphic(guestIcons);
                }
            }
        });

        timeSlotColumn.setCellFactory(column -> new TableCell<Table, String>() {
            private final ComboBox<String> timeSlotComboBox = new ComboBox<>();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Table table = getTableView().getItems().get(getIndex());
                    List<String> availableSlots = tableService.getAvailableTimeSlots(restaurant, selectedDate, selectedGuests, table);

                    timeSlotComboBox.setItems(FXCollections.observableArrayList(availableSlots));
                    timeSlotComboBox.setOnAction(event -> {
                        String selectedSlot = timeSlotComboBox.getSelectionModel().getSelectedItem();
                        onTimeSlotSelected.accept(selectedSlot);
                    });
                    timeSlotComboBox.getSelectionModel().selectFirst();

                    setGraphic(timeSlotComboBox);
                }
            }
        });

        Collections.addAll(tableView.getColumns(), tableNumberColumn, numberOfSeatsColumn, priceColumn, timeSlotColumn, reserveColumn);
        tableView.setStyle(TABLE_VIEW_STYLE);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        List<Table> availableTables = tableService.getAvailableTables(restaurant.getRestaurantId(), selectedDate, selectedGuests);
        tableView.setItems(FXCollections.observableArrayList(availableTables));

        return tableView;
    }

    public void displayReviews(VBox reviewsOverlay, List<Review> reviews) {
        reviewsOverlay.getChildren().clear();
        if (reviews.isEmpty()) {
            VBox reviewBox = createEmptyReviewBox();
            reviewsOverlay.getChildren().add(reviewBox);
        } else {
            for (Review review : reviews) {
                VBox reviewBox = createReviewBox(review);
                reviewsOverlay.getChildren().add(reviewBox);
            }
        }
        reviewsOverlay.setVisible(true);
    }

    private VBox createEmptyReviewBox() {
        VBox reviewBox = new VBox(new Label("No reviews yet"));
        reviewBox.setStyle(REVIEW_BOX_STYLE);
        return reviewBox;
    }

    private VBox createReviewBox(Review review) {
        Label usernameLabel = new Label(review.getUsername().substring(0, 2) + "****");
        usernameLabel.setStyle(USERNAME_LABEL_STYLE);

        String stars = "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating());
        Label ratingLabel = new Label(stars);
        ratingLabel.setStyle(RATING_LABEL_STYLE);

        Label feedbackLabel = new Label(review.getFeedback());
        feedbackLabel.setStyle(FEEDBACK_LABEL_STYLE);

        Label dateLabel = new Label("Date of Experience: " + review.getDateOfExperience());
        dateLabel.setStyle(DATE_LABEL_STYLE);

        VBox reviewBox = new VBox(usernameLabel, ratingLabel, feedbackLabel, dateLabel);
        reviewBox.setStyle(REVIEW_BOX_STYLE);
        return reviewBox;
    }


    public void viewMenu(Restaurant restaurant) {
        try {
            // Load the PDF file associated with the restaurant
            InputStream pdfStream = loadPDF(restaurant.getMenuPdf());
            if (pdfStream == null) {
                UIUtil.displayAlert("Error", "Menu not found");
                return;
            }

            // Check if the Desktop class is supported on this system
            if (Desktop.isDesktopSupported()) {
                // Create temp file for the PDF if not temp then it will save pdf in project folder somewhere....
                File tempFile = createTempPDFFile(pdfStream);
                if (tempFile != null) {
                    // Open PDF file using system's default PDF viewer
                    Desktop.getDesktop().open(tempFile);
                } else {
                    UIUtil.displayAlert("Error", "Error creating temporary file for the PDF.");
                }
            } else {
                System.err.println("PDF viewing not supported on this system.");
            }
        } catch (IOException e) {
            UIUtil.displayAlert("Error", "Error opening the PDF file: " + e.getMessage());
        }
    }

    // Creates a temporary file and copies the PDF content to it
    private File createTempPDFFile(InputStream pdfStream) throws IOException {
        File tempFile = File.createTempFile("menu", ".pdf");
        tempFile.deleteOnExit();
        Files.copy(pdfStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return tempFile;
    }

    // Loads PDF file from the specified path within the "menus" directory.
    private InputStream loadPDF(String menuName) {
        return getClass().getResourceAsStream("/menus/" + menuName);
    }


}