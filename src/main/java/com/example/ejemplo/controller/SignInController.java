package com.example.ejemplo.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;

import java.io.IOException;
import java.util.List;

public class SignInController {

    public Stage splashStage;

    @FXML
    public TextField emailField;

    @FXML
    public PasswordField passwordField;

    public UserController userController = new UserController();

    /**
     * Método de inicialización del controlador.
     * Establece el foco en el campo de correo electrónico al iniciar la vista.
     */
    @FXML
    private void initialize() {
        emailField.requestFocus();
    }

    /**
     * Método para realizar el inicio de sesión.
     * Comprueba las credenciales ingresadas y carga el panel de control correspondiente.
     * Muestra un mensaje de error si las credenciales son inválidas.
     */
    @FXML
    public void login() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email.isEmpty() || password.isEmpty()) {
            showError("Por favor, introduce el correo electrónico y la contraseña.");
            return;
        }
        List<User> userList = userController.getAll();
        for (User user : userList) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                // Muestra la pantalla de carga (splash screen) mientras se carga el panel de control
                showSplashScreen(() -> Platform.runLater(() -> loadDashboard(user.getRole(), user)));
                return;
            }
        }
        showError("Credenciales inválidas. Por favor, inténtalo de nuevo.");
    }

    /**
     * Método para cargar el panel de control correspondiente según el rol del usuario.
     * @param role Rol del usuario
     * @param user Objeto de usuario
     */
    private void loadDashboard(User.UserRole role, User user) {
        String fxmlPath;
        switch (role) {
            case ADMINISTRATOR:
                fxmlPath = Constants.DASHBOARD_ADMIN_FXML;
                break;
            case STUDENT:
                fxmlPath = Constants.DASHBOARD_STUDENT_FXML;
                break;
            case OWNER:
                fxmlPath = Constants.DASHBOARD_OWNER_FXML;
                break;
            default:
                showError(Constants.INVALID_USER_ROLE);
                return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent dashboard = loader.load();

            // Obtener el controlador cargado
            Object controller = loader.getController();
            System.out.println(controller);

            // Verificar que el controlador cargado sea del tipo correcto
            switch (role) {
                case ADMINISTRATOR:
                    if (!(controller instanceof AdminDashboardController)) {
                        showError(Constants.DASHBOARD_LOAD_ERROR);
                        return;
                    }
                    ((AdminDashboardController) controller).initData(user);
                    break;
                case STUDENT:
                    if (!(controller instanceof StudentDashboardController)) {
                        showError("Inicio de sesion Estudiante");
                        return;
                    }
                    ((StudentDashboardController) controller).initData(user);
                    break;
                case OWNER:
                    if (!(controller instanceof OwnerDashboardController)) {
                        showError("Inicio de sesion Propietario");
                        return;
                    }
                    ((OwnerDashboardController) controller).initData(user);
                    break;
            }

            Scene scene = new Scene(dashboard);
            Stage stage = (Stage) emailField.getScene().getWindow();

            stage.setScene(scene);
            stage.show();

            // Cerrar la pantalla de carga (splash screen)
           closeSplashScreen();
        } catch (IOException ex) {
            showError(Constants.DASHBOARD_LOAD_ERROR);
            ex.printStackTrace();
        }
    }

    /**
     * Método para mostrar un mensaje de error.
     * @param message Mensaje de error
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Método para mostrar la pantalla de carga (splash screen).
     * @param onSplashScreenFinished Acción a realizar cuando se completa la pantalla de carga
     */
    private void showSplashScreen(Runnable onSplashScreenFinished) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.SPLASH_SCREEN_FXML));
            Parent root = loader.load();

            splashStage = new Stage();
            splashStage.initStyle(StageStyle.TRANSPARENT);
            Scene scene = new Scene(root);
            splashStage.setScene(scene);
            splashStage.show();

            // Pausa antes de ejecutar la acción después de la pantalla de carga
            PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
            delay.setOnFinished(event -> onSplashScreenFinished.run());
            delay.play();
        } catch (IOException e) {
            e.printStackTrace();
            onSplashScreenFinished.run();
        }
    }

    /**
     * Método para cerrar la pantalla de carga (splash screen).
     */
    private void closeSplashScreen() {
        if (splashStage != null) {
            splashStage.close();
        }
    }

    /**
     * Método para cerrar la aplicación.
     */
    @FXML
    private void closeApp() {
        System.exit(0);
    }
}
