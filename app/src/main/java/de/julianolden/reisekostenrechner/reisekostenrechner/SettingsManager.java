package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Category;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Julia Nolden on 04.12.2016.
 */
public class SettingsManager {

    private static SharedPreferences sharedPreferences;
    private String KEY_LIST_TRIPS = "LIST_TRIPS";
    private String KEY_LIST_CATEGORIES = "LIST_CATEGORIES";

    public SettingsManager() {
    }

    private static class SettingsManagerLoader {
        private static final SettingsManager INSTANCE = new SettingsManager();
    }

    public static SettingsManager getInstance() {
        return SettingsManagerLoader.INSTANCE;
    }

    public static void initializeSettingsManager(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Liest den letzten gespeicherten Stand der Liste der Trip Objekte aus
     * der Android Settings Datenbank. Da diese als serialisierter Json-String vorliegen,
     * werden diese noch deserialisert
     *
     * @return Liste der gespeicherten Trip Objekte
     */
    public List<Trip> readTripListFromStorage() {
        KEY_LIST_TRIPS = "listTrips";
        String value = sharedPreferences.getString(KEY_LIST_TRIPS, null);
        if (value != null) {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Trip[] list = gson.fromJson(value, Trip[].class);
            return new ArrayList<Trip>(Arrays.asList(list));
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Liest den letzten gespeicherten Stand der Liste der Category Objekte aus
     * der Android Settings Datenbank. Da diese als serialisierter Json-String vorliegen,
     * werden diese noch deserialisert
     *
     * @return Liste der gespeicherten Category Objekte
     */
    public List<Category> readCategoriesListFromStorage() {
        String value = sharedPreferences.getString(KEY_LIST_CATEGORIES, null);
        if (value != null) {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Category[] list = gson.fromJson(value, Category[].class);
            return new ArrayList<Category>(Arrays.asList(list));
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Speichert alle vorhandenen Trip Objekte in die Android Settings Datenbank.
     * Dazu wird die Liste der Reisen in Json serialisiert ( In Json String Zeichenkette umgewandelt),
     * da Android nur Zeichenketten und keine Objekte in der Settings Datenbank speichern
     * kann
     *
     * @param list Liste der zu speichernden Trip Objekte
     */
    public void saveTripListToStorage(List<Trip> list) {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        String value = gson.toJson(list);
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(KEY_LIST_TRIPS, value);
        e.apply();
    }

    /**
     * Speichert alle vorhandenen Category Objekte in die Android Settings Datenbank.
     * Dazu wird die Liste der Category in Json serialisiert ( In Json String Zeichenkette umgewandelt),
     * da Android nur Zeichenketten und keine Objekte in der Settings Datenbank speichern
     * kann
     *
     * @param list Liste der zu speichernden Category Objekte
     */
    public void saveCategoriesListToStorage(List<Category> list) {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        String value = gson.toJson(list);
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.putString(KEY_LIST_CATEGORIES, value);
        e.apply();
    }
}
