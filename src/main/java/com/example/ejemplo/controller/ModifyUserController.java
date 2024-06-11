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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.ejemplo.model.User;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Controlador para la ventana de modificación de usuarios.
 */
public class ModifyUserController {

    @FXML
    public TextField txtName;
    @FXML
    public TextField txtPhone;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtPassword;
    @FXML
    public Button btnAccept;
    @FXML
    public Button btnCancel;
    @FXML
    public TextField txtApellidos;
    @FXML
    public ChoiceBox<String> genderChoiceBox;
    @FXML
    public TextField txtDNI;

    private User user;
    public UserController userController;
    private AdminDashboardController adminDashboardController;

    private static final Pattern DNI_PATTERN = Pattern.compile("^[0-9]{8}[A-Za-z]$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[\\p{L} .'-]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{9}$");

    /**
     * Maneja la acción de aceptar la modificación de usuario.
     *
     * @param actionEvent Evento del botón de aceptar
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
        if (name.isEmpty() || surnames.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || dni.isEmpty() || genderChoiceBox.getValue() == null) {
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

        // Verifica si el correo electrónico ya existe
        try {
            if (!email.equals(user.getEmail()) && userExists(email)) {
                errors.append("The email is already in use.\n");
            }
        } catch (SQLException e) {
            errors.append("Error checking email in the database: ").append(e.getMessage()).append("\n");
        }

        if (errors.length() > 0) {
            showError(errors.toString());
            return;
        }

        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setPhone(phone);
        user.setGender(gender);
        user.setLastName(surnames);
        user.setDni(dni);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Modify User");
        alert.setContentText("Are you sure you want to modify this user?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            userController.update(user);
            updateItemAdminList();
        }

        // Cierra la ventana de modificación
        ((Stage) btnAccept.getScene().getWindow()).close();
    }

    /**
     * Maneja la acción de cancelar la modificación de usuario.
     *
     * @param actionEvent Evento del botón de cancelar
     */
    public void handleCancel(ActionEvent actionEvent) {
        // Cierra la ventana de modificación
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    /**
     * Inicializa los datos del usuario en la ventana de modificación.
     *
     * @param user Usuario a modificar
     * @param userController Controlador de usuarios
     * @param dashboard Controlador del panel de administrador
     */
    public void initData(User user, UserController userController, AdminDashboardController dashboard) {
        this.userController = userController;
        this.user = user;
        this.adminDashboardController = dashboard;

        // Configura los valores iniciales de los campos con los datos del usuario
        if (user != null) {
            String[] genders = {"Male", "Female", "Other"};
            genderChoiceBox.getItems().addAll(genders);
            genderChoiceBox.setValue("Gender");

            if (user.getGender() != null) {
                switch (user.getGender()) {
                    case MALE:
                        genderChoiceBox.setValue("Male");
                        break;
                    case FEMALE:
                        genderChoiceBox.setValue("Female");
                        break;
                    case OTHER:
                        genderChoiceBox.setValue("Other");
                        break;
                }
            }

            txtName.setText(user.getName());
            txtEmail.setText(user.getEmail());
            txtPhone.setText(user.getPhone());
            txtPassword.setText(user.getPassword());
            txtApellidos.setText(user.getLastName());
            txtDNI.setText(user.getDni());
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
        // Obtiene el controlador de la lista de usuarios de la ventana de administrador
        ItemUserListController itemAdminListController = (ItemUserListController) btnAccept.getScene().getWindow().getUserData();
        // Actualiza los datos del usuario en la lista
        itemAdminListController.updateUserData(user);
    }

    /**
     * Obtiene el usuario.
     *
     * @return El usuario
     */
    public User getUser() {
        return user;
    }
}
