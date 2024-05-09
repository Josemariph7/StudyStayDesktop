package com.example.ejemplo.controller;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.Message;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ItemConversationListController {
    @FXML public Label lblConversationId;
    @FXML public Label lblConversationUser1;
    @FXML public Label lblConversationUser2;
    @FXML public Label lblNumberMessages;
    @FXML public Button btnConverDetails;
    @FXML public Button btnModifyConver;
    @FXML public Button btnDeleteConver;


    private ConversationController conversationController;
    private Conversation conversation;
    private Node node;
    private VBox pnItemsConver;
    private AdminDashboardController adminDashboardController;

    public void initData(Conversation conver, ConversationController conversationController, Node node, VBox pnItems, AdminDashboardController adminDashboardController) {
        this.adminDashboardController = adminDashboardController;
        this.pnItemsConver = pnItems;
        this.conversation = conver;
        this.node = node;
        this.conversationController = conversationController;
        lblConversationId.setText(String.valueOf(conver.getConversationId()));
        if(conver.getUser1Id()!=null && conver.getUser2Id()!=null) {
            UserController userController=new UserController();
            lblConversationUser1.setText(userController.getById(conver.getUser1Id()).getName() + " " + userController.getById(conver.getUser1Id()).getLastName());
            lblConversationUser2.setText(userController.getById(conver.getUser2Id()).getName() + " " + userController.getById(conver.getUser2Id()).getLastName());
        }else{
            //conversationController.deleteConversation(conver.getConversationId());
        }
        MessageController messageController=new MessageController();
        List<Message> messages=messageController.getMessagesByConversation(conver.getConversationId());
        lblNumberMessages.setText(String.valueOf(messages.size())+" Messages in the Conversation");
    }

    public void handleModify(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MODIFYCONVERSATION_FXML));
            Parent root = loader.load();
            ModifyConversationController modify = loader.getController();
            modify.initData(conversation, conversationController);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ModifyConversationController modifyController = loader.getController();
            adminDashboardController.refresh();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos de la conversación después de la modificación.
     *
     * @param conver La conversacion modificada.
     */
    public void updateConversationData(Conversation conver) {
        this.conversation = conver;
        lblConversationId.setText(String.valueOf(conver.getConversationId()));
        if(conver.getUser1Id()!=null && conver.getUser2Id()!=null) {
            UserController userController=new UserController();
            lblConversationUser1.setText(userController.getById(conver.getUser1Id()).getName() + " " + userController.getById(conver.getUser1Id()).getLastName());
            lblConversationUser2.setText(userController.getById(conver.getUser2Id()).getName() + " " + userController.getById(conver.getUser2Id()).getLastName());
        }else{
            //conversationController.deleteConversation(conver.getConversationId());
        }
        MessageController messageController=new MessageController();
        List<Message> messages=messageController.getMessagesByConversation(conver.getConversationId());
        lblNumberMessages.setText(String.valueOf(messages.size())+" Messages in the Conversation");
    }

    public void handleDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Delete Conversation");
        alert.setContentText("Are you sure you want to delete this Conversation?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            conversationController.deleteConversation(conversation.getConversationId());
            int index = pnItemsConver.getChildren().indexOf(node);
            if (index != -1) {
                pnItemsConver.getChildren().remove(index);
            } else {
                System.out.println("El nodo no se encontró en el VBox.");
            }
            AdminDashboardController AdminDashboardController = new AdminDashboardController();
            adminDashboardController.refresh();
        }

    }
}
