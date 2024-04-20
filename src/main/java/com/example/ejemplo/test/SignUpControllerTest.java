package com.example.ejemplo.test;
import com.example.ejemplo.controller.SignUpController;
import com.example.ejemplo.controller.UserController;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import com.example.ejemplo.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SignUpControllerTest {

    @BeforeAll
    public static void setupJavaFX() {
        // Configurar JavaFX para pruebas no interactivas
        new JFXPanel();
    }

    @Test
    public void testSignUpWithValidData() {
        // Arrange
        SignUpController signUpController = new SignUpController();
        signUpController.fullNameField = new TextField("John Doe");
        signUpController.signupEmailField = new TextField("john.doe@example.com");
        signUpController.passwordField = new TextField("Password123");
        signUpController.passwordField2 = new TextField("+34123456789");
        signUpController.roleChoiceBox = new ChoiceBox<>();
        signUpController.roleChoiceBox.getItems().addAll(User.UserRole.values());
        signUpController.roleChoiceBox.getSelectionModel().selectFirst();

        signUpController.userController = new UserController() {
            @Override
            public List<User> getAll() {
                return new ArrayList<>();
            }
        };

        // Act
        signUpController.signUp();
    }

    @Test
    public void testSignUpWithInvalidData() {
        // Arrange
        SignUpController signUpController = new SignUpController();
        signUpController.fullNameField = new TextField("John");
        signUpController.signupEmailField = new TextField("invalidemail");
        signUpController.passwordField = new TextField("password");
        signUpController.passwordField2 = new TextField("+341234567890"); // Teléfono demasiado largo
        signUpController.roleChoiceBox = new ChoiceBox<>();
        signUpController.roleChoiceBox.getItems().addAll(User.UserRole.values());
        signUpController.roleChoiceBox.getSelectionModel().selectFirst();

        signUpController.userController = new UserController() {
            @Override
            public List<User> getAll() {
                return new ArrayList<>();
            }
        };

        // Act
        signUpController.signUp();
    }
}