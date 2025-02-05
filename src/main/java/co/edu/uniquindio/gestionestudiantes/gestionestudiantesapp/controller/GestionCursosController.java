package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.ProfesorDto;

import java.util.List;

public class GestionCursosController extends CoreController {
    public GestionCursosController() {
        super();
    }

    public List<CursoDto> getCursos() {
        return modelFactory.getCursos();
    }

    public List<EstudianteDto> getEstudiantesCurso(CursoDto cursoSeleccionado) {
        return modelFactory.getEstudiantesCurso(cursoSeleccionado);
    }

    public boolean agregarCursos(CursoDto cursoDto) {
        return modelFactory.agregarCursos(cursoDto);
    }

    public List<EstudianteDto> getEstudiantes() {
        return modelFactory.getEstudiantesDto();
    }

    public List<CursoDto> getCursosEstudiante(EstudianteDto estudianteSeleccionado) {
        List<CursoDto> cursos = modelFactory.getCursosEstudiante(estudianteSeleccionado);
        System.out.println("Cursos obtenidos para el estudiante: " + cursos); // Depuración
        return cursos;
    }

    public boolean asignarCursoEstudiante(String idEstudiante, String codigoCurso) {
        return modelFactory.asignarCursoEstudiante(idEstudiante, codigoCurso);
    }

    public boolean tieneCursoAsignado(String idEstudiante, String codigoCurso) {
        return modelFactory.tieneCursoAsignado(idEstudiante, codigoCurso);
    }

    public List<ProfesorDto> getProfesores() {
        return modelFactory.getProfesores();
    }
}
