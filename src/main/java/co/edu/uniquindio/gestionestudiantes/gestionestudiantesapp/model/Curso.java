package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String codigo;
    private Profesor profesor;
    private List<Estudiante> listaEstudiantes;

    // Constructor por defecto necesario para XMLEncoder
    public Curso() {
        this.listaEstudiantes = new ArrayList<>();
    }

    public Curso(String nombre, String codigo, Profesor profesor) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.profesor = profesor;
        this.listaEstudiantes = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Estudiante> getListaEstudiantes() {
        if (listaEstudiantes == null) {
            listaEstudiantes = new ArrayList<>();
        }
        return listaEstudiantes;
    }

    public void setListaEstudiantes(List<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes != null ? listaEstudiantes : new ArrayList<>();
    }


}
