package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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
        String user = ChoiceBoxUser.getValue();
        String[] partes = user.split("\\s", 2);
        booking.setUser(userController.getById(Long.parseLong(partes[0])));
        String accommodation = ChoiceBoxAccommodation.getValue();
        partes = accommodation.split("\\s", 2);
        booking.setAccommodation(accommodationController.getAccommodationById(Long.parseLong(partes[0])));

        // Validar el estado de la reserva
        if (ChoiceBoxStatus.getValue() != null) {
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
        }

        // Validar las fechas de inicio y fin
        if (DatePickerEnd.getValue() != null && DatePickerStart.getValue() != null) {
            LocalDate startDate = DatePickerStart.getValue();
            LocalDate endDate = DatePickerEnd.getValue();
            LocalDate today = LocalDate.now();

            if (!startDate.isAfter(today)) {
                showError("La fecha de inicio debe ser posterior a la fecha actual.");
                return;
            }
            if (!endDate.isAfter(startDate)) {
                showError("La fecha de fin debe ser posterior a la fecha de inicio.");
                return;
            }

            booking.setStartDate(startDate.atStartOfDay());
            booking.setEndDate(endDate.atStartOfDay());
        } else {
            showError("Por favor, selecciona las fechas de inicio y fin.");
            return;
        }

        // Verificar la disponibilidad del alojamiento
        try {
            Accommodation accommodationObj = booking.getAccommodation();
            List<Booking> bookings = bookingController.getAllBookings();
            long currentBookings = bookings.stream()
                    .filter(b -> b.getAccommodation().getAccommodationId().equals(accommodationObj.getAccommodationId()))
                    .count();

            if (currentBookings >= accommodationObj.getCapacity()) {
                showError("Este alojamiento está completo.");
                return;
            }

            if (bookingExists(booking)) {
                showError("La reserva ya existe.");
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Add Booking");
            alert.setContentText("¿Estás seguro de que deseas agregar la nueva reserva?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                bookingController.createBooking(booking);
                accommodationObj.getTenants().add(booking.getUser());
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
