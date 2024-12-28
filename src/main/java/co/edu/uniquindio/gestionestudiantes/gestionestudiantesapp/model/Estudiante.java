package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Curso> listaCursos;

    public Estudiante(String nombre, String id, String correo, String telefono) {
        super(nombre, id, correo, telefono); // Llamada al constructor de Persona
        this.listaCursos = new ArrayList<>();
    }

    public Estudiante() {
        super(null, null, null, null); // Llamada al constructor de Persona
        this.listaCursos = new ArrayList<>();
    }

    public List<Curso> getListaCursos() {
        if (listaCursos == null) {
            listaCursos = new ArrayList<>();
        }
        return listaCursos;
    }
    public void setListaCursos(List<Curso> listaCursos) {
        this.listaCursos = listaCursos != null ? listaCursos : new ArrayList<>();
    }

    @Override
    public String toString() {
        return getId(); // Reutiliza el método de Persona
    }
}
