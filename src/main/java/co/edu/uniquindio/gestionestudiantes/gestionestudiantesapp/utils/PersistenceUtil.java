package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils;

import co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.model.Universidad;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PersistenceUtil {
    private static final ResourceBundle config = ResourceBundle.getBundle("co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.config.config");

    public static final String BACKUP_FILE_PATH = config.getString("backupFilePath");
    public static final String UNIVERSIDAD_XML_FILE_PATH = config.getString("universidadXmlFilePath");
    public static final String LOG_FILE_PATH = config.getString("logFilePath");
    public static final String ADMIN_EMAIL = config.getString("admin.correo");
    public static final String ADMIN_ID = config.getString("admin.id");

    public static void saveLogRecord(String logMessage, int level, String action) {
        FileUtil.saveLogRecord(logMessage, level, action, LOG_FILE_PATH);
    }

    public static Universidad loadXMLUniversadResource() {
        return loadXMLVirtualWalletResource(UNIVERSIDAD_XML_FILE_PATH);
    }

    private static Universidad loadXMLVirtualWalletResource(String universidadXmlFilePath) {
        Universidad universidad = null;
        try {
            universidad = (Universidad) FileUtil.loadSerializedXMLResource(universidadXmlFilePath);
        } catch (Exception e) {
            File backupDir = new File(BackupUtil.BACKUP_DIR);
            File[] backupUtils = backupDir.listFiles((dir, name) -> name.endsWith(".xml"));
            if (backupUtils != null && backupUtils.length > 0) {
                Arrays.sort(backupUtils, Comparator.comparingLong(File::lastModified).reversed());
                for (File backupFile : backupUtils) {
                    try {
                        universidad = (Universidad) FileUtil.loadSerializedXMLResource(backupFile.getPath());
                        if (universidad != null) {
                            System.out.println("Loaded backup from: " + backupFile.getPath());
                        }
                    } catch (Exception backupException) {
                        backupException.printStackTrace();
                    }
                }
            }
            System.out.println("No backups found. Creating new XML.");
        }
        return universidad;
    }

    public static void saveXMLUniversidadResourse(Universidad universidad) {
        try {
            FileUtil.saveSerializedXMLResource(UNIVERSIDAD_XML_FILE_PATH, universidad);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
