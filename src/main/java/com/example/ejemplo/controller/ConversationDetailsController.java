package com.example.ejemplo.controller;

import com.example.ejemplo.model.Conversation;
import com.example.ejemplo.model.Message;
import com.example.ejemplo.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Controlador para la vista de detalles de la conversación.
 */
public class ConversationDetailsController {
    @FXML public Label idUser1;
    @FXML public Label nameUser1;
    @FXML public Button btnBack;
    @FXML public Button btnDelete; // Botón eliminar
    @FXML public Button btnModify; // Botón modificar
    @FXML public Label idUser2;
    @FXML public Label nameUser2;
    @FXML public ListView<Message> listViewMessages;

    private ObservableList<Message> messages;

    private Conversation conver;
    public ConversationController converCtrl;
    private AdminDashboardController adminDashboardController;

    /**
     * Inicializa los datos de la conversación y la interfaz de usuario.
     *
     * @param conversation La conversación actual.
     * @param conversationController El controlador de la conversación.
     * @param adminDashboardController El controlador del panel de administrador.
     */
    public void initData(Conversation conversation, ConversationController conversationController, AdminDashboardController adminDashboardController) {
        this.converCtrl = conversationController;
        this.conver = conversation;
        this.adminDashboardController = adminDashboardController;
        UserController userController = new UserController();
        User user1 = userController.getById(conver.getUser1Id());
        User user2 = userController.getById(conver.getUser2Id());

        idUser1.setText(user1.getUserId().toString());
        idUser2.setText(user2.getUserId().toString());
        nameUser1.setText(user1.getName() + " " + user1.getLastName());
        nameUser2.setText(user2.getName() + " " + user2.getLastName());

        // Inicializa la lista de mensajes
        messages = FXCollections.observableArrayList();
        listViewMessages.setItems(messages);

        // Configurar celda personalizada en el ListView
        listViewMessages.setCellFactory(param -> new ListCell<Message>() {
            private VBox content;
            private TextFlow messageTextFlow;
            private Text userName;
            private Text messageContent;
            private Text dateTime;

            @Override
            protected void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);
                if (content == null) {
                    content = new VBox();
                    messageTextFlow = new TextFlow();
                    messageTextFlow.setPrefWidth(listViewMessages.getWidth() - 20);
                    userName = new Text();
                    messageContent = new Text();
                    dateTime = new Text();
                    content.getChildren().addAll(messageTextFlow, dateTime);
                }
                if (empty || message == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: white;"); // Fondo blanco para todas las celdas
                } else {
                    User sender = userController.getById(message.getSenderId());
                    userName.setText(sender.getName() + " " + sender.getLastName() + ": ");
                    if (message.getSenderId().equals(conver.getUser1Id())) {
                        userName.setStyle("-fx-fill: #2196f3; -fx-font-weight: bold;"); // Estilo para el primer usuario
                    } else {
                        userName.setStyle("-fx-fill: #029c5e; -fx-font-weight: bold;"); // Estilo para el segundo usuario
                    }
                    messageContent.setText(message.getContent());
                    messageContent.setStyle("-fx-fill: black;"); // Estilo para el contenido del mensaje
                    dateTime.setText(message.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    dateTime.setStyle("-fx-fill: gray; -fx-font-size: 10px;"); // Estilo para la fecha y hora

                    messageTextFlow.getChildren().setAll(userName, messageContent);

                    // Aplicar estilos a la celda
                    setStyle("-fx-background-color: white;"); // Fondo blanco para todas las celdas

                    // Cambiar el color de fondo de la celda seleccionada
                    if (listViewMessages.getSelectionModel().getSelectedItem() == message) {
                        setStyle("-fx-background-color: #e0f7fa;");
                    } else {
                        setStyle("-fx-background-color: white;");
                    }

                    listViewMessages.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection == message) {
                            setStyle("-fx-background-color: #e0f7fa;");
                        } else {
                            setStyle("-fx-background-color: white;");
                        }
                    });

                    setGraphic(content);
                }
            }
        });

        // Añade los mensajes de la conversación al ListView
        messages.addAll(conver.getMessages());

        // Añadir listener para manejar el mensaje seleccionado
        listViewMessages.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleSelectedMessage(newSelection);
            }
        });
    }

    /**
     * Maneja el mensaje seleccionado.
     *
     * @param selectedMessage El mensaje seleccionado.
     */
    private void handleSelectedMessage(Message selectedMessage) {
        // Aquí puedes manejar el mensaje seleccionado
        System.out.println("Mensaje seleccionado: " + selectedMessage.getContent());
    }

    /**
     * Maneja la acción de eliminar el mensaje seleccionado.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleDelete(ActionEvent actionEvent) {
        Message selectedMessage = listViewMessages.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Delete Message");
            alert.setContentText("Are you sure you want to delete this message?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Eliminar el mensaje del modelo de datos
                MessageController messageController = new MessageController();
                messageController.deleteMessage(selectedMessage.getMessageId());
                // Eliminar el mensaje de la lista
                messages.remove(selectedMessage);
                adminDashboardController.refresh();
            }
        }
    }

    /**
     * Añade un mensaje a la lista.
     *
     * @param message El mensaje a añadir.
     */
    private void addMessage(Message message) {
        messages.add(message);
        // Desplazarse automáticamente al final para ver el nuevo mensaje
        listViewMessages.scrollTo(messages.size() - 1);
    }
}
