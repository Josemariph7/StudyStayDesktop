package com.example.ejemplo.controller;

import com.example.ejemplo.model.User;
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

    /*
    *
    *
    * REVISAR EL TEMA DE PATTERNS PARA EL RESTO DE CAMPOS DEL REGISTRO
    * MIRAR TAMBIEN Y CAMBIAR EL PATTER DE APELLIDOS QUE ANTES ERA SOLO 1
    * TAMBIEN EL TEMA DE LOS MENSAJES DE ERROR
    *
    *
    * */

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
    @FXML
    private Button signUpButton;
    @FXML
    private ChoiceBox<String> GenderChoiceBox;

    public UserController userController = new UserController();

    /**
     * Método de inicialización del controlador.
     * Establece las opciones del ChoiceBox para el rol del usuario.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] genders={"Male", "Female", "Other"};
        GenderChoiceBox.getItems().addAll(genders);
        GenderChoiceBox.setValue("Gender");
        birthDatePicker.setValue(LocalDate.now());
        System.out.println(birthDatePicker.getValue());
    }

    /**
     * Método para registrar un nuevo usuario.
     */
    @FXML
    public void signUp() {
        String email = signupEmailField.getText();
        String name = nameField.getText();
        String surnames = surnamesField.getText();
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
        if(GenderChoiceBox.getValue()!=null){
            if(Objects.equals(GenderChoiceBox.getValue(), "Male")){
                gender=User.Gender.MALE;
            }else {
                if (Objects.equals(GenderChoiceBox.getValue(), "Female")) {
                    gender = User.Gender.FEMALE;
                } else {
                    if (Objects.equals(GenderChoiceBox.getValue(), "Other")) {
                        gender = User.Gender.OTHER;
                    }
                }
            }
        }
        String dni= dniField.getText();
        boolean isAdmin= adminCheckbox.isSelected() ;
        String password = passwordField.getText();
        String phone = phoneField.getText();

        // Validación de campos
        if (!validateEmail(email)) errors.append("Formato de email inválido.\n");
        if (!validateName(name)) errors.append("El nombre debe contener al menos un apellido y solo caracteres válidos.\n");
        if (!validatePassword(password)) errors.append("La contraseña debe tener más de 8 caracteres y contener al menos una letra mayúscula y un número.\n");
        if (!validatePhone(phone)) errors.append("El teléfono debe comenzar con +34 seguido de 9 dígitos.\n");

        if (!errors.isEmpty()) {
            showError(errors.toString());
            return;
        }

        // Verifica que todos los campos estén completos
        if (email.isEmpty() || name.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            showError("Por favor, rellena todos los campos.");
            return;
        }

        try {
            // Verifica si el usuario ya existe
            if (userExists(email)) {
                showError(Constants.USER_EXISTS_ERROR);
                return;
            }
            System.out.println();
            // Crea un nuevo objeto de usuario y lo guarda en la base de datos
            User user = new User(name,surnames,email,password,phone,birthDate, LocalDateTime.now(),gender,dni,null,null,isAdmin);
            userController.create(user);
            System.out.println(user);
            showSuccess("Registro exitoso.");
            // Limpia los campos después del registro exitoso
            nameField.setText("");
            signupEmailField.setText("");
            passwordField.setText("");
            surnamesField.setText("");
            phoneField.setText("");
            dniField.setText("");
            birthDatePicker.setValue(null);
            GenderChoiceBox.setValue("");
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
