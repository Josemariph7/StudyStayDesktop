package com.example.ejemplo.test;
import com.example.ejemplo.controller.AdminDashboardController;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AdminDashboardControllerTest {

    @BeforeClass
    public static void initToolkit() {
        // Inicializamos JavaFX Toolkit para que la GUI pueda funcionar en los tests
        new JFXPanel();
    }

    @Test
    public void testLoadAdminDashboard() {
        try {
            // Cargamos el archivo FXML y obtenemos el controlador
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminDashboard.fxml"));
            Parent root = loader.load();
            AdminDashboardController controller = loader.getController();

            // Creamos una escena y una etapa para mostrar la GUI
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            // Verificamos que el controlador y la escena se hayan creado correctamente
            assertNotNull(controller);
            assertNotNull(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
