package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils.pdf;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.CursoDto;
import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.dto.EstudianteDto;


import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class EstudianteCursosPdfGenerator {

    private final Document document;
    private final EstudianteDto estudiante;
    private final List<CursoDto> cursos;
    private final String periodoAcademico;

    public EstudianteCursosPdfGenerator(
            OutputStream outputStream,
            EstudianteDto estudiante,
            List<CursoDto> cursos,
            String periodoAcademico) throws DocumentException {
        this.document = new Document(PageSize.A4, 36, 36, 60, 36);
        PdfWriter.getInstance(document, outputStream);
        this.estudiante = estudiante;
        this.cursos = cursos;
        this.periodoAcademico = periodoAcademico;
    }

    public void generarPdf() throws DocumentException {
        document.open();
        agregarEncabezadoInstitucional();
        agregarLineaSeparadora();
        agregarInformacionEstudiante();
        agregarTablaCursos();
        agregarPiePagina();
        document.close();
    }

    private void agregarEncabezadoInstitucional() throws DocumentException {
        // Crear tabla para el encabezado institucional
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(100);

        // Celda para el nombre de la institución
        PdfPCell institutionCell = new PdfPCell();
        institutionCell.setPadding(20);
        institutionCell.setBorder(Rectangle.NO_BORDER);
        institutionCell.setBackgroundColor(PdfStyle.PRIMARY_COLOR);

        // Nombre de la institución
        Paragraph institutionName = new Paragraph();
        Font institutionFont = new Font(Font.FontFamily.HELVETICA, 30, Font.BOLD, BaseColor.WHITE);
        institutionName.add(new Phrase("TE AYUDAMOS", institutionFont));
        institutionName.setAlignment(Element.ALIGN_CENTER);
        institutionCell.addElement(institutionName);

        // Subtítulo del reporte
        Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.WHITE);
        Paragraph subtitle = new Paragraph("Reporte de Cursos Matriculados", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingBefore(10);
        institutionCell.addElement(subtitle);

        headerTable.addCell(institutionCell);
        document.add(headerTable);
    }

    private void agregarLineaSeparadora() throws DocumentException {
        // Añadir espacio después del encabezado
        document.add(new Paragraph("\n"));

        // Crear línea separadora con degradado
        PdfPTable lineTable = new PdfPTable(1);
        lineTable.setWidthPercentage(100);
        PdfPCell lineCell = new PdfPCell();
        lineCell.setFixedHeight(3f);
        lineCell.setBackgroundColor(PdfStyle.LIGHT_PRIMARY);
        lineCell.setBorder(Rectangle.NO_BORDER);
        lineTable.addCell(lineCell);
        document.add(lineTable);

        document.add(new Paragraph("\n"));
    }

    private void agregarInformacionEstudiante() throws DocumentException {
        PdfPTable infoTable = new PdfPTable(1);
        infoTable.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(15);
        cell.setBackgroundColor(PdfStyle.BACKGROUND_COLOR);
        cell.setBorder(Rectangle.NO_BORDER);

        // Información del estudiante con mejor formato
        Paragraph info = new Paragraph();
        info.add(new Phrase("INFORMACIÓN DEL ESTUDIANTE\n", PdfStyle.getSubtitleFont()));
        info.add(Chunk.NEWLINE);
        info.add(new Phrase("Nombre: ", PdfStyle.getHeaderFont()));
        info.add(new Phrase(estudiante.nombre().toUpperCase() + "\n\n", PdfStyle.getSubtitleFont()));
        info.add(new Phrase("Periodo Académico: ", PdfStyle.getHeaderFont()));
        info.add(new Phrase(periodoAcademico + "\n\n", PdfStyle.getNormalFont()));
        info.add(new Phrase("Total de cursos matriculados: ", PdfStyle.getHeaderFont()));
        info.add(new Phrase(String.valueOf(cursos.size()), PdfStyle.getNormalFont()));

        cell.addElement(info);
        infoTable.addCell(cell);
        document.add(infoTable);
        document.add(new Paragraph("\n"));
    }

    private void agregarTablaCursos() throws DocumentException {
        // Título de la sección de cursos
        Paragraph cursosTitle = new Paragraph("CURSOS MATRICULADOS", PdfStyle.getSubtitleFont());
        cursosTitle.setSpacingBefore(10);
        cursosTitle.setSpacingAfter(10);
        document.add(cursosTitle);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        float[] columnWidths = {2, 4, 3};
        table.setWidths(columnWidths);

        agregarEncabezadosTabla(table);
        agregarContenidoTabla(table);

        document.add(table);
    }

    private void agregarEncabezadosTabla(PdfPTable table) {
        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(PdfStyle.PRIMARY_COLOR);
        headerCell.setPadding(12);
        headerCell.setBorderColor(BaseColor.WHITE);

        // Fuente blanca para los encabezados
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);

        for (String header : new String[]{"Código", "Nombre del Curso", "Profesor"}) {
            headerCell.setPhrase(new Phrase(header, headerFont));
            table.addCell(headerCell);
        }
    }

    private void agregarContenidoTabla(PdfPTable table) {
        boolean alternarColor = false;
        for (CursoDto curso : cursos) {
            agregarCeldaTabla(table, curso.codigo(), alternarColor);
            agregarCeldaTabla(table, curso.nombre(), alternarColor);
            agregarCeldaTabla(table, curso.profesor().nombre(), alternarColor);
            alternarColor = !alternarColor;
        }
    }

    private void agregarCeldaTabla(PdfPTable table, String contenido, boolean alternarColor) {
        PdfPCell cell = new PdfPCell(new Phrase(contenido, PdfStyle.getNormalFont()));
        cell.setPadding(10);
        cell.setBorderColor(BaseColor.WHITE);
        if (alternarColor) {
            cell.setBackgroundColor(PdfStyle.BACKGROUND_COLOR);
        }
        table.addCell(cell);
    }

    private void agregarPiePagina() throws DocumentException {
        document.add(new Paragraph("\n"));

        PdfPTable footerTable = new PdfPTable(1);
        footerTable.setWidthPercentage(100);

        PdfPCell footerCell = new PdfPCell();
        footerCell.setPadding(8);
        footerCell.setBorder(Rectangle.TOP);
        footerCell.setBorderColor(PdfStyle.LIGHT_PRIMARY);

        Paragraph footer = new Paragraph();
        footer.add(new Phrase("Documento generado por TE AYUDAMOS\n", PdfStyle.getSmallFont()));
        footer.add(new Phrase("Fecha de generación: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
                PdfStyle.getSmallFont()));

        footerCell.addElement(footer);
        footerTable.addCell(footerCell);
        document.add(footerTable);
    }
}
