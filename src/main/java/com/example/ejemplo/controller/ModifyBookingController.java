package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controlador para modificar una reserva.
 */
public class ModifyBookingController {
    @FXML public ChoiceBox<String> ChoiceBoxUser;
    @FXML public ChoiceBox<String> ChoiceBoxAccommodation;
    @FXML public DatePicker DatePickerStart;
    @FXML public DatePicker DatePickerEnd;
    @FXML public ChoiceBox<String> ChoiceBoxStatus;
    @FXML public Button btnAccept;
    @FXML public Button btnCancel;

    public BookingController bookingController;
    private Booking booking;
    private AdminDashboardController adminDashboardController;

    /**
     * Maneja el evento de aceptación para modificar la reserva.
     *
     * @param actionEvent El evento de acción.
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
        Accommodation selectedAccommodation = accommodationController.getAccommodationById(Long.parseLong(partes[0]));

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

        // Verificar la disponibilidad del alojamiento si se cambia
        try {
            if (!booking.getAccommodation().getAccommodationId().equals(selectedAccommodation.getAccommodationId())) {
                List<Booking> bookings = bookingController.getAllBookings();
                long currentBookings = bookings.stream()
                        .filter(b -> b.getAccommodation().getAccommodationId().equals(selectedAccommodation.getAccommodationId()))
                        .count();

                if (currentBookings >= selectedAccommodation.getCapacity()) {
                    showError("This accommodation is fully booked.");
                    return;
                }

                booking.setAccommodation(selectedAccommodation);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Modify Booking");
            alert.setContentText("Are you sure you want to modify this booking?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                bookingController.updateBooking(booking);
                adminDashboardController.refreshBookings();
                ((Stage) btnAccept.getScene().getWindow()).close();
            }
        } catch (Exception e) {
            showError("Database error: " + e.getMessage());
        }
    }


    /**
     * Inicializa los datos de la reserva.
     *
     * @param booking La reserva a modificar.
     * @param bookingController El controlador de la reserva.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(Booking booking, BookingController bookingController, AdminDashboardController adminDashboardController) {
        this.booking = booking;
        this.bookingController = bookingController;
        this.adminDashboardController = adminDashboardController;
        List<User> userlist;
        UserController userCtrl = new UserController();
        userlist = userCtrl.getAll();
        List<String> users = new ArrayList<>();
        for (User user : userlist) {
            users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
            if (Objects.equals(user.getUserId(), booking.getUser().getUserId())) {
                ChoiceBoxUser.setValue(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
            }
        }
        List<Accommodation> accommodationlist;
        AccommodationController accommodationCtrl = new AccommodationController();
        accommodationlist = accommodationCtrl.getAllAccommodations();
        List<String> accommodations = new ArrayList<>();
        for (Accommodation accommodation : accommodationlist) {
            accommodations.add(accommodation.getAccommodationId() + "  " + accommodation.getAddress());
            if (Objects.equals(accommodation.getAccommodationId(), booking.getAccommodation().getAccommodationId())) {
                ChoiceBoxAccommodation.setValue(accommodation.getAccommodationId() + "  " + accommodation.getAddress());
            }
        }
        ChoiceBoxUser.getItems().addAll(users);
        ChoiceBoxAccommodation.getItems().addAll(accommodations);
        DatePickerEnd.setValue(booking.getEndDate().toLocalDate());
        DatePickerStart.setValue(booking.getStartDate().toLocalDate());
        String[] status = {"Pending", "Confirmed", "Canceled"};
        ChoiceBoxStatus.getItems().addAll(status);
        ChoiceBoxStatus.setValue(booking.getStatus().toString());
    }

    /**
     * Muestra un mensaje de error de campos incompletos.
     *
     * @param message el mensaje de error a mostrar
     */
    private void showFieldError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incomplete fields");
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
