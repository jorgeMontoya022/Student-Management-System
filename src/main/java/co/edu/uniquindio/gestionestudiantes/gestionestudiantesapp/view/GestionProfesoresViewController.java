package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionProfesorController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.ProfesorDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Admin;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.session.Sesion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GestionProfesoresViewController extends CoreViewController {
    Admin loggedAdmin;

    ObservableList<ProfesorDto> listaProfesoresDto = FXCollections.observableArrayList();
    ProfesorDto profesorSeleccionado;

    GestionProfesorController gestionProfesorController;

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
    private Button btnVerCursos;

    @FXML
    private TableView<ProfesorDto> tableProfesor;

    @FXML
    private TableColumn<ProfesorDto, String> tcCelular;

    @FXML
    private TableColumn<ProfesorDto, String> tcCorreo;

    @FXML
    private TableColumn<ProfesorDto, String> tcDocumento;

    @FXML
    private TableColumn<ProfesorDto, String> tcNombre;

    @FXML
    private TextField txtCelular;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtFiltrar;

    @FXML
    private TextField txtNombre;

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
    void onVerCursos(ActionEvent event) {

    }

    @FXML
    void initialize() {
        gestionProfesorController = new GestionProfesorController();
        loggedAdmin = (Admin) Sesion.getInstance().getPersona();
        initView();
        setupFilter();



    }

    private void initView() {
        initDataBinding();
        getProfesores();
        tableProfesor.getItems().clear();
        tableProfesor.setItems(listaProfesoresDto);
        listenerSelection();

    }

    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        tcDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
    }

    private void getProfesores() {
        listaProfesoresDto.clear();
        listaProfesoresDto.addAll(gestionProfesorController.getProfesores());
    }

    private void setupFilter() {
        txtFiltrar.textProperty().addListener((observable, oldValue, newValue) ->{
            List<ProfesorDto> originalList = gestionProfesorController.getProfesores();
            ObservableList<ProfesorDto> filteredList = filtrarLista(originalList, newValue);
            tableProfesor.setItems(filteredList);
        });
    }

    private ObservableList<ProfesorDto> filtrarLista(List<ProfesorDto> originalList, String searchText) {
        List<ProfesorDto> filteredList = new ArrayList<>();
        for (ProfesorDto profesorDto: originalList) {
            if (buscarProfesor(profesorDto, searchText)) filteredList.add(profesorDto);

        }
        return FXCollections.observableList(filteredList);
    }

    private boolean buscarProfesor(ProfesorDto profesorDto, String searchText) {
        return (profesorDto.id().toLowerCase().contains(searchText.toLowerCase())) ||
                profesorDto.nombre().toLowerCase().contains(searchText.toLowerCase());
    }

    private void listenerSelection() {
        tableProfesor.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            profesorSeleccionado = newSelection;
            if (profesorSeleccionado != null) {
                showInformation(profesorSeleccionado);
            }
        });
    }

    private void showInformation(ProfesorDto profesorSeleccionado) {
        if (profesorSeleccionado != null) {
            txtNombre.setText(profesorSeleccionado.nombre());
            txtDocumento.setText(profesorSeleccionado.id());
            txtCelular.setText(profesorSeleccionado.telefono());
            txtCorreo.setText(profesorSeleccionado.correo());
        }
    }


}
