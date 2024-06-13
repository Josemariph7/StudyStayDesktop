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
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controlador para agregar una nueva reserva.
 */
public class AddBookingController {

    public Button btnCancel;
    public Button btnAccept;
    public ChoiceBox<String> ChoiceBoxStatus;
    public DatePicker DatePickerEnd;
    public DatePicker DatePickerStart;
    public ChoiceBox<String> ChoiceBoxAccommodation;
    public ChoiceBox<String> ChoiceBoxUser;

    public BookingController bookingController;
    private Booking booking;

    /**
     * Maneja la acción de aceptar para agregar una nueva reserva.
     *
     * @param actionEvent el evento de acción
     */
    public void handleAccept(ActionEvent actionEvent) {
        UserController userController = new UserController();
        AccommodationController accommodationController = new AccommodationController();

        // Verificar campos obligatorios
        if (ChoiceBoxUser.getValue() == null || ChoiceBoxAccommodation.getValue() == null || Objects.equals(ChoiceBoxStatus.getValue(), "Status") ||
                DatePickerStart.getValue() == null || DatePickerEnd.getValue() == null) {
            showFieldError("All fields are required.");
            return;
        }

        String user = ChoiceBoxUser.getValue();
        String[] partes = user.split("\\s", 2);
        booking.setUser(userController.getById(Long.parseLong(partes[0])));
        String accommodation = ChoiceBoxAccommodation.getValue();
        partes = accommodation.split("\\s", 2);
        booking.setAccommodation(accommodationController.getAccommodationById(Long.parseLong(partes[0])));

        // Validar el estado de la reserva
        switch (ChoiceBoxStatus.getValue()) {
            case "Pending":
                booking.setStatus(Booking.BookingStatus.PENDING);
                break;
            case "Confirmed":
                booking.setStatus(Booking.BookingStatus.CONFIRMED);
                break;
            case "Canceled":
                booking.setStatus(Booking.BookingStatus.CANCELLED);
                break;
        }

        // Validar las fechas de inicio y fin
        LocalDate startDate = DatePickerStart.getValue();
        LocalDate endDate = DatePickerEnd.getValue();
        LocalDate today = LocalDate.now();

        StringBuilder errors = new StringBuilder();

        if (!startDate.isAfter(today)) {
            errors.append("The start date must be after today's date.\n");
        }
        if (!endDate.isAfter(startDate)) {
            errors.append("The end date must be after the start date.\n");
        }

        if (errors.length() > 0) {
            showError(errors.toString());
            return;
        }

        booking.setStartDate(startDate.atStartOfDay());
        booking.setEndDate(endDate.atStartOfDay());

        // Verificar la disponibilidad del alojamiento
        try {
            Accommodation accommodationObj = booking.getAccommodation();
            List<Booking> bookings = bookingController.getAllBookings();
            long currentBookings = bookings.stream()
                    .filter(b -> b.getAccommodation().getAccommodationId().equals(accommodationObj.getAccommodationId()))
                    .count();
            if (currentBookings >= accommodationObj.getCapacity()) {
                showError("This accommodation is fully booked.");
                return;
            }

            if (bookingExists(booking)) {
                showError("The booking already exists.");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Add Booking");
            alert.setContentText("Are you sure you want to add this booking?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                bookingController.createBooking(booking);
                accommodationObj.getTenants().add(booking.getUser());
                System.out.println(accommodationObj.getTenants().toString());
                AdminDashboardController itemCtrl;
                itemCtrl = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
                itemCtrl.refreshBookings();
                ((Stage) btnAccept.getScene().getWindow()).close();
            }
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }


    /**
     * Inicializa los datos necesarios para la interfaz de usuario.
     */
    public void initData() {
        bookingController = new BookingController();
        booking = new Booking();
        List<User> userlist;
        UserController userCtrl = new UserController();
        userlist = userCtrl.getAll();
        List<String> users = new ArrayList<>();
        for (User user : userlist) {
            users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
        }
        List<Accommodation> accommodationlist;
        AccommodationController accommodationCtrl = new AccommodationController();
        accommodationlist = accommodationCtrl.getAllAccommodations();
        List<String> accommodations = new ArrayList<>();
        for (Accommodation accommodation : accommodationlist) {
            accommodations.add(accommodation.getAccommodationId() + "  " + accommodation.getAddress());
        }
        ChoiceBoxAccommodation.getItems().addAll(accommodations);
        ChoiceBoxUser.getItems().addAll(users);
        String[] status = {"Pending", "Confirmed", "Canceled"};
        ChoiceBoxStatus.getItems().addAll(status);
        ChoiceBoxStatus.setValue("Status");
    }

    /**
     * Verifica si la reserva ya existe.
     *
     * @param book la reserva a verificar
     * @return true si la reserva existe, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
    private boolean bookingExists(Booking book) throws SQLException {
        List<Booking> bookings = bookingController.getAllBookings();
        for (Booking booking : bookings) {
            if (Objects.equals(booking.getUser().getUserId(), book.getUser().getUserId())
                    && Objects.equals(booking.getAccommodation().getAccommodationId(), book.getAccommodation().getAccommodationId())) {
                return true;
            }
        }
        return false;
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
