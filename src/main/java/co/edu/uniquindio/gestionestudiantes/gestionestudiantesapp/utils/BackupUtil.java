package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

import static co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils.PersistenceUtil.BACKUP_FILE_PATH;

public class BackupUtil {
    protected static final String BACKUP_DIR = BACKUP_FILE_PATH;
    private static final int MAX_BACKUPS = 3;

    public static void backupXMLFile(String originalFilePath) {
        try {
            if (Files.size(Paths.get(originalFilePath)) == 0) {
                System.out.println("The XML file is empty, no backup will be created.");
                return;
            }

            File backupDir = new File(BACKUP_DIR);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
            }

            File[] backupFiles = backupDir.listFiles((dir, name) -> name.endsWith(".xml"));
            if (backupFiles != null && backupFiles.length >= MAX_BACKUPS) {
                Arrays.sort(backupFiles, Comparator.comparing(File::lastModified));
                backupFiles[0].delete();
            }

            String originalFileName = new File(originalFilePath).getName();
            String baseName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy_HH_mm_ss"));
            String backupFileName = baseName + "-" + timestamp + ".xml";
            Path backupFilePath = Paths.get(BACKUP_DIR, backupFileName);

            Files.copy(Paths.get(originalFilePath), backupFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup created at: " + backupFilePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
