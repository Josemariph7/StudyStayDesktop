package com.example.ejemplo.controller;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controlador para modificar una conversación.
 */
public class ModifyConversationController {
    @FXML public ChoiceBox<String> ChoiceBoxModifyConverUser1;
    @FXML public ChoiceBox<String> ChoiceBoxModifyConverUser2;
    @FXML public Button btnAccept;
    @FXML public Button btnCancel;

    private Conversation conver;
    public ConversationController converCtrl;
    private AdminDashboardController adminDashboardController;

    /**
     * Inicializa los datos de la conversación en la ventana de modificación.
     *
     * @param conver                   La conversación a modificar.
     * @param conversationController   El controlador de conversaciones.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(Conversation conver, ConversationController conversationController, AdminDashboardController adminDashboardController) {
        this.converCtrl = conversationController;
        this.conver = conver;
        this.adminDashboardController = adminDashboardController;
        if (conver != null) {
            List<User> userlist;
            UserController userCtrl = new UserController();
            userlist = userCtrl.getAll();
            List<String> users = new ArrayList<>();
            for (User user : userlist) {
                users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
            }
            ChoiceBoxModifyConverUser1.getItems().addAll(users);
            ChoiceBoxModifyConverUser2.getItems().addAll(users);
            User user1 = userCtrl.getById(conver.getUser1Id());
            User user2 = userCtrl.getById(conver.getUser2Id());
            ChoiceBoxModifyConverUser1.getSelectionModel().select(user1.getUserId() + "  " + user1.getName() + " " + user1.getLastName());
            ChoiceBoxModifyConverUser2.getSelectionModel().select(user2.getUserId() + "  " + user2.getName() + " " + user2.getLastName());
        }
    }

    /**
     * Maneja el evento de aceptación para modificar la conversación.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleAccept(ActionEvent actionEvent) {
        if (ChoiceBoxModifyConverUser1.getValue() == null || ChoiceBoxModifyConverUser2.getValue() == null) {
            showFieldError("All fields are required.");
            return;
        } else if (ChoiceBoxModifyConverUser1.getValue().equals(ChoiceBoxModifyConverUser2.getValue())) {
            showFieldError("The same user cannot be selected for both fields.");
            return;
        }

        String[] partes = ChoiceBoxModifyConverUser1.getValue().toString().split("\\s", 2);
        conver.setUser1Id(Long.valueOf(partes[0]));
        partes = ChoiceBoxModifyConverUser2.getValue().toString().split("\\s", 2);
        conver.setUser2Id(Long.valueOf(partes[0]));

        try {
            if (conversationExists(conver)) {
                showError("This conversation already exists.");
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Modify Conversation");
            alert.setContentText("Are you sure you want to modify this Conversation?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                converCtrl.updateConversation(conver);
                ItemConversationListController itemCtrl;
                itemCtrl = (ItemConversationListController) btnAccept.getScene().getWindow().getUserData();
                itemCtrl.updateConversationData(conver);
                adminDashboardController.refreshConversations();
            }
            ((Stage) btnAccept.getScene().getWindow()).close();
        } catch (Exception e) {
            showError("Database error: " + e.getMessage());
        }
    }

    /**
     * Verifica si la conversación ya existe.
     *
     * @param conver la conversación a verificar
     * @return true si la conversación existe, false en caso contrario
     */
    private boolean conversationExists(Conversation conver) throws SQLException {
        List<Conversation> convers = converCtrl.getAllConversations();
        for (Conversation conversation : convers) {
            if ((Objects.equals(conversation.getUser1Id(), conver.getUser1Id()) && Objects.equals(conversation.getUser2Id(), conver.getUser2Id())) ||
                    (Objects.equals(conversation.getUser2Id(), conver.getUser1Id()) && Objects.equals(conversation.getUser1Id(), conver.getUser2Id()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Muestra un mensaje de error de campos incompletos.
     *
     * @param message el mensaje de error a mostrar
     */
    private void showFieldError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Incomplete Fields");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param message el mensaje de error a mostrar
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
