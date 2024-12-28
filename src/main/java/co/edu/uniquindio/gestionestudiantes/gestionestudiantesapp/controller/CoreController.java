package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller;

public abstract class CoreController {

    ModelFactory modelFactory;

    public CoreController() {
        modelFactory = ModelFactory.getInstance();
    }
}
