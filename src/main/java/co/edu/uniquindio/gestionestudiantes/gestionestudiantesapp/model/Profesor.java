package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Profesor extends Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Curso> listaCursos;

    public Profesor () {
        this.listaCursos = new ArrayList<>();
    }

    public Profesor(String nombre, String id, String correo, String telefono) {
        super(nombre, id, correo, telefono);

    }

    public List<Curso> getListaCursos() {
        if (listaCursos == null) {
            return new ArrayList<>();
        }
        return listaCursos;
    }

    public void setListaCursos(List<Curso> listaCursos) {
        if (listaCursos != null) {
            this.listaCursos = listaCursos;
        } else {
            this.listaCursos = new ArrayList<>();
        }
    }
}
