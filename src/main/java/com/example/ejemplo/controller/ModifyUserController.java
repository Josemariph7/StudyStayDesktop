package com.example.ejemplo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.ejemplo.model.User;
import javafx.stage.StageStyle;

import java.util.Objects;
import java.util.Optional;

/**
 * Controlador para la ventana de modificación de usuarios.
 */
public class ModifyUserController {

    @FXML
    public TextField txtName;
    @FXML
    public TextField txtPhone;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtPassword;
    @FXML
    public Button btnAccept;
    @FXML
    public Button btnCancel;
    @FXML
    public TextField txtApellidos;
    @FXML
    public ChoiceBox genderChoiceBox;
    @FXML
    public TextField txtDNI;

    private User user;
    public UserController userController;
    private AdminDashboardController adminDashboardController;

    /**
     * Maneja la acción de aceptar la modificación de usuario.
     *
     * @param actionEvent Evento del botón de aceptar
     */
    public void handleAccept(ActionEvent actionEvent) {
        user.setEmail(txtEmail.getText());
        user.setName(txtName.getText());
        user.setPassword(txtPassword.getText());
        user.setPhone(txtPhone.getText());

        if(genderChoiceBox.getValue() != null) {
            if(genderChoiceBox.getValue().toString().equalsIgnoreCase("Male")) {
                user.setGender(User.Gender.MALE);
                if(Objects.equals(user.getUserId(), adminDashboardController.currentUser.getUserId()))
                    adminDashboardController.genrelabel.setText("Male");
            } else if(genderChoiceBox.getValue().toString().equalsIgnoreCase("Female")) {
                user.setGender(User.Gender.FEMALE);
                if(Objects.equals(user.getUserId(), adminDashboardController.currentUser.getUserId()))
                    adminDashboardController.genrelabel.setText("Female");
            } else if(genderChoiceBox.getValue().toString().equalsIgnoreCase("Other")) {
                user.setGender(User.Gender.OTHER);
                if(Objects.equals(user.getUserId(), adminDashboardController.currentUser.getUserId()))
                    adminDashboardController.genrelabel.setText("Other");
            }
        }

        user.setLastName(txtApellidos.getText());
        user.setDni(txtDNI.getText());
        System.out.println(user);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Modificar Usuario");
        alert.setContentText("¿Está seguro de que desea modificar este usuario?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            userController.update(user);
            updateItemAdminList();
        }

        // Cierra la ventana de modificación
        ((Stage) btnAccept.getScene().getWindow()).close();
    }

    /**
     * Maneja la acción de cancelar la modificación de usuario.
     *
     * @param actionEvent Evento del botón de cancelar
     */
    public void handleCancel(ActionEvent actionEvent) {
        // Cierra la ventana de modificación
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    /**
     * Inicializa los datos del usuario en la ventana de modificación.
     *
     * @param user Usuario a modificar
     * @param userController Controlador de usuarios
     * @param dashboard Controlador del panel de administrador
     */
    public void initData(User user, UserController userController, AdminDashboardController dashboard) {
        this.userController = userController;
        this.user = user;
        this.adminDashboardController = dashboard;

        // Configura los valores iniciales de los campos con los datos del usuario
        if(user != null) {
            String[] genders = {"Male", "Female", "Other"};
            genderChoiceBox.getItems().addAll(genders);
            genderChoiceBox.setValue("Gender");

            if(user.getGender() != null) {
                if(user.getGender().equals(User.Gender.MALE)) {
                    genderChoiceBox.setValue("Male");
                } else if(user.getGender().equals(User.Gender.FEMALE)) {
                    genderChoiceBox.setValue("Female");
                } else if(user.getGender().equals(User.Gender.OTHER)) {
                    genderChoiceBox.setValue("Other");
                }
            }

            txtName.setText(user.getName());
            txtEmail.setText(user.getEmail());
            txtPhone.setText(user.getPhone());
            txtPassword.setText(user.getPassword());
            txtApellidos.setText(user.getLastName());
            txtDNI.setText(user.getDni());
        }
    }

    /**
     * Actualiza la lista de usuarios en la interfaz de administrador después de la modificación.
     */
    private void updateItemAdminList() {
        // Obtiene el controlador de la lista de usuarios de la ventana de administrador
        ItemUserListController itemAdminListController = (ItemUserListController) btnAccept.getScene().getWindow().getUserData();
        // Actualiza los datos del usuario en la lista
        itemAdminListController.updateUserData(user);
    }

    /**
     * Obtiene el usuario.
     *
     * @return El usuario
     */
    public User getUser() {
        return user;
    }
}
