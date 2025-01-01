package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class WelcomeViewController extends CoreViewController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnLogin;

    @FXML
    void onLoginClick(ActionEvent event) {
        mostrarMensaje("Aplicación en Desarrollo", "¡Aviso Importante!", "Esta aplicación se encuentra en fase de desarrollo. Algunas funcionalidades pueden no estar disponibles o presentar comportamientos inesperados. Estamos trabajando para mejorar su experiencia. Gracias por su comprensión.",
                AlertType.WARNING);
        browseWindow("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/login-view.fxml", "Inicio de Sesión", event);
    }

    @FXML
    void initialize() {

    }
}