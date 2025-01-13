package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionCursosController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Admin;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.session.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AsignacionCursosViewController extends CoreViewController {

    Admin loggedAdmin;
    ObservableList<EstudianteDto>listaEstudiantesDto = FXCollections.observableArrayList();
    EstudianteDto estudianteSeleccionado;

    GestionCursosController gestionCursosController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAsignar;

    @FXML
    private Button btnEliminarCurso;

    @FXML
    private ComboBox<CursoDto> cbCursos;

    @FXML
    private Label lblEstudianteSeleccionado;

    @FXML
    private TableView<CursoDto> tableCursosAsignados;

    @FXML
    private TableView<EstudianteDto> tableEstudiantes;

    @FXML
    private TableColumn<CursoDto, String> tcCodigo;

    @FXML
    private TableColumn<CursoDto, String> tcCurso;

    @FXML
    private TableColumn<EstudianteDto, String> tcDocumento;

    @FXML
    private TableColumn<EstudianteDto, String> tcNombre;

    @FXML
    private TableColumn<CursoDto, String> tcProfesor;

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
        gestionCursosController = new GestionCursosController();
        loggedAdmin = (Admin) Sesion.getInstance().getPersona();
        initView();

    }

    private void initView() {
        initDataBinding();
        getEstudiantes();
        tableEstudiantes.getItems().clear();
        tableEstudiantes.setItems(listaEstudiantesDto);
        cargarCursosCombobox();
    }


    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        tcProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreProfesor()));

    }

    private void getEstudiantes() {
        listaEstudiantesDto.clear();
        listaEstudiantesDto.addAll(gestionCursosController.getEstudiantes());
    }

    private void cargarCursosCombobox() {
        ObservableList<CursoDto> listaCursosDto = FXCollections.observableArrayList(gestionCursosController.getCursos());
        cbCursos.setItems(listaCursosDto);

        initializeComboBox(cbCursos, listaCursosDto, cursoDto -> cursoDto.nombre());
    }






}
