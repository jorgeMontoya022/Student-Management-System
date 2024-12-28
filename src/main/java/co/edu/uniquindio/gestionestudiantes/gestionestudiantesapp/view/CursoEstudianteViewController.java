package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionEstudianteController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class CursoEstudianteViewController {

    @FXML
    private Label labelNombre;

    @FXML
    private TableView<CursoDto> tableCursos;

    @FXML
    private TableColumn<CursoDto, String> tcCodigo;

    @FXML
    private TableColumn<CursoDto, String> tcCursoNombre;

    @FXML
    private TableColumn<CursoDto, String> tcProfesor;

    private ObservableList<CursoDto> listaCursosDto = FXCollections.observableArrayList();
    GestionEstudianteController gestionEstudianteController;
    EstudianteDto estudianteSeleccionado;

    @FXML
    public void initialize() {

        gestionEstudianteController = new GestionEstudianteController();
        // Inicialización básica de columnas
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
        listaCursosDto.addAll(gestionEstudianteController.getCursosEstudiante(estudianteSeleccionado));
        tableCursos.setItems(listaCursosDto);
    }
}
