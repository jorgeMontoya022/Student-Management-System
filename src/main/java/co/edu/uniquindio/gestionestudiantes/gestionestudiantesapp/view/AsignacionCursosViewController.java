package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AsignacionCursosViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAsignar;

    @FXML
    private Button btnEliminarCurso;

    @FXML
    private ComboBox<?> cbCursos;

    @FXML
    private Label lblEstudianteSeleccionado;

    @FXML
    private TableView<?> tableCursosAsignados;

    @FXML
    private TableView<?> tableEstudiantes;

    @FXML
    private TableColumn<?, ?> tcCodigo;

    @FXML
    private TableColumn<?, ?> tcCurso;

    @FXML
    private TableColumn<?, ?> tcDocumento;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcProfesor;

    @FXML
    private TextField txtBuscarEstudiante;

    @FXML
    void onAsignarCurso(ActionEvent event) {

    }

    @FXML
    void onEliminarCurso(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAsignar != null : "fx:id=\"btnAsignar\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert btnEliminarCurso != null : "fx:id=\"btnEliminarCurso\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert cbCursos != null : "fx:id=\"cbCursos\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert lblEstudianteSeleccionado != null : "fx:id=\"lblEstudianteSeleccionado\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert tableCursosAsignados != null : "fx:id=\"tableCursosAsignados\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert tableEstudiantes != null : "fx:id=\"tableEstudiantes\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert tcCodigo != null : "fx:id=\"tcCodigo\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert tcCurso != null : "fx:id=\"tcCurso\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert tcDocumento != null : "fx:id=\"tcDocumento\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert tcNombre != null : "fx:id=\"tcNombre\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert tcProfesor != null : "fx:id=\"tcProfesor\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";
        assert txtBuscarEstudiante != null : "fx:id=\"txtBuscarEstudiante\" was not injected: check your FXML file 'asignacion-cursos-view.fxml'.";

    }

}
