package com.example.ejemplo.test;

import com.example.ejemplo.controller.ModifyController;
import com.example.ejemplo.controller.UserController;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import com.example.ejemplo.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModifyControllerTest {

    private ModifyController modifyController;
    private UserController userController;
    private User testUser;

    @BeforeEach
    public void setUp() {
        modifyController = new ModifyController();
        userController = new UserController(); // Utilizamos la implementación real
        modifyController.userController = userController;
        testUser = new User();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setPhone("123456789");
        testUser.setPassword("testpassword");
    }

    @Test
    public void testHandleAccept() {
        // Inicializamos los datos del usuario en ModifyController
        modifyController.initData(testUser, userController);

        // Creamos un botón y lo asociamos al evento handleAccept
        Button acceptButton = new Button();
        acceptButton.setOnAction(event -> modifyController.handleAccept(null));

        // Llamamos al método handleAccept simulando un clic en el botón
        acceptButton.fire();

        // Verificamos que los datos del usuario se hayan actualizado correctamente
        assertEquals(testUser.getRole(), modifyController.getUser().getRole());
        assertEquals(testUser.getEmail(), modifyController.txtEmail.getText());
        assertEquals(testUser.getName(), modifyController.txtName.getText());
        assertEquals(testUser.getPassword(), modifyController.txtPassword.getText());
        assertEquals(testUser.getPhone(), modifyController.txtPhone.getText());
    }

    @Test
    public void testHandleCancel() {
        // Creamos un botón y lo asociamos al evento handleCancel
        Button cancelButton = new Button();
        cancelButton.setOnAction(event -> modifyController.handleCancel(null));

        // Llamamos al método handleCancel simulando un clic en el botón
        cancelButton.fire();

        // Verificamos que la ventana de modificación se haya cerrado
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        assertEquals(true, stage.isShowing());
    }
}
