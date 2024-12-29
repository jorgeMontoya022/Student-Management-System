package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.mapping;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Curso;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Estudiante;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IGestionEstudiantesMapper {
    IGestionEstudiantesMapper INSTANCE = Mappers.getMapper(IGestionEstudiantesMapper.class);

    @Named("estudianteToEstudianteDto")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "listaCursos", target = "listaCursos", qualifiedByName = "cursoToSimpleCursoDto")
    EstudianteDto estudianteToEstudianteDto(Estudiante estudiante);

    @Named("estudianteDtoToEstudiante")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "listaCursos", target = "listaCursos", qualifiedByName = "cursoDtoToSimpleCurso")
    Estudiante estudianteDtoToEstudiante(EstudianteDto estudianteDto);

    @Named("cursoToCursoDto")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "nombreProfesor", target = "nombreProfesor")
    @Mapping(source = "listaEstudiantes", target = "listaEstudiantes", qualifiedByName = "estudianteToSimpleEstudianteDto")
    CursoDto cursoToCursoDto(Curso curso);

    @Named("cursoDtoToCurso")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "nombreProfesor", target = "nombreProfesor")
    @Mapping(source = "listaEstudiantes", target = "listaEstudiantes", qualifiedByName = "estudianteDtoToSimpleEstudiante")
    Curso cursoDtoToCurso(CursoDto cursoDto);

    // Mapeos simplificados para romper la recursión
    @Named("cursoToSimpleCursoDto")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "nombreProfesor", target = "nombreProfesor")
    @Mapping(target = "listaEstudiantes", ignore = true)
    CursoDto cursoToSimpleCursoDto(Curso curso);

    @Named("cursoDtoToSimpleCurso")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "codigo", target = "codigo")
    @Mapping(source = "nombreProfesor", target = "nombreProfesor")
    @Mapping(target = "listaEstudiantes", ignore = true)
    Curso cursoDtoToSimpleCurso(CursoDto cursoDto);

    @Named("estudianteToSimpleEstudianteDto")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(target = "listaCursos", ignore = true)
    EstudianteDto estudianteToSimpleEstudianteDto(Estudiante estudiante);

    @Named("estudianteDtoToSimpleEstudiante")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "correo", target = "correo")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(target = "listaCursos", ignore = true)
    Estudiante estudianteDtoToSimpleEstudiante(EstudianteDto estudianteDto);

    @IterableMapping(qualifiedByName = "estudianteToEstudianteDto")
    List<EstudianteDto> getListaEstudiantesDto(List<Estudiante> listaEstudiantes);

    @IterableMapping(qualifiedByName = "estudianteDtoToEstudiante")
    List<Estudiante> getListaEstudiantes(List<EstudianteDto> listaEstudiantesDto);

    @IterableMapping(qualifiedByName = "cursoToCursoDto")
    List<CursoDto> getListaCursosDto(List<Curso> listaCursos);

    @IterableMapping(qualifiedByName = "cursoDtoToCurso")
    List<Curso> getListaCursos(List<CursoDto> listaCursosDto);
}

