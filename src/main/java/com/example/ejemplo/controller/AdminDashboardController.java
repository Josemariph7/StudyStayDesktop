package com.example.ejemplo.controller;

import com.example.ejemplo.model.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.example.ejemplo.utils.Constants;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.ejemplo.utils.Constants.*;
import static java.time.LocalDate.now;

/**
 * Controlador para el panel de administrador.
 */
public class AdminDashboardController implements Initializable {

    @FXML
    public VBox pnItemsForum;
    @FXML
    public VBox pnAccommodationItems;
    @FXML
    public Label genrelabel;
    @FXML
    public Label txtBirthDate;
    @FXML
    public TextArea bioTextArea;
    @FXML
    public VBox pnConversationsItems;
    @FXML
    public Label NewMessages;
    @FXML
    public Label NewConversations;
    @FXML
    public Label TotalMessages;
    @FXML
    public Label TotalConversations;
    @FXML
    public Pane pnlConversations;
    @FXML
    public Label NewComments;
    @FXML
    public Label NewTopics;
    @FXML
    public Label TotalForumComments;
    @FXML
    public Label TotalForumTopics;
    @FXML
    public VBox pnBookingsItems;
    @FXML
    public Label bookingsLastWeek1;
    @FXML
    public Label averagePrice1;
    @FXML
    public Label currentBookings;
    @FXML
    public Label totalBookings;
    @FXML
    public Pane pnlBookings;
    @FXML
    public Label accommodationsLastWeek;
    @FXML
    public Label averagePrice;
    @FXML
    public Label availableAccommodations;
    @FXML
    public Label totalAccommodations;
    @FXML
    public Button btnAddUser;
    @FXML
    public Button btnConversations;
    @FXML
    public Button btnAboutUs;
    @FXML
    public Button btnBookings;
    @FXML
    public Pane pnlAboutUs;
    @FXML
    public Button btnAddConversation;
    @FXML
    public Button btnRefresh;
    @FXML
    public Button btnBackup;
    @FXML
    private Button btnExit;
    @FXML
    private Label namelabel;
    @FXML
    private Label idlabel;
    @FXML
    private Label passwordlabel;
    @FXML
    private Label datelabel;
    @FXML
    private Label emaillabel;
    @FXML
    private Label phonelabel;
    @FXML
    private Button btnChangePhoto;
    @FXML
    private Pane dragArea;
    @FXML
    private Label username;
    @FXML
    private VBox pnItems;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnUsers;
    @FXML
    private Button btnAccommodations;
    @FXML
    private Button btnForum;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlProfile;
    @FXML
    private Pane pnlUsers;
    @FXML
    private Pane pnlForum;
    @FXML
    private Pane pnlAccommodations;
    @FXML
    private Label totalusers;
    @FXML
    private Label totalclients;
    @FXML
    private Label totaladmins;
    @FXML
    private Label lastweek;
    @FXML
    private Circle circle;
    @FXML
    private Circle circleProfile;
    @FXML
    private VBox pnItemsForumTopics;

    // Otros atributos
    public User currentUser;
    private double xOffset = 0;
    private double yOffset = 0;
    private final UserController userController = new UserController();
    private final ForumTopicController topicController = new ForumTopicController();
    private final AccommodationController accommodationController = new AccommodationController();
    private final ConversationController conversationController = new ConversationController();
    private final BookingController bookingController = new BookingController();
    private final LocalDate oneWeekAgo = now().minusWeeks(1);

    /**
     * Inicializa el controlador.
     *
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
        refresh();
    }


    /**
     * Actualiza la interfaz de usuario y las estadísticas.
     */
    public void refresh() {
        pnItems.getChildren().clear();
        pnItemsForum.getChildren().clear();
        pnAccommodationItems.getChildren().clear();
        pnConversationsItems.getChildren().clear();
        pnBookingsItems.getChildren().clear();

        if (this.currentUser != null) {
            refreshProfile();
        }

        List<User> users = userController.getAll();
        for (User user : users) {
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

        List<Booking> bookings = bookingController.getAllBookings();
        for (Booking booking : bookings) {
            try {
                System.out.println(booking);
                FXMLLoader loaderBookings = new FXMLLoader(getClass().getResource(Constants.ITEM_BOOKING_LIST_FXML));
                Node node = loaderBookings.load();
                ItemBookingListController controller = loaderBookings.getController();
                controller.initData(booking, bookingController, node, pnBookingsItems, this);
                pnBookingsItems.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Obtener todos los alojamientos y actualizar estadísticas
        List<Accommodation> accommodations = accommodationController.getAllAccommodations();
        for (Accommodation acco : accommodations) {
            try {
                FXMLLoader loaderAccommodation = new FXMLLoader(getClass().getResource(Constants.ITEM_ACCOMMODATION_LIST_FXML));
                Node node = loaderAccommodation.load();
                ItemAccommodationListController controller = loaderAccommodation.getController();
                controller.initData(acco, accommodationController, node, pnAccommodationItems, this);
                pnAccommodationItems.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Obtener todas las conversaciones y actualizar estadísticas
        List<Conversation> conversations = conversationController.getAllConversations();
        for (Conversation conver : conversations) {
            try {
                FXMLLoader loaderConversations = new FXMLLoader(getClass().getResource(Constants.ITEM_CONVERSATION_LIST_FXML));
                Node node = loaderConversations.load();
                ItemConversationListController controller = loaderConversations.getController();
                controller.initData(conver, conversationController, node, pnConversationsItems, this);
                pnConversationsItems.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        updateUserStatistics();
        updateForumStatistics();
        updateConversationStatistics();
        updateBookingStatistics();
        updateAccommodationStatistics();
    }


    /**
     * Actualiza las estadísticas de conversaciones.
     */
    private void updateConversationStatistics() {
        List<Conversation> convers = conversationController.getAllConversations();
        MessageController messageController = new MessageController();
        List<Message> messages = messageController.getAllMessages();
        int totalConversations = convers.size();
        int totalMessages = messages.size();
        int averageMessagesConver;
        int newMessages = 0;
        if (messageController.getAllMessages() != null) {
            for (Message message : messages) {
                if (message.getDateTime().isAfter(oneWeekAgo.atStartOfDay())) {
                    newMessages++;
                }
            }
        }
        averageMessagesConver = totalMessages / totalConversations;
        TotalConversations.setText(String.valueOf(totalConversations));
        TotalMessages.setText(String.valueOf(totalMessages));
        NewConversations.setText(String.valueOf(averageMessagesConver));
        NewMessages.setText(String.valueOf(newMessages));
    }

    /**
     * Actualiza las estadísticas del foro.
     */
    private void updateForumStatistics() {
        List<ForumTopic> topics = topicController.getAllTopics();
        List<ForumComment> forumComments = new ForumCommentController().getAllComments();
        int totalForums = topics.size();
        int totalForumComments = forumComments.size();
        int commentsLastWeek = 0;
        int topicsLastWeek = 0;
        for (ForumComment comment : forumComments) {
            if (comment.getDateTime().isAfter(oneWeekAgo.atStartOfDay())) {
                commentsLastWeek++;
            }
        }
        for (ForumTopic topic : topics) {
            if (topic.getDateTime().isAfter(oneWeekAgo.atStartOfDay())) {
                topicsLastWeek++;
            }
        }
        this.NewTopics.setText(String.valueOf(topicsLastWeek));
        this.NewComments.setText(String.valueOf(commentsLastWeek));
        this.TotalForumComments.setText(String.valueOf(totalForumComments));
        this.TotalForumTopics.setText(String.valueOf(totalForums));
    }

    /**
     * Actualiza las estadísticas de reservas.
     */
    private void updateBookingStatistics() {
        int totalBookings = bookingController.getAllBookings().size();
        int currentBookings = 0;
        int pendingBookings = 0;
        int cancelledBookings = 0;
        double totalAveragedPrice = 0.0;
        for (Booking booking : bookingController.getAllBookings()) {
            if (booking.getStatus() == Booking.BookingStatus.CONFIRMED && booking.getEndDate().isAfter(LocalDateTime.now())) {
                currentBookings++;
            } else if (booking.getStatus() == Booking.BookingStatus.PENDING) {
                pendingBookings++;
            } else if (booking.getStatus() == Booking.BookingStatus.CANCELLED) {
                cancelledBookings++;
            }
        }
        this.totalBookings.setText(String.valueOf(totalBookings));
        this.currentBookings.setText(String.valueOf(currentBookings));
        this.averagePrice1.setText(String.valueOf(pendingBookings));
        this.bookingsLastWeek1.setText(String.valueOf(cancelledBookings));
    }

    /**
     * Actualiza las estadísticas de alojamientos.
     */
    private void updateAccommodationStatistics() {
        List<Accommodation> allAccommodations = accommodationController.getAllAccommodations();
        List<AccommodationReview> reviews = new AccommodationReviewController().getAllReviews();
        int totalAccommodations = allAccommodations.size();
        int totalReviews = reviews.size();
        int availableAccommodations = 0;
        BigDecimal totalPrices = new BigDecimal(0);

        for (Accommodation accommodation : allAccommodations) {
            if (accommodation.isAvailability()) {
                availableAccommodations++;
            }
            totalPrices = totalPrices.add(accommodation.getPrice());
        }

        BigDecimal averagePrice = totalAccommodations == 0 ? BigDecimal.ZERO : totalPrices.divide(BigDecimal.valueOf(totalAccommodations), 0, RoundingMode.HALF_UP);

        this.accommodationsLastWeek.setText(String.valueOf(totalReviews));
        this.averagePrice.setText(String.valueOf(averagePrice) + "€");
        this.availableAccommodations.setText(String.valueOf(availableAccommodations));
        this.totalAccommodations.setText(String.valueOf(totalAccommodations));
    }

    /**
     * Actualiza las estadísticas de usuarios.
     */
    public void updateUserStatistics() {
        List<User> usersAux = userController.getAll();
        int totalUsers = 0;
        int totalClients = 0;
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
     *
     * @param actionEvent El evento de acción.
     */
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnProfile) {
            pnlProfile.setVisible(true);
            pnlProfile.toFront();
            pnlUsers.setVisible(false);
            pnlAccommodations.setVisible(false);
            pnlForum.setVisible(false);
            pnlBookings.setVisible(false);
            pnlConversations.setVisible(false);
            pnlAboutUs.setVisible(false);
        }
        if (actionEvent.getSource() == btnUsers) {
            pnlUsers.setVisible(true);
            pnlUsers.toFront();
            pnlProfile.setVisible(false);
            pnlAccommodations.setVisible(false);
            pnlForum.setVisible(false);
            pnlBookings.setVisible(false);
            pnlConversations.setVisible(false);
            pnlAboutUs.setVisible(false);
        }
        if (actionEvent.getSource() == btnAccommodations) {
            pnlAccommodations.setVisible(true);
            pnlAccommodations.toFront();
            pnlProfile.setVisible(false);
            pnlUsers.setVisible(false);
            pnlForum.setVisible(false);
            pnlBookings.setVisible(false);
            pnlConversations.setVisible(false);
            pnlAboutUs.setVisible(false);
        }
        if (actionEvent.getSource() == btnBookings) {
            pnlBookings.setVisible(true);
            pnlBookings.toFront();
            pnlProfile.setVisible(false);
            pnlUsers.setVisible(false);
            pnlAccommodations.setVisible(false);
            pnlForum.setVisible(false);
            pnlConversations.setVisible(false);
            pnlAboutUs.setVisible(false);
        }
        if (actionEvent.getSource() == btnForum) {
            pnlForum.setVisible(true);
            pnlForum.toFront();
            pnlProfile.setVisible(false);
            pnlUsers.setVisible(false);
            pnlAccommodations.setVisible(false);
            pnlBookings.setVisible(false);
            pnlConversations.setVisible(false);
            pnlAboutUs.setVisible(false);
        }
        if (actionEvent.getSource() == btnConversations) {
            pnlConversations.setVisible(true);
            pnlConversations.toFront();
            pnlProfile.setVisible(false);
            pnlUsers.setVisible(false);
            pnlAccommodations.setVisible(false);
            pnlBookings.setVisible(false);
            pnlAboutUs.setVisible(false);
            pnlForum.setVisible(false);
        }
        if (actionEvent.getSource() == btnAboutUs) {
            pnlAboutUs.setVisible(true);
            pnlAboutUs.toFront();
            pnlProfile.setVisible(false);
            pnlUsers.setVisible(false);
            pnlAccommodations.setVisible(false);
            pnlBookings.setVisible(false);
            pnlConversations.setVisible(false);
            pnlForum.setVisible(false);
        }
    }

    /**
     * Realiza el cierre de sesión.
     *
     * @param actionEvent El evento del mouse.
     */
    public void signOut(MouseEvent actionEvent) {
        if (actionEvent.getSource() == btnSignout) {
            showAlertLogOutConfirmation();
        }
    }

    /**
     * Actualiza la información del perfil del usuario.
     */
    public void refreshProfile() {
        username.setText(currentUser.getName());
        namelabel.setText(currentUser.getName());
        idlabel.setText(String.valueOf(currentUser.getUserId()));
        passwordlabel.setText(currentUser.getPassword());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentUser.getRegistrationDate().format(formatter);
        datelabel.setText(formattedDate);
        emaillabel.setText(currentUser.getEmail());
        phonelabel.setText(currentUser.getPhone());

        if(currentUser.getGender() != null){
            if(currentUser.getGender().toString().equalsIgnoreCase("Male")){
                genrelabel.setText("Male");
            } else if (currentUser.getGender().toString().equalsIgnoreCase("Female")) {
                genrelabel.setText("Female");
            } else if (currentUser.getGender().toString().equalsIgnoreCase("Other")) {
                genrelabel.setText("Other");
            }
        }
    }

    /**
     * Inicializa los datos del usuario.
     *
     * @param user El usuario actual.
     */
    public void initData(User user) {

        // Cargar la imagen de perfil del usuario desde la base de datos
        this.currentUser = user;
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
        refresh();
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
            circleProfile.setStrokeWidth(4);
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
            AtomicReference<FXMLLoader> loader = new AtomicReference<>(new FXMLLoader(getClass().getResource(Constants.MODIFYUSER_FXML)));
            Parent root = loader.get().load();
            System.out.println("Usuario que se intenta modificar: " + currentUser);
            ModifyUserController modify = loader.get().getController();
            modify.initData(currentUser, userController, this);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            // Configurar el controlador actual como userData
            stage.setUserData(this);
            // Configurar el evento para el botón Cancelar
            ModifyUserController modifyController = loader.get().getController();
            modifyController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            modifyController.btnAccept.setOnAction(event -> {

                currentUser.setName(modifyController.txtName.getText());
                currentUser.setPhone(modifyController.txtPhone.getText());
                currentUser.setAdmin(modifyController.getUser().isAdmin());
                currentUser.setPassword(modifyController.txtPassword.getText());
                currentUser.setEmail(modifyController.txtEmail.getText());
                System.out.println(currentUser);
                username.setText(currentUser.getName());
                namelabel.setText(currentUser.getName());
                idlabel.setText(String.valueOf(currentUser.getUserId()));
                passwordlabel.setText(currentUser.getPassword());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDate = currentUser.getRegistrationDate().format(formatter);
                datelabel.setText(formattedDate);
                emaillabel.setText(currentUser.getEmail());
                phonelabel.setText(currentUser.getPhone());

                if(modifyController.genderChoiceBox.getValue() != null){
                    if(modifyController.genderChoiceBox.getValue().toString().equalsIgnoreCase("Male")){
                        currentUser.setGender(User.Gender.MALE);
                        genrelabel.setText("Male");
                    } else if (modifyController.genderChoiceBox.getValue().toString().equalsIgnoreCase("Female")) {
                        currentUser.setGender(User.Gender.FEMALE);
                        genrelabel.setText("Female");
                    } else if (modifyController.genderChoiceBox.getValue().toString().equalsIgnoreCase("Other")) {
                        currentUser.setGender(User.Gender.OTHER);
                        genrelabel.setText("Other");
                    }
                }
                System.out.println(currentUser);
                initData(currentUser);
                userController.update(currentUser);
                refresh();
                stage.close();
            });
            stage.show();
            refresh();
            initData(currentUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia la foto de perfil del usuario.
     *
     * @param event El evento del botón.
     */
    @FXML
    private void handleChangePhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        showAlertExitConfirmation();
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param message El mensaje de error.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de confirmación para salir de la aplicación.
     */
    @FXML
    private void showAlertExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Exit");
        alert.setContentText("Are you sure to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Muestra una alerta de confirmación para cerrar sesión.
     */
    @FXML
    private void showAlertLogOutConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("LogOut");
        alert.setContentText("Are you sure to logout?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
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
                showError(Constants.LOAD_VIEW_ERROR);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Abre la ventana para agregar un nuevo usuario.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    public void handleAddUser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ADDUSER_FXML));
            Parent root = loader.load();
            AddUserController add = loader.getController();
            add.initData();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            AddUserController addController = loader.getController();
            this.updateUserStatistics();
            addController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana para agregar una nueva conversación.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    public void handleAddConversation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ADDCONVERSATION_FXML));
            Parent root = loader.load();
            AddConversationController add = loader.getController();
            add.initData();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            AddConversationController addController = loader.getController();
            refresh();
            addController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana para agregar un nuevo tema en el foro.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    public void handleAddForumTopic(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ADDFORUMTOPIC_FXML));
            Parent root = loader.load();
            AddForumTopicController add = loader.getController();
            add.initData();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            AddForumTopicController addController = loader.getController();
            refresh();
            addController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana para agregar un nuevo alojamiento.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    public void handleAddAccommodation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ADDACCOMMODATION_FXML));
            Parent root = loader.load();
            AddAccommodationController add = loader.getController();
            add.initData();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            AddAccommodationController addController = loader.getController();
            refresh();
            addController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre la ventana para agregar una nueva reserva.
     *
     * @param actionEvent El evento de acción.
     */
    @FXML
    public void handleAddBooking(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ADDBOOKING_FXML));
            Parent root = loader.load();
            AddBookingController add = loader.getController();
            add.initData();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.setUserData(this);
            AddBookingController addController = loader.getController();
            refresh();
            addController.btnCancel.setOnAction(event -> {
                stage.close();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Minimiza la ventana.
     *
     * @param event El evento del mouse.
     */
    @FXML
    private void handleMinimize(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * Refresca la interfaz de usuario y muestra una alerta de información.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleRefresh(ActionEvent actionEvent) {
        refresh();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Refresh");
        alert.setHeaderText(null);
        alert.setContentText("Database reloaded");
        alert.show();
    }

    /**
     * Maneja la acción de respaldo de la base de datos.
     *
     * @param actionEvent El evento de acción.
     */
    public void handleBackup(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Select an option");
        alert.setHeaderText(null);
        alert.setContentText("What do you want to do?");

        ButtonType buttonImportar = new ButtonType("Import DB");
        ButtonType buttonExportar = new ButtonType("Export DB");
        ButtonType buttonCancelar = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(buttonImportar, buttonExportar, buttonCancelar);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == buttonImportar) {
                // Importar el archivo SQL
                importDB();
            } else if (result.get() == buttonExportar) {
                // Exportar la base de datos a un archivo SQL
                exportDB();
            } else {
                System.out.println("Operación cancelada.");
            }
        }
    }

    /**
     * Exporta la base de datos a un archivo SQL.
     */
    public void exportDB() {
        String dbName = "studystaydb";
        String dbUser = "admin";
        String dbPass = "admin";

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a directory to backup");
        Stage stage = (Stage) btnBackup.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
            String formattedDateTime = now.format(formatter);
            String backupPath = selectedDirectory.getAbsolutePath() + File.separator + formattedDateTime + ".sql";

            String mysqlDumpPath = "C:\\xampp\\mysql\\bin\\mysqldump";

            try {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command(mysqlDumpPath, "-u" + dbUser, "-p" + dbPass, dbName); // Opciones adicionales para eliminar comentarios
                processBuilder.redirectOutput(new File(backupPath));

                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Backup");
                    alert.setHeaderText(null);
                    alert.setContentText("Security DataBase copy generated in: " + backupPath);
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Backup");
                    alert.setHeaderText(null);
                    alert.setContentText("Error creating a backup. Output code: " + exitCode);
                    alert.show();
                }
                // removeCommentsFromFile(backupPath);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Importa un archivo SQL a la base de datos.
     */
    public void importDB() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the SQL file to import");

        // Configurar filtro para mostrar solo archivos .sql
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SQL files (*.sql)", "*.sql");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            Task<Void> importTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    String dbName = "studystaydb";
                    String dbUser = "admin";
                    String dbPass = "admin";
                    String mysqlPath = "C:\\xampp\\mysql\\bin\\mysql";

                    List<String> command = new ArrayList<>();
                    command.add(mysqlPath);
                    command.add("-u" + dbUser);
                    command.add("-p" + dbPass);
                    command.add(dbName);
                    command.add("-e");
                    command.add("source " + selectedFile.getAbsolutePath());

                    ProcessBuilder processBuilder = new ProcessBuilder(command);
                    processBuilder.redirectErrorStream(true);

                    Process process = processBuilder.start();
                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        updateMessage("Database imported successfully.");
                    } else {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        String line;
                        StringBuilder errorMessage = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            errorMessage.append(line).append("\n");
                        }
                        updateMessage("Error importing the database. Exit code: " + exitCode + "\n" + errorMessage.toString());
                    }
                    return null;
                }
            };

            importTask.setOnSucceeded(e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Import");
                alert.setHeaderText(null);
                alert.setContentText(importTask.getMessage());
                alert.show();
            });

            importTask.setOnFailed(e -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Import");
                alert.setHeaderText(null);
                alert.setContentText("Error importing the database.");
                alert.show();
            });

            new Thread(importTask).start();
        } else {
            System.out.println("File was not selected");
        }
    }
}
