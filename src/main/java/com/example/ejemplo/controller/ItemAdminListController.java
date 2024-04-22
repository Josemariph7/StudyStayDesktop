package com.example.ejemplo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

/**
 * Controlador para los elementos de la lista de usuarios en el panel de administrador.
 */
public class ItemAdminListController {

    @FXML
    public Label lblUserId;
    @FXML
    public Label lblName;
    @FXML
    public Label lblEmail;
    @FXML
    public Label lblPhone;
    @FXML
    public Label lblRole;
    @FXML
    public Label lblRegDate;
    @FXML
    public Button btnDelete;
    @FXML
    public Button btnModify;

    private User user;
    private UserController userController;
    private Node node;
    private VBox pnItems;
    private AdminDashboardController dashboard;

    /**
     * Inicializa el controlador.
     */
    @FXML
    private void initialize() {
        btnDelete.setOnAction(event -> handleDelete());
        btnModify.setOnAction(event -> handleModify());
    }

    /**
     * Inicializa los datos del usuario en el elemento de la lista.
     *
     * @param user                      El usuario a mostrar.
     * @param userController            El controlador de usuario.
     * @param node                      El nodo de la lista.
     * @param pnItems                   El contenedor de la lista.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(User user, UserController userController, Node node, VBox pnItems, AdminDashboardController adminDashboardController) {
        this.dashboard = adminDashboardController;
        this.pnItems = pnItems;
        this.user = user;
        this.node = node;
        this.userController = userController;
        lblUserId.setText(String.valueOf(user.getUserId()));
        lblName.setText(user.getName());
        lblEmail.setText(user.getEmail());
        lblPhone.setText(user.getPhone());
        if(user.isAdmin()) {
            lblRole.setText("Administrator");
        }else{
            lblRole.setText("User");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = user.getRegistrationDate().format(formatter);
        lblRegDate.setText(formattedDate);
    }

    /**
     * Maneja el evento de eliminación de usuario.
     */
    @FXML
    public void handleDelete() {
        userController.delete(user.getUserId());
        int index = pnItems.getChildren().indexOf(node);
        if (index != -1) {
            pnItems.getChildren().remove(index);
        } else {
            System.out.println("El nodo no se encontró en el VBox.");
        }
        dashboard.updateStatistics();
        System.out.println("Eliminar usuario: " + user);
    }

    /**
     * Maneja el evento de modificación de usuario.
     */
    @FXML
    private void handleModify() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MODIFY_FXML));
            Parent root = loader.load();
            System.out.println("Usuario que se intenta modificar: " + user);
            ModifyController modify = loader.getController();
            modify.initData(user, userController);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ModifyController modifyController = loader.getController();
            dashboard.updateStatistics();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Modificar usuario: " + user);
    }

    /**
     * Actualiza los datos del usuario después de la modificación.
     *
     * @param updatedUser El usuario modificado.
     */
    public void updateUserData(User updatedUser) {
        this.user = updatedUser;
        lblUserId.setText(String.valueOf(user.getUserId()));
        lblName.setText(user.getName());
        lblEmail.setText(user.getEmail());
        lblPhone.setText(user.getPhone());
        if(user.isAdmin()) {
            lblRole.setText("Administrator");
        }else{
            lblRole.setText("User");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = user.getRegistrationDate().format(formatter);
        lblRegDate.setText(formattedDate);
        dashboard.updateStatistics();
    }
}

