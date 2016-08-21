package de.julianolden.reisekostenrechner.reisekostenrechner;

import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Category;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia Nolden on 18.06.2016.
 */
public class GlobalStorageSingleton {

    private static final GlobalStorageSingleton instance = new GlobalStorageSingleton();

    private final List<Trip> trips;
    private final List<User> users;
    private final List<Category> categories;

    protected GlobalStorageSingleton() {
        //TODO Daten aus Speicher auslesen.
        trips = new ArrayList<Trip>();
        users = new ArrayList<User>();
        categories = new ArrayList<Category>();
    }

    public void saveData() {
        // TODO Daten persistent speichern
    }

    public static GlobalStorageSingleton getInstance() {
        return instance;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
