package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.DatosEstudianteController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Estudiante;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.session.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class DatosEstudianteViewController extends CoreViewController{

    Estudiante person;
    DatosEstudianteController datosEstudianteController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblId;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    void onDashboardClick(ActionEvent event) {
        mostrarMensaje("Error", "Error al iniciar en el Dashboard",
                "Actualmente estamos realizando mejoras significativas en la experiencia de usuario. Esto puede provocar ciertos inconvenientes temporales en la funcionalidad de la plataforma. Le pedimos disculpas por las molestias ocasionadas y le agradecemos su comprensión y paciencia mientras resolvemos este inconveniente.", Alert.AlertType.ERROR);

    }

    @FXML
     void onLogoutClick(ActionEvent event) {
      Sesion.getInstance().closeSesion();
      browseWindow("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/login-view.fxml", "Inicio de Sesión", event);
    }

    @FXML
    void initialize() {
        person = (Estudiante) Sesion.getInstance().getPersona();
        datosEstudianteController = new DatosEstudianteController();
        initView();

    }

    private void initView() {
        mostrarInformacion();
    }

    private void mostrarInformacion() {
        lblNombre.setText(person.getNombre());
        lblCorreo.setText(person.getCorreo());
        lblId.setText(person.getId());
        lblTelefono.setText(person.getTelefono());
    }

}