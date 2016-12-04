package de.julianolden.reisekostenrechner.reisekostenrechner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia Nolden on 29.05.2016.
 */
public class Utils {
    // Datumsformat im Datepicker Popup (ausfuehrlich)
    public static final String defaultDateFormatDatepicker = "EEEE, dd.MM.yyyy";
    // Datumsformat kompakt fuer Restdarstellungen
    public static final String defaultDateFormat = "dd.MM.yyyy";

    /**
     * Erzeugt einen String aus einer Liste mit allen enthaltenen Objekten
     *
     * @param list aus der ein String erzeugt werden soll
     * @param <T>  Typ der Listenelemente
     * @return String des Listeninhalts
     */
    public static <T> List<String> getListAsStrings(List<T> list) {
        List<String> listConv = new ArrayList<>();
        for (T obj : list) {
            listConv.add(String.valueOf(obj));
        }
        return listConv;
    }
}
