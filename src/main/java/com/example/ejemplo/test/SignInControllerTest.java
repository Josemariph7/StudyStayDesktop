package com.example.ejemplo.test;

import com.example.ejemplo.controller.SignInController;
import com.example.ejemplo.controller.UserController;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.ejemplo.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class SignInControllerTest {

    @BeforeAll
    public static void setupJavaFX() {
        // Configurar JavaFX para pruebas no interactivas
        new JFXPanel();
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Arrange
        SignInController signInController = new SignInController();
        signInController.emailField = new TextField("test@example.com");
        signInController.passwordField = new PasswordField();
        signInController.userController = new UserController() {
            @Override
            public List<User> getAll() {
                List<User> userList = new ArrayList<>();
                User testUser = new User();
                testUser.setEmail("test@example.com");
                testUser.setPassword("password123");
                userList.add(testUser);
                return userList;
            }
        };

        // Act
        signInController.login();

        // Assert
        assertEquals(null, signInController.splashStage);
        // En este punto, esperarías que la pantalla del tablero se haya cargado correctamente.
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Arrange
        SignInController signInController = new SignInController();
        signInController.emailField = new TextField("test@example.com");
        signInController.passwordField = new PasswordField();
        signInController.userController = new UserController() {
            @Override
            public List<User> getAll() {
                return new ArrayList<>();
            }
        };

        // Act
        signInController.login();

    }
}
