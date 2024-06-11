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
import java.util.regex.Pattern;

/**
 * Controlador para modificar un alojamiento.
 */
public class ModifyAccommodationController {
    public Button btnCancel;
    public Button btnAccept;
    public TextArea txtAreaDescription;
    public ChoiceBox<Integer> CapacityChoiceBox;
    public ChoiceBox<String> CityChoiceBox;
    public ChoiceBox<String> OwnerChoiceBox;
    public TextField txtServices;
    public TextField txtPrice;
    public TextField txtAddress;

    private Accommodation accommodation;
    public AccommodationController accommodationController;
    private AdminDashboardController adminDashboardController;

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[\\p{L}0-9\\s]+$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    private static final Pattern SERVICES_PATTERN = Pattern.compile("^[\\p{L},\\s]+$");
    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("^[\\p{L}0-9\\s]+$");

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
        String description = txtAreaDescription.getText();
        String priceText = txtPrice.getText();
        String address = txtAddress.getText();
        String services = txtServices.getText();
        StringBuilder errors = new StringBuilder();

        // Verificar campos obligatorios
        if (description.isEmpty() || priceText.isEmpty() || address.isEmpty() || services.isEmpty() || CityChoiceBox.getValue() == null ||
                OwnerChoiceBox.getValue() == null || CapacityChoiceBox.getValue() == null) {
            showFieldError("All fields are required.");
            return;
        }

        // Validar formato de los campos
        if (!ADDRESS_PATTERN.matcher(address).matches()) {
            errors.append("The address contains invalid characters.\n");
        }
        if (!PRICE_PATTERN.matcher(priceText).matches()) {
            errors.append("The price format is invalid.\n");
        }
        if (!SERVICES_PATTERN.matcher(services).matches()) {
            errors.append("The services contain invalid characters.\n");
        }
        if (!DESCRIPTION_PATTERN.matcher(description).matches()) {
            errors.append("The description contains invalid characters.\n");
        }

        if (errors.length() > 0) {
            showError(errors.toString());
            return;
        }

        BigDecimal price = new BigDecimal(priceText);

        UserController userController = new UserController();
        String[] partes = OwnerChoiceBox.getValue().toString().split("\\s", 2);
        accommodation.setOwner(userController.getById(Long.valueOf(partes[0])));
        accommodation.setDescription(description);
        accommodation.setCapacity(Integer.parseInt(CapacityChoiceBox.getValue().toString()));
        accommodation.setCity(CityChoiceBox.getValue().toString());
        accommodation.setServices(services);
        accommodation.setPrice(price);
        accommodation.setAddress(address);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Modify Accommodation");
        alert.setContentText("Are you sure you want to modify this accommodation?");
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

    /**
     * Muestra un mensaje de error de campos incompletos.
     *
     * @param message el mensaje de error a mostrar
     */
    private void showFieldError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incomplete Fields");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param message el mensaje de error a mostrar
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
