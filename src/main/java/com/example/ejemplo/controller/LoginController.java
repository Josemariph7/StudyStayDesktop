package com.example.ejemplo.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import com.example.ejemplo.utils.Constants;

/**
 * Controlador para la ventana de inicio de sesión.
 */
public class LoginController implements Initializable {

    @FXML
    public VBox vbox;
    private Parent fxml;

    /**
     * Inicializa la ventana de inicio de sesión.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Transición de animación para mostrar el formulario de inicio de sesión
        TranslateTransition t = new TranslateTransition(Duration.seconds(0.01), vbox);
        vbox.setLayoutX(vbox.getLayoutX() * 17); // Mueve el VBox fuera de la pantalla
        t.setToX(0); // Mueve el VBox a su posición original
        t.play();
        t.setOnFinished((e) ->{
            // Cargar el formulario de inicio de sesión
            try{
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.SIGNIN_FXML)));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ignored){

            }
        });
    }

    /**
     * Abre el formulario de inicio de sesión.
     * @param event Evento del botón
     */
    @FXML
    public void open_signin(ActionEvent event){
        // Transición de animación para mostrar el formulario de inicio de sesión
        TranslateTransition t = new TranslateTransition(Duration.seconds(0.8), vbox);
        t.setToX(0); // Mueve el VBox a su posición original
        t.play();
        t.setOnFinished((e) ->{
            // Cargar el formulario de inicio de sesión
            try{
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.SIGNIN_FXML)));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ignored){

            }
        });
    }

    /**
     * Abre el formulario de registro.
     * @param event Evento del botón
     */
    @FXML
    public void open_signup(ActionEvent event){
        // Transición de animación para mostrar el formulario de registro
        TranslateTransition t = new TranslateTransition(Duration.seconds(Constants.TRANSITION_TIME), vbox);
        t.setToX(vbox.getLayoutX() / -1.084); // Mueve el VBox a la izquierda para mostrar el formulario de registro
        t.play();
        t.setOnFinished((e) ->{
            // Cargar el formulario de registro
            try{
                fxml = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.SIGNUP_FXML)));
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            }catch(IOException ignored){
            }
        });
    }
}
