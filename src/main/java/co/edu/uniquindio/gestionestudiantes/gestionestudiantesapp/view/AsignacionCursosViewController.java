package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionCursosController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Admin;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.session.Sesion;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view.observer.EventType;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view.observer.ObserverManagement;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view.observer.ObserverView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AsignacionCursosViewController extends CoreViewController implements ObserverView {

    Admin loggedAdmin;
    ObservableList<EstudianteDto> listaEstudiantesDto = FXCollections.observableArrayList();
    ObservableList<CursoDto> listaCursosAsignadosDto = FXCollections.observableArrayList();
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
        CursoDto cursoDto = buildCursoDto();
        if(cursoDto == null) {
            mostrarMensaje("Error", "Datos no válidos", "No se ha seleccionado un curso válido", Alert.AlertType.ERROR);
            return;
        }

        if(validarDatosAsignacion(cursoDto)){
            if(gestionCursosController.asignarCursoEstudiante(estudianteSeleccionado.id(), cursoDto.codigo())) {
                listaCursosAsignadosDto.add(cursoDto);
                mostrarMensaje("Notificación", "Curso asignado",
                        "El curso ha sido asignado con éxito", Alert.AlertType.INFORMATION);
                ObserverManagement.getInstance().notifyObservers(EventType.CURSO);
                cbCursos.setValue(null);
            } else {
                mostrarMensaje("Error", "Curso no asignado",
                        "El curso no pudo ser asignado", Alert.AlertType.ERROR);
            }
        }
    }

    private CursoDto buildCursoDto() {
        return cbCursos.getValue();
    }


    private boolean validarDatosAsignacion(CursoDto cursoDto) {
        String mensaje = "";

        if(estudianteSeleccionado == null) {
            mensaje += "Debe seleccionar un estudiante\n";
        }

        if(cursoDto == null) {
            mensaje += "Debe seleccionar un curso\n";
        }

        // Modificar esta validación para usar el controlador
        if(gestionCursosController.tieneCursoAsignado(estudianteSeleccionado.id(), cursoDto.codigo())) {
            mensaje += "El estudiante ya tiene este curso asignado\n";
        }

        if(mensaje.isEmpty()) {
            return true;
        } else {
            mostrarMensaje("Error", "Datos no válidos", mensaje, Alert.AlertType.ERROR);
            return false;
        }
    }

    @FXML
    void onEliminarCurso(ActionEvent event) {

    }

    @FXML
    void initialize() {
        gestionCursosController = new GestionCursosController();
        loggedAdmin = (Admin) Sesion.getInstance().getPersona();
        initView();
        ObserverManagement.getInstance().addObserver(EventType.CURSO, this);
        ObserverManagement.getInstance().addObserver(EventType.ESTUDIANTE, this);
        setupFilter();

    }


    private void initView() {
        initDataBinding();
        initializeTableListeners();
        getEstudiantes();
        tableEstudiantes.getItems().clear();
        tableEstudiantes.setItems(listaEstudiantesDto);
        tableCursosAsignados.getItems().clear();
        tableCursosAsignados.setItems(listaCursosAsignadosDto);
        cargarCursosCombobox();
    }


    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        tcProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().profesor().nombre()));

    }

    private void initializeTableListeners() {
        tableEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                estudianteSeleccionado = newSelection;
                lblEstudianteSeleccionado.setText("Estudiante: " + estudianteSeleccionado.nombre());
                actualizarCursosEstudiante();
            }
        });
    }

    private void actualizarCursosEstudiante() {
        if (estudianteSeleccionado != null) {
            // Aquí obtenemos los cursos actualizados para el estudiante
            List<CursoDto> cursosActualizados = gestionCursosController.getCursosEstudiante(estudianteSeleccionado);
            // Limpiamos la lista de cursos asignados
            listaCursosAsignadosDto.setAll(cursosActualizados);
            tableCursosAsignados.setItems(listaCursosAsignadosDto);
            tableCursosAsignados.refresh(); // Refrescamos la tabla
        }
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

    private void setupFilter() {
        txtBuscarEstudiante.textProperty().addListener((observable, oldValue, newValue) -> {
            List<EstudianteDto> originalList = gestionCursosController.getEstudiantes();
            ObservableList<EstudianteDto> filteredList = filtrarLista(originalList, newValue);
            tableEstudiantes.setItems(filteredList);
        });
    }

    private ObservableList<EstudianteDto> filtrarLista(List<EstudianteDto> originalList, String searchText) {
        List<EstudianteDto> filteredList = new ArrayList<>();
        for (EstudianteDto estudianteDto: originalList) {
            if (buscarEstudiante(estudianteDto, searchText)) filteredList.add(estudianteDto);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean buscarEstudiante(EstudianteDto estudianteDto, String searchText) {
        return (estudianteDto.id().toLowerCase().contains(searchText.toLowerCase())) ||
                estudianteDto.nombre().toLowerCase().contains(searchText.toLowerCase());
    }

    @Override
    public void updateView(EventType event) {
        switch (event) {
            case ESTUDIANTE:
                getEstudiantes();
                tableEstudiantes.refresh();
                break;
            case CURSO:
                cargarCursosCombobox();
                actualizarCursosEstudiante();
                tableCursosAsignados.refresh();
                break;
            default:
                break;
        }
    }
}
