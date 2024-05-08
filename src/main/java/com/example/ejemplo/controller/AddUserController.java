package com.example.ejemplo.controller;

import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AddUserController {

    @FXML  public Button btnCancel;
    @FXML  public Button btnAccept;
    @FXML  public CheckBox adminCheckbox;
    @FXML  public DatePicker birthDatePicker;
    @FXML  public ChoiceBox genderChoiceBox;
    @FXML  public TextField txtDNI;
    @FXML  public TextField txtPhone;
    @FXML  public TextField txtPassword;
    @FXML  public TextField txtEmail;
    @FXML  public TextField txtApellidos;
    @FXML  public TextField txtName;

    public void handleAccept(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String name = txtName.getText();
        String surnames = txtApellidos.getText();
        StringBuilder errors = new StringBuilder();

        //ESTO PUEDE SER NULL, ESTABLECER UNA FECHA PREDETERMINADA O ALGO
        System.out.println(birthDatePicker.getValue());
        LocalDate birthDate = null;
        if (birthDatePicker.getValue() != null) {
            birthDate = birthDatePicker.getValue().atStartOfDay().toLocalDate();
        } else {
            errors.append("Debe establacer una fecha de nacimiento válida.\n");
        }
        if (birthDate.isAfter(LocalDate.now())){
            errors.append("Debe establacer una fecha de nacimiento válida.\n");
        }

        User.Gender gender=null;
        if(genderChoiceBox.getValue()!=null){
            if(Objects.equals(genderChoiceBox.getValue(), "Male")){
                gender=User.Gender.MALE;
            }else {
                if (Objects.equals(genderChoiceBox.getValue(), "Female")) {
                    gender = User.Gender.FEMALE;
                } else {
                    if (Objects.equals(genderChoiceBox.getValue(), "Other")) {
                        gender = User.Gender.OTHER;
                    }
                }
            }
        }
        String dni= txtDNI.getText();
        boolean isAdmin= adminCheckbox.isSelected() ;
        String password = txtPassword.getText();
        String phone = txtPhone.getText();
        try {
            // Verifica si el usuario ya existe
            if (userExists(email)) {
                showError(Constants.USER_EXISTS_ERROR);
                return;
            }
            // Crea un nuevo objeto de usuario y lo guarda en la base de datos
            User user = new User(name,surnames,email,password,phone,birthDate, LocalDateTime.now(),gender,dni,null,null,isAdmin);
            UserController userController = new UserController();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Add User");
            alert.setContentText("Are you sure to add the new user?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                userController.create(user);
                System.out.println(user);
                showSuccess("Registro exitoso.");
                ((Stage) btnAccept.getScene().getWindow()).close();
                updateItemAdminList();
            }
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }

    /**
     * Método para verificar si un usuario ya existe en la base de datos.
     */
    private boolean userExists(String email) throws SQLException {
        UserController userController = new UserController();
        List<User> users = userController.getAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para mostrar un mensaje de error.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Método para mostrar un mensaje de éxito.
     */
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Maneja la acción de cancelar la modificación de usuario.
     * @param actionEvent Evento del botón de cancelar
     */
    public void handleCancel(ActionEvent actionEvent) {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    /**
     * Actualiza la lista de usuarios en la interfaz de administrador después de la modificación.
     */
    private void updateItemAdminList() {
    AdminDashboardController adminDashboardController = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
        adminDashboardController.refresh();
    }

    public void initData() {
        String[] genders={"Male", "Female", "Other"};
        genderChoiceBox.getItems().addAll(genders);
        genderChoiceBox.setValue("Gender");
    }
}
