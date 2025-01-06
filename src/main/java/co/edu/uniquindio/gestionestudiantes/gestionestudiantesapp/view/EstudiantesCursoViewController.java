package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionCursosController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EstudiantesCursoViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbTipoFiltro;

    @FXML
    private Label labelCurso;

    @FXML
    private Label labelPeriodoAcadémico;

    @FXML
    private Label labelTotalEstudiantes;

    @FXML
    private Label labelUltimaActualizacion;

    @FXML
    private TableView<EstudianteDto> tableEstudiantes;

    @FXML
    private TableColumn<EstudianteDto, String> tcCorreo;

    @FXML
    private TableColumn<EstudianteDto, String> tcDocumento;

    @FXML
    private TableColumn<EstudianteDto, String> tcNombre;

    @FXML
    private TableColumn<EstudianteDto, String> tcTelefono;

    @FXML
    private TextField txtFiltrarEstudiante;

    ObservableList<EstudianteDto>listaEstudiantesDto = FXCollections.observableArrayList();
    private ObservableList<EstudianteDto> listaOriginal = FXCollections.observableArrayList();
    private GestionCursosController gestionCursosController;

     CursoDto cursoSeleccionado;

    @FXML
    void initialize() {
        gestionCursosController = new GestionCursosController();
        setupFilter();
        labelPeriodoAcadémico.setText("Periodo Académico: " + obtenerPeriodoAcadémico());
        initDataBinding(); // Podemos mantener esto aquí ya que no depende del curso
    }

    public void setCurso(CursoDto curso) {
        this.cursoSeleccionado = curso;
        labelCurso.setText(cursoSeleccionado.nombre().toUpperCase());
        cargarEstudiantesCurso(cursoSeleccionado); // Ahora tenemos la garantía de que curso no es null
    }

    private String obtenerPeriodoAcadémico() {
        LocalDate fechaActual = LocalDate.now();
        int anio = fechaActual.getYear();
        int mes = fechaActual.getMonthValue();
        int numeroPeriodo;

        if (mes <= 6) {
            numeroPeriodo = 1;
        } else {
            numeroPeriodo = 2;
        }

        return anio + "-" + numeroPeriodo;
    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        tcDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));

    }



    private void setupFilter() {
        cbTipoFiltro.setItems(FXCollections.observableArrayList("Nombre", "Documento"));
        cbTipoFiltro.setValue("Nombre");

        txtFiltrarEstudiante.textProperty().addListener((observable, oldValue, newValue)->{
            filtrarEstudiante(newValue);
        });

    }

    private void filtrarEstudiante(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            tableEstudiantes.setItems(listaOriginal);
            return;
        }

        ObservableList<EstudianteDto> filteredList = FXCollections.observableArrayList();
        String filterType = cbTipoFiltro.getValue();

        for (EstudianteDto estudiante : listaOriginal) {
            if (coincideFiltro(estudiante, searchText.toLowerCase(), filterType)) {
                filteredList.add(estudiante);
            }
        }
        tableEstudiantes.setItems(filteredList);
    }

    private boolean coincideFiltro(EstudianteDto estudiante, String searchText, String filterType) {
        switch (filterType) {
            case "Nombre":
                return estudiante.nombre().toLowerCase().contains(searchText);
            case "Documento":
                return estudiante.id().toLowerCase().contains(searchText);
            default:
                return false;
        }
    }



    private void cargarEstudiantesCurso(CursoDto cursoSeleccionado) {
        listaEstudiantesDto.clear();
        listaOriginal.clear();
        listaEstudiantesDto.addAll(gestionCursosController.getEstudiantesCurso(cursoSeleccionado));
        listaOriginal.addAll(listaEstudiantesDto);
        tableEstudiantes.setItems(listaEstudiantesDto);
        labelTotalEstudiantes.setText("Total estudiantes: " + String.valueOf(listaOriginal.size()));
        actualizarFechaActualizacion();

    }

    private void actualizarFechaActualizacion() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        labelUltimaActualizacion.setText("Última actualización: " + ahora.format(formatter));
    }
}
