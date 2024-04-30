package com.example.ejemplo.controller;

import com.example.ejemplo.model.ForumTopic;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.example.ejemplo.model.User;
import com.example.ejemplo.utils.Constants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Controlador para el panel de administrador.
 */
public class AdminDashboardController implements Initializable {

    @FXML public VBox pnItemsForum;
    // Atributos de la interfaz gráfica
    @FXML private Button btnExit;
    @FXML private Label namelabel;
    @FXML private Label idlabel;
    @FXML private Label passwordlabel;
    @FXML private Label datelabel;
    @FXML private Label rolelabel;
    @FXML private Label emaillabel;
    @FXML private Label phonelabel;
    @FXML private Button btnChangePhoto;
    @FXML private Pane dragArea;
    @FXML private Label username;
    @FXML private VBox pnItems;
    @FXML private Button btnProfile;
    @FXML private Button btnUsers;
    @FXML private Button btnAccommodations;
    @FXML private Button btnForum;
    @FXML private Button btnSignout;
    @FXML private Pane pnlProfile;
    @FXML private Pane pnlUsers;
    @FXML private Pane pnlForum;
    @FXML private Pane pnlAccommodations;
    @FXML private Label totalusers;
    @FXML private Label totalclients;
    @FXML private Label totaladmins;
    @FXML private Label lastweek;
    @FXML private Circle circle;
    @FXML private Circle circleProfile;
    @FXML private VBox pnItemsForumTopics;

    // Otros atributos
    private User currentUser;
    private double xOffset = 0;
    private double yOffset = 0;
    private final UserController userController = new UserController();
    private final ForumTopicController topicController = new ForumTopicController();
    private final LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);

    /**
     * Inicializa el controlador.
     * @param location  La ubicación utilizada para resolver las rutas relativas para el objeto raíz.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar el arrastre de la ventana
        dragArea.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        dragArea.setOnMouseDragged(event -> {
            dragArea.getScene().getWindow().setX(event.getScreenX() - xOffset);
            dragArea.getScene().getWindow().setY(event.getScreenY() - yOffset);
        });
        dragArea.toFront();

        // Obtener todos los usuarios y actualizar estadísticas
        List<User> users = userController.getAll();
        for (User user : users) {
            updateStatistics();
            try {
                FXMLLoader loaderUsers = new FXMLLoader(getClass().getResource(Constants.ITEM_USER_LIST_FXML));
                Node node = loaderUsers.load();

                ItemUserListController controller = loaderUsers.getController();
                controller.initData(user, userController, node, pnItems, this);

                pnItems.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Obtener todos los temas del foro y actualizar estadísticas
        List<ForumTopic> topics = topicController.getAllTopics();
        for (ForumTopic topic : topics) {
            updateStatistics();
            try {
                FXMLLoader loaderForumTopics = new FXMLLoader(getClass().getResource(Constants.ITEM_FORUMTOPIC_LIST_FXML));
                Node node = loaderForumTopics.load();

                ItemForumListController controller = loaderForumTopics.getController();
                controller.initData(topic, topicController, node, pnItemsForum, this);

                pnItemsForum.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void updateForumStatistics() {
    }

    /**
     * Actualiza las estadísticas de usuarios.
     */
    public void updateStatistics() {
        List<User> usersAux = userController.getAll();
        int totalUsers = 0;
        int totalClients=0;
        int totalAdmins = 0;
        int registeredLastWeek = 0;

        for (User user : usersAux) {
            totalUsers++;
            if (!user.isAdmin()) {
                totalClients++;
            }
            if (user.isAdmin()) {
                totalAdmins++;
            }
            LocalDate registrationDate = user.getRegistrationDate().toLocalDate();
            if (registrationDate.isAfter(oneWeekAgo) || registrationDate.equals(oneWeekAgo)) {
                registeredLastWeek++;
            }
        }
        totalusers.setText(String.valueOf(totalUsers));
        totalclients.setText(String.valueOf(totalClients));
        totaladmins.setText(String.valueOf(totalAdmins));
        lastweek.setText(String.valueOf(registeredLastWeek));
    }

    /**
     * Gestiona los clics en los botones.
     * @param actionEvent El evento de acción.
     */
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnProfile) {
            pnlProfile.setVisible(true);
            pnlProfile.toFront();
            pnlUsers.setVisible(false);
            pnlAccommodations.setVisible(false);
            pnlForum.setVisible(false);
        }
        if(actionEvent.getSource()== btnUsers)
        {
            pnlUsers.setVisible(true);
            pnlUsers.toFront();
            pnlProfile.setVisible(false);
            pnlAccommodations.setVisible(false);
            pnlForum.setVisible(false);
        }
        if (actionEvent.getSource() == btnAccommodations) {
            pnlAccommodations.setVisible(true);
            pnlAccommodations.toFront();
            pnlProfile.setVisible(false);
            pnlUsers.setVisible(false);
            pnlForum.setVisible(false);
        }
        if (actionEvent.getSource() == btnForum) {
            pnlForum.setVisible(true);
            pnlForum.toFront();
            pnlProfile.setVisible(false);
            pnlUsers.setVisible(false);
            pnlAccommodations.setVisible(false);
        }
    }

    /**
     * Realiza el cierre de sesión.
     * @param actionEvent El evento del mouse.
     */
    public void signOut(MouseEvent actionEvent) {
        if (actionEvent.getSource() == btnSignout) {
            Stage stage = (Stage) btnSignout.getScene().getWindow();

            try {
                URL fxmlUrl = getClass().getResource(Constants.LOGIN_FXML);
                if (fxmlUrl == null) {
                    throw new IllegalArgumentException(Constants.FILE_NOT_FOUND_ERROR);
                }
                Parent root = FXMLLoader.load(fxmlUrl);
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showError(Constants.LOAD_VIEW_ERROR);
            }
        }
    }

    /**
     * Inicializa los datos del usuario.
     * @param user El usuario actual.
     */
    public void initData(User user) {
        this.currentUser = user;
        username.setText(currentUser.getName());
        namelabel.setText(currentUser.getName());
        idlabel.setText(String.valueOf(currentUser.getUserId()));
        rolelabel.setText(currentUser.isAdmin() ? "Administrator" : "Client");
        passwordlabel.setText(currentUser.getPassword());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = currentUser.getRegistrationDate().format(formatter);
        datelabel.setText(formattedDate);
        emaillabel.setText(currentUser.getEmail());
        phonelabel.setText(currentUser.getPhone());

        // Cargar la imagen de perfil del usuario desde la base de datos
        byte[] profilePictureBytes = currentUser.getProfilePicture();
        if (profilePictureBytes != null && profilePictureBytes.length > 0) {
            try {
                Image profilePicture = new Image(new ByteArrayInputStream(profilePictureBytes));

                circle.setFill(new ImagePattern(profilePicture));
                circleProfile.setFill(new ImagePattern(profilePicture));
                circleProfile.setStroke(Color.web("#151928"));
                circleProfile.setStrokeWidth(3);
            } catch (Exception e) {
                e.printStackTrace();
                cargarImagenPredeterminada(); // Cargar imagen predeterminada en caso de error
            }
        } else {
            cargarImagenPredeterminada(); // Cargar imagen predeterminada si los bytes son nulos o vacíos
        }
    }

    /**
     * Carga la imagen predeterminada del usuario.
     */
    private void cargarImagenPredeterminada() {
        String defaultImageUrl = Constants.DEFAULT_PROFILE_PICTURE;
        URL defaultResource = getClass().getResource(defaultImageUrl);
        if (defaultResource != null) {
            Image defaultProfilePicture = new Image(defaultResource.toExternalForm());
            circle.setFill(new ImagePattern(defaultProfilePicture));
            circleProfile.setFill(new ImagePattern(defaultProfilePicture));
            circleProfile.setStroke(Color.web("#151928"));
            circleProfile.setStrokeWidth(5);
        } else {
            System.out.println("No se pudo cargar la imagen predeterminada.");
        }
    }

    /**
     * Abre la ventana de modificación del usuario.
     */
    @FXML
    private void handleModify() {
        try {
            AtomicReference<FXMLLoader> loader = new AtomicReference<>(new FXMLLoader(getClass().getResource(Constants.MODIFY_FXML)));
            Parent root = loader.get().load();
            System.out.println("Usuario que se intenta modificar: "+currentUser);
            ModifyController modify = loader.get().getController();
            modify.initData(currentUser, userController);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            // Configurar el controlador actual como userData
            stage.setUserData(this);

            // Configurar el evento para el botón Cancelar
            ModifyController modifyController = loader.get().getController();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });

            modifyController.btnAccept.setOnAction(event -> {

                System.out.println(currentUser);
                currentUser.setName(modifyController.txtName.getText());
                currentUser.setPhone(modifyController.txtPhone.getText());
                currentUser.setAdmin(modifyController.getUser().isAdmin());
                currentUser.setPassword(modifyController.txtPassword.getText());
                currentUser.setEmail(modifyController.txtEmail.getText());
                System.out.println(currentUser);
                username.setText(currentUser.getName());
                namelabel.setText(currentUser.getName());
                idlabel.setText(String.valueOf(currentUser.getUserId()));
                if(currentUser.isAdmin()){
                    rolelabel.setText("Administrator");
                }else{
                    rolelabel.setText("Standard User");
                }
                passwordlabel.setText(currentUser.getPassword());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedDate = currentUser.getRegistrationDate().format(formatter);
                datelabel.setText(formattedDate);
                emaillabel.setText(currentUser.getEmail());
                phonelabel.setText(currentUser.getPhone());
                userController.update(currentUser);
                System.out.println("SE HA MODIFICADO: "+currentUser);
                List<User> users = userController.getAll();
                while(!pnItems.getChildren().isEmpty()) {
                    pnItems.getChildren().remove(0);
                }
                System.out.println(currentUser);
                initData(currentUser);
                System.out.println(currentUser);

                for (User user : users) {
                    updateStatistics();
                    try {
                        loader.set(new FXMLLoader(getClass().getResource(Constants.ITEM_USER_LIST_FXML)));
                        Node node = loader.get().load();
                        // Configurar el controlador del nodo
                        ItemUserListController controller = loader.get().getController();
                        controller.initData(user, userController, node, pnItems, this); // Pasa el usuario al controlador del nodo

                        pnItems.getChildren().add(node);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                stage.close();
            });
            stage.show();
            initData(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia la foto de perfil del usuario.
     * @param event El evento del botón.
     */
    @FXML
    private void handleChangePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                // Leer la imagen como un array de bytes
                byte[] imageBytes = Files.readAllBytes(file.toPath());

                // Actualizar la imagen en la interfaz gráfica
                Image image = new Image(new ByteArrayInputStream(imageBytes));
                circle.setFill(new ImagePattern(image));
                circleProfile.setFill(new ImagePattern(image));

                // Actualizar la imagen en el objeto de usuario (currentUser)
                currentUser.setProfilePicture(imageBytes);

                // Actualizar el usuario en la base de datos a través del controlador de usuario
                userController.update(currentUser);
                System.out.println(Arrays.toString(currentUser.getProfilePicture()));

            } catch (IOException e) {
                e.printStackTrace();
                showError(Constants.IMAGE_SAVE_ERROR);
            }
        }
    }


    /**
     * Cierra la aplicación.
     */
    @FXML
    private void closeApp() {
        System.exit(0);
    }

    /**
     * Muestra un mensaje de error.
     * @param message El mensaje de error.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}