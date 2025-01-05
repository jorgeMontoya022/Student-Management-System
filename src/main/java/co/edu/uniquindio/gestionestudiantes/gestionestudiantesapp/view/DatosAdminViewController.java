package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller.DatosAdminController;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Admin;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.session.Sesion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DatosAdminViewController extends CoreViewController {

    Admin person;
    DatosAdminController datosAdminController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblCorreo;

    @FXML
    private Label lblId;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblTelefono;

    @FXML
    void onDashboardClick(ActionEvent event) {
        browseWindow("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/admin-container-view.fxml", "Dashboard", event);
    }

    @FXML
    void onLogoutClick(ActionEvent event) {
        Sesion.getInstance().closeSesion();
        browseWindow("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/login-view.fxml", "Inicio de Sesión", event);


    }

    @FXML
    void initialize() {
        person = (Admin) Sesion.getInstance().getPersona();
        datosAdminController = new DatosAdminController();
        initView();

    }

    private void initView() {
        mostrarInformacion();
    }

    private void mostrarInformacion() {
        lblNombre.setText(person.getNombre());
        lblCorreo.setText(person.getCorreo());
        lblId.setText(person.getId());
    }

}