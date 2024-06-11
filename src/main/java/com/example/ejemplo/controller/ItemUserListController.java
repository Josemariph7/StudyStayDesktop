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

import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para los elementos de la lista de usuarios en el panel de administrador.
 */
public class ItemUserListController {

    @FXML
    public Label lblUserId;
    @FXML
    public Label lblName;
    @FXML
    public Label lblEmail;
    @FXML
    public Label lblPhone;
    @FXML
    public Label lblRole;
    @FXML
    public Label lblRegDate;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnModify;
    @FXML
    public Label lblBirthDate;
    @FXML
    public Label lblDni;

    private User user;
    private UserController userController;
    private Node node;
    private VBox pnItems;
    private AdminDashboardController dashboard;

    /**
     * Inicializa el controlador.
     */
    @FXML
    private void initialize() {
        btnDelete.setOnAction(event -> handleDelete());
        btnModify.setOnAction(event -> handleModify());
    }

    /**
     * Inicializa los datos del usuario en el elemento de la lista.
     *
     * @param user El usuario a mostrar.
     * @param userController El controlador de usuario.
     * @param node El nodo de la lista.
     * @param pnItems El contenedor de la lista.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(User user, UserController userController, Node node, VBox pnItems, AdminDashboardController adminDashboardController) {
        this.dashboard = adminDashboardController;
        this.pnItems = pnItems;
        this.user = user;
        this.node = node;
        this.userController = userController;
        lblUserId.setText(String.valueOf(user.getUserId()));
        lblName.setText(user.getName() + " " + user.getLastName());
        lblEmail.setText(user.getEmail());
        lblPhone.setText(user.getPhone());
        lblRole.setText(user.isAdmin() ? "Admin" : "User");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = user.getRegistrationDate().format(formatter);
        lblRegDate.setText(formattedDate);
        lblBirthDate.setText(user.getBirthDate().format(formatter));
        lblDni.setText(user.getDni());
    }

    /**
     * Maneja el evento de eliminación de usuario.
     */
    @FXML
    public void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Delete User");
        alert.setContentText("Are you sure you want to delete this User?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Eliminar las reservas del usuario
                BookingController bookingController = new BookingController();
                List<Booking> userBookings = bookingController.getBookingsByUser(user.getUserId());
                for (Booking booking : userBookings) {
                    bookingController.deleteBooking(booking.getBookingId());
                }

                // Eliminar el usuario
                userController.delete(user.getUserId());

                int index = pnItems.getChildren().indexOf(node);
                if (index != -1) {
                    pnItems.getChildren().remove(index);
                } else {
                    System.out.println("Node not found in VBox.");
                }
                dashboard.updateUserStatistics();
                System.out.println("Deleted user and their bookings: " + user);
            } catch (SQLException e) {
                showError("Database error: " + e.getMessage());
            }
        }
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


    /**
     * Maneja el evento de modificación de usuario.
     */
    @FXML
    private void handleModify() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MODIFYUSER_FXML));
            Parent root = loader.load();
            System.out.println("Usuario que se intenta modificar: " + user);
            ModifyUserController modify = loader.getController();
            modify.initData(user, userController, dashboard);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ModifyUserController modifyController = loader.getController();
            dashboard.updateUserStatistics();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Modificar usuario: " + user);
    }

    /**
     * Actualiza los datos del usuario después de la modificación.
     *
     * @param updatedUser El usuario modificado.
     */
    public void updateUserData(User updatedUser) {
        this.user = updatedUser;
        lblUserId.setText(String.valueOf(user.getUserId()));
        lblName.setText(user.getName());
        lblEmail.setText(user.getEmail());
        lblPhone.setText(user.getPhone());
        lblRole.setText(user.isAdmin() ? "Administrator" : "User");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = user.getRegistrationDate().format(formatter);
        lblRegDate.setText(formattedDate);
        dashboard.refreshUsers();
    }
}
