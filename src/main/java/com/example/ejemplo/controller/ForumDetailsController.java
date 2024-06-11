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

import com.example.ejemplo.model.ForumTopic;
import com.example.ejemplo.model.ForumComment;
import com.example.ejemplo.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

public class ForumDetailsController {

    @FXML public Button btnBack;
    @FXML public Label lblAuthor;
    @FXML public Label lblTitle;
    @FXML public Label lblCreationDate;
    @FXML public TextArea txtAreaDescription;
    @FXML public ListView<ForumComment> listViewComments;

    private ObservableList<ForumComment> comments;

    private ForumTopicController forumTopicController;
    private ForumTopic forumTopic;
    private AdminDashboardController adminDashboardController;
    private Random random = new Random();

    private static final String[] COLORS = {
            "#FF5733", "#33FF57", "#3357FF", "#FF33A1", "#A133FF", "#33FFD5",
            "#FFD733", "#C70039", "#900C3F", "#581845"
    };

    public void initData(ForumTopic forumTopic, ForumTopicController forumTopicController, AdminDashboardController dashboard) {
        this.forumTopicController = forumTopicController;
        this.forumTopic = forumTopic;
        this.adminDashboardController = dashboard;
        lblAuthor.setText(forumTopic.getAuthor().getName() + " " + forumTopic.getAuthor().getLastName());
        lblTitle.setText(forumTopic.getTitle());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = forumTopic.getDateTime().format(formatter);
        lblCreationDate.setText(formattedDate);
        txtAreaDescription.setText(forumTopic.getDescription());

        // Inicializa la lista de comentarios
        comments = FXCollections.observableArrayList();
        listViewComments.setItems(comments);

        // Configurar celda personalizada en el ListView
        listViewComments.setCellFactory(param -> new ListCell<ForumComment>() {
            private VBox content;
            private Text userName;
            private Text commentContent;
            private Text dateTime;
            private Separator separator;

            @Override
            protected void updateItem(ForumComment comment, boolean empty) {
                super.updateItem(comment, empty);
                if (content == null) {
                    content = new VBox();
                    userName = new Text();
                    commentContent = new Text();
                    commentContent.wrappingWidthProperty().bind(listViewComments.widthProperty().subtract(20)); // Wrap del texto
                    dateTime = new Text();
                    separator = new Separator();
                    content.getChildren().addAll(userName, commentContent, dateTime, separator);
                }
                if (empty || comment == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: white;"); // Fondo blanco para todas las celdas
                } else {
                    UserController userController = new UserController();
                    User author = userController.getById(comment.getAuthor().getUserId());
                    userName.setText(author.getName() + " " + author.getLastName());
                    String color = getRandomColor();
                    userName.setStyle("-fx-fill: " + color + "; -fx-font-weight: bold;"); // Estilo para el nombre del usuario
                    commentContent.setText(comment.getContent());
                    commentContent.setStyle("-fx-fill: black;"); // Estilo para el contenido del comentario
                    dateTime.setText(comment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    dateTime.setStyle("-fx-fill: gray; -fx-font-size: 10px;"); // Estilo para la fecha y hora

                    // Limpiar y agregar de nuevo los nodos para asegurar el orden correcto
                    content.getChildren().clear();
                    content.getChildren().addAll(userName, commentContent, dateTime, separator);

                    // Aplicar estilos a la celda
                    setStyle("-fx-background-color: white;"); // Fondo blanco para todas las celdas

                    // Cambiar el color de fondo de la celda seleccionada
                    if (listViewComments.getSelectionModel().getSelectedItem() == comment) {
                        setStyle("-fx-background-color: #e0f7fa;");
                    } else {
                        setStyle("-fx-background-color: white;");
                    }

                    listViewComments.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection == comment) {
                            setStyle("-fx-background-color: #e0f7fa;");
                        } else {
                            setStyle("-fx-background-color: white;");
                        }
                    });
                    setGraphic(content);
                }
            }
        });

        // Añade los comentarios del tema al ListView
        ForumCommentController forumCommentController = new ForumCommentController();
        comments.addAll(forumCommentController.getCommentsByTopic(forumTopic.getTopicId()));

        // Añadir listener para manejar el comentario seleccionado
        listViewComments.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleSelectedComment(newSelection);
            }
        });
    }

    // Método para obtener un color aleatorio
    private String getRandomColor() {
        return COLORS[random.nextInt(COLORS.length)];
    }

    // Método para manejar el comentario seleccionado
    private void handleSelectedComment(ForumComment selectedComment) {
        // Aquí puedes manejar el comentario seleccionado
        System.out.println("Comentario seleccionado: " + selectedComment.getContent());
    }

    // Método para eliminar el comentario seleccionado
    public void handleDelete(ActionEvent actionEvent) {
        ForumComment selectedComment = listViewComments.getSelectionModel().getSelectedItem();
        if (selectedComment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Delete Comment");
            alert.setContentText("Are you sure you want to delete this comment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Eliminar el comentario del modelo de datos
                ForumCommentController commentController = new ForumCommentController();
                commentController.deleteComment(selectedComment.getCommentId());
                // Eliminar el comentario de la lista
                comments.remove(selectedComment);
                adminDashboardController.refreshForumTopics();
            }
        }
    }
}

