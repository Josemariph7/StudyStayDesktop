package com.example.ejemplo.controller;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AddForumTopicController {
    @FXML public Button btnCancel;
    @FXML public Button btnAccept;
    @FXML public ChoiceBox ChoiceBoxAuthor;
    @FXML public TextArea txtAreaDescription;
    @FXML public TextField lblTopicTitle;

    public ForumTopicController forumTopicController;
    private ForumTopic forumTopic;

    public void initData() {
        forumTopic=new ForumTopic();
        forumTopicController=new ForumTopicController();
        List<User> userlist;
        UserController userCtrl = new UserController();
        userlist = userCtrl.getAll();
        List<String> users = new ArrayList<>();
        for (User user : userlist) {
            users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
        }
        ChoiceBoxAuthor.getItems().addAll(users);
        ChoiceBoxAuthor.setValue("Select an Author");
        lblTopicTitle.setText(forumTopic.getTitle());
        txtAreaDescription.setText(forumTopic.getDescription());
    }

    public void handleAccept(ActionEvent actionEvent) {
        UserController userController = new UserController();
        String id=ChoiceBoxAuthor.getValue().toString();
        String[] partes = id.split("\\s", 2);
        forumTopic.setAuthor(userController.getById(Long.valueOf(partes[0])));
        forumTopic.setComments(null);
        forumTopic.setTitle(lblTopicTitle.getText());
        forumTopic.setDescription(txtAreaDescription.getText());
        forumTopic.setDateTime(LocalDateTime.now());
        try {
            if (forumTopicExists(forumTopic)) {
                showError("This conversation already exists");
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Create Forum Topic");
            alert.setContentText("Are you sure to create this Forum Topic?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                forumTopicController.createTopic(forumTopic);
                AdminDashboardController itemCtrl;
                itemCtrl = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
                itemCtrl.refresh();
            }
            ((Stage) btnAccept.getScene().getWindow()).close();
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }

    private boolean forumTopicExists(ForumTopic forumTopic) throws SQLException {
        List<ForumTopic> topics = forumTopicController.getAllTopics();
        for (ForumTopic topic : topics) {
            if (topic.getTitle().equals(forumTopic.getTitle()) && topic.getDescription().equals(forumTopic.getDescription())) {
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
