package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionCursosController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.ProfesorDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Admin;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.session.Sesion;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view.observer.EventType;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view.observer.ObserverManagement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private ComboBox<ProfesorDto> cbProfesor;

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
    void onActualizar(ActionEvent event) {

    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarCurso();

    }

    @FXML
    void onEliminar(ActionEvent event) {

    }

    @FXML
    void onLimpiar(ActionEvent event) {
        clearFields();
        deselectTable();

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
        initializeDataComboBox();
        tableCursos.getItems().clear();
        tableCursos.setItems(listaCursosDto);
        listenerSelection();
    }




    private void initDataBinding() {
        tcNombreCurso.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().codigo()));
        tcProfesor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().profesor().nombre()));

    }

    private void getCursos() {
        listaCursosDto.clear();
        listaCursosDto.addAll(gestionCursosController.getCursos());
    }

    private void initializeDataComboBox() {
        ObservableList<ProfesorDto> listaProfesoresDto = FXCollections.observableArrayList(gestionCursosController.getProfesores());
        cbProfesor.setItems(listaProfesoresDto);

        initializeComboBox(cbProfesor, listaProfesoresDto, profesorDto -> profesorDto.nombre());

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
        tableCursos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
            controller.setCurso(cursoSeleccionado);
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

            cbProfesor.getSelectionModel().select(
                    cbProfesor.getItems().stream()
                            .filter(profesor -> profesor.nombre().equals(cursoSeleccionado.profesor().nombre()))
                            .findFirst()
                            .orElse(null)

            );
        }
    }

    private void clearFields() {
        cbProfesor.getSelectionModel().clearSelection();
        txtCodigo.clear();
        txtNombreCurso.clear();
        txtFiltrar.clear();
    }

    private void deselectTable() {
        tableCursos.getSelectionModel().clearSelection();
        cursoSeleccionado = null;
    }

    private void agregarCurso() {
        CursoDto cursoDto = buildCursoDto();
        if (cursoDto == null) {
            mostrarMensaje("Error", "Datos no validos", "El tipo seleccionado no es valido", Alert.AlertType.ERROR);
            return;
        }
        if (validarDatos(cursoDto)) {
            if (gestionCursosController.agregarCursos(cursoDto)) {
                listaCursosDto.add(cursoDto);
                mostrarMensaje("Notificación", "Curso agregado exitosamente", "El curso ha sido registrado correctamente en el sistema. Ahora puede proceder a asignar estudiantes, configurar horarios o realizar cualquier gestión adicional relacionada con este curso.", Alert.AlertType.INFORMATION);
                ObserverManagement.getInstance().notifyObservers(EventType.CURSO);
                clearFields();
            }
        } else {
            mostrarMensaje("Error", "No se pudo agregar el curso", "Ocurrió un problema al intentar registrar el curso en el sistema. Por favor, revise los datos ingresados y vuelva a intentarlo.", Alert.AlertType.ERROR);
        }
    }

    private boolean validarDatos(CursoDto cursoDto) {
        String mensaje = "";
        if (cursoDto.nombre().isEmpty()) {
            mensaje += "El nombre del curso es requerido.\n";
        }
        if (cursoDto.codigo().isEmpty()) {
            mensaje += "El código del curso es requerido.\n";
        }
        if (cursoDto.profesor() == null) {
            mensaje += "El profesor es requerido.\n";
        }
        if (!mensaje.isEmpty()) {
            mostrarMensaje("Notificación de validación", "Datos no validos", mensaje, Alert.AlertType.WARNING);
            return false;
        }
        return true;

    }

    private CursoDto buildCursoDto() {
        String nombre = txtNombreCurso.getText().trim();
        String codigo = txtCodigo.getText().trim();
        ProfesorDto profesor = cbProfesor.getValue();

        return new CursoDto(nombre, codigo, profesor, new ArrayList<>());
    }
}
