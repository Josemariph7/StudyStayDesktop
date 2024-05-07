package com.example.ejemplo.controller;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.Message;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        //try {
        /*
        * MODIFICAR ESTO Y LO DE LLAMAR AL ADD TAMBIEN, LO DEJE POR AQUI
        *
        * */
          /*  FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MODIFYCONVERSATION_FXML));
            Parent root = loader.load();
            ModifyConversationController modify = loader.getController();
            modify.initData(user, userController);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ModifyUserController modifyController = loader.getController();
            dashboard.updateStatistics();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Modificar usuario: " + user);*/
    }
}
