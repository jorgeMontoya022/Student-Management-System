package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.*;

import java.util.ArrayList;

public class UniversidadUtils {
    public static Universidad initializeData() {

        Estudiante estudiante1 = new Estudiante(
                "Jorge William Montoya",
                "1097032932",
                "jorgetoro708@gmail.com",
                "3244544139"
        );

        Estudiante estudiante2 = new Estudiante(
                "Mariana López Cardona",
                "1095023489",
                "marianalopez98@gmail.com",
                "3117896543"
        );

        Estudiante estudiante3 = new Estudiante(
                "Carlos Andrés Valencia",
                "1098056781",
                "carlosvalencia77@gmail.com",
                "3005678912"
        );

        Profesor profesor1 = new Profesor(
                "Juan Diego Mayorca",
                "1098788900",
                "juandie1243@gmail.com",
                "3145678900"
        );

        Profesor profesor2 = new Profesor(
                "Carlos Alberto Ramírez",
                "1098765432",
                "carlos.ramirez@gmail.com",
                "3101234567"
        );

        Profesor profesor3 = new Profesor(
                "Marcela Fernanda Gómez",
                "1002345678",
                "marcela.gomez@gmail.com",
                "3127654321"
        );

        Profesor profesor4 = new Profesor(
                "Andrés Felipe Torres",
                "1011121314",
                "andres.torres@gmail.com",
                "3156789012"
        );

        Profesor profesor5 = new Profesor(
                "Luisa Fernanda Pérez",
                "1020304050",
                "luisa.perez@gmail.com",
                "3209876543"
        );


        Admin admin = new Admin(
                "admin",
                PersistenceUtil.ADMIN_ID,
                PersistenceUtil.ADMIN_EMAIL,
                "3207748610");

        Curso curso1 = new Curso(
                "Programación Orientada a Objetos",
                "CS101",
                profesor1
        );

        Curso curso2 = new Curso(
                "Estructuras de Datos",
                "CS102",
                profesor2
        );

        Curso curso3 = new Curso(
                "Bases de Datos",
                "CS103",
                profesor3
        );

        Curso curso4 = new Curso(
                "Ingeniería de Software",
                "CS104",
                profesor4
        );

        Curso curso5 = new Curso(
                "Redes de Computadores",
                "CS105",
                profesor5
        );

        // Asignar estudiantes a cursos y cursos a estudiantes

        curso1.getListaEstudiantes().add(estudiante1);
        curso1.getListaEstudiantes().add(estudiante3);
        curso2.getListaEstudiantes().add(estudiante2);
        curso2.getListaEstudiantes().add(estudiante1);
        curso2.getListaEstudiantes().add(estudiante3);
        curso3.getListaEstudiantes().add(estudiante2);
        curso3.getListaEstudiantes().add(estudiante3);
        curso3.getListaEstudiantes().add(estudiante1);

        estudiante1.getListaCursos().add(curso1);
        estudiante1.getListaCursos().add(curso3);
        estudiante1.getListaCursos().add(curso2);
        estudiante2.getListaCursos().add(curso2);
        estudiante2.getListaCursos().add(curso3);
        estudiante3.getListaCursos().add(curso1);
        estudiante3.getListaCursos().add(curso2);
        estudiante3.getListaCursos().add(curso3);


        // Crear listas de estudiantes, profesores y cursos
        ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();
        listaEstudiantes.add(estudiante1);
        listaEstudiantes.add(estudiante2);
        listaEstudiantes.add(estudiante3);

        ArrayList<Curso> listaCursos = new ArrayList<>();
        listaCursos.add(curso1);
        listaCursos.add(curso2);
        listaCursos.add(curso3);
        listaCursos.add(curso4);
        listaCursos.add(curso5);

        ArrayList<Profesor> listaProfesores = new ArrayList<>();
        listaProfesores.add(profesor1);
        listaProfesores.add(profesor2);
        listaProfesores.add(profesor3);
        listaProfesores.add(profesor4);
        listaProfesores.add(profesor5);

        // Crear la universidad
        Universidad universidad = new Universidad();
        universidad.setListaEstudiantes(listaEstudiantes);
        universidad.setListaCursos(listaCursos);
        universidad.setListaProfesores(listaProfesores);
        universidad.setAdmin(admin);

        return universidad;
    }
}
