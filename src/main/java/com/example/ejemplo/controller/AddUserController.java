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

/**
 * Controlador para agregar un nuevo usuario.
 */
public class AddUserController {

    @FXML public Button btnCancel;
    @FXML public Button btnAccept;
    @FXML public DatePicker birthDatePicker;
    @FXML public ChoiceBox genderChoiceBox;
    @FXML public TextField txtDNI;
    @FXML public TextField txtPhone;
    @FXML public TextField txtPassword;
    @FXML public TextField txtEmail;
    @FXML public TextField txtApellidos;
    @FXML public TextField txtName;

    /**
     * Maneja la acción de aceptar para agregar un nuevo usuario.
     *
     * @param actionEvent el evento de acción
     */
    public void handleAccept(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String name = txtName.getText();
        String surnames = txtApellidos.getText();
        StringBuilder errors = new StringBuilder();

        // Verifica la fecha de nacimiento
        System.out.println(birthDatePicker.getValue());
        LocalDate birthDate = null;
        if (birthDatePicker.getValue() != null) {
            birthDate = birthDatePicker.getValue().atStartOfDay().toLocalDate();
        } else {
            errors.append("Debe establecer una fecha de nacimiento válida.\n");
        }
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            errors.append("Debe establecer una fecha de nacimiento válida.\n");
        }

        User.Gender gender = null;
        if (genderChoiceBox.getValue() != null) {
            if (Objects.equals(genderChoiceBox.getValue(), "Male")) {
                gender = User.Gender.MALE;
            } else if (Objects.equals(genderChoiceBox.getValue(), "Female")) {
                gender = User.Gender.FEMALE;
            } else if (Objects.equals(genderChoiceBox.getValue(), "Other")) {
                gender = User.Gender.OTHER;
            }
        }
        String dni = txtDNI.getText();
        String password = txtPassword.getText();
        String phone = txtPhone.getText();
        try {
            // Verifica si el usuario ya existe
            if (userExists(email)) {
                showError(Constants.USER_EXISTS_ERROR);
                return;
            }
            // Crea un nuevo objeto de usuario y lo guarda en la base de datos
            User user = new User(name, surnames, email, password, phone, birthDate, LocalDateTime.now(), gender, dni, null, null, false);
            UserController userController = new UserController();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Add User");
            alert.setContentText("Are you sure to add the new User?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                userController.create(user);
                System.out.println(user);
                ((Stage) btnAccept.getScene().getWindow()).close();
                updateItemAdminList();
            }
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }

    /**
     * Verifica si un usuario ya existe en la base de datos.
     *
     * @param email el correo electrónico del usuario a verificar
     * @return true si el usuario existe, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
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
     * Actualiza la lista de usuarios en la interfaz de administrador después de la modificación.
     */
    private void updateItemAdminList() {
        AdminDashboardController adminDashboardController = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
        adminDashboardController.refreshUsers();
    }

    /**
     * Inicializa los datos necesarios para la interfaz de usuario.
     */
    public void initData() {
        String[] genders = {"Male", "Female", "Other"};
        genderChoiceBox.getItems().addAll(genders);
        genderChoiceBox.setValue("Gender");
    }
}
