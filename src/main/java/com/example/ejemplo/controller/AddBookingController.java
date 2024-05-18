package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AddBookingController {
    public Button btnCancel;
    public Button btnAccept;
    public ChoiceBox ChoiceBoxStatus;
    public DatePicker DatePickerEnd;
    public DatePicker DatePickerStart;
    public ChoiceBox ChoiceBoxAccommodation;
    public ChoiceBox ChoiceBoxUser;

    public BookingController bookingController;
    private Booking booking;

    public void handleAccept(ActionEvent actionEvent) {
        UserController userController = new UserController();
        AccommodationController accommodationController = new AccommodationController();
        String user=ChoiceBoxUser.getValue().toString();
        String[] partes = user.split("\\s", 2);
        booking.setUser(userController.getById(Long.parseLong(partes[0])));
        String accommodation=ChoiceBoxAccommodation.getValue().toString();
        partes=accommodation.split("\\s", 2);
        booking.setAccommodation(accommodationController.getAccommodationById(Long.parseLong(partes[0])));
        if (ChoiceBoxStatus.getValue()!=null){
            if(Objects.equals(ChoiceBoxStatus.getValue(), "Pending")){
                booking.setStatus(Booking.BookingStatus.PENDING);
            }else {
                if (Objects.equals(ChoiceBoxStatus.getValue(), "Confirmed")) {
                    booking.setStatus(Booking.BookingStatus.CONFIRMED);
                } else {
                    if (Objects.equals(ChoiceBoxStatus.getValue(), "Canceled")) {
                        booking.setStatus(Booking.BookingStatus.CANCELLED);
                    }
                }
            }
        }
        if (DatePickerEnd.getValue() != null && DatePickerStart.getValue() !=null) {
            if(DatePickerEnd.getValue().isAfter(DatePickerStart.getValue())&&DatePickerStart.getValue().isBefore(LocalDate.now())){
                booking.setStartDate(DatePickerStart.getValue().atStartOfDay());
                booking.setEndDate(DatePickerEnd.getValue().atStartOfDay());
            }
        }
        try {
            if (bookingExists(booking)) {
                showError(Constants.USER_EXISTS_ERROR);
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Add Booking");
            alert.setContentText("Are you sure to add the new Booking?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if(booking.getAccommodation().getCapacity()<=booking.getAccommodation().getTenants().size()) {
                    bookingController.createBooking(booking);
                    booking.getAccommodation().getTenants().add(booking.getUser());
                    AdminDashboardController itemCtrl;
                    itemCtrl = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
                    itemCtrl.refresh();
                }else{
                    booking.getAccommodation().setAvailability(false);
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setContentText("This accommodation is full");
                }
            }
            ((Stage) btnAccept.getScene().getWindow()).close();
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }

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
        accommodationlist=accommodationCtrl.getAllAccommodations();
        List<String> accommodations = new ArrayList<>();
        for (Accommodation accommodation : accommodationlist) {
            accommodations.add(accommodation.getAccommodationId()+"  "+accommodation.getAddress());
        }
        ChoiceBoxAccommodation.getItems().addAll(accommodations);
        ChoiceBoxUser.getItems().addAll(users);
        String[] status={"Pending", "Confirmed", "Canceled"};
        ChoiceBoxStatus.getItems().addAll(status);
        ChoiceBoxStatus.setValue("Status");
    }

    private boolean bookingExists(Booking book) throws SQLException {
        List<Booking> bookings = bookingController.getAllBookings();
        for (Booking booking : bookings) {
            if (Objects.equals(booking.getUser().getUserId(), book.getUser().getUserId()) && Objects.equals(booking.getAccommodation().getAccommodationId(), book.getBookingId())) {
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

}
