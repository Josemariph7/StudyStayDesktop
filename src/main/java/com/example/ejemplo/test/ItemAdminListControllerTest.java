package com.example.ejemplo.test;
import com.example.ejemplo.controller.ItemAdminListController;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.example.ejemplo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ItemAdminListControllerTest {

    private ItemAdminListController controller;
    private User user;

    @BeforeEach
    public void setUp() {
        controller = new ItemAdminListController();
        user = new User();
        user.setUserId(1L);
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPhone("123456789");
        user.setRole(User.UserRole.OWNER);
        user.setRegistrationDate(LocalDateTime.now());
    }

    @Test
    public void testInitialization() {
        VBox pnItems = new VBox();
        controller.initData(user, null, null, pnItems, null);

        assertNotNull(controller.lblUserId);
        assertNotNull(controller.lblName);
        assertNotNull(controller.lblEmail);
        assertNotNull(controller.lblPhone);
        assertNotNull(controller.lblRole);
        assertNotNull(controller.lblRegDate);
        assertNotNull(controller.btnDelete);
        assertNotNull(controller.btnModify);

        assertEquals(String.valueOf(user.getUserId()), controller.lblUserId.getText());
        assertEquals(user.getName(), controller.lblName.getText());
        assertEquals(user.getEmail(), controller.lblEmail.getText());
        assertEquals(user.getPhone(), controller.lblPhone.getText());
        assertEquals(user.getRole().toString(), controller.lblRole.getText());
    }

    @Test
    public void testHandleDelete() {
        VBox pnItems = new VBox();
        controller.initData(user, null, null, pnItems, null);

        controller.handleDelete();

        assertEquals(0, pnItems.getChildren().size());
    }
}
