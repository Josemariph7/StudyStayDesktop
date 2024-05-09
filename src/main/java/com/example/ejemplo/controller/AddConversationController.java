package com.example.ejemplo.controller;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AddConversationController {
    public ChoiceBox ChoiceBoxAddConverUser1;
    public ChoiceBox ChoiceBoxAddConverUser2;
    public Button btnAccept;
    public Button btnCancel;

    private Conversation conver;
    public ConversationController converCtrl;

    public void initData() {
        conver=new Conversation();
        converCtrl=new ConversationController();
        List<User> userlist;
        UserController userCtrl = new UserController();
        userlist = userCtrl.getAll();
        List<String> users = new ArrayList<>();
        for (User user : userlist) {
            users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
        }
        ChoiceBoxAddConverUser1.getItems().addAll(users);
        ChoiceBoxAddConverUser2.getItems().addAll(users);
        ChoiceBoxAddConverUser1.setValue("Select User 1");
        ChoiceBoxAddConverUser2.setValue("Select User 2");
    }

    public void handleAccept(ActionEvent actionEvent) {
        UserController userController = new UserController();
        String id=ChoiceBoxAddConverUser1.getValue().toString();
        String[] partes = id.split("\\s", 2);

        conver.setUser1Id(Long.valueOf(partes[0]));
        id=ChoiceBoxAddConverUser2.getValue().toString();
        partes = id.split("\\s", 2);
        conver.setUser2Id(Long.valueOf(partes[0]));
        conver.setMessages(null);
        try {
            if (conversationExists(conver)) {
                showError("This conversation already exists");
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Create Conversation");
            alert.setContentText("Are you sure to create this Conversation?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                converCtrl.createConversation(conver);
                AdminDashboardController itemCtrl;
                itemCtrl = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
                itemCtrl.refresh();
            }
            ((Stage) btnAccept.getScene().getWindow()).close();
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    private boolean conversationExists(Conversation conver) throws SQLException {
        List<Conversation> convers = converCtrl.getAllConversations();
        for (Conversation conversation : convers) {
            if (Objects.equals(conversation.getUser1Id(), conver.getUser1Id()) && Objects.equals(conversation.getUser2Id(), conver.getUser2Id())
            || Objects.equals(conversation.getUser2Id(), conver.getUser1Id()) && Objects.equals(conversation.getUser1Id(), conver.getUser2Id()) || Objects.equals(conver.getUser1Id(), conver.getUser2Id())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Método para mostrar un mensaje de error.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
