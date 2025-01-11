package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionEstudianteController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.services.PdfExportService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CursosEstudianteViewController {

    @FXML
    private Button btnExportarLista;

    @FXML
    private ComboBox<String> cbtipoFiltro;

    @FXML
    private Label labelNombre;

    @FXML
    private Label labelPeriodoAcadémico;

    @FXML
    private Label labelTotalCursos;

    @FXML
    private Label labelUltimaActualizacion;

    @FXML
    private TableView<CursoDto> tableCursos;

    @FXML
    private TableColumn<CursoDto, String> tcCodigo;

    @FXML
    private TableColumn<CursoDto, String> tcCursoNombre;

    @FXML
    private TableColumn<CursoDto, String> tcProfesor;

    @FXML
    void onExportarLista(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar reporte PDF");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
        );
        fileChooser.setInitialFileName("cursos_" +
                estudianteSeleccionado.nombre().replaceAll("\\s+", "_").toLowerCase() + ".pdf");

        File file = fileChooser.showSaveDialog(btnExportarLista.getScene().getWindow());

        if (file != null) {
            try {
                PdfExportService exportService = new PdfExportService();
                exportService.exportarCursosEstudiante(
                        file.getAbsolutePath(),
                        estudianteSeleccionado,
                        new ArrayList<>(listaCursosDto),
                        obtenerPeriodoAcadémico()
                );

                mostrarMensajeExito();
            } catch (Exception e) {
                mostrarMensajeError(e.getMessage());
            }
        }
    }

    private void mostrarMensajeExito() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText("El reporte PDF se ha generado exitosamente.");
        alert.showAndWait();
    }

    private void mostrarMensajeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Error al generar el PDF: " + mensaje);
        alert.showAndWait();
    }

    @FXML
    private TextField txtFiltrarCurso;


    private ObservableList<CursoDto> listaCursosDto = FXCollections.observableArrayList();
    private ObservableList<CursoDto> listaOriginal = FXCollections.observableArrayList();
    GestionEstudianteController gestionEstudianteController;
    EstudianteDto estudianteSeleccionado;

    @FXML
    public void initialize() {
        gestionEstudianteController = new GestionEstudianteController();
        initView();
        setupFilter();
        labelPeriodoAcadémico.setText("Periodo Académico: " + obtenerPeriodoAcadémico());


    }

    private void initView() {
        initDataBinding();
        cargarCursosEstudiante(estudianteSeleccionado);


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

    private void setupFilter() {
        // Configurar las opciones del ComboBox
        cbtipoFiltro.setItems(FXCollections.observableArrayList("Nombre", "Código"));
        cbtipoFiltro.setValue("Nombre"); // Valor por defecto

        // Configurar el listener del TextField
        txtFiltrarCurso.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarCursos(newValue);
        });
    }




    private void initDataBinding() {
        tcCursoNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        tcProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreProfesor()));
    }

    public void setEstudiante(EstudianteDto estudiante) {
        this.estudianteSeleccionado = estudiante;
        labelNombre.setText(estudianteSeleccionado.nombre().toUpperCase());
        cargarCursosEstudiante(estudianteSeleccionado);
    }

    private void cargarCursosEstudiante(EstudianteDto estudianteSeleccionado) {
        listaCursosDto.clear();
        listaOriginal.clear();
        listaCursosDto.addAll(gestionEstudianteController.getCursosEstudiante(estudianteSeleccionado));
        listaOriginal.addAll(listaCursosDto);
        tableCursos.setItems(listaCursosDto);
        labelTotalCursos.setText("total cursos: " + String.valueOf(listaOriginal.size()));
        actualizarFechaActualizacion();
    }

    private void actualizarFechaActualizacion() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        labelUltimaActualizacion.setText("Última actualización: " + ahora.format(formatter));
    }

    private void filtrarCursos(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            tableCursos.setItems(listaOriginal);
            return;
        }

        ObservableList<CursoDto> filteredList = FXCollections.observableArrayList();
        String filterType = cbtipoFiltro.getValue();

        for (CursoDto curso : listaOriginal) {
            if (coincideFiltro(curso, searchText.toLowerCase(), filterType)) {
                filteredList.add(curso);
            }
        }

        tableCursos.setItems(filteredList);
    }

    private boolean coincideFiltro(CursoDto curso, String searchText, String filterType) {
        switch (filterType) {
            case "Nombre":
                return curso.nombre().toLowerCase().contains(searchText);
            case "Código":
                return curso.codigo().toLowerCase().contains(searchText);
            default:
                return false;
        }
    }
}
