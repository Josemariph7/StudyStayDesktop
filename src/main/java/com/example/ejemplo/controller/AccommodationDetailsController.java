package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationPhoto;
import com.example.ejemplo.model.AccommodationReview;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class AccommodationDetailsController {
    @FXML
    public Button btnBack;
    @FXML
    private Label lblAuthor;
    @FXML
    private Label lblCreationDate;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblCity;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblCapacity;
    @FXML
    private Label lblServices;
    @FXML
    private Label lblAvailability;
    @FXML
    private Label lblRating;
    @FXML
    private ListView<AccommodationReview> listViewComments;
    @FXML
    private StackPane imageCarousel;
    @FXML
    private Button btnDelete;

    private Accommodation accommodation;
    private AccommodationReviewController reviewController = new AccommodationReviewController();
    private AccommodationController accommodationController = new AccommodationController();
    private AdminDashboardController adminDashboardController = new AdminDashboardController();

    public void initData(Accommodation accommodation, AccommodationController accommodationController, AdminDashboardController dashboard) {
        this.accommodation = accommodation;
        this.adminDashboardController = dashboard;
        this.accommodationController = accommodationController;
        displayAccommodationInfo();
        loadReviews();
        setupImageCarousel();
    }

    private void displayAccommodationInfo() {
        lblAuthor.setText(accommodation.getOwner().getName() + " " + accommodation.getOwner().getLastName());
        lblCreationDate.setText(accommodation.getDescription());  // Assuming description is the creation date, adjust as needed
        lblAddress.setText("Address: " + accommodation.getAddress());
        lblCity.setText("City: " + accommodation.getCity());
        lblPrice.setText("Price: " + accommodation.getPrice().toString());
        lblCapacity.setText("Capacity: " + accommodation.getCapacity());
        lblServices.setText("Services: " + accommodation.getServices());
        lblAvailability.setText("Availability: " + (accommodation.isAvailability() ? "Available" : "Not Available"));
        lblRating.setText("Rating: " + accommodation.getRating());
    }

    private void loadReviews() {
        List<AccommodationReview> reviews = reviewController.getAllReviews();
        List<AccommodationReview> reviewsToAdd = new ArrayList<>();
        for (AccommodationReview review : reviews) {
            if (review.getAccommodation().equals(accommodation)) {
                reviewsToAdd.add(review);
            }
        }
        listViewComments.getItems().addAll(reviewsToAdd);
    }

    private void setupImageCarousel() {
        List<AccommodationPhoto> photos = accommodation.getPhotos();
        if (photos == null || photos.isEmpty()) {
            ImageView defaultImageView = new ImageView(new Image(Constants.DEFAULT_ACCOMMODATION_PICTURE));
            defaultImageView.setFitHeight(200);
            defaultImageView.setFitWidth(600);
            imageCarousel.getChildren().add(defaultImageView);
        } else {
            for (AccommodationPhoto photo : photos) {
                Image image = new Image(new ByteArrayInputStream(photo.getPhotoData()));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(200);
                imageView.setFitWidth(600);
                imageCarousel.getChildren().add(imageView);
            }
        }
    }

    @FXML
    private void handleDelete() {
        AccommodationReview selectedReview = listViewComments.getSelectionModel().getSelectedItem();
        if (selectedReview != null) {
            boolean result = reviewController.deleteReview(selectedReview.getReviewId());
            if (result) {
                listViewComments.getItems().remove(selectedReview);
                Notifications.create()
                        .title("Success")
                        .text("Review deleted successfully!")
                        .hideAfter(Duration.seconds(3))
                        .showInformation();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not delete the review.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No review selected.");
            alert.showAndWait();
        }
    }
}
