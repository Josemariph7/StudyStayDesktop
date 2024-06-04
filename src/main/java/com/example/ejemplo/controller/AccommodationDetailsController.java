package com.example.ejemplo.controller;

import com.example.ejemplo.model.*;
import com.example.ejemplo.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.controlsfx.control.Notifications;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private AccommodationPhotoController accommodationPhotoController = new AccommodationPhotoController();

    private int currentPhotoIndex = 0;
    private List<AccommodationPhoto> photos;

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
        photos = new ArrayList<>();
        displayAccommodationInfo();
        loadReviews();
        loadTenants();
        setupImageCarousel();
    }

    /**
     * Muestra la información del alojamiento en la interfaz de usuario.
     */
    private void displayAccommodationInfo() {
        lblAuthor.setText(accommodation.getOwner().getName() + " " + accommodation.getOwner().getLastName());
        lblCreationDate.setText(accommodation.getDescription());  // Asumiendo que la descripción es la fecha de creación, ajustar según sea necesario
        lblAddress.setText(accommodation.getAddress());
        lblCity.setText(accommodation.getCity());
        lblPrice.setText(accommodation.getPrice().toString() + " €");
        lblCapacity.setText(accommodation.getCapacity() + " tenants");
        lblAvailability.setText(accommodation.isAvailability() ? "Available" : "Not Available");
        lblRating.setText(accommodation.getRating() + "/5");
    }

    /**
     * Carga y muestra las reseñas del alojamiento.
     */
    private void loadReviews() {
        List<AccommodationReview> reviews = reviewController.getAllReviews();
        listViewComments.getItems().clear();
        listViewComments.setCellFactory(param -> new ListCell<>() {
            private final TextFlow textFlow = new TextFlow();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    String[] parts = item.split("\n");
                    textFlow.getChildren().clear();
                    for (String part : parts) {
                        if (part.contains(":")) {
                            String[] keyValue = part.split(": ", 2);
                            Text key = new Text(keyValue[0] + ": ");
                            key.setStyle("-fx-font-weight: bold;");
                            Text value = new Text(keyValue[1] + "\n");
                            textFlow.getChildren().addAll(key, value);
                        } else {
                            Text value = new Text(part + "\n");
                            textFlow.getChildren().add(value);
                        }
                    }
                    textFlow.setPrefWidth(listViewComments.getWidth() - 20);
                    setGraphic(textFlow);
                }
            }
        });
        for (AccommodationReview review : reviews) {
            if (review.getAccommodation().equals(accommodation)) {
                listViewComments.getItems().add(formatReview(review));
            }
        }
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
     * Carga y muestra los inquilinos del alojamiento.
     */
    private void loadTenants() {
        List<Booking> bookings = bookingController.getAllBookings();
        listViewTenants.getItems().clear();
        listViewTenants.setCellFactory(param -> new ListCell<>() {
            private final TextFlow textFlow = new TextFlow();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    String[] parts = item.split("\n");
                    textFlow.getChildren().clear();
                    for (String part : parts) {
                        if (part.contains(":")) {
                            String[] keyValue = part.split(": ", 2);
                            Text key = new Text(keyValue[0] + ": ");
                            key.setStyle("-fx-font-weight: bold;");
                            Text value = new Text(keyValue[1] + "\n");
                            textFlow.getChildren().addAll(key, value);
                        } else {
                            Text value = new Text(part + "\n");
                            textFlow.getChildren().add(value);
                        }
                    }
                    textFlow.setPrefWidth(listViewTenants.getWidth() - 20);
                    setGraphic(textFlow);
                }
            }
        });
        for (Booking book : bookings) {
            if (Objects.equals(book.getAccommodation().getAccommodationId(), accommodation.getAccommodationId())) {
                listViewTenants.getItems().add(formatTenant(userController.getById(book.getUser().getUserId()), book));
            }
        }
    }


    /**
     * Formatea un inquilino para mostrarlo en la interfaz de usuario.
     *
     * @param user el usuario inquilino
     * @param book la reserva asociada al inquilino
     * @return la cadena formateada que representa al inquilino
     */
    private String formatTenant(User user, Booking book) {
        return String.format("Tenant Name: %s\nStart Date: %s\nEnd Date: %s",
                user.getName() + " " + user.getLastName(),
                book.getStartDate().toLocalDate(),
                book.getEndDate().toLocalDate());
    }

    private void setupImageCarousel() {
        List<AccommodationPhoto> allPhotos = accommodationPhotoController.getAllPhotos();
        photos.clear();
        if (allPhotos != null) {
            for (AccommodationPhoto accommodationPhoto : allPhotos) {
                if (Objects.equals(accommodationPhoto.getAccommodation().getAccommodationId(), accommodation.getAccommodationId())) {
                    photos.add(accommodationPhoto);
                }
            }
        }
        currentPhotoIndex = 0;
        showPhoto(currentPhotoIndex);
    }

    private void showPhoto(int index) {
        imageCarousel.getChildren().clear();
        if (photos != null && !photos.isEmpty() && index >= 0 && index < photos.size()) {
            Image image = new Image(new ByteArrayInputStream(photos.get(index).getPhotoData()));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.fitWidthProperty().bind(imageCarousel.widthProperty());
            imageView.fitHeightProperty().bind(imageCarousel.heightProperty());
            imageCarousel.getChildren().add(imageView);
        } else {
            Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Constants.DEFAULT_ACCOMMODATION_PICTURE)));
            ImageView defaultImageView = new ImageView(defaultImage);
            defaultImageView.setPreserveRatio(true);
            defaultImageView.fitWidthProperty().bind(imageCarousel.widthProperty());
            defaultImageView.fitHeightProperty().bind(imageCarousel.heightProperty());
            imageCarousel.getChildren().add(defaultImageView);
        }
    }

    @FXML
    private void setNextPhoto() {
        if (photos != null && !photos.isEmpty()) {
            currentPhotoIndex = (currentPhotoIndex + 1) % photos.size();
            showPhoto(currentPhotoIndex);
        }
    }

    @FXML
    private void setPreviousPhoto() {
        if (photos != null && !photos.isEmpty()) {
            currentPhotoIndex = (currentPhotoIndex - 1 + photos.size()) % photos.size();
            showPhoto(currentPhotoIndex);
        }
    }

    @FXML
    private void handleDeletePhoto() {
        if (photos != null && !photos.isEmpty()) {
            AccommodationPhoto photoToDelete = photos.get(currentPhotoIndex);
            boolean confirmation = showConfirmationDialog("Delete Photo", "Are you sure you want to delete this photo?");
            if (confirmation) {
                photos.remove(currentPhotoIndex);
                accommodationPhotoController.deletePhoto(photoToDelete.getPhotoId()); // Llama al controlador para eliminar la foto de la base de datos
                if (currentPhotoIndex >= photos.size()) {
                    currentPhotoIndex = 0;
                }
                showPhoto(currentPhotoIndex);
                showAlert("Photo Deleted", "The photo has been deleted successfully.");
            }
        } else {
            showAlert("No photo to delete", "There are no photos to delete.");
        }
    }

    @FXML
    private void handleDeleteTenant() {
        String selectedTenant = listViewTenants.getSelectionModel().getSelectedItem();
        if (selectedTenant != null) {
            boolean confirmation = showConfirmationDialog("Delete Tenant", "Are you sure you want to delete this tenant and the related booking?");
            if (confirmation) {
                Booking bookingToDelete = findBookingByTenant(selectedTenant);
                if (bookingToDelete != null) {
                    bookingController.deleteBooking(bookingToDelete.getBookingId());
                    listViewTenants.getItems().remove(selectedTenant);
                    showAlert("Tenant Deleted", "The tenant and related booking have been deleted successfully.");
                    adminDashboardController.refreshBookings();
                }
            }
        } else {
            showAlert("No tenant selected", "Please select a tenant to delete.");
        }
    }

    private Booking findBookingByTenant(String tenantInfo) {
        List<Booking> bookings = bookingController.getAllBookings();
        for (Booking booking : bookings) {
            User user = userController.getById(booking.getUser().getUserId());
            String formattedTenant = formatTenant(user, booking);
            if (formattedTenant.equals(tenantInfo)) {
                return booking;
            }
        }
        return null;
    }

    @FXML
    private void handleDeleteReview() {
        String selectedReview = listViewComments.getSelectionModel().getSelectedItem();
        if (selectedReview != null) {
            boolean confirmation = showConfirmationDialog("Delete Review", "Are you sure you want to delete this review?");
            if (confirmation) {
                AccommodationReview reviewToDelete = findReviewByText(selectedReview);
                if (reviewToDelete != null) {
                    reviewController.deleteReview(reviewToDelete.getReviewId());
                    listViewComments.getItems().remove(selectedReview);
                    showAlert("Review Deleted", "The review has been deleted successfully.");
                    adminDashboardController.refreshAccommodations();
                }
            }
        } else {
            showAlert("No review selected", "Please select a review to delete.");
        }
    }

    private AccommodationReview findReviewByText(String reviewText) {
        List<AccommodationReview> reviews = reviewController.getAllReviews();
        for (AccommodationReview review : reviews) {
            String formattedReview = formatReview(review);
            if (formattedReview.equals(reviewText)) {
                return review;
            }
        }
        return null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    private byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}
