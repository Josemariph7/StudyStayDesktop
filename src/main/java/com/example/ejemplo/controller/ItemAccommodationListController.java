package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.AccommodationPhoto;
import com.example.ejemplo.model.AccommodationReview;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.utils.Constants;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ItemAccommodationListController {


    public Label lblAccommodationId;
    public Label lblAdress;
    public Button btnDetailedAccommodation;
    public Label lblCity;
    public Label lblPrice;
    public Label lblCapacity;
    public Button btnAddAccommodation;
    public Label lblAvailability;
    public Label lblRating;
    public Button btnModifyAccommodation;
    public TextArea areaDescription;
    public Button btnDeleteAccommodation;
    public Pane accommodationPhoto;
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
        lblAccommodationId.setText("ID-"+accommodation.getAccommodationId().toString());
        lblAdress.setText(accommodation.getAddress());
        lblCity.setText("City: "+ accommodation.getCity());
        lblPrice.setText("Price: "+accommodation.getPrice().toString());
        lblCapacity.setText(String.valueOf("Capacity: "+ accommodation.getCapacity()));
        /*
        lblAvailability.
HACER EL TEMA DE LA IMAGEN VERDE O ROJA
*/
        if (accommodation.getPhotos() != null && accommodation.getPhotos().size() > 0) {
            // Si hay fotos en la lista de alojamientos
            AccommodationPhoto firstPhoto = accommodation.getPhotos().get(0); // Obtener la primera foto
            byte[] photoData = firstPhoto.getPhotoData(); // Obtener los datos de la foto
            // Convierte los datos de la foto en una imagen
            Image image = new Image(new ByteArrayInputStream(photoData));

            // Establece la imagen en accommodationPhoto
            accommodationPhoto.setBackground(new Background(new BackgroundImage(
                    image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        } else {
            // Si la lista de fotos está vacía, establece la imagen por defecto
            String defaultImageUrl = Constants.DEFAULT_ACCOMMODATION_PICTURE;
            URL defaultResource = getClass().getResource(defaultImageUrl);
            javafx.scene.image.Image defaultProfilePicture = new javafx.scene.image.Image(defaultResource.toExternalForm());
            ImageView defaultImageView = null;
            defaultImageView.setImage(defaultProfilePicture);
            // Ajusta el tamaño de la imagen al tamaño del Pane accommodationPhoto
            defaultImageView.fitWidthProperty().bind(accommodationPhoto.widthProperty());
            defaultImageView.fitHeightProperty().bind(accommodationPhoto.heightProperty());

            // Limpia el contenido anterior del Pane
            accommodationPhoto.getChildren().clear();
            // Agrega el ImageView de la imagen por defecto al Pane
            accommodationPhoto.getChildren().add(defaultImageView);
        }

        List<AccommodationReview> reviews = accommodation.getReviews();
        double average=0, cont=0, nReviews=0;
        for (AccommodationReview review: reviews){
            cont=cont+review.getRating();
            nReviews++;
        }

        average=cont/nReviews;
        lblRating.setText("Rating: "+String.valueOf(average));
        areaDescription.setText(accommodation.getDescription());
    }
}
