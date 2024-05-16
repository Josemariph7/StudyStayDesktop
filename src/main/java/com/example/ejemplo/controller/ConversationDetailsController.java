package com.example.ejemplo.controller;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConversationDetailsController {
    @FXML public Label idUser1;
    @FXML public Label nameUser1;
    @FXML public Button btnBack;
    @FXML public Label idUser2;
    @FXML public Label nameUser2;

    private Conversation conver;
    public ConversationController converCtrl;
    private AdminDashboardController adminDashboardController;

    public void initData(Conversation conversation, ConversationController conversationController, AdminDashboardController adminDashboardController) {
        this.converCtrl = conversationController;
        this.conver = conversation;
        this.adminDashboardController=adminDashboardController;
        UserController userController=new UserController();
        User user1=userController.getById(conver.getUser1Id());
        User user2=userController.getById(conver.getUser2Id());

        idUser1.setText(user1.getUserId().toString());
        idUser2.setText(user2.getUserId().toString());
        nameUser1.setText(user1.getName()+" "+user1.getLastName());
        nameUser2.setText(user2.getName()+" "+user2.getLastName());


    }
}
