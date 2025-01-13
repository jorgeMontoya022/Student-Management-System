package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.LoginController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Estudiante;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Persona;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.services.EmailServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController extends CoreViewController {

   private final EmailServices emailServices = new EmailServices();

    LoginController loginController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnIngresar;

    @FXML
    private Button btnRecordarContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtId;

    @FXML
    void onIngresar(ActionEvent event) {
        ingresar(event);

    }

    @FXML
    void onRecordarContraseña(ActionEvent event) {

    }

    @FXML
    void onVolverClick(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/WelcomeView.fxml", "Institución educativa", event);

    }

    @FXML
    void initialize() {
        loginController = new LoginController();

    }

    private void ingresar(ActionEvent event) {
        // Validación inicial de campos vacíos
        if (!validarCamposRequeridos()) {
            return;
        }

        try {
            String correo = txtCorreo.getText().trim();
            String id = txtId.getText().trim();

            // Validar formato de correo
            if (!esCorreoValido(correo)) {
                mostrarMensaje(
                        "Formato inválido",
                        "Correo electrónico incorrecto",
                        "Por favor ingrese una dirección de correo electrónico válida.",
                        Alert.AlertType.WARNING
                );
                txtCorreo.requestFocus();
                return;
            }

            Persona usuarioValidado = loginController.validarAcceso(correo, id);

            if (usuarioValidado == null) {
                mostrarMensaje(
                        "Credenciales inválidas",
                        "Error de inicio de sesión",
                        "El correo o el número de identificación son incorrectos. Por favor verifique e intente nuevamente.",
                        Alert.AlertType.ERROR
                );
                return;
            }

            // Procesar inicio de sesión exitoso
            loginController.guardarSesion(usuarioValidado);
            redirigirSegunTipoUsuario(usuarioValidado, event);

        } catch (Exception e) {
            manejarErrorInicioSesion(e);
        }
    }

    private boolean validarCamposRequeridos() {
        if (txtCorreo.getText().trim().isEmpty() || txtId.getText().trim().isEmpty()) {
            mostrarMensaje(
                    "Campos requeridos",
                    "Información incompleta",
                    "Por favor complete todos los campos antes de continuar.",
                    Alert.AlertType.WARNING
            );
            return false;
        }
        return true;
    }

    private boolean esCorreoValido(String correo) {
        Pattern patron = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = patron.matcher(correo);
        return matcher.find();
    }

    private void redirigirSegunTipoUsuario(Persona usuario, ActionEvent event) {
        try {
            if (usuario instanceof Estudiante) {
                emailServices.enviarMensajeInicioSesion(usuario);
                browseWindow(
                        "/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/datos-estudiante-view.fxml",
                        "Datos del estudiante",
                        event
                );
            } else {
                browseWindow(

                        "/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/datos-admin-view.fxml",
                        "Datos del administrador",
                        event
                );
            }
        } catch (Exception e) {
            mostrarMensaje(
                    "Error de redirección",
                    "No se pudo cargar la siguiente ventana",
                    "Ocurrió un error al intentar cargar la ventana de usuario. Por favor intente nuevamente.",
                    Alert.AlertType.ERROR
            );
        }
    }

    private void manejarErrorInicioSesion(Exception e) {
        mostrarMensaje(
                "Error de inicio de sesión",
                "No se pudo completar el inicio de sesión",
                "Ocurrió un error inesperado durante el inicio de sesión: " + e.getMessage() +
                        "\nPor favor intente nuevamente o contacte al administrador del sistema.",
                Alert.AlertType.ERROR
        );
        e.printStackTrace(); // Para registro/debugging
    }

}