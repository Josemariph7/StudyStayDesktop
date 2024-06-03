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
import java.util.regex.Pattern;

/**
 * Controlador para agregar un nuevo usuario.
 */
public class AddUserController {

    @FXML public Button btnCancel;
    @FXML public Button btnAccept;
    @FXML public DatePicker birthDatePicker;
    @FXML public ChoiceBox<String> genderChoiceBox;
    @FXML public TextField txtDNI;
    @FXML public TextField txtPhone;
    @FXML public TextField txtPassword;
    @FXML public TextField txtEmail;
    @FXML public TextField txtApellidos;
    @FXML public TextField txtName;

    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Za-z]$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{9}$");

    /**
     * Maneja la acción de aceptar para agregar un nuevo usuario.
     *
     * @param actionEvent el evento de acción
     */
    public void handleAccept(ActionEvent actionEvent) {
        String email = txtEmail.getText();
        String name = txtName.getText();
        String surnames = txtApellidos.getText();
        String dni = txtDNI.getText();
        String password = txtPassword.getText();
        String phone = txtPhone.getText();
        StringBuilder errors = new StringBuilder();

        // Verifica los campos obligatorios
        if (name.isEmpty() || surnames.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || dni.isEmpty() || genderChoiceBox.getValue() == null || birthDatePicker.getValue() == null) {
            showFieldError("All fields are required.");
            return;
        }

        // Verifica el formato de los campos
        if (!DNI_PATTERN.matcher(dni).matches()) {
            errors.append("The DNI format is invalid.\n");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.append("The email format is invalid.\n");
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            errors.append("The phone number format is invalid.\n");
        }
        if (!NAME_PATTERN.matcher(name).matches() || !NAME_PATTERN.matcher(surnames).matches()) {
            errors.append("Name or surnames contain invalid characters.\n");
        }
        if (password.length() < 6) {
            errors.append("The password must be at least 6 characters long.\n");
        }

        // Verifica el género
        User.Gender gender = null;
        if (genderChoiceBox.getValue() != null) {
            switch (genderChoiceBox.getValue()) {
                case "Male":
                    gender = User.Gender.MALE;
                    break;
                case "Female":
                    gender = User.Gender.FEMALE;
                    break;
                case "Other":
                    gender = User.Gender.OTHER;
                    break;
                default:
                    errors.append("You must select a valid gender.\n");
                    break;
            }
        } else {
            errors.append("You must select a gender.\n");
        }

        // Verifica la fecha de nacimiento
        LocalDate birthDate = birthDatePicker.getValue();
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            errors.append("You must set a valid birth date.\n");
        }

        if (errors.length() > 0) {
            showError(errors.toString());
            return;
        }

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
            alert.setContentText("Are you sure you want to add this user?");
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
