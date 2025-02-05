package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.exceptions.CursoException;
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

    private List<Profesor> listaProfesores;

    public Universidad() {
        this.listaCursos = new ArrayList<>();
        this.listaEstudiantes = new ArrayList<>();
        this.listaProfesores = new ArrayList<>();
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

    public List<Profesor> getListaProfesores() {
        return listaProfesores;
    }

    public void setListaProfesores(List<Profesor> listaProfesores) {
        this.listaProfesores = listaProfesores;
    }

    public List<Curso> getCursosEstudiante(Estudiante estudiante) {
        if (estudiante == null) {
            return new ArrayList<>();
        }
        if (estudiante.getListaCursos() == null) {
            return new ArrayList<>();
        }
        // Crear una nueva lista para evitar referencias directas
        return new ArrayList<>(estudiante.getListaCursos());
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

    public boolean actualizarEstudiante(String id, Estudiante estudiante) throws EstudianteException {
        Estudiante estudianteActual = buscarEstudianteId(id);
        if (estudianteActual == null) {
            throw new EstudianteException("El estudiante a actualizar no existe");
        } else {
            estudianteActual.setNombre(estudiante.getNombre());
            estudianteActual.setTelefono(estudiante.getTelefono());
            estudianteActual.setId(estudiante.getId());
            estudianteActual.setCorreo(estudiante.getCorreo());

            if (estudiante.getListaCursos() != null) {
                estudianteActual.getListaCursos().addAll(estudiante.getListaCursos());

            }
            return true;
        }
    }

    private Estudiante buscarEstudianteId(String id) {
        for (Estudiante estudiante : getListaEstudiantes()) {
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

    public boolean verificarCursoExiste(String codigo) {
        for (Curso curso : listaCursos) {
            if (curso.getCodigo().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public void addCurso(Curso curso) throws CursoException {
        if (curso == null) {
            throw new CursoException("El curso no puede ser nulo");

        }
        listaCursos.add(curso);
    }

    public Persona validarAcceso(String correo, String id) throws Exception {
        if (admin != null && admin.getCorreo().equals(correo) &&
                admin.getId().equals(id)) {
            return admin;
        }
        for (Estudiante estudiante : listaEstudiantes) {
            if (verificarEstudianteExiste(id)) {
                if (estudiante.getCorreo().equals(correo) && estudiante.getId().equals(id)) {
                    return estudiante;

                }
            } else {
                throw new Exception("El usuario no existe");
            }
        }
        throw new Exception("Identificación o documento de identidad incorrecto");
    }

    public boolean asignarCursoEstudiante(String idEstudiante, String codigoCurso) {
        Estudiante estudiante = buscarEstudianteId(idEstudiante);
        Curso curso = verificarCursoExisteCodigo(codigoCurso);

        // Verificar que tanto el estudiante como el curso existan
        if (estudiante == null || curso == null) {
            return false;
        }

        // Verificar si el estudiante ya tiene el curso
        for (Curso cursoEstudiante : estudiante.getListaCursos()) {
            if (cursoEstudiante.getCodigo().equals(codigoCurso)) {
                return false; // El curso ya está asignado
            }
        }

        // Evitar NullPointerException asegurando que las listas estén inicializadas
        if (estudiante.getListaCursos() == null) {
            estudiante.setListaCursos(new ArrayList<>());
        }
        if (curso.getListaEstudiantes() == null) {
            curso.setListaEstudiantes(new ArrayList<>());
        }

        // Asignar el curso al estudiante y viceversa
        estudiante.getListaCursos().add(curso);
        curso.getListaEstudiantes().add(estudiante);

        return true;


    }

    private Curso verificarCursoExisteCodigo(String codigoCurso) {

        for (Curso curso : listaCursos) {
            if (curso.getCodigo().equals(codigoCurso)) {
                return curso;
            }
        }
        return null;
    }

    public boolean tieneCursoAsignado(String idEstudiante, String codigoCurso) {
        Estudiante estudiante = buscarEstudianteId(idEstudiante);
        if (estudiante != null) {
            for (Curso curso : estudiante.getListaCursos()) {
                if (curso.getCodigo().equals(codigoCurso)) {
                    return true;
                }
            }
        }
        return false;
    }
}
