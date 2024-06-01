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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para modificar una conversación.
 */
public class ModifyConversationController {
    @FXML public ChoiceBox ChoiceBoxModifyConverUser1;
    @FXML public ChoiceBox ChoiceBoxModifyConverUser2;
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
        String[] partes = ChoiceBoxModifyConverUser1.getValue().toString().split("\\s", 2);
        conver.setUser1Id(Long.valueOf(partes[0]));
        partes = ChoiceBoxModifyConverUser2.getValue().toString().split("\\s", 2);
        conver.setUser2Id(Long.valueOf(partes[0]));

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Modify Conversation");
        alert.setContentText("Are you sure to modify this Conversation?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            converCtrl.updateConversation(conver);
            ItemConversationListController itemCtrl;
            itemCtrl = (ItemConversationListController) btnAccept.getScene().getWindow().getUserData();
            itemCtrl.updateConversationData(conver);
            adminDashboardController.refreshConversations();
        }
        ((Stage) btnAccept.getScene().getWindow()).close();
    }
}
