package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;
import java.util.function.Function;

public abstract class CoreViewController {


    public void mostrarMensaje(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.initStyle(StageStyle.TRANSPARENT);

        // Personalizar el DialogPane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                CoreViewController.class.getResource("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/css/modern-alerts.css").toExternalForm()
        );
        dialogPane.getStyleClass().add("modern-alert");

        // Agregar clases de estilo específicas según el tipo de alerta
        switch (alertType) {
            case ERROR:
                dialogPane.getStyleClass().add("error-alert");
                break;
            case WARNING:
                dialogPane.getStyleClass().add("warning-alert");
                break;
            case INFORMATION:
                dialogPane.getStyleClass().add("info-alert");
                break;
            case CONFIRMATION:
                dialogPane.getStyleClass().add("confirmation-alert");
                break;
        }

        // Crear el área de texto
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.getStyleClass().add("modern-text-area");

        // Configurar el TextArea para que use todo el ancho
        textArea.setMaxWidth(Double.MAX_VALUE);

        // Calcular el número de líneas necesarias
        String[] lines = content.split("\n");
        StringBuilder wrappedText = new StringBuilder();
        int totalLines = 0;

        for (String line : lines) {
            int lineLength = line.length();
            int estimatedLines = (int) Math.ceil(lineLength / 60.0); // Asumiendo ~60 caracteres por línea
            totalLines += Math.max(1, estimatedLines);
            wrappedText.append(line).append("\n");
        }

        // Establecer la altura basada en el número de líneas
        double lineHeight = 20; // altura aproximada por línea en píxeles
        textArea.setPrefRowCount(totalLines);
        textArea.setPrefHeight((totalLines + 1) * lineHeight);
        textArea.setMinHeight((totalLines + 1) * lineHeight);

        // Deshabilitar la barra de desplazamiento usando CSS
        textArea.setStyle("-fx-scrollbar-vertical: false; -fx-scrollbar-horizontal: false;");

        // Configurar el DialogPane
        dialogPane.setContent(textArea);
        dialogPane.setPrefWidth(600);

        alert.showAndWait();
    }
    public boolean mostrarMensajeConfirmacion(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initStyle(StageStyle.TRANSPARENT);

        // Personalizar el DialogPane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                CoreViewController.class.getResource("/co/edu/uniquindio/gestionestudiantes/gestionestudiantesapp/css/modern-alerts.css").toExternalForm()
        );
        dialogPane.getStyleClass().addAll("modern-alert", "confirmation-alert");

        // Personalizar los botones
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.getStyleClass().add("confirm-button");

        Button cancelButton = (Button) dialogPane.lookupButton(ButtonType.CANCEL);
        cancelButton.getStyleClass().add("cancel-button");

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
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

    protected  <T> void initializeComboBox(ComboBox<T> comboBox,
                                           ObservableList<T> items,
                                           Function<T, String> displayFunction) {
        comboBox.setItems(items);
        comboBox.setCellFactory(lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : displayFunction.apply(item));
            }
        });
        comboBox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : displayFunction.apply(item));
            }
        });
    }

}
