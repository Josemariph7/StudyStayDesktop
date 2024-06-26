/*
 * StudyStay © 2024
 *
 * All rights reserved.
 *
 * This software and associated documentation files (the "Software") are owned by StudyStay. Unauthorized copying, distribution, or modification of this Software is strictly prohibited.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this Software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * StudyStay
 * José María Pozo Hidalgo
 * Email: josemariph7@gmail.com
 *
 *
 */

package com.example.ejemplo.controller;

import com.example.ejemplo.model.*;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

/**
 * Controlador para la vista de elementos de la lista de alojamientos.
 */
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
    private VBox pnItemsAccommodation;
    private AdminDashboardController dashboard;

    /**
     * Inicializa los datos del alojamiento y la interfaz de usuario.
     *
     * @param acco El alojamiento.
     * @param accommodationController El controlador de alojamiento.
     * @param node El nodo actual.
     * @param pnItems El VBox de los elementos de alojamiento.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(Accommodation acco, AccommodationController accommodationController, Node node, VBox pnItems, AdminDashboardController adminDashboardController) {
        this.dashboard = adminDashboardController;
        this.pnItemsAccommodation = pnItems;
        this.accommodation = acco;
        this.node = node;
        this.accommodationController = accommodationController;
        lblAccommodationId.setText("ID-" + accommodation.getAccommodationId().toString());
        lblAdress.setText(accommodation.getAddress());
        lblCity.setText("City: " + accommodation.getCity());
        lblPrice.setText("Price: " + accommodation.getPrice().toString());
        lblCapacity.setText("Capacity: " + accommodation.getCapacity());

        // Cargar la primera foto del alojamiento
        accommodationPhoto.getChildren().clear(); // Limpiar cualquier imagen previa
        loadAccommodationPhoto(accommodation);

        // Calcular y mostrar la calificación promedio
        double average = 0, cont = 0, nReviews = 0;
        AccommodationReviewController reviewController = new AccommodationReviewController();
        List<AccommodationReview> allreviews = reviewController.getAllReviews();
        for (AccommodationReview review : allreviews) {
            if (Objects.equals(review.getAccommodation().getAccommodationId(), accommodation.getAccommodationId())) {
                cont += review.getRating();
                nReviews++;
            }
        }
        average = nReviews > 0 ? cont / nReviews : 0;
        lblRating.setText("Rating: " + average);
        areaDescription.setText(accommodation.getDescription());

        // Establecer el ícono de disponibilidad
        BookingController bookingController = new BookingController();
        List<Booking> bookings = bookingController.getAllBookings();
        int counter=0;
        for (Booking book : bookings) {
            if (Objects.equals(book.getAccommodation().getAccommodationId(), accommodation.getAccommodationId())) {
                counter++;
            }
        }
        if(counter>=accommodation.getCapacity()){
            accommodation.setAvailability(false);
        }
        setAvailabilityIcon(accommodation.isAvailability());
    }

    private void loadAccommodationPhoto(Accommodation accommodation) {
        boolean photoFound = false;
        AccommodationPhotoController photoController = new AccommodationPhotoController();
        List<AccommodationPhoto> photos = photoController.getAllPhotos();
        if (photos != null) {
            for (AccommodationPhoto photo : photos) {
                if (photo.getAccommodation().getAccommodationId().equals(accommodation.getAccommodationId())) {
                    if (photo.getPhotoData() != null) {
                        Image image = new Image(new ByteArrayInputStream(photo.getPhotoData()));
                        if (image != null && !image.isError()) {
                            ImageView imageView = new ImageView(image);
                            imageView.setFitWidth(accommodationPhoto.getPrefWidth());
                            imageView.setFitHeight(accommodationPhoto.getPrefHeight());
                            imageView.setPreserveRatio(true); // Añadir esta línea para preservar el aspecto de la imagen
                            accommodationPhoto.getChildren().add(imageView);
                            photoFound = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!photoFound) {
            loadDefaultImage();
        }
    }

    private void loadDefaultImage() {
        Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(DEFAULT_ACCOMMODATION_PICTURE)));
        ImageView defaultImageView = new ImageView(defaultImage);
        defaultImageView.setFitWidth(accommodationPhoto.getPrefWidth());
        defaultImageView.setFitHeight(accommodationPhoto.getPrefHeight());
        defaultImageView.setPreserveRatio(true); // Añadir esta línea para preservar el aspecto de la imagen
        accommodationPhoto.getChildren().add(defaultImageView);
    }

    /**
     * Maneja la acción de modificar el alojamiento.
     */
    @FXML
    private void handleModify() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MODIFYACCOMMODATION_FXML));
            Parent root = loader.load();
            ModifyAccommodationController modify = loader.getController();
            modify.initData(accommodation, accommodationController, dashboard);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ModifyAccommodationController modifyController = loader.getController();
            // dashboard.refreshAccommodations();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establece el ícono de disponibilidad según el estado del alojamiento.
     *
     * @param isAvailable Indica si el alojamiento está disponible.
     */
    private void setAvailabilityIcon(boolean isAvailable) {
        try {
            String iconPath = isAvailable ? "/com/example/ejemplo/multimedia/icons8-de-acuerdo-48.png" : "/com/example/ejemplo/multimedia/icons8-cancelar-48.png";
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream(iconPath)));
            imgAvailability.setImage(icon);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load availability icon. Check the file path.");
        }
        if(isAvailable==false){
            accommodation.setAvailability(false);
            accommodationController.updateAccommodation(accommodation);
        }
    }

    /**
     * Maneja la acción de eliminar el alojamiento.
     */
    @FXML
    public void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Delete Accommodation");
        alert.setContentText("Are you sure you want to delete this Accommodation?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            accommodationController.deleteAccommodation(accommodation.getAccommodationId());
            int index = pnItemsAccommodation.getChildren().indexOf(node);
            if (index != -1) {
                pnItemsAccommodation.getChildren().remove(index);
            } else {
                System.out.println("El nodo no se encontró en el VBox.");
            }
            dashboard.refreshAccommodations();
        }
    }

    /**
     * Actualiza los datos del alojamiento en la interfaz de usuario.
     *
     * @param accommodation El alojamiento actualizado.
     */
    public void updateAccommodationData(Accommodation accommodation) {
        this.accommodation = accommodation;
        lblAccommodationId.setText("ID-" + accommodation.getAccommodationId().toString());
        lblAdress.setText("Adress: " + accommodation.getAddress());
        lblCity.setText("City: " + accommodation.getCity());
        lblPrice.setText("Price: " + accommodation.getPrice().toString());
        lblCapacity.setText("Capacity: " + accommodation.getCapacity());
        lblAvailability.setText("Availability: " + accommodation.isAvailability());
        lblRating.setText("Rating: " + accommodation.getRating());
        areaDescription.setText(accommodation.getDescription());
        dashboard.refreshAccommodations();
    }

    /**
     * Maneja la acción de mostrar los detalles del alojamiento.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleDetails(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.DETAILSACCOMMODATION_FXML));
            Parent root = loader.load();
            AccommodationDetailsController details = loader.getController();
            details.initData(accommodation, accommodationController, dashboard);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            AccommodationDetailsController detailsController = loader.getController();
            //dashboard.refreshAccommodations();
            detailsController.btnBack.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
