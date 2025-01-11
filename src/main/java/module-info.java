module co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mapstruct.processor;
    requires org.mapstruct;
    requires java.desktop;
    requires java.logging;
    requires org.simplejavamail.core;
    requires org.simplejavamail;
    requires itextpdf;


    opens co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp to javafx.fxml;
    exports co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp;
    opens co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view to javafx.fxml;
    exports co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.view;

    opens co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils to javafx.fxml;
    exports co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils;
    exports co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.mapping;
    opens co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.mapping to org.mapstruct;
    opens co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;
    exports co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;


}