package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ModifyAccommodationController {
    public Button btnCancel;
    public Button btnAccept;
    public TextArea txtAreaDescription;
    public ChoiceBox CapacityChoiceBox;
    public ChoiceBox CityChoiceBox;
    public ChoiceBox OwnerChoiceBox;
    public TextField txtServices;
    public TextField txtPrice;
    public TextField txtAddress;

    public void initData(Accommodation accommodation, AccommodationController accommodationController, AdminDashboardController dashboard) {
        
    }

    public void handleAccept(ActionEvent actionEvent) {
        
    }
}
