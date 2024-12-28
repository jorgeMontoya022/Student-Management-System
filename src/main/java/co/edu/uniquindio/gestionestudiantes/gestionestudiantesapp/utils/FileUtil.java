package co.edu.uniquindio.gestionestudiantes.gestionestudiantesapp.utils;

import java.beans.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class FileUtil {

    static String systemDate = "";

    public static void saveLogRecord(String logMessage, int level, String action, String logFilePath) {
        String log = "";
        Logger LOGGER = Logger.getLogger(action);
        FileHandler fileHandler = null;
        loadSystemDate();
        try {
            fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            switch (level) {
                case 1:
                    LOGGER.log(Level.INFO, action + "," + logMessage + "," + systemDate);
                    break;
                case 2:
                    LOGGER.log(Level.WARNING, action + "," + logMessage + "," + systemDate);
                    break;
                case 3:
                    LOGGER.log(Level.SEVERE, action + "," + logMessage + "," + systemDate);
                    break;
                default:
                    break;
            }
        } catch (SecurityException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        } finally {
            fileHandler.close();
        }
    }

    private static void loadSystemDate() {
        String dayStr = "";
        String monthStr = "";
        String yearStr = "";

        Calendar cal1 = Calendar.getInstance();

        int day = cal1.get(Calendar.DATE);
        int month = cal1.get(Calendar.MONTH) + 1;
        int year = cal1.get(Calendar.YEAR);
        int hour = cal1.get(Calendar.HOUR);
        int minute = cal1.get(Calendar.MINUTE);

        if (day < 10) {
            dayStr += "0" + day;
        } else {
            dayStr += "" + day;
        }
        if (month < 10) {
            monthStr += "0" + month;
        } else {
            monthStr += "" + month;
        }

        systemDate = year + "-" + monthStr + "-" + dayStr;
    }

    public static Object loadSerializedXMLResource(String filePath) throws IOException {
        XMLDecoder xmlDecoder;
        Object xmlObject;

        xmlDecoder = new XMLDecoder(new FileInputStream(filePath));
        xmlObject = xmlDecoder.readObject();
        xmlDecoder.close();
        return xmlObject;
    }

    public static void saveSerializedXMLResource(String filePath, Object object) throws IOException {
        XMLEncoder xmlEncoder = null;
        try {
            xmlEncoder = new XMLEncoder(new FileOutputStream(filePath));

            // Configurar el XMLEncoder para manejar las colecciones
            xmlEncoder.setPersistenceDelegate(ArrayList.class,
                    new DefaultPersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object oldInstance, Encoder out) {
                            return new Expression(oldInstance, ArrayList.class, "new", new Object[0]);
                        }
                    });

            // Registrar LocalDate
            xmlEncoder.setPersistenceDelegate(LocalDate.class,
                    new DefaultPersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object obj, Encoder enc) {
                            LocalDate date = (LocalDate) obj;
                            return new Expression(obj, LocalDate.class, "of",
                                    new Object[]{date.getYear(), date.getMonthValue(), date.getDayOfMonth()});
                        }
                    });

            xmlEncoder.writeObject(object);
        } finally {
            if (xmlEncoder != null) {
                xmlEncoder.close();
            }
        }
    }

}
