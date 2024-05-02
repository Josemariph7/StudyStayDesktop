package com.example.ejemplo.utils;

public class Constants {
    // Rutas FXML

    public static final String ITEM_USER_LIST_FXML = "/com/example/ejemplo/fxml/ItemUserList.fxml";
    public static final String ITEM_FORUMTOPIC_LIST_FXML = "/com/example/ejemplo/fxml/ItemForumList.fxml";
    public static final String ITEM_ACCOMMODATION_LIST_FXML = "/com/example/ejemplo/fxml/ItemAccommodationList.fxml";
    public static final String LOGIN_FXML = "/com/example/ejemplo/fxml/LogIn.fxml";
    public static final String MODIFY_FXML = "/com/example/ejemplo/fxml/Modify.fxml";
    public static final String SIGNIN_FXML = "/com/example/ejemplo/fxml/SignIn.fxml";
    public static final String SIGNUP_FXML = "/com/example/ejemplo/fxml/SignUp.fxml";
    public static final String DASHBOARD_ADMIN_FXML = "/com/example/ejemplo/fxml/DashboardAdmin.fxml";
    public static final String SPLASH_SCREEN_FXML = "/com/example/ejemplo/fxml/SplashScreen.fxml";
    public static final String DEFAULT_PROFILE_PICTURE = "/com/example/ejemplo/multimedia/default.png";
    public static final String DEFAULT_ACCOMMODATION_PICTURE = "/com/example/ejemplo/multimedia/defaultaccommodation.jpg";

    // Mensajes de error
    public static final String FILE_NOT_FOUND_ERROR = "No se pudo encontrar el archivo fxml";
    public static final String LOAD_VIEW_ERROR = "Error al cargar la vista de inicio de sesión.";
    public static final String IMAGE_SAVE_ERROR = "Error saving the image.";
    public static final String DASHBOARD_LOAD_ERROR = "No se pudo cargar el panel de control. Inténtalo de nuevo más tarde.";
    public static final String USER_EXISTS_ERROR = "El usuario ya existe.";
    public static final String DATABASE_ERROR = "Error al registrar el usuario: ";

    // Patrones de validación
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    public static final String PHONE_REGEX = "^\\+34[0-9]{9}$";
    public static final String NAME_REGEX = "^[\\p{L}]+(?: [\\p{L}]+)+$";

    // Duraciones
    public static final double TRANSITION_TIME = 0.8;
}
