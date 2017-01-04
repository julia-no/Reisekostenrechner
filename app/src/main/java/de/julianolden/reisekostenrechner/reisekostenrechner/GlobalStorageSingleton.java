package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.Context;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Category;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Julia Nolden on 18.06.2016.
 */
public class GlobalStorageSingleton {

    private static final GlobalStorageSingleton instance = new GlobalStorageSingleton();

    private List<Trip> trips;
    private List<Category> categories;
    private Context appContext;

    protected GlobalStorageSingleton() {
    }

    public static GlobalStorageSingleton getInstance() {
        return instance;
    }

    /**
     * Initialisiert den Settingsmanager und laedt den letzten gespeicherten Datenstand aus der Android Settings Datenbank.
     * Muss vor erster Benutzung dieser Klasse beim Start der App aufgerufen werden
     *
     * @param context Application Context zum Initialisieren des Android SettingsManager
     */
    public void initialize(Context context) {
        appContext = context;
        SettingsManager.initializeSettingsManager(appContext);
        trips = SettingsManager.getInstance().readTripListFromStorage();
        //TODO Categories dynamisch editierbar implementieren und dann auch laden
        //categories = SettingsManager.getInstance().readCategoriesListFromStorage();
        categories = Arrays.asList(
                new Category("Eintritt"),
                new Category("Nahrungsmittel"),
                new Category("Mobilität"),
                new Category("Souvenirs"),
                new Category("Sonstiges"),
                new Category("Übernachtung")
        );
    }

    /**
     * Speichert den aktuellen Datenstand in Android Settings Datenbank.
     */
    public void saveDataToStorage() {
        SettingsManager.getInstance().saveCategoriesListToStorage(categories);
        SettingsManager.getInstance().saveTripListToStorage(trips);
    }


    public List<Trip> getTrips() {
        return trips;
    }

    /**
     * Fuegt eine Reise hinzu und speichert den aktuellen Stand
     *
     * @param tripToAdd
     */
    public void addTrip(Trip tripToAdd) {
        trips.add(tripToAdd);
        saveDataToStorage();
    }

    /**
     * Loescht eine Reise und speichert den aktuellen Stand
     *
     * @param tripToRemove
     */
    public void removeTrip(Trip tripToRemove) {
        trips.remove(tripToRemove);
        saveDataToStorage();
    }

    /**
     * Findet ein Trip Objekt anhand seines Titels
     *
     * @param tripName Titel des zu findenden Trip Objekts
     * @return gefundenes Trip Objekt oder NULL, wenn Trip mit gegebenen Titel nicht vorhanden ist
     */
    public Trip findTripByTitle(String tripName) {
        for (Trip trip : trips) {
            if (trip.getTitle().equals(tripName)) {
                return trip;
            }
        }
        return null;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
