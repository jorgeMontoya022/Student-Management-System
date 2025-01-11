package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.services;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils.pdf.EstudianteCursosPdfGenerator;

import java.io.FileOutputStream;
import java.util.List;

public class PdfExportService {
    public void exportarCursosEstudiante(
            String filePath,
            EstudianteDto estudiante,
            List<CursoDto> cursos,
            String periodoAcademico) throws Exception {

        try (FileOutputStream output = new FileOutputStream(filePath)) {
            EstudianteCursosPdfGenerator pdfGenerator =
                    new EstudianteCursosPdfGenerator(output, estudiante, cursos, periodoAcademico);
            pdfGenerator.generarPdf();
        }
    }
}
