package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionEstudianteController;
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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GestionEstudiantesViewController extends CoreViewController {

    Admin loggedAdmin;
    ObservableList<EstudianteDto> listaEstudiantesDto = FXCollections.observableArrayList();
    EstudianteDto estudianteSeleccionado;
    GestionEstudianteController gestionEstudianteController;

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
    private TableView<EstudianteDto> tableEstudiante;

    @FXML
    private TableColumn<EstudianteDto, String> tcCelular;

    @FXML
    private TableColumn<EstudianteDto, String> tcCorreo;

    @FXML
    private TableColumn<EstudianteDto, String> tcDocumento;

    @FXML
    private TableColumn<EstudianteDto, String> tcNombre;

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
    private Button btnVerCursos;

    @FXML
    void onActualizar(ActionEvent event) {
        actualizarEstudiante();

    }

    @FXML
    void onAgregar(ActionEvent event) {
        agregarEstudiante();

    }


    @FXML
    void onEliminar(ActionEvent event) {
        eliminarEstudiante();
    }

    @FXML
    void onLimpiar(ActionEvent event) {
        clearFields();
        deselectTable();


    }

    @FXML
    void onVerCursos(ActionEvent event) {
        if (estudianteSeleccionado != null) {
            openCursosWindow(estudianteSeleccionado);
        } else {
            mostrarMensaje("Error", "Estudiante no seleccionado", "Por favor seleccione un estudiante primero.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    void initialize() {
        gestionEstudianteController = new GestionEstudianteController();
        loggedAdmin = (Admin) Sesion.getInstance().getPersona();
        initView();
        setupFilter();
    }


    private void initView() {
        initDataBinding();
        getEstudiantes();
        tableEstudiante.getItems().clear();
        tableEstudiante.setItems(listaEstudiantesDto);
        listenerSelection();
    }


    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nombre()));
        tcCelular.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().telefono()));
        tcCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().correo()));
        tcDocumento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().id()));
    }

    private void getEstudiantes() {
        listaEstudiantesDto.clear();
        listaEstudiantesDto.addAll(gestionEstudianteController.getEstudiantes());
    }

    private void setupFilter() {
        txtFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            List<EstudianteDto> originalList = gestionEstudianteController.getEstudiantes();
            ObservableList<EstudianteDto> filteredList = filtrarLista(originalList, newValue);
            tableEstudiante.setItems(filteredList);
        });
    }

    private ObservableList<EstudianteDto> filtrarLista(List<EstudianteDto> originalList, String searchText) {
        List<EstudianteDto> filteredList = new ArrayList<>();
        for (EstudianteDto estudianteDto : originalList) {
            if (buscarEstudiante(estudianteDto, searchText)) filteredList.add(estudianteDto);
        }
        return FXCollections.observableList(filteredList);
    }

    private boolean buscarEstudiante(EstudianteDto estudianteDto, String searchText) {
        return (estudianteDto.id().toLowerCase().contains(searchText.toLowerCase())) ||
                estudianteDto.nombre().toLowerCase().contains(searchText.toLowerCase());
    }

    private void listenerSelection() {
        tableEstudiante.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            estudianteSeleccionado = newSelection;
            if (estudianteSeleccionado != null) {
                showInformation(estudianteSeleccionado);
                btnVerCursos.setVisible(true);
            } else {
                btnVerCursos.setVisible(false);
            }

        });

    }


    private void openCursosWindow(EstudianteDto estudianteSeleccionado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/CursosEstudianteView.fxml"));
            AnchorPane root = loader.load();
            CursoEstudianteViewController controller = loader.getController();
            controller.setEstudiante(estudianteSeleccionado);
            Stage cursosStage = new Stage();
            cursosStage.setTitle("Cursos del estudiante " + estudianteSeleccionado.nombre());
            cursosStage.setScene(new Scene(root));
            cursosStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showInformation(EstudianteDto estudianteSeleccionado) {
        if (estudianteSeleccionado != null) {
            txtNombre.setText(estudianteSeleccionado.nombre());
            txtDocumento.setText(estudianteSeleccionado.id());
            txtCelular.setText(estudianteSeleccionado.telefono());
            txtCorreo.setText(estudianteSeleccionado.correo());
        }
    }

    private void clearFields() {
        txtNombre.clear();
        txtFiltrar.clear();
        txtCorreo.clear();
        txtCelular.clear();
        txtDocumento.clear();
    }

    private void deselectTable() {
        tableEstudiante.getSelectionModel().clearSelection();
        estudianteSeleccionado = null;
    }

    private void agregarEstudiante() {
        EstudianteDto estudianteDto = buildEstudianteDto();
        if (estudianteDto == null) {
            mostrarMensaje("Error", "Datos no validos", "El tipo de usuario seleccionado no es valido", Alert.AlertType.ERROR);
            return;
        }
        if (validarDatos(estudianteDto)) {
            if (gestionEstudianteController.agregarEstudiante(estudianteDto)) {
                listaEstudiantesDto.add(estudianteDto);
                mostrarMensaje("Notificación", "Estudiante agregado", "El estudiante ha sido aagregado con éxito", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                mostrarMensaje("Error", "Estudiante no agregado", "El Estudiante no pudo ser agregado", Alert.AlertType.ERROR);
            }
        }
    }

    private void eliminarEstudiante() {
        boolean estudianteEliminado = false;
        if (estudianteSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Está seguro de eliminar a " + estudianteSeleccionado.nombre() + " ?")) {
                estudianteEliminado = gestionEstudianteController.eliminarEstudiante(estudianteSeleccionado);
                if (estudianteEliminado) {
                    listaEstudiantesDto.remove(estudianteSeleccionado);
                    mostrarMensaje("Notificación", "Estudiante eliminado", "El estudiante ha sido eliminado con éxito", Alert.AlertType.INFORMATION);
                    deselectTable();
                    clearFields();
                } else {
                    mostrarMensaje("Error", "Estudiante no eliminado", "El estudiante no pudo ser eliminado", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Error", "Estudiante no seleccionado", "Por favor seleccione un usuario para eliminar", Alert.AlertType.ERROR);
        }
    }

    private void actualizarEstudiante() {
        boolean estudianteActualizado = false;
        EstudianteDto estudianteDto = buildEstudianteDto();
        if (estudianteSeleccionado != null) {
            if (validarDatos(estudianteDto)) {
                if (!hasChanges(estudianteSeleccionado, estudianteDto)) {
                    mostrarMensaje("Error", "Sin cambios", "No se puede actualizar el estudiante sin cambios", Alert.AlertType.INFORMATION);
                    return;
                }
                int index = tableEstudiante.getSelectionModel().getSelectedIndex();
                estudianteActualizado = gestionEstudianteController.actualizarEstudiante(estudianteSeleccionado, estudianteDto);
                if (estudianteActualizado) {
                    listaEstudiantesDto.set(index, estudianteDto);
                    tableEstudiante.refresh();
                    tableEstudiante.getSelectionModel().select(index);
                    mostrarMensaje("Notificación", "Estudiante actualizado",
                            "El Estudiante ha sido actualizado con éxito", Alert.AlertType.INFORMATION);
                    deselectTable();
                    clearFields();

                } else {
                    mostrarMensaje("Error", "Estudiante no actualizado",
                            "El Estudiante no pudo ser actualizado", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Error", "Estudiante no seleccionado",
                    "Por favor, seleccione un estudiante para actualizar", Alert.AlertType.ERROR);
        }
    }

    private boolean hasChanges(EstudianteDto estudianteSeleccionado, EstudianteDto estudianteDto) {
        boolean hasChanges = false;

        hasChanges = !estudianteSeleccionado.id().equals(estudianteDto.id()) ||
                !estudianteSeleccionado.nombre().equals(estudianteDto.nombre()) ||
                !estudianteSeleccionado.correo().equals(estudianteDto.correo()) ||
                !estudianteSeleccionado.telefono().equals(estudianteDto.telefono());
        return hasChanges;
    }


    private boolean validarDatos(EstudianteDto estudianteDto) {
        String mensaje = "";
        if (estudianteDto.nombre().isEmpty()) {
            mensaje += "El nombre del usuario es requerido.\n";
        }
        if (estudianteDto.id().isEmpty()) {
            mensaje += "El id del usuario es requerido.\n";
        }
        if (estudianteDto.telefono().isEmpty()) {
            mensaje += "El número de teléfono es requerido.\n";
        }
        if (estudianteDto.correo().isEmpty()) {
            mensaje += "El email es requerido.\n";
        }
        if (!mensaje.isEmpty()) {
            mostrarMensaje("Notificación de validación", "Datos no validos", mensaje, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private EstudianteDto buildEstudianteDto() {
        String nombre = txtNombre.getText().trim();
        String id = txtDocumento.getText().trim();
        String correo = txtCorreo.getText().trim();
        String celular = txtCelular.getText().trim();

        return new EstudianteDto(nombre, id, correo, celular, new ArrayList<>());
    }

}
