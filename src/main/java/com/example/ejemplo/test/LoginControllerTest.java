package com.example.ejemplo.test;


import com.example.ejemplo.controller.LoginController;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    private LoginController loginController;

    @BeforeEach
    public void setUp() {
        loginController = new LoginController();
        loginController.vbox = new VBox();
    }

    @Test
    public void testInitialization() {
        // Verifica que el VBox no sea nulo después de la inicialización
        assertNotNull(loginController.vbox);
    }

    @Test
    public void testOpenSignIn() {
        // Verifica que al abrir la pantalla de inicio de sesión, la transición se realiza correctamente
        double initialLayoutX = loginController.vbox.getLayoutX();
        loginController.open_signin(null);
        double finalLayoutX = loginController.vbox.getLayoutX();
        assertEquals(0, finalLayoutX);
        assertNotEquals(initialLayoutX, finalLayoutX);
    }

    @Test
    public void testOpenSignUp() {
        // Verifica que al abrir la pantalla de registro, la transición se realiza correctamente
        double initialLayoutX = loginController.vbox.getLayoutX();
        loginController.open_signup(null);
        double finalLayoutX = loginController.vbox.getLayoutX();
        assertEquals(initialLayoutX / -1.084, finalLayoutX, 0.001);
        assertNotEquals(initialLayoutX, finalLayoutX);
    }
}
