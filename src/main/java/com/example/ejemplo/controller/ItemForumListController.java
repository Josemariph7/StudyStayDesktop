package com.example.ejemplo.controller;

import com.example.ejemplo.model.Accommodation;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class ItemForumListController {
    @FXML
    public Label lblTopicId;
    @FXML
    public Button btnAddTopic;
    @FXML
    public Button btnTopicDetails;
    @FXML
    public Button btnModifyTopic;
    @FXML
    public Button btnDeleteTopic;
    @FXML
    public Label lblTopicCreationDate;
    @FXML
    public Label lblTopicAuthor;
    @FXML
    public Label lblTopicTitle;

    private ForumTopicController forumTopicController;
    private ForumTopic forumTopic;
    private Node node;
    private VBox pnItemsTopic;
    private AdminDashboardController dashboard;

    public void initData(ForumTopic topic, ForumTopicController forumTopicController, Node node, VBox pnItems, AdminDashboardController adminDashboardController) {
        this.dashboard = adminDashboardController;
        this.pnItemsTopic = pnItems;
        this.forumTopic = topic;
        this.node = node;
        this.forumTopicController = forumTopicController;
        lblTopicId.setText(String.valueOf(topic.getTopicId()));
        lblTopicAuthor.setText(topic.getAuthor().getName()+" "+topic.getAuthor().getLastName());
        lblTopicTitle.setText(topic.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = topic.getDateTime().format(formatter);
        lblTopicCreationDate.setText(formattedDate);
    }
}