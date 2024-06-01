package com.example.ejemplo.controller;

import com.example.ejemplo.model.ForumTopic;
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
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Controlador para la vista de elementos de la lista de temas del foro.
 */
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

    /**
     * Inicializa los datos del tema del foro y la interfaz de usuario.
     *
     * @param topic El tema del foro.
     * @param forumTopicController El controlador del tema del foro.
     * @param node El nodo actual.
     * @param pnItems El VBox de los elementos del tema del foro.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(ForumTopic topic, ForumTopicController forumTopicController, Node node, VBox pnItems, AdminDashboardController adminDashboardController) {
        this.dashboard = adminDashboardController;
        this.pnItemsTopic = pnItems;
        this.forumTopic = topic;
        this.node = node;
        this.forumTopicController = forumTopicController;
        lblTopicId.setText(String.valueOf(topic.getTopicId()));
        if (topic.getAuthor() != null) {
            lblTopicAuthor.setText(topic.getAuthor().getName() + " " + topic.getAuthor().getLastName());
        } else {
            forumTopicController.deleteTopic(topic.getTopicId());
        }
        lblTopicTitle.setText(topic.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = topic.getDateTime().format(formatter);
        lblTopicCreationDate.setText(formattedDate);
    }

    /**
     * Maneja la acción de modificar el tema del foro.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleModify(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MODIFYFORUMTOPIC_FXML));
            Parent root = loader.load();
            ModifyForumTopicController modify = loader.getController();
            modify.initData(forumTopic, forumTopicController, dashboard);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ModifyForumTopicController modifyController = loader.getController();
            dashboard.refreshForumTopics();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza los datos del tema del foro después de la modificación.
     *
     * @param topic El tema del foro modificado.
     */
    public void updateForumTopicData(ForumTopic topic) {
        this.forumTopic = topic;
        lblTopicTitle.setText(String.valueOf(forumTopic.getTitle()));
        lblTopicAuthor.setText(forumTopic.getAuthor().getName() + " " + forumTopic.getAuthor().getLastName());
        lblTopicCreationDate.setText(forumTopic.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    /**
     * Maneja la acción de eliminar el tema del foro.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Delete Forum Topic");
        alert.setContentText("Are you sure you want to delete this Forum Topic?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            forumTopicController.deleteTopic(forumTopic.getTopicId());
            int index = pnItemsTopic.getChildren().indexOf(node);
            if (index != -1) {
                pnItemsTopic.getChildren().remove(index);
            } else {
                System.out.println("El nodo no se encontró en el VBox.");
            }
            dashboard.refreshForumTopics();
        }
    }

    /**
     * Maneja la acción de mostrar los detalles del tema del foro.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleDetails(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.DETAILSFORUM_FXML));
            Parent root = loader.load();
            ForumDetailsController details = loader.getController();
            details.initData(forumTopic, forumTopicController, dashboard);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ForumDetailsController detailsController = loader.getController();
            detailsController.btnBack.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
