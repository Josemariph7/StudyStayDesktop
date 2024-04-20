package com.example.ejemplo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {

    @FXML
    private Button btnSignout;

    /**
     * Método de inicialización del controlador.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Método para cerrar la sesión y volver a la pantalla de inicio de sesión.
     */
    @FXML
    public void signOut(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnSignout) {
            Stage stage = (Stage) btnSignout.getScene().getWindow();

            try {
                // Carga la vista de inicio de sesión
                URL fxmlUrl = getClass().getResource(Constants.LOGIN_FXML);
                if (fxmlUrl == null) {
                    throw new IllegalArgumentException(Constants.FILE_NOT_FOUND_ERROR);
                }
                Parent root = FXMLLoader.load(fxmlUrl);
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showError(Constants.LOAD_VIEW_ERROR);
            }
        }
    }

    /**
     * Método para cerrar la aplicación.
     */
    @FXML
    private void closeApp() {
        System.exit(0);
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

    public void initData(User user) {
    }
}
