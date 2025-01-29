package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto;

import java.util.List;

public record CursoDto(
        String nombre,
        String codigo,
        ProfesorDto profesor,
        List<EstudianteDto> listaEstudiantes
) {
}
