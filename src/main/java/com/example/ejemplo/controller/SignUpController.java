package com.example.ejemplo.controller;

import com.example.ejemplo.model.User;
import com.example.ejemplo.model.User.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import com.example.ejemplo.utils.Constants;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SignUpController implements Initializable {

    private final Pattern emailPattern = Pattern.compile(Constants.EMAIL_REGEX);
    private final Pattern passwordPattern = Pattern.compile(Constants.PASSWORD_REGEX);
    private final Pattern phonePattern = Pattern.compile(Constants.PHONE_REGEX);
    private final Pattern namePattern = Pattern.compile(Constants.NAME_REGEX);

    @FXML
    public TextField fullNameField;
    @FXML
    public TextField signupEmailField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField passwordField2;
    @FXML
    public ChoiceBox<UserRole> roleChoiceBox;
    @FXML
    private Button signUpButton;

    public UserController userController = new UserController();

    /**
     * Método de inicialización del controlador.
     * Establece las opciones del ChoiceBox para el rol del usuario.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleChoiceBox.getItems().addAll(UserRole.values());
        roleChoiceBox.getSelectionModel().selectFirst();
    }

    /**
     * Método para registrar un nuevo usuario.
     */
    @FXML
    public void signUp() {
        String email = signupEmailField.getText();
        String fullName = fullNameField.getText();
        String password = passwordField.getText();
        String phone = passwordField2.getText();
        UserRole role = roleChoiceBox.getValue();

        // Validación de campos
        StringBuilder errors = new StringBuilder();
        if (!validateEmail(email)) errors.append("Formato de email inválido.\n");
        if (!validateName(fullName)) errors.append("El nombre debe contener al menos un apellido y solo caracteres válidos.\n");
        if (!validatePassword(password)) errors.append("La contraseña debe tener más de 8 caracteres y contener al menos una letra mayúscula y un número.\n");
        if (!validatePhone(phone)) errors.append("El teléfono debe comenzar con +34 seguido de 9 dígitos.\n");

        if (errors.length() > 0) {
            showError(errors.toString());
            return;
        }

        // Verifica que todos los campos estén completos
        if (email.isEmpty() || fullName.isEmpty() || password.isEmpty() || phone.isEmpty() || role == null) {
            showError("Por favor, rellena todos los campos.");
            return;
        }

        try {
            // Verifica si el usuario ya existe
            if (userExists(email)) {
                showError(Constants.USER_EXISTS_ERROR);
                return;
            }
            // Crea un nuevo objeto de usuario y lo guarda en la base de datos
            User user = new User(fullName, email, password, phone, role);
            userController.create(user);
            showSuccess("Registro exitoso.");
            // Limpia los campos después del registro exitoso
            fullNameField.setText("");
            signupEmailField.setText("");
            passwordField.setText("");
            passwordField2.setText("");
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }

    /**
     * Método para verificar si un usuario ya existe en la base de datos.
     */
    private boolean userExists(String email) throws SQLException {
        List<User> users = userController.getAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Métodos de validación para los diferentes campos del formulario.
     */
    private boolean validateEmail(String email) {
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePassword(String password) {
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    private boolean validatePhone(String phone) {
        Matcher matcher = phonePattern.matcher(phone);
        return matcher.matches();
    }

    private boolean validateName(String name) {
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
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
     * Método para cerrar la aplicación.
     */
    @FXML
    private void closeApp() {
        System.exit(0);
    }
}
