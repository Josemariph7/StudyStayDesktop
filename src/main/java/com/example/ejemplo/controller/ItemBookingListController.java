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
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
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
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Controlador para la vista de elementos de la lista de reservas.
 */
public class ItemBookingListController {
    @FXML
    public Button btnModifyTopic;
    @FXML public Button btnDeleteTopic;
    @FXML public Button btnTopicDetails;
    @FXML public Label lblEndDate;
    @FXML public Label lblBookingCity;
    @FXML public Label lblStartDate;
    @FXML public Label lblBookingUser;
    @FXML public Label lblBookingid;
    @FXML public Label lblStatus;

    private Booking booking;
    private BookingController bookingController;
    private Node node;
    private VBox pnItemsBooking;
    private AdminDashboardController adminDashboardController;

    /**
     * Maneja la acción de modificar la reserva.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleModify(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MODIFYBOOKING_FXML));
            Parent root = loader.load();
            ModifyBookingController modify = loader.getController();
            modify.initData(booking, bookingController, adminDashboardController);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ModifyBookingController modifyController = loader.getController();
            adminDashboardController.refreshBookings();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicializa los datos de la reserva y la interfaz de usuario.
     *
     * @param booking La reserva.
     * @param bookingController El controlador de reserva.
     * @param node El nodo actual.
     * @param pnBookingsItems El VBox de los elementos de reserva.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(Booking booking, BookingController bookingController, Node node, VBox pnBookingsItems, AdminDashboardController adminDashboardController) {
        this.booking = booking;
        this.node = node;
        this.pnItemsBooking = pnBookingsItems;
        this.adminDashboardController = adminDashboardController;
        this.bookingController = bookingController;
        lblBookingid.setText(booking.getBookingId().toString());
        if (booking.getUser() != null) {
            lblBookingUser.setText(booking.getUser().getName() + " " + booking.getUser().getLastName());
        } else {
            //eliminar
        }
        Accommodation accommodation = booking.getAccommodation();
        if (accommodation != null) {
            lblBookingCity.setText(accommodation.getCity());
        } else {
            lblBookingCity.setText("Unknown");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = booking.getStartDate().format(formatter);
        lblStartDate.setText(formattedDate);
        formattedDate = booking.getEndDate().format(formatter);
        lblEndDate.setText(formattedDate);
        lblStatus.setText(booking.getStatus().toString());
    }

    /**
     * Actualiza los datos de la reserva en la interfaz de usuario.
     *
     * @param book La reserva actualizada.
     */
    public void updateForumTopicData(Booking book) {
        this.booking = book;
        lblStatus.setText(booking.getStatus().toString());
        lblStartDate.setText(booking.getStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        lblBookingid.setText(book.getBookingId().toString());
        lblBookingUser.setText(booking.getUser().getName() + " " + booking.getUser().getLastName());
        lblEndDate.setText(booking.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        lblBookingCity.setText(booking.getAccommodation().getCity());
    }

    /**
     * Maneja la acción de eliminar la reserva.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Delete Booking");
        alert.setContentText("Are you sure you want to delete this Booking?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            bookingController.deleteBooking(booking.getBookingId());
            int index = pnItemsBooking.getChildren().indexOf(node);
            if (index != -1) {
                pnItemsBooking.getChildren().remove(index);
            } else {
                System.out.println("El nodo no se encontró en el VBox.");
            }
            adminDashboardController.refreshBookings();
        }
    }
}
