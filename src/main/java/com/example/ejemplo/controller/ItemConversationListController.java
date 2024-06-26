/*
 * StudyStay © 2024
 *
 * All rights reserved.
 *
 * This software and associated documentation files (the "Software") are owned by StudyStay. Unauthorized copying, distribution, or modification of this Software is strictly prohibited.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this Software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * StudyStay
 * José María Pozo Hidalgo
 * Email: josemariph7@gmail.com
 *
 *
 */

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la vista de elementos de la lista de conversaciones.
 */
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

    /**
     * Inicializa los datos de la conversación y la interfaz de usuario.
     *
     * @param conver La conversación.
     * @param conversationController El controlador de la conversación.
     * @param node El nodo actual.
     * @param pnItems El VBox de los elementos de conversación.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(Conversation conver, ConversationController conversationController, Node node, VBox pnItems, AdminDashboardController adminDashboardController) {
        this.adminDashboardController = adminDashboardController;
        this.pnItemsConver = pnItems;
        this.conversation = conver;
        this.node = node;
        this.conversationController = conversationController;
        lblConversationId.setText(String.valueOf(conver.getConversationId()));
        if (conver.getUser1Id() != null && conver.getUser2Id() != null) {
            UserController userController = new UserController();
            lblConversationUser1.setText(userController.getById(conver.getUser1Id()).getName() + " " + userController.getById(conver.getUser1Id()).getLastName());
            lblConversationUser2.setText(userController.getById(conver.getUser2Id()).getName() + " " + userController.getById(conver.getUser2Id()).getLastName());
        }
        MessageController messageController = new MessageController();
        List<Message> messages = messageController.getMessagesByConversation(conver.getConversationId());
        lblNumberMessages.setText(String.valueOf(messages.size()) + " Messages in the Conversation");
    }

    /**
     * Maneja la acción de modificar la conversación.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleModify(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.MODIFYCONVERSATION_FXML));
            Parent root = loader.load();
            ModifyConversationController modify = loader.getController();
            modify.initData(conversation, conversationController, adminDashboardController);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ModifyConversationController modifyController = loader.getController();
            adminDashboardController.refreshConversations();
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
     * @param conver La conversación modificada.
     */
    public void updateConversationData(Conversation conver) {
        this.conversation = conver;
        lblConversationId.setText(String.valueOf(conver.getConversationId()));
        if (conver.getUser1Id() != null && conver.getUser2Id() != null) {
            UserController userController = new UserController();
            lblConversationUser1.setText(userController.getById(conver.getUser1Id()).getName() + " " + userController.getById(conver.getUser1Id()).getLastName());
            lblConversationUser2.setText(userController.getById(conver.getUser2Id()).getName() + " " + userController.getById(conver.getUser2Id()).getLastName());
        }
        MessageController messageController = new MessageController();
        List<Message> messages = messageController.getMessagesByConversation(conver.getConversationId());
        lblNumberMessages.setText(String.valueOf(messages.size()) + " Messages in the Conversation");
    }

    /**
     * Maneja la acción de eliminar la conversación.
     *
     * @param actionEvent El evento de acción.
     */
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
            adminDashboardController.refreshConversations();
        }
    }

    /**
     * Maneja la acción de mostrar los detalles de la conversación.
     *
     * @param actionEvent El evento del mouse.
     */
    public void handleDetails(MouseEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.DETAILSCONVERSATION_FXML));
            Parent root = loader.load();
            ConversationDetailsController details = loader.getController();
            details.initData(conversation, conversationController, adminDashboardController);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            ConversationDetailsController detailsController = loader.getController();
            adminDashboardController.refreshConversations();
            detailsController.btnBack.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
