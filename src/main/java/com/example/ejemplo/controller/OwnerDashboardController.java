package com.example.ejemplo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controlador para el panel de control del propietario.
 */
public class OwnerDashboardController implements Initializable {

    @FXML
    private VBox pnItems;

    @FXML
    private Button btnSignout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Maneja el evento de cierre de sesión del propietario.
     * @param actionEvent Evento de clic en el botón de cierre de sesión
     */
    @FXML
    public void signOut(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnSignout) {
            Stage stage = (Stage) btnSignout.getScene().getWindow();
            try {
                // Cargar la vista de inicio de sesión al cerrar sesión
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

    public void initData(User user) {
    }

    /**
     * Cierra la aplicación al hacer clic en el botón de cierre de la ventana.
     */
    @FXML
    private void closeApp() {
        System.exit(0);
    }

    /**
     * Muestra un mensaje de error en una ventana emergente.
     * @param message Mensaje de error a mostrar
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
