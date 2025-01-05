package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AdminContainerViewController {

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
    private AnchorPane cursosView;

    @FXML
    private AnchorPane estudiantesView;

    @FXML
    void onNavigateToAsignacion(ActionEvent event) {
        asignacionView.setVisible(true);
        cursosView.setVisible(false);
        estudiantesView.setVisible(false);

        // Estilos de los botones
        btnAsignacion.setStyle("-fx-background-color: #303f9f; -fx-text-fill: white;");
        btnCursos.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        btnEstudiantes.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
    }

    @FXML
    void onNavigateToCursos(ActionEvent event) {
        cursosView.setVisible(true);
        estudiantesView.setVisible(false);
        asignacionView.setVisible(false);

        // Estilos de los botones
        btnCursos.setStyle("-fx-background-color: #303f9f; -fx-text-fill: white;");
        btnAsignacion.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        btnEstudiantes.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
    }


    @FXML
    void onNavigateToEstudiantes(ActionEvent event) {
        estudiantesView.setVisible(true);
        cursosView.setVisible(false);
        asignacionView.setVisible(false);

        // Actualiza los estilos de los botones
        btnEstudiantes.setStyle("-fx-background-color: #303f9f; -fx-text-fill: white;");
        btnCursos.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        btnAsignacion.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");


    }

    @FXML
    void initialize() {


    }

}
