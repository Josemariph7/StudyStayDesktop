package com.example.ejemplo.controller;

import com.example.ejemplo.model.*;
import com.example.ejemplo.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controlador para mostrar los detalles de un alojamiento.
 */
public class AccommodationDetailsController {

    @FXML
    public Button btnBack;
    @FXML
    public Button deletePhoto;
    @FXML
    public ListView<String> listViewTenants;
    @FXML
    public Button previousPhoto;
    @FXML
    public Button nextPhoto;
    @FXML
    public Button btnDeleteReview;
    @FXML
    public Button btnDeleteTenant;
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
    private ListView<String> listViewComments;
    @FXML
    private StackPane imageCarousel;
    @FXML
    private Button btnDelete;

    private Accommodation accommodation;
    private AccommodationReviewController reviewController = new AccommodationReviewController();
    private UserController userController = new UserController();
    private BookingController bookingController = new BookingController();
    private AccommodationController accommodationController = new AccommodationController();
    private AdminDashboardController adminDashboardController = new AdminDashboardController();

    /**
     * Inicializa los datos del alojamiento y configura la interfaz de usuario con la información del alojamiento.
     *
     * @param accommodation el alojamiento a mostrar
     * @param accommodationController el controlador de alojamiento
     * @param dashboard el controlador del panel de administración
     */
    public void initData(Accommodation accommodation, AccommodationController accommodationController, AdminDashboardController dashboard) {
        this.accommodation = accommodation;
        this.adminDashboardController = dashboard;
        this.accommodationController = accommodationController;
        displayAccommodationInfo();
        setupImageCarousel();
        loadReviews();
        loadTenants();
        //setupImageCarousel();
    }

    /**
     * Muestra la información del alojamiento en la interfaz de usuario.
     */
    private void displayAccommodationInfo() {
        lblAuthor.setText(accommodation.getOwner().getName() + " " + accommodation.getOwner().getLastName());
        lblCreationDate.setText(accommodation.getDescription());  // Asumiendo que la descripción es la fecha de creación, ajustar según sea necesario
        lblAddress.setText(accommodation.getAddress());
        lblCity.setText(accommodation.getCity());
        lblPrice.setText(accommodation.getPrice().toString()+" €");
        lblCapacity.setText(accommodation.getCapacity()+" tenants");
        lblAvailability.setText((accommodation.isAvailability() ? "Available" : "Not Available"));
        lblRating.setText(accommodation.getRating()+"/5");
    }

    /**
     * Carga y muestra las reseñas del alojamiento.
     */
    private void loadReviews() {
        List<AccommodationReview> reviews = reviewController.getAllReviews();
        List<String> formattedReviews = new ArrayList<>();
        for (AccommodationReview review : reviews) {
            if (review.getAccommodation().equals(accommodation)) {
                formattedReviews.add(formatReview(review));
            }
        }
        listViewComments.getItems().addAll(formattedReviews);
    }

    /**
     * Formatea una reseña para mostrarla en la interfaz de usuario.
     *
     * @param review la reseña a formatear
     * @return la cadena formateada que representa la reseña
     */
    private String formatReview(AccommodationReview review) {
        return String.format("Author: %s\nRating: %.1f\nDate: %s\nComment: %s\n",
                review.getAuthor().getName(),
                review.getRating(),
                review.getDateTime().toLocalDate(),
                review.getComment());
    }

    /**
     * Carga y muestra las reseñas del alojamiento.
     */
    private void loadTenants() {
        List<Booking> bookings = bookingController.getAllBookings();
        List<String> formattedTenants = new ArrayList<>();
        for (Booking book : bookings) {
            if (Objects.equals(book.getAccommodation().getAccommodationId(), accommodation.getAccommodationId())) {
                formattedTenants.add(formatTenant(userController.getById(book.getUser().getUserId()), book));
            }
        }
        listViewTenants.getItems().addAll(formattedTenants);
    }

    /**
     * Formatea una reseña para mostrarla en la interfaz de usuario.
     *
     * @param user
     * @param book
     * @return la cadena formateada que representa la reseña
     */
    private String formatTenant(User user, Booking book) {
        return String.format("Tenant Name: %s\nStart Date: %s\nEnd Date: %s",
                user.getName()+" "+user.getLastName(),
                book.getStartDate().toLocalDate(),
                book.getEndDate().toLocalDate());
    }

    /**
     * Configura el carrusel de imágenes del alojamiento.
     */
    private void setupImageCarousel() {
        List<AccommodationPhoto> photos = accommodation.getPhotos();
        if (photos == null || photos.isEmpty()) {
            //ImageView defaultImageView = new ImageView(new Image(Constants.DEFAULT_ACCOMMODATION_PICTURE));
            //defaultImageView.setFitHeight(200);
            //defaultImageView.setFitWidth(600);
           // imageCarousel.getChildren().add(defaultImageView);
        } else {
            for (AccommodationPhoto photo : photos) {
                Image image = new Image(new ByteArrayInputStream(photo.getPhotoData()));
                ImageView imageView = new ImageView(image);
                //imageView.setFitHeight(200);
                //imageView.setFitWidth(600);
                imageCarousel.getChildren().add(imageView);
            }
        }
    }

    /**
     * Maneja la acción de eliminar una reseña seleccionada.
     */
    @FXML
    private void handleDeleteReview() {/*
        String selectedReview = listViewComments.getSelectionModel().getSelectedItem();
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
        }*/
    }

    @FXML
    private void handleDeleteTenant() {

    }

    @FXML
    private void handleDeletePhoto() {

    }

    @FXML
    private void setNextPhoto() {

    }
    @FXML
    private void setPreviousPhoto() {

    }
}
