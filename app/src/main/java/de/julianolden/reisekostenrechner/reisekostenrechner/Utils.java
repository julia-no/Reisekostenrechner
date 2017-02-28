package de.julianolden.reisekostenrechner.reisekostenrechner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Julia Nolden on 29.05.2016.
 */
public class Utils {
    // Datumsformat im Datepicker Popup (ausfuehrlich)
    public static final String DEFAULT_DATE_FORMAT_DATEPICKER = "EEEE, dd.MM.yyyy";
    // Datumsformat kompakt fuer Restdarstellungen
    public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";
    public static final SimpleDateFormat DATE_TEXT_VIEW_FORMATTER = new SimpleDateFormat(Utils.DEFAULT_DATE_FORMAT_DATEPICKER, Locale.GERMANY);
    public static final SimpleDateFormat DATE_FORMATTER_SHORT = new SimpleDateFormat(Utils.DEFAULT_DATE_FORMAT, Locale.GERMANY);

    /**
     * Erzeugt einen Liste von String aus einer Liste mit allen enthaltenen Objekten
     *
     * @param list aus der eine String Liste erzeugt werden soll
     * @param <T>  Typ der Listenelemente
     * @return String Liste aus Listeninhalts
     */
    public static <T> List<String> getListAsStringList(List<T> list) {
        List<String> listConv = new ArrayList<>();
        for (T obj : list) {
            listConv.add(String.valueOf(obj));
        }
        return listConv;
    }

    /**
     * Erzeugt einen String aus einer Liste mit allen enthaltenen Objekten
     *
     * @param list      aus der ein String erzeugt werden soll
     * @param separator Separator Zeichen, z.B. ", "
     * @param <T>       Typ der Listenelemente
     * @return String des Listeninhalts
     */
    public static <T> String getListAsStrings(List<T> list, String separator) {
        String str = "";
        for (T obj : list) {
            if (!str.isEmpty()) {
                str += separator;
            }
            str += String.valueOf(obj);
        }
        return str;
    }


    /**
     * Formatiert einen Waehrungsbetrag mit Runden
     * Grundlage: http://stackoverflow.com/a/2379340/1965084
     *
     * @param number Waehrungsbetrag zum runden und formatieren
     * @return formatierten und gerundeten Betrag
     */
    public static String formatDecimal(float number) {
        float epsilon = 0.004f; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number); // sdb
        } else {
            return String.format("%10.2f", number); // dj_segfault
        }
    }
}
