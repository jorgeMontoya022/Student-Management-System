package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;

import java.io.Serializable;

public class Admin extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    public Admin(String nombre, String id, String correo, String telefono) {
        super(nombre, id, correo, telefono); // Llamada al constructor de Persona
    }

    public Admin() {
        super(null, null, null, null); // Constructor vacío con valores iniciales nulos
    }
}
