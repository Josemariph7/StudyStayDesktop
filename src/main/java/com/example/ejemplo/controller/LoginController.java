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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
