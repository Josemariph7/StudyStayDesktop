package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationPhoto;
import com.example.ejemplo.model.AccommodationReview;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;

public class ItemAccommodationListController {
    // Constante para la imagen predeterminada
    private static final String DEFAULT_ACCOMMODATION_PICTURE = "/com/example/ejemplo/multimedia/icons8-cabaña-100.png";

    @FXML
    public Label lblAccommodationId;
    @FXML
    public Label lblAdress;
    @FXML
    public Button btnDetailedAccommodation;
    @FXML
    public Label lblCity;
    @FXML
    public Label lblPrice;
    @FXML
    public Label lblCapacity;
    @FXML
    public Button btnAddAccommodation;
    @FXML
    public Label lblAvailability;
    @FXML
    public Label lblRating;
    @FXML
    public Button btnModifyAccommodation;
    @FXML
    public TextArea areaDescription;
    @FXML
    public Button btnDeleteAccommodation;
    @FXML
    public Pane accommodationPhoto;
    @FXML
    public ImageView imgAvailability;

    private AccommodationController accommodationController;
    private Accommodation accommodation;
    private Node node;
    private VBox pnItemsTopic;
    private AdminDashboardController dashboard;

    public void initData(Accommodation acco, AccommodationController accommodationController, Node node, VBox pnItems, AdminDashboardController adminDashboardController) {
        this.dashboard = adminDashboardController;
        this.pnItemsTopic = pnItems;
        this.accommodation = acco;
        this.node = node;
        this.accommodationController = accommodationController;
        lblAccommodationId.setText("ID-" + accommodation.getAccommodationId().toString());
        lblAdress.setText(accommodation.getAddress());
        lblCity.setText("City: " + accommodation.getCity());
        lblPrice.setText("Price: " + accommodation.getPrice().toString());
        lblCapacity.setText("Capacity: " + accommodation.getCapacity());

        // Cargar las fotos del alojamiento
        if (accommodation.getPhotos() != null && !accommodation.getPhotos().isEmpty()) {
            // Mostrar la primera foto del alojamiento
            AccommodationPhoto photo = accommodation.getPhotos().get(0);
            Image image = new Image(new ByteArrayInputStream(photo.getPhotoData()));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(accommodationPhoto.getPrefWidth());
            imageView.setFitHeight(accommodationPhoto.getPrefHeight());
            accommodationPhoto.getChildren().add(imageView);
        } else {
            // Mostrar la imagen predeterminada
            Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_ACCOMMODATION_PICTURE)));
            ImageView defaultImageView = new ImageView(defaultImage);
            defaultImageView.setFitWidth(accommodationPhoto.getPrefWidth());
            defaultImageView.setFitHeight(accommodationPhoto.getPrefHeight());
            accommodationPhoto.getChildren().add(defaultImageView);
        }

        // Calcular y mostrar la calificación promedio
        double average = 0, cont = 0, nReviews = 0;
        AccommodationReviewController reviewController = new AccommodationReviewController();
        List<AccommodationReview> allreviews = reviewController.getAllReviews();
        for (AccommodationReview review : allreviews) {
            if (Objects.equals(review.getAccommodation().getAccommodationId(), accommodation.getAccommodationId())) {
                cont = cont + review.getRating();
                nReviews++;
            }
        }
        average = nReviews > 0 ? cont / nReviews : 0;
        lblRating.setText("Rating: " + average);
        areaDescription.setText(accommodation.getDescription());

        // Establecer el ícono de disponibilidad
        setAvailabilityIcon(accommodation.isAvailability());
    }

    private void setAvailabilityIcon(boolean isAvailable) {
        try {
            String iconPath = isAvailable ? "/com/example/ejemplo/multimedia/icons8-de-acuerdo-48.png" : "/com/example/ejemplo/multimedia/icons8-cancelar-48.png";
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
            imgAvailability.setImage(icon);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load availability icon. Check the file path.");
        }
    }
}
