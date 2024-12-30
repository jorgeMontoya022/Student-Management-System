package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.exceptions.EstudianteException;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.mapping.IGestionEstudiantesMapper;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Curso;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Estudiante;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Persona;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Universidad;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils.BackupUtil;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils.PersistenceUtil;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils.UniversidadUtils;

import java.util.ArrayList;
import java.util.List;

public class ModelFactory {

    Universidad universidad;
    IGestionEstudiantesMapper gestionEstudiantesMapper = IGestionEstudiantesMapper.INSTANCE;


    private static class SinglentonHolder {
        private static final ModelFactory eINSTANCE = new ModelFactory();
    }

    public static ModelFactory getInstance() {
        return SinglentonHolder.eINSTANCE;
    }

    private ModelFactory() {
        System.out.println("singleton class invocation");

        loadXMLResource();

        BackupUtil.backupXMLFile(PersistenceUtil.UNIVERSIDAD_XML_FILE_PATH);


        saveXMLResource();

        if (universidad == null) {
            initializeData();
            saveXMLResource();
        }
        registerSystemActions("Login", 1, "login");


    }

    private void initializeData() {
        universidad = UniversidadUtils.initializeData();
    }

    private void registerSystemActions(String logMessage, int level, String action) {
        PersistenceUtil.saveLogRecord(logMessage, level, action);
    }

    private void saveXMLResource() {
        PersistenceUtil.saveXMLUniversidadResourse(universidad);
    }

    private void loadXMLResource() {
        universidad = PersistenceUtil.loadXMLUniversadResource();
    }

    public List<EstudianteDto> getEstudiantesDto() {
        List<EstudianteDto> estudiantesDto = new ArrayList<>();
        estudiantesDto.addAll(gestionEstudiantesMapper.getListaEstudiantesDto(universidad.getListaEstudiantes()));
        return estudiantesDto;
    }

    public List<CursoDto> getCursosEstudiante(EstudianteDto estudianteDto) {
        Estudiante estudiante = gestionEstudiantesMapper.estudianteDtoToEstudiante(estudianteDto);
        List<CursoDto> listaCursosDto = gestionEstudiantesMapper.getListaCursosDto(universidad.getCursosEstudiante(estudiante));
        return listaCursosDto;
    }

    public boolean agregarEstudiante(EstudianteDto estudianteDto) {
        try {
            if (universidad.verificarEstudianteExiste(estudianteDto.id())) {
                return false;
            }
            Estudiante estudiante = gestionEstudiantesMapper.estudianteDtoToEstudiante(estudianteDto);
            registerSystemActions("Estudiante agregado: " + estudianteDto.id(), 1, "agregarEstudiante");
            universidad.addEstudiante(estudiante);
            saveXMLResource();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            registerSystemActions(e.getMessage(), 3, "agregarEstudiante");
            return false;

        }
    }

    public boolean eliminarEstudiante(EstudianteDto estudianteSeleccionado) {
        boolean flagExist = false;
        try {
            flagExist = universidad.eliminarEstudiante(estudianteSeleccionado.id());

            if (flagExist) {
                registerSystemActions("Usuario eliminado: " + estudianteSeleccionado.id(), 1, "removeUser");
                saveXMLResource();
            } else {
                registerSystemActions("Intento de eliminar un usuario no existente: " + estudianteSeleccionado.id(), 2, "removeUser");
            }
        } catch (EstudianteException e) {
            registerSystemActions(e.getMessage(), 3, "eliminarEstudiante");
            e.printStackTrace();
        }
        return flagExist;
    }

    public boolean actualizarEstudiante(EstudianteDto estudianteSeleccionado, EstudianteDto estudianteDto) {
        boolean estudianteActualizado = false;
        try {
            Estudiante estudiante = gestionEstudiantesMapper.estudianteDtoToEstudiante(estudianteDto);
            estudianteActualizado = universidad.actualizarEstudiante(estudianteSeleccionado.id(), estudiante);
            if(estudianteActualizado) {
                registerSystemActions("Usuario actualizado: "+ estudianteDto.nombre(), 1, "actualizarUsario");
                saveXMLResource();
            } else {
                registerSystemActions("Error al actualizar al usario: "+estudianteDto.nombre(), 3, "actualizarUsario");
            }
        } catch (Exception e) {
            registerSystemActions(e.getMessage(), 3, "actualizarUsario");
            e.printStackTrace();
        }
        return estudianteActualizado;
    }

    public List<CursoDto> getCursos() {
        List<Curso> cursos = universidad.getListaCursos();
        return cursos != null ? gestionEstudiantesMapper.getListaCursosDto(cursos) : new ArrayList<>();
    }

    public List<EstudianteDto> getEstudiantesCurso(CursoDto cursoSeleccionado) {
        Curso curso = gestionEstudiantesMapper.cursoDtoToCurso(cursoSeleccionado);
        List<EstudianteDto>listaEstudiantes = gestionEstudiantesMapper.getListaEstudiantesDto(universidad.getEstudiantesCurso(curso));
        return listaEstudiantes;
    }

    public boolean agregarCursos(CursoDto cursoDto) {
        try {
            if (universidad.verificarCursoExiste(cursoDto.codigo())) {
                return false;
            }
            Curso curso = gestionEstudiantesMapper.cursoDtoToCurso(cursoDto);
            universidad.addCurso(curso);
            registerSystemActions("Curso agregado: "+ cursoDto.codigo(), 1, "AgregarCurso");
            saveXMLResource();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            registerSystemActions(e.getMessage(), 3, "AgregarCurso");
            return false;
        }
    }

    public Persona validarAcceso(String correo, String id) throws Exception {
        return universidad.validarAcceso(correo, id);
    }



}
