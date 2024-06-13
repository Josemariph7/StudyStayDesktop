package com.example.ejemplo.controller;

import com.example.ejemplo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.example.ejemplo.utils.Constants;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SignUpController implements Initializable {

    private final Pattern emailPattern = Pattern.compile(Constants.EMAIL_REGEX);
    private final Pattern passwordPattern = Pattern.compile(Constants.PASSWORD_REGEX);
    private final Pattern phonePattern = Pattern.compile(Constants.PHONE_REGEX);
    private final Pattern namePattern = Pattern.compile(Constants.NAME_REGEX);

    @FXML
    public TextField nameField;
    @FXML
    public TextField signupEmailField;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField dniField;
    @FXML
    public TextField phoneField;
    @FXML
    public TextField surnamesField;
    @FXML
    public DatePicker birthDatePicker;
    @FXML
    public CheckBox adminCheckbox;
    @FXML public TextField adminCodeField;
    @FXML
    private Button signUpButton;
    @FXML
    private ChoiceBox<String> GenderChoiceBox;

    public UserController userController = new UserController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] genders = {"Male", "Female", "Other"};
        GenderChoiceBox.getItems().addAll(genders);
        GenderChoiceBox.setValue("Gender");
        birthDatePicker.setValue(LocalDate.now());
    }

    @FXML
    public void signUp() {
        String email = signupEmailField.getText();
        String name = nameField.getText();
        String surnames = surnamesField.getText();
        StringBuilder errors = new StringBuilder();

        LocalDate birthDate = birthDatePicker.getValue();
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            errors.append("You must set a valid birth date.\n");
        }

        User.Gender gender = null;
        switch (GenderChoiceBox.getValue()) {
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

        String dni = dniField.getText();
        boolean isAdmin = adminCheckbox.isSelected();
        String password = passwordField.getText();
        String phone = phoneField.getText();

        // Validación de campos
        if (!validateEmail(email)) errors.append("Invalid email format.\n");
        if (!validateName(name)) errors.append("The name must contain only valid characters.\n");
        if (!validatePassword(password)) errors.append("The password must be more than 8 characters long and contain at least one uppercase letter and one number.\n");
        if (!validatePhone(phone)) errors.append("The phone number must have 9 digits.\n");

        if (errors.length() > 0) {
            showError(errors.toString());
            return;
        }

        // Verifica que todos los campos estén completos
        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || phone.isEmpty() || dni.isEmpty()) {
            showError("Please fill in all the fields.");
            return;
        }

        try {
            if (userExists(email)) {
                showError(Constants.USER_EXISTS_ERROR);
                return;
            }
            if (isAdmin && !Objects.equals(adminCodeField.getText(), Constants.ADMINCODE)) {
                showError(Constants.ADMIN_ERROR);
                return;
            }

            // Crea un nuevo objeto de usuario y lo guarda en la base de datos
            User user = new User(name, surnames, email, password, phone, birthDate, LocalDateTime.now(), gender, dni, null, null, isAdmin);
            userController.create(user);
            showSuccess("Registration successful.");
            clearFields();
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }

    private boolean userExists(String email) throws SQLException {
        List<User> users = userController.getAll();
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }

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

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        nameField.setText("");
        signupEmailField.setText("");
        passwordField.setText("");
        surnamesField.setText("");
        phoneField.setText("");
        dniField.setText("");
        birthDatePicker.setValue(null);
        GenderChoiceBox.setValue("Gender");
        adminCodeField.setText("");
        adminCheckbox.setSelected(false);
    }

    @FXML
    private void closeApp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public void handleDisable(ActionEvent actionEvent) {
        adminCodeField.setVisible(adminCheckbox.isSelected());
    }
}
