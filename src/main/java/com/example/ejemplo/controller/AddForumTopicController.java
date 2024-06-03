package com.example.ejemplo.controller;

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

/**
 * Controlador para agregar un nuevo tema en el foro.
 */
public class AddForumTopicController {
    @FXML public Button btnCancel;
    @FXML public Button btnAccept;
    @FXML public ChoiceBox<String> ChoiceBoxAuthor;
    @FXML public TextArea txtAreaDescription;
    @FXML public TextField lblTopicTitle;

    public ForumTopicController forumTopicController;
    private ForumTopic forumTopic;

    /**
     * Inicializa los datos necesarios para la interfaz de usuario.
     */
    public void initData() {
        forumTopic = new ForumTopic();
        forumTopicController = new ForumTopicController();
        List<User> userlist;
        UserController userCtrl = new UserController();
        userlist = userCtrl.getAll();
        List<String> users = new ArrayList<>();
        for (User user : userlist) {
            users.add(user.getUserId() + "  " + user.getName() + " " + user.getLastName());
        }
        ChoiceBoxAuthor.getItems().addAll(users);
        ChoiceBoxAuthor.setValue("Select an Author");
    }

    /**
     * Maneja la acción de aceptar para agregar un nuevo tema en el foro.
     *
     * @param actionEvent el evento de acción
     */
    public void handleAccept(ActionEvent actionEvent) {
        UserController userController = new UserController();
        ForumCommentController forumCommentController = new ForumCommentController();

        // Verificar campos obligatorios
        if (Objects.equals(ChoiceBoxAuthor.getValue(), "Select an Author") || lblTopicTitle.getText().isEmpty() || txtAreaDescription.getText().isEmpty()) {
            showFieldError("All fields are required.");
            return;
        }

        String id = ChoiceBoxAuthor.getValue();
        String[] partes = id.split("\\s", 2);
        forumTopic.setAuthor(userController.getById(Long.valueOf(partes[0])));
       // forumTopic.setComments(forumCommentController.getCommentsByTopic(forumTopic.getTopicId()));
        forumTopic.setTitle(lblTopicTitle.getText());
        forumTopic.setDescription(txtAreaDescription.getText());
        forumTopic.setDateTime(LocalDateTime.now());

        try {
            if (forumTopicExists(forumTopic)) {
                showError("This forum topic already exists.");
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Create Forum Topic");
            alert.setContentText("Are you sure you want to create this Forum Topic?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                forumTopicController.createTopic(forumTopic);
                AdminDashboardController itemCtrl;
                itemCtrl = (AdminDashboardController) btnAccept.getScene().getWindow().getUserData();
                itemCtrl.refreshForumTopics();
            }
            ((Stage) btnAccept.getScene().getWindow()).close();
        } catch (SQLException e) {
            showError(Constants.DATABASE_ERROR + e.getMessage());
        }
    }

    /**
     * Verifica si el tema del foro ya existe.
     *
     * @param forumTopic el tema del foro a verificar
     * @return true si el tema del foro existe, false en caso contrario
     * @throws SQLException si ocurre un error al acceder a la base de datos
     */
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