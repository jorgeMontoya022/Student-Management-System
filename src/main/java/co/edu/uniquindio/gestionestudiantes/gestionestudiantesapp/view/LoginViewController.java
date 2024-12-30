package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.LoginController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Estudiante;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController extends CoreViewController {

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
        ingregar(event);

    }

    @FXML
    void onRecordarContraseña(ActionEvent event) {

    }

    @FXML
    void initialize() {
        loginController = new LoginController();

    }

    private void ingregar(ActionEvent event) {
        try {
            String correo = txtCorreo.getText().trim();
            String id = txtId.getText();

            Persona usuarioValidado = loginController.validarAcceso(correo, id);

            if (usuarioValidado == null) {
                mostrarMensaje("El usuario no existe", "Error de inicio de sesión", "El usuario no existe en el sistema", Alert.AlertType.ERROR);
            } else {
                loginController.guardarSesion(usuarioValidado);

                if (usuarioValidado instanceof Estudiante) {
                    browseWindow("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/datos-estudiante-view.fxml", "Datos del estudiante", event);
                }else{
                    mostrarMensaje("Error", "Error al incio de sesión", "Actualmente estamos trabajando en la optimización del manejo de datos del administrador. Este proceso podría ocasionar dificultades temporales al intentar iniciar sesión. Agradecemos su comprensión mientras implementamos estas mejoras para garantizar una experiencia más eficiente y segura.", Alert.AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            mostrarMensaje("Error de inicio de sesión", e.getMessage(), "No se pudieron validar las credenciales proporcionadas. Asegúrese de que el nombre de correo y el número de identificación sean correctos e intente nuevamente. Si el problema persiste, comuníquese con el administrador del sistema para obtener ayuda.", Alert.AlertType.ERROR);
        }
    }

}