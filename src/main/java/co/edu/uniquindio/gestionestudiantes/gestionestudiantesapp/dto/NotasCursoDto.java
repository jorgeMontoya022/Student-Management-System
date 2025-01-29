// NotasCursoDto.java
package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto;

public record NotasCursoDto(
        String idEstudiante,
        String codigoCurso,
        double nota1,
        double nota2,
        double nota3,
        double nota4,
        double promedio){

}
