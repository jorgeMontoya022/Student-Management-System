package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.controller;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Persona;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.session.Sesion;

public class LoginController extends CoreController {

    public LoginController(){
        super();
    }
    public Persona validarAcceso(String correo, String id) throws Exception {
        return modelFactory.validarAcceso(correo, id);
    }

    public void guardarSesion(Persona usuarioValidado) {
        Sesion.getInstance().setPersona(usuarioValidado);
    }
}
