package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionCursosController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EstudiantesCursoViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label labelCurso;

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

    ObservableList<EstudianteDto>listaEstudiantesDto = FXCollections.observableArrayList();
    private GestionCursosController gestionCursosController;

    CursoDto cursoSeleccionado;

    @FXML
    void initialize() {
        gestionCursosController = new GestionCursosController();

        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        tcDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
        tcTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));


    }

    public void setEstudiante(CursoDto curso) {
        this.cursoSeleccionado = curso;
        labelCurso.setText(cursoSeleccionado.nombre().toUpperCase());
        cargarEstudiantesCurso(cursoSeleccionado);

    }

    private void cargarEstudiantesCurso(CursoDto cursoSeleccionado) {
        listaEstudiantesDto.clear();  
        listaEstudiantesDto.addAll(gestionCursosController.getEstudiantesCurso(cursoSeleccionado));
        tableEstudiantes.getItems().clear();
        tableEstudiantes.setItems(listaEstudiantesDto);
    }
}
