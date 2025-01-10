package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.GestionEstudianteController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Admin;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.services.EmailServices;
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

    private final EmailServices emailService = new EmailServices();

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
            CursosEstudianteViewController controller = loader.getController();
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
            mostrarMensaje("Error", "Datos no válidos", "El tipo de usuario seleccionado no es válido", Alert.AlertType.ERROR);
            return;
        }
        if (validarDatos(estudianteDto)) {
            if (gestionEstudianteController.agregarEstudiante(estudianteDto)) {
                listaEstudiantesDto.add(estudianteDto);
                notificarEstudianteAgregado(estudianteDto);
                mostrarMensaje("Notificación", "Estudiante agregado", "El registro del estudiante se ha completado exitosamente. Todos los datos han sido almacenados de forma segura en el sistema.", Alert.AlertType.INFORMATION);
                clearFields();
            } else {
                mostrarMensaje("Error", "Estudiante no agregado", "El estudiante no pudo ser agregado", Alert.AlertType.ERROR);
            }
        }
    }

    private void notificarEstudianteAgregado(EstudianteDto estudiante) {
        try {
            emailService.enviarCorreoBienvenida(estudiante);
        } catch (Exception e) {
            mostrarMensaje(
                    "Advertencia",
                    "Error en envío de correo",
                    "El estudiante fue registrado pero no se pudo enviar el correo de bienvenida: " + e.getMessage(),
                    Alert.AlertType.WARNING
            );
        }
    }

    private void eliminarEstudiante() {
        boolean estudianteEliminado = false;
        if (estudianteSeleccionado != null) {
            if (mostrarMensajeConfirmacion("¿Está seguro de eliminar a " + estudianteSeleccionado.nombre() + " ?")) {
                estudianteEliminado = gestionEstudianteController.eliminarEstudiante(estudianteSeleccionado);
                if (estudianteEliminado) {
                    try {
                        emailService.enviarCorreoEliminacion(estudianteSeleccionado);
                        listaEstudiantesDto.remove(estudianteSeleccionado);
                        mostrarMensaje("Notificación", "Estudiante eliminado",
                                "El estudiante ha sido eliminado con éxito y se le ha notificado por correo",
                                Alert.AlertType.INFORMATION);
                    } catch (Exception e) {
                        listaEstudiantesDto.remove(estudianteSeleccionado);
                        mostrarMensaje("Advertencia", "Estudiante eliminado",
                                "El estudiante ha sido eliminado con éxito pero no se pudo enviar el correo de notificación: "
                                        + e.getMessage(),
                                Alert.AlertType.WARNING);
                    } finally {
                        deselectTable();
                        clearFields();
                    }
                } else {
                    mostrarMensaje("Error", "Estudiante no eliminado",
                            "El estudiante no pudo ser eliminado",
                            Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Error", "Estudiante no seleccionado",
                    "Por favor, seleccione un usuario de la lista para proceder con su eliminación. Es necesario elegir un registro válido para completar esta acción.",
                    Alert.AlertType.ERROR);
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
                    emailService.enviarCorreoActualizacion(estudianteSeleccionado, estudianteDto);
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
        StringBuilder mensaje = new StringBuilder();

        // Validación de campos vacíos
        if (estudianteDto.nombre() == null || estudianteDto.nombre().isEmpty()) {
            mensaje.append("El nombre del usuario es requerido.\n");
        }
        if (estudianteDto.id() == null || estudianteDto.id().isEmpty()) {
            mensaje.append("El id del usuario es requerido.\n");
        }
        if (estudianteDto.telefono() == null || estudianteDto.telefono().isEmpty()) {
            mensaje.append("El número de teléfono es requerido.\n");
        }
        if (estudianteDto.correo() == null || estudianteDto.correo().isEmpty()) {
            mensaje.append("El email es requerido.\n");
        } else if (!validarFormatoEmail(estudianteDto.correo())) {
            mensaje.append("El formato del email no es válido.\n");
        }

        if (mensaje.length() > 0) {
            mostrarMensaje(
                    "Notificación de validación",
                    "Datos no válidos",
                    mensaje.toString(),
                    Alert.AlertType.WARNING
            );
            return false;
        }
        return true;
    }

    /**
     * Valida que el formato del email sea correcto usando expresiones regulares
     * @param email el email a validar
     * @return true si el formato es válido, false en caso contrario
     */
    private boolean validarFormatoEmail(String email) {
        // Patrón para validar el email
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(email);
        return matcher.find();
    }

    private EstudianteDto buildEstudianteDto() {
        String nombre = txtNombre.getText().trim();
        String id = txtDocumento.getText().trim();
        String correo = txtCorreo.getText().trim();
        String celular = txtCelular.getText().trim();

        return new EstudianteDto(nombre, id, correo, celular, new ArrayList<>());
    }

}
