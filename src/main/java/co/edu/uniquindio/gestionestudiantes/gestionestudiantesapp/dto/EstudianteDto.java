package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto;

import java.util.List;

public record EstudianteDto(
        String nombre,
        String id,
        String correo,
        String telefono,
        List<CursoDto> listaCursos
) {
}
