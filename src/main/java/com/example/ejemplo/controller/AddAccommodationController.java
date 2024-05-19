package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddAccommodationController {
    public Button btnCancel;
    public Button btnAccept;
    public TextArea txtAreaDescription;
    public ChoiceBox CapacityChoiceBox;
    public ChoiceBox CityChoiceBox;
    public ChoiceBox OwnerChoiceBox;
    public TextField txtServices;
    public TextField txtPrice;
    public TextField txtAddress;

    public void handleAccept(ActionEvent actionEvent) {
        String description = txtAreaDescription.getText();
        BigDecimal price=new BigDecimal(txtPrice.getText());
        String address = txtAddress.getText();
        String services = txtServices.getText();
        String city=CityChoiceBox.getValue().toString();

        UserController userController = new UserController();
        String id=OwnerChoiceBox.getValue().toString();
        String[] partes = id.split("\\s", 2);
        User user=userController.getById(Long.valueOf(partes[0]));

        int capacity=Integer.parseInt(CapacityChoiceBox.getValue().toString());

        try {
            // Crea un nuevo objeto de usuario y lo guarda en la base de datos
            Accommodation accommodation=new Accommodation(user, address, city, price, description, capacity, services);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Add Accommodation");
            alert.setContentText("Are you sure to add this Accommodation?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK&& !accommodationExists(accommodation)) {
                AccommodationController accommodationController = new AccommodationController();
                accommodationController.createAccommodation(accommodation);
                ((Stage) btnAccept.getScene().getWindow()).close();
                updateItemAdminList();
            }else{
                showError(Constants.USER_EXISTS_ERROR);
                return;
            }
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }

    }

    private boolean accommodationExists(Accommodation accommodation) throws SQLException {
        AccommodationController accommodationController = new AccommodationController();
        List<Accommodation> accommodations = accommodationController.getAllAccommodations();
        for (Accommodation acco : accommodations) {
            if (acco.getOwner().equals(accommodation.getOwner())&& acco.getAddress().equals(accommodation.getAddress())) {
                return true;
            }
        }
        return false;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateItemAdminList() {
        AdminDashboardController adminDashboardController = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
        adminDashboardController.refresh();
    }

    public void initData() {
        initializeCities();
        initializeOwners();
        initializeCapacity();
        OwnerChoiceBox.setValue("Select Owner");
        CityChoiceBox.setValue("Select City");
        CapacityChoiceBox.setValue("Select Capacity");
    }

    private void initializeCities() {
        ObservableList<String> cities = FXCollections.observableArrayList(
                "Albacete", "Alicante", "Almería", "Ávila", "Badajoz", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Castellón",
                "Ciudad Real", "Córdoba", "Cuenca", "Girona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaén", "León",
                "Lleida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Ourense", "Palencia", "Pontevedra", "Salamanca",
                "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Zamora", "Zaragoza"
        );
        CityChoiceBox.setItems(cities);
    }

    private void initializeOwners() {
        List<User> userlist;
        UserController userCtrl = new UserController();
        userlist = userCtrl.getAll();
        List<String> users = new ArrayList<>();
        for (User user : userlist) {
            users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
        }
        OwnerChoiceBox.setItems(FXCollections.observableArrayList(users));
    }

    private void initializeCapacity() {
        ObservableList<Integer> capacity = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            capacity.add(i);
        }
        CapacityChoiceBox.setItems(capacity);
    }
}
