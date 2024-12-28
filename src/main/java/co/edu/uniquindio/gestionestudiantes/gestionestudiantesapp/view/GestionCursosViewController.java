package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GestionCursosViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TableView<?> tableCursos;

    @FXML
    private TableColumn<?, ?> tcCodigo;

    @FXML
    private TableColumn<?, ?> tcNombreCurso;

    @FXML
    private TableColumn<?, ?> tcProfesor;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtFiltrar;

    @FXML
    private TextField txtNombreCurso;

    @FXML
    private TextField txtProfesor;

    @FXML
    void onActualizar(ActionEvent event) {

    }

    @FXML
    void onAgregar(ActionEvent event) {

    }

    @FXML
    void onEliminar(ActionEvent event) {

    }

    @FXML
    void onLimpiar(ActionEvent event) {

    }

    @FXML
    void initialize() {


    }

}
