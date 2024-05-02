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
        if (accommodation.getPhotos() != null && !accommodation.getPhotos().isEmpty()) {

        } else {

        }
/*
        List<AccommodationReview> reviews = accommodation.getReviews();
        double average=0, cont=0, nReviews=0;
        for (AccommodationReview review: reviews){
            cont=cont+review.getRating();
            nReviews++;
        }

        average=cont/nReviews;
        lblRating.setText("Rating: "+String.valueOf(average));*/
        areaDescription.setText(accommodation.getDescription());
    }
}
