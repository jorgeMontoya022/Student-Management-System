package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.session;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Persona;

public class Sesion {
    private Persona persona;
    private static Sesion INSTANCE;

    // Constructor privado
    private Sesion() {
    }

    // Método para obtener la única instancia de la clase (patrón Singleton)
    public static Sesion getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Sesion();
        }
        return INSTANCE;
    }

    // Setter para la persona en la sesión
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    // Getter para obtener la persona en la sesión
    public Persona getPersona() {
        return persona;
    }

    // Método para cerrar la sesión (eliminar la persona asociada)
    public void closeSesion() {
        this.persona = null;
    }
}
