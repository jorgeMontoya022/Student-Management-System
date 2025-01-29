package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AdminContainerViewController extends CoreViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane asignacionView;

    @FXML
    private Button btnAsignacion;

    @FXML
    private Button btnCursos;

    @FXML
    private Button btnEstudiantes;

    @FXML
    private Button btnProfesores;

    @FXML
    private AnchorPane profesoresView;

    @FXML
    private AnchorPane cursosView;

    @FXML
    private AnchorPane estudiantesView;

    private static final String BUTTON_ACTIVE = "-fx-background-color: #303f9f; -fx-text-fill: white; -fx-alignment: CENTER_LEFT; -fx-padding: 15 20; -fx-pref-width: 250;";
    private static final String BUTTON_INACTIVE = "-fx-background-color: transparent; -fx-text-fill: white; -fx-alignment: CENTER_LEFT; -fx-padding: 15 20; -fx-pref-width: 250;";

    @FXML
    void onNavigateToAsignacion(ActionEvent event) {
        asignacionView.setVisible(true);
        cursosView.setVisible(false);
        estudiantesView.setVisible(false);
        profesoresView.setVisible(false);

        btnAsignacion.setStyle(BUTTON_ACTIVE);
        btnCursos.setStyle(BUTTON_INACTIVE);
        btnEstudiantes.setStyle(BUTTON_INACTIVE);
        btnProfesores.setStyle(BUTTON_INACTIVE);
    }

    @FXML
    void onNavigateToCursos(ActionEvent event) {
        cursosView.setVisible(true);
        estudiantesView.setVisible(false);
        asignacionView.setVisible(false);
        profesoresView.setVisible(false);

        btnCursos.setStyle(BUTTON_ACTIVE);
        btnAsignacion.setStyle(BUTTON_INACTIVE);
        btnEstudiantes.setStyle(BUTTON_INACTIVE);
        btnProfesores.setStyle(BUTTON_INACTIVE);
    }

    @FXML
    void onNavigateToEstudiantes(ActionEvent event) {
        estudiantesView.setVisible(true);
        cursosView.setVisible(false);
        asignacionView.setVisible(false);
        profesoresView.setVisible(false);

        btnEstudiantes.setStyle(BUTTON_ACTIVE);
        btnCursos.setStyle(BUTTON_INACTIVE);
        btnAsignacion.setStyle(BUTTON_INACTIVE);
        btnProfesores.setStyle(BUTTON_INACTIVE);
    }


    @FXML
    void onNavigateToProfesores(ActionEvent event) {
        profesoresView.setVisible(true);
        cursosView.setVisible(false);
        estudiantesView.setVisible(false);
        asignacionView.setVisible(false);

        btnProfesores.setStyle(BUTTON_ACTIVE);
        btnCursos.setStyle(BUTTON_INACTIVE);
        btnAsignacion.setStyle(BUTTON_INACTIVE);
        btnEstudiantes.setStyle(BUTTON_ACTIVE);


    }

    @FXML
    void onNavigateToHome(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/datos-admin-view.fxml", "Datos del Administrador", event);
    }

    @FXML
    void initialize() {
        // Establecer el estilo inicial de los botones
        btnEstudiantes.setStyle(BUTTON_ACTIVE);
        btnCursos.setStyle(BUTTON_INACTIVE);
        btnAsignacion.setStyle(BUTTON_INACTIVE);
        btnProfesores.setStyle(BUTTON_INACTIVE);
    }
}