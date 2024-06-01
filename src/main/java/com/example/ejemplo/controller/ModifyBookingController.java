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
    @FXML public ChoiceBox ChoiceBoxUser;
    @FXML public ChoiceBox ChoiceBoxAccommodation;
    @FXML public DatePicker DatePickerStart;
    @FXML public DatePicker DatePickerEnd;
    @FXML public ChoiceBox ChoiceBoxStatus;
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
        String user = ChoiceBoxUser.getValue().toString();
        String[] partes = user.split("\\s", 2);
        booking.setUser(userController.getById(Long.parseLong(partes[0])));
        String accommodation = ChoiceBoxAccommodation.getValue().toString();
        partes = accommodation.split("\\s", 2);
        booking.setAccommodation(accommodationController.getAccommodationById(Long.parseLong(partes[0])));
        if (ChoiceBoxStatus.getValue() != null) {
            if (Objects.equals(ChoiceBoxStatus.getValue(), "Pending")) {
                booking.setStatus(Booking.BookingStatus.PENDING);
            } else if (Objects.equals(ChoiceBoxStatus.getValue(), "Confirmed")) {
                booking.setStatus(Booking.BookingStatus.CONFIRMED);
            } else if (Objects.equals(ChoiceBoxStatus.getValue(), "Canceled")) {
                booking.setStatus(Booking.BookingStatus.CANCELLED);
            }
        }
        if (DatePickerEnd.getValue() != null && DatePickerStart.getValue() != null) {
            if (DatePickerEnd.getValue().isAfter(DatePickerStart.getValue()) && DatePickerStart.getValue().isBefore(LocalDate.now())) {
                booking.setStartDate(DatePickerStart.getValue().atStartOfDay());
                booking.setEndDate(DatePickerEnd.getValue().atStartOfDay());
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Modify Booking");
        alert.setContentText("Are you sure to modify this Booking?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            bookingController.updateBooking(booking);
            ItemBookingListController itemCtrl;
            itemCtrl = (ItemBookingListController) btnAccept.getScene().getWindow().getUserData();
            adminDashboardController.refreshBookings();
        }
        ((Stage) btnAccept.getScene().getWindow()).close();
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
        ChoiceBoxStatus.setValue(booking.getStatus());
    }
}
