package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.Booking;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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
                booking.setStartDate(DatePickerEnd.getValue().atStartOfDay());
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
                AdminDashboardController itemCtrl;
                itemCtrl = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
                itemCtrl.refresh();
            }
            ((Stage) btnAccept.getScene().getWindow()).close();
    }

    public void initData(Booking booking, BookingController bookingController) {
        this.booking=booking;
        this.bookingController=bookingController;
        List<User> userlist;
        UserController userCtrl = new UserController();
        userlist = userCtrl.getAll();
        List<String> users = new ArrayList<>();
        for (User user : userlist) {
            users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
            if(Objects.equals(user.getUserId(), booking.getUser().getUserId())){
                ChoiceBoxUser.setValue(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
            }
        }
        List<Accommodation> accommodationlist;
        AccommodationController accommodationCtrl = new AccommodationController();
        accommodationlist=accommodationCtrl.getAllAccommodations();
        List<String> accommodations = new ArrayList<>();
        for (Accommodation accommodation : accommodationlist) {
            accommodations.add(accommodation.getAccommodationId()+"  "+accommodation.getAddress());
            if(Objects.equals(accommodation.getAccommodationId(), booking.getAccommodation().getAccommodationId())){
                ChoiceBoxAccommodation.setValue(accommodation.getAccommodationId()+"  "+accommodation.getAddress());
            }
        }
        ChoiceBoxUser.getItems().addAll(users);
        ChoiceBoxAccommodation.getItems().addAll(accommodations);
        DatePickerEnd.setValue(booking.getEndDate().toLocalDate());
        DatePickerStart.setValue(booking.getStartDate().toLocalDate());
        ChoiceBoxStatus.setValue(booking.getStatus());
    }
}
