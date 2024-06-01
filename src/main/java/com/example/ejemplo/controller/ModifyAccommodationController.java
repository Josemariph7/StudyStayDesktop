package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para modificar un alojamiento.
 */
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

    private Accommodation accommodation;
    public AccommodationController accommodationController;
    private AdminDashboardController adminDashboardController;

    /**
     * Inicializa los datos del alojamiento.
     *
     * @param accommodation El alojamiento a modificar.
     * @param accommodationController El controlador del alojamiento.
     * @param dashboard El controlador del panel de administrador.
     */
    public void initData(Accommodation accommodation, AccommodationController accommodationController, AdminDashboardController dashboard) {
        this.accommodation = accommodation;
        this.accommodationController = accommodationController;
        this.adminDashboardController = dashboard;
        initChoiceBoxes();
    }

    /**
     * Maneja el evento de aceptación para modificar el alojamiento.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleAccept(ActionEvent actionEvent) {
        UserController userController = new UserController();
        String[] partes = OwnerChoiceBox.getValue().toString().split("\\s", 2);
        accommodation.setOwner(userController.getById(Long.valueOf(partes[0])));
        accommodation.setDescription(txtAreaDescription.getText());
        accommodation.setCapacity(Integer.parseInt(CapacityChoiceBox.getValue().toString()));
        accommodation.setCity(CityChoiceBox.getValue().toString());
        accommodation.setServices(txtServices.getText());
        accommodation.setPrice(BigDecimal.valueOf(Long.parseLong(txtPrice.getText())));
        accommodation.setAddress(txtAddress.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Modify Accommodation");
        alert.setContentText("Are you sure to modify this Accommodation?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            accommodationController.updateAccommodation(accommodation);
            ItemAccommodationListController itemCtrl;
            itemCtrl = (ItemAccommodationListController) btnAccept.getScene().getWindow().getUserData();
            itemCtrl.updateAccommodationData(accommodation);
            adminDashboardController.refreshAccommodations();
        }
        ((Stage) btnAccept.getScene().getWindow()).close();
    }

    /**
     * Maneja el evento de cancelación.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleCancel(ActionEvent actionEvent) {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    /**
     * Obtiene el alojamiento.
     *
     * @return El alojamiento.
     */
    public Accommodation getAccommodation() {
        return accommodation;
    }

    /**
     * Inicializa los ChoiceBoxes con los datos correspondientes.
     */
    public void initChoiceBoxes() {
        initializeCities();
        initializeOwners();
        initializeCapacity();
        OwnerChoiceBox.setValue(accommodation.getOwner().getUserId() + " " + accommodation.getOwner().getName() + " " + accommodation.getOwner().getLastName());
        CityChoiceBox.setValue(accommodation.getCity());
        CapacityChoiceBox.setValue(accommodation.getCapacity());
        txtAddress.setText(accommodation.getAddress());
        txtPrice.setText(accommodation.getPrice().toString());
        txtServices.setText(accommodation.getServices());
        txtAreaDescription.setText(accommodation.getDescription());
    }

    /**
     * Inicializa el ChoiceBox de ciudades.
     */
    private void initializeCities() {
        ObservableList<String> cities = FXCollections.observableArrayList(
                "Albacete", "Alicante", "Almería", "Ávila", "Badajoz", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Castellón",
                "Ciudad Real", "Córdoba", "Cuenca", "Girona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaén", "León",
                "Lleida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Ourense", "Palencia", "Pontevedra", "Salamanca",
                "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Zamora", "Zaragoza"
        );
        CityChoiceBox.setItems(cities);
    }

    /**
     * Inicializa el ChoiceBox de propietarios.
     */
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

    /**
     * Inicializa el ChoiceBox de capacidad.
     */
    private void initializeCapacity() {
        ObservableList<Integer> capacity = FXCollections.observableArrayList();
        for (int i = 1; i <= 10; i++) {
            capacity.add(i);
        }
        CapacityChoiceBox.setItems(capacity);
    }
}
