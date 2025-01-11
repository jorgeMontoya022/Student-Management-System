package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils.pdf;

import com.itextpdf.text.*;

public class PdfStyle {
    public static final BaseColor PRIMARY_COLOR = new BaseColor(70, 159, 255); // #469fff
    public static final BaseColor LIGHT_PRIMARY = new BaseColor(70, 159, 255, 34); // #469fff22
    public static final BaseColor BACKGROUND_COLOR = new BaseColor(248, 249, 250); // #f8f9fa
    public static final BaseColor TEXT_COLOR = new BaseColor(45, 52, 54); // #2d3436
    public static final BaseColor SECONDARY_TEXT = new BaseColor(102, 102, 102); // #666666

    // Fuentes predefinidas
    public static Font getTitleFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
        font.setColor(TEXT_COLOR);
        return font;
    }

    public static Font getSubtitleFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        font.setColor(PRIMARY_COLOR);
        return font;
    }

    public static Font getHeaderFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        font.setColor(TEXT_COLOR);
        return font;
    }

    public static Font getNormalFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 11);
        font.setColor(TEXT_COLOR);
        return font;
    }

    public static Font getSmallFont() {
        Font font = new Font(Font.FontFamily.HELVETICA, 9);
        font.setColor(SECONDARY_TEXT);
        return font;
    }
}
