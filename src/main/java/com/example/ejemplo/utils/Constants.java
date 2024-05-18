package com.example.ejemplo.utils;

public class Constants {
    // Rutas FXML

    public static final String ITEM_USER_LIST_FXML = "/com/example/ejemplo/fxml/ItemUserList.fxml";
    public static final String ITEM_FORUMTOPIC_LIST_FXML = "/com/example/ejemplo/fxml/ItemForumList.fxml";
    public static final String ITEM_ACCOMMODATION_LIST_FXML = "/com/example/ejemplo/fxml/ItemAccommodationList.fxml";
    public static final String ITEM_CONVERSATION_LIST_FXML = "/com/example/ejemplo/fxml/ItemConversationList.fxml";
    public static final String ITEM_BOOKING_LIST_FXML = "/com/example/ejemplo/fxml/ItemBookingList.fxml";
    public static final String LOGIN_FXML = "/com/example/ejemplo/fxml/LogIn.fxml";
    public static final String MODIFYUSER_FXML = "/com/example/ejemplo/fxml/ModifyUser.fxml";
    public static final String ADDUSER_FXML = "/com/example/ejemplo/fxml/AddUser.fxml";
    public static final String ADDCONVERSATION_FXML = "/com/example/ejemplo/fxml/AddConversation.fxml";
    public static final String ADDFORUMTOPIC_FXML = "/com/example/ejemplo/fxml/AddForumTopic.fxml";
    public static final String ADDBOOKING_FXML = "/com/example/ejemplo/fxml/AddBooking.fxml";
    public static final String ADDACCOMMODATION_FXML = "/com/example/ejemplo/fxml/AddAccommodation.fxml";

    public static final String MODIFYCONVERSATION_FXML = "/com/example/ejemplo/fxml/ModifyConversation.fxml";
    public static final String MODIFYFORUMTOPIC_FXML = "/com/example/ejemplo/fxml/ModifyForumTopic.fxml";
    public static final String DETAILSCONVERSATION_FXML = "/com/example/ejemplo/fxml/ConversationDetails.fxml";
    public static final String DETAILSFORUM_FXML = "/com/example/ejemplo/fxml/ForumDetails.fxml";
    public static final String MODIFYBOOKING_FXML = "/com/example/ejemplo/fxml/ModifyBooking.fxml";
    public static final String SIGNIN_FXML = "/com/example/ejemplo/fxml/SignIn.fxml";
    public static final String SIGNUP_FXML = "/com/example/ejemplo/fxml/SignUp.fxml";
    public static final String DASHBOARD_ADMIN_FXML = "/com/example/ejemplo/fxml/DashboardAdmin.fxml";
    public static final String SPLASH_SCREEN_FXML = "/com/example/ejemplo/fxml/SplashScreen.fxml";
    public static final String DEFAULT_PROFILE_PICTURE = "/com/example/ejemplo/multimedia/default.png";
    public static final String DEFAULT_ACCOMMODATION_PICTURE = "/com/example/ejemplo/multimedia/defaultaccommodation.jpg";

    // Mensajes de error
    public static final String FILE_NOT_FOUND_ERROR = "Program cant load fxml";
    public static final String LOAD_VIEW_ERROR = "Error loading login";
    public static final String IMAGE_SAVE_ERROR = "Error saving the image.";
    public static final String DASHBOARD_LOAD_ERROR = "Error loading Dashboard";
    public static final String USER_EXISTS_ERROR = "This user already exists.";
    public static final String DATABASE_ERROR = "Error loading Database: ";
    public static final String ADMIN_ERROR = "Admin code is not correct";
    public static final String EMPTY_CODE = "To register an administrator you need an admin code";

    // Patrones de validación
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    public static final String PHONE_REGEX = "^\\[0-9]{9}$";
    public static final String NAME_REGEX = "^[\\p{L}]+(?: [\\p{L}]+)+$";

    // Duraciones
    public static final double TRANSITION_TIME = 0.8;
    public static final String ADMINCODE = "q#uT7D&9*2@L$p5!eFxJg";

}
