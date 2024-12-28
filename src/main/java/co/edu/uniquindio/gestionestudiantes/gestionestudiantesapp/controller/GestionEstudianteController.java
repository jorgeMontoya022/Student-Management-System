package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;

import java.util.List;

public class GestionEstudianteController extends CoreController{

    public GestionEstudianteController() {
        super();
    }

    public List<EstudianteDto> getEstudiantes() {
        return modelFactory.getEstudiantesDto();
    }

    public List<CursoDto> getCursosEstudiante(EstudianteDto estudiante) {
        return modelFactory.getCursosEstudiante(estudiante);
    }

    public boolean agregarEstudiante(EstudianteDto estudianteDto) {
        return modelFactory.agregarEstudiante(estudianteDto);
    }

    public boolean eliminarEstudiante(EstudianteDto estudianteSeleccionado) {
        return modelFactory.eliminarEstudiante(estudianteSeleccionado);
    }

    public boolean actualizarEstudiante(EstudianteDto estudianteSeleccionado, EstudianteDto estudianteDto) {
        return modelFactory.actualizarEstudiante(estudianteSeleccionado, estudianteDto);
    }
}
