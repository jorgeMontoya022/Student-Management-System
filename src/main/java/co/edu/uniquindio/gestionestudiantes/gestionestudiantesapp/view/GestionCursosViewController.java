package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GestionCursosViewController extends CoreViewController {

    Admin loggedAdmin;
    ObservableList<CursoDto> listaCursosDto = FXCollections.observableArrayList();
    CursoDto cursoSeleccionado;
    GestionCursosController gestionCursosController;

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
    private Button btnVerEstudiantes;

    @FXML
    private TableView<CursoDto> tableCursos;

    @FXML
    private TableColumn<CursoDto, String> tcCodigo;

    @FXML
    private TableColumn<CursoDto, String> tcNombreCurso;

    @FXML
    private TableColumn<CursoDto, String> tcProfesor;

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
    void onVerEstudiantes(ActionEvent event) {
        if (cursoSeleccionado != null) {
            openStudentsWindow(cursoSeleccionado);
        }
    }



    @FXML
    void initialize() {
        gestionCursosController = new GestionCursosController();
        loggedAdmin = (Admin) Sesion.getInstance().getPersona();
        initView();
        setupFilter();


    }

    private void initView() {
        initDataBinding();
        getCursos();
        tableCursos.getItems().clear();
        tableCursos.setItems(listaCursosDto);
        listenerSelection();
    }


    private void initDataBinding() {
        tcNombreCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        tcProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombreProfesor()));

    }

    private void getCursos() {
        listaCursosDto.clear();
        listaCursosDto.addAll(gestionCursosController.getCursos());
    }

    private void setupFilter() {
        txtFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            List<CursoDto> originalList = gestionCursosController.getCursos();
            ObservableList<CursoDto> filteredList = filtrarLista(originalList, newValue);
            tableCursos.setItems(filteredList);
        });
    }

    private ObservableList<CursoDto> filtrarLista(List<CursoDto> originalList, String searchText) {
        List<CursoDto> filteredList = new ArrayList<>();
        for (CursoDto cursoDto : originalList) {
            if (buscarCurso(cursoDto, searchText)) filteredList.add(cursoDto);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean buscarCurso(CursoDto cursoDto, String searchText) {
        return (cursoDto.codigo().toLowerCase().contains(searchText.toLowerCase())) ||
                cursoDto.nombre().toLowerCase().contains(searchText.toLowerCase());
    }

    private void listenerSelection() {
        tableCursos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            cursoSeleccionado = newSelection;
            if (cursoSeleccionado != null) {
                showInformation(cursoSeleccionado);
                btnVerEstudiantes.setVisible(true);

            } else {
                btnVerEstudiantes.setVisible(false);
            }
        });
    }

    private void openStudentsWindow(CursoDto cursoSeleccionado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/estudiantesCursoView.fxml"));
            AnchorPane root = loader.load();
            EstudiantesCursoViewController controller = loader.getController();
            controller.setEstudiante(cursoSeleccionado);
            Stage cursosStage = new Stage();
            cursosStage.setTitle("Estudiantes de " + cursoSeleccionado.nombre());
            cursosStage.setScene(new Scene(root));
            cursosStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showInformation(CursoDto cursoSeleccionado) {
        if (cursoSeleccionado != null) {
            txtNombreCurso.setText(cursoSeleccionado.nombre());
            txtCodigo.setText(cursoSeleccionado.codigo());
            txtProfesor.setText(cursoSeleccionado.nombreProfesor());
        }
    }


}
