package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.exceptions.EstudianteException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Universidad implements Serializable {
    private static final long serialVersionUID = 1L;
    private Admin admin;
    private List<Estudiante> listaEstudiantes;
    private List<Curso> listaCursos;

    public Universidad() {
        this.listaCursos = new ArrayList<>();
        this.listaEstudiantes = new ArrayList<>();
    }

    public List<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(List<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public List<Curso> getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(List<Curso> listaCursos) {
        this.listaCursos = listaCursos;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Curso> getCursosEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            return new ArrayList<>();
        }
        if (estudiante.getListaCursos() == null) {
            return new ArrayList<>();
        }
        System.out.println("Cursos del estudiante: " + estudiante.getListaCursos().size());
        return estudiante.getListaCursos();
    }


    public void addEstudiante(Estudiante estudiante) throws EstudianteException {
        if (estudiante == null) {
            throw new EstudianteException("El usuario no puede ser nulo");
        }
        getListaEstudiantes().add(estudiante);
    }

    public boolean verificarEstudianteExiste(String id) {
        for (Estudiante estudiante : getListaEstudiantes()) {
            if (estudiante.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean eliminarEstudiante(String id) throws EstudianteException {
        Iterator<Estudiante> iterator = getListaEstudiantes().iterator();
        while (iterator.hasNext()) {
            Estudiante estudiante = iterator.next();
            if (estudiante.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        throw new EstudianteException("Estudiante con ID " + id + "no encontrado");
    }

    public boolean actualizarEstudiante(String id, Estudiante estudiante) throws EstudianteException{
        Estudiante estudianteActual = buscarEstudianteId(id);
        if(estudianteActual == null) {
            throw new EstudianteException("El estudiante a actualizar no existe");
        } else {
            estudianteActual.setNombre(estudiante.getNombre());
            estudianteActual.setTelefono(estudiante.getTelefono());
            estudianteActual.setId(estudiante.getId());
            estudianteActual.setCorreo(estudiante.getCorreo());

            if(estudiante.getListaCursos() != null) {
                estudianteActual.getListaCursos().addAll(estudiante.getListaCursos());

            }
            return true;
        }
    }

    private Estudiante buscarEstudianteId(String id) {
        for (Estudiante estudiante: getListaEstudiantes()) {
            if (estudiante.getId().equals(id)) {
                return estudiante;
            }
        }
        return null;
    }

    public List<Estudiante> getEstudiantesCurso(Curso curso) {
        if (curso.getListaEstudiantes() == null) {
            return new ArrayList<>();
        } else {
            System.out.println("Estudiantes del curso: " + curso.getListaEstudiantes().size());
            return curso.getListaEstudiantes();
        }
    }
}
