package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.Optional;

public abstract class CoreViewController {


    public void mostrarMensaje(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);

        // Crear un TextArea para mostrar contenido largo
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(140, 140); // Ajusta el tamaño del TextArea

        // Configurar el contenido expandible de la alerta
        alert.getDialogPane().setExpandableContent(textArea);
        alert.getDialogPane().setExpanded(true);

        alert.showAndWait();
    }

    public boolean mostrarMensajeConfirmacion(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText(message);
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

    public void browseWindow(String nameFileFxml, String titleWindow, ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFileFxml));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle(titleWindow);
            stage.show();

            closeWindow(actionEvent);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
