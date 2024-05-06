package com.example.ejemplo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.ejemplo.model.User;

import java.util.Objects;

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

    /**
     * Maneja la acción de aceptar la modificación de usuario.
     * @param actionEvent Evento del botón de aceptar
     */
    public void handleAccept(ActionEvent actionEvent) {
        // Actualiza los datos del usuario con los valores de los campos
        //user.setRole(roleChoiceBox.getValue());
        user.setEmail(txtEmail.getText());
        user.setName(txtName.getText());
        user.setPassword(txtPassword.getText());
        user.setPhone(txtPhone.getText());
        User.Gender gender;
        if(genderChoiceBox.getValue()!=null){
            if(Objects.equals(genderChoiceBox.getValue(), "Male")){
                gender=User.Gender.MALE;
            }else {
                if (Objects.equals(genderChoiceBox.getValue(), "Female")) {
                    gender = User.Gender.FEMALE;
                } else {
                    if (Objects.equals(genderChoiceBox.getValue(), "Other")) {
                        gender = User.Gender.OTHER;
                    }
                }
            }
        }
        user.setLastName(txtApellidos.getText());
        user.setDni(txtDNI.getText());
        System.out.println(user);

        // Actualiza el usuario en la base de datos
        userController.update(user);
        // Actualiza la lista de usuarios en la interfaz de administrador
        updateItemAdminList();

        // Cierra la ventana de modificación
        ((Stage) btnAccept.getScene().getWindow()).close();
    }

    /**
     * Maneja la acción de cancelar la modificación de usuario.
     * @param actionEvent Evento del botón de cancelar
     */
    public void handleCancel(ActionEvent actionEvent) {
        // Cierra la ventana de modificación
        ((Stage) btnCancel.getScene().getWindow()).close();
    }

    /**
     * Inicializa los datos del usuario en la ventana de modificación.
     * @param user Usuario a modificar
     * @param userController Controlador de usuarios
     */
    public void initData(User user, UserController userController) {
        this.userController = userController;
        this.user = user;
        // Configura los valores iniciales de los campos con los datos del usuario
        if (user != null) {
            //roleChoiceBox.getItems().addAll(User.UserRole.values());
            //roleChoiceBox.getSelectionModel().select(user.getRole());
            genderChoiceBox.getItems().clear();
            genderChoiceBox.setValue(user.getGender());
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

    public User getUser() {
        return user;
    }
}
