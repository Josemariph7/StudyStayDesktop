package com.example.ejemplo.controller;

import com.almasb.fxgl.app.scene.FXGLDefaultMenu;
import com.example.ejemplo.model.ForumTopic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.time.format.DateTimeFormatter;

public class ForumDetailsController {

    @FXML public Button btnBack;
    @FXML public Label lblAuthor;
    @FXML public Label lblTitle;
    @FXML public Label lblCreationDate;
    @FXML public TextArea txtAreaDescription;

    private ForumTopicController forumTopicController;
    private ForumTopic forumTopic;
    private AdminDashboardController adminDashboardController;

    public void initData(ForumTopic forumTopic, ForumTopicController forumTopicController, AdminDashboardController dashboard) {
        this.forumTopicController = forumTopicController;
        this.forumTopic = forumTopic;
        this.adminDashboardController = dashboard;
        lblAuthor.setText(forumTopic.getAuthor().getName()+" "+forumTopic.getAuthor().getLastName());
        lblTitle.setText(forumTopic.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = forumTopic.getDateTime().format(formatter);
        lblCreationDate.setText(formattedDate);
    }
}
