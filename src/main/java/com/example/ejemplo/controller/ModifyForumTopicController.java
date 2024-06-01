package com.example.ejemplo.controller;

import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.User;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para modificar un tema del foro.
 */
public class ModifyForumTopicController {
    public Button btnCancel;
    public Button btnAccept;
    public ChoiceBox ChoiceBoxTopicAuthor;
    public TextArea txtAreaTopicDescription;
    public TextField lblForumTopicTitle;

    public ForumTopicController forumTopicController;
    private ForumTopic forumTopic;
    private AdminDashboardController adminDashboardController;

    /**
     * Maneja el evento de aceptación para modificar el tema del foro.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleAccept(ActionEvent actionEvent) {
        User user1 = new User();
        UserController userController = new UserController();
        String[] partes = ChoiceBoxTopicAuthor.getValue().toString().split("\\s", 2);
        forumTopic.setAuthor(userController.getById(Long.valueOf(partes[0])));
        forumTopic.setDescription(txtAreaTopicDescription.getText());
        forumTopic.setTitle(lblForumTopicTitle.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Modify Forum Topic");
        alert.setContentText("Are you sure to modify this Forum Topic?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            forumTopicController.updateTopic(forumTopic);
            ItemForumListController itemCtrl;
            itemCtrl = (ItemForumListController) btnAccept.getScene().getWindow().getUserData();
            itemCtrl.updateForumTopicData(forumTopic);
            adminDashboardController.refreshForumTopics();
        }
        ((Stage) btnAccept.getScene().getWindow()).close();
    }

    /**
     * Inicializa los datos del tema del foro en la ventana de modificación.
     *
     * @param forumTopic                El tema del foro a modificar.
     * @param forumTopicController      El controlador de temas del foro.
     * @param dashboard  El controlador del panel de administrador.
     */
    public void initData(ForumTopic forumTopic, ForumTopicController forumTopicController, AdminDashboardController dashboard) {
        this.forumTopic = forumTopic;
        this.forumTopicController = forumTopicController;
        this.adminDashboardController = dashboard;
        List<User> userlist;
        UserController userCtrl = new UserController();
        userlist = userCtrl.getAll();
        List<String> users = new ArrayList<>();
        for (User user : userlist) {
            users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
        }
        String author = forumTopic.getAuthor().getUserId() + "  " + forumTopic.getAuthor().getName() + " " + forumTopic.getAuthor().getLastName();
        ChoiceBoxTopicAuthor.getItems().addAll(users);
        ChoiceBoxTopicAuthor.setValue(author);
        lblForumTopicTitle.setText(forumTopic.getTitle());
        txtAreaTopicDescription.setText(forumTopic.getDescription());
    }
}
