package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.ProfesorDto;

import java.util.List;

public class GestionProfesorController extends CoreController{
    public GestionProfesorController(){
        super();
    }

    public List<ProfesorDto> getProfesores() {
        return modelFactory.getProfesores();
    }
}
