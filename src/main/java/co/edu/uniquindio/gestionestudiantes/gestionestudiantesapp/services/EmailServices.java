package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.services;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils.EmailUtil;

public class EmailServices {
    private static final String INSTITUCION_NOMBRE = "Universidad TE AYUDAMOS";
    private static final String FIRMA_OFICIAL = """
            
            ------------------------------------------
            Universidad TE AYUDAMOS
            Educación de Calidad para un Futuro Mejor
            Tel: (+57) 6 7359300
            Email: contacto@teayudamos.edu.co
            Dirección: Carrera 15 Calle 12 Norte
            Armenia, Quindío, Colombia
            www.teayudamos.edu.co
            ------------------------------------------
            Este correo es confidencial y está destinado únicamente para el uso de la persona o entidad a la que está dirigido.
            """;

    public void enviarCorreoBienvenida(EstudianteDto estudiante) {
        String asunto = "¡Bienvenido/a a " + INSTITUCION_NOMBRE + "!";
        String mensaje = construirMensajeBienvenida(estudiante);

        EmailUtil emailUtil = new EmailUtil(estudiante.correo(), asunto, mensaje);
        emailUtil.sendNotification();
    }

    private String construirMensajeBienvenida(EstudianteDto estudiante) {
        return String.format("""
                        Estimado/a %s,
                                
                        ¡Bienvenido/a a %s! Nos complace informarte que has sido registrado/a exitosamente en nuestro sistema.
                                
                        Tus datos registrados son:
                        - Nombre: %s
                        - ID: %s
                        - Correo: %s
                        - Teléfono: %s
                                
                        Si tienes alguna pregunta o necesitas asistencia, no dudes en contactarnos.
                                
                        ¡Te deseamos muchos éxitos en tu proceso educativo!
                                
                        Saludos cordiales,
                        Departamento de Registro y Control
                        Universidad TE AYUDAMOS%s
                        """,
                estudiante.nombre(),
                INSTITUCION_NOMBRE,
                estudiante.nombre(),
                estudiante.id(),
                estudiante.correo(),
                estudiante.telefono(),
                FIRMA_OFICIAL
        );
    }

    public void enviarCorreoEliminacion(EstudianteDto estudiante) {
        String asunto = "Notificación de eliminación de registro - " + INSTITUCION_NOMBRE;
        String mensaje = construirMensajeEliminacion(estudiante);

        EmailUtil emailUtil = new EmailUtil(estudiante.correo(), asunto, mensaje);
        emailUtil.sendNotification();
    }

    private String construirMensajeEliminacion(EstudianteDto estudiante) {
        return String.format("""
            Estimado/a %s,
                    
            Te informamos que tu registro en %s ha sido eliminado del sistema.
                    
            Datos del registro eliminado:
            - Nombre: %s
            - ID: %s
            - Correo: %s
            - Teléfono: %s
                    
            Si consideras que esto ha sido un error o necesitas información adicional, 
            por favor contáctanos respondiendo a este correo.
                    
            Saludos cordiales,
            Departamento de Registro y Control
            Universidad TE AYUDAMOS%s
            """,
                estudiante.nombre(),
                INSTITUCION_NOMBRE,
                estudiante.nombre(),
                estudiante.id(),
                estudiante.correo(),
                estudiante.telefono(),
                FIRMA_OFICIAL
        );
    }

    public void enviarCorreoActualizacion(EstudianteDto estudianteAntiguo, EstudianteDto estudianteNuevo) {
        String asunto = "Actualización de datos - " + INSTITUCION_NOMBRE;
        String mensaje = construirMensajeActualizacion(estudianteAntiguo, estudianteNuevo);

        EmailUtil emailUtil = new EmailUtil(estudianteNuevo.correo(), asunto, mensaje);
        emailUtil.sendNotification();
    }

    private String construirMensajeActualizacion(EstudianteDto estudianteAntiguo, EstudianteDto estudianteNuevo) {
        return String.format("""
                Estimado/a %s,
                
                Te informamos que tus datos han sido actualizados exitosamente en nuestro sistema académico.
                
                Datos anteriores:
                - Nombre: %s
                - ID: %s
                - Correo: %s
                - Teléfono: %s
                
                Datos actualizados:
                - Nombre: %s
                - ID: %s
                - Correo: %s
                - Teléfono: %s
                
                Si no reconoces estos cambios o detectas alguna inconsistencia, por favor contáctanos.
                
                Saludos cordiales,
                Departamento de Registro y Control
                Universidad TE AYUDAMOS%s
                """,
                estudianteNuevo.nombre(),
                estudianteAntiguo.nombre(),
                estudianteAntiguo.id(),
                estudianteAntiguo.correo(),
                estudianteAntiguo.telefono(),
                estudianteNuevo.nombre(),
                estudianteNuevo.id(),
                estudianteNuevo.correo(),
                estudianteNuevo.telefono(),
                FIRMA_OFICIAL
        );
    }
}