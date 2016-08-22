package de.julianolden.reisekostenrechner.reisekostenrechner.objects;

/**
 * Objekt zum Modelieren der Benutzer bzw der Teilnehmers mit global eindeutigem Namen
 *
 * Created by Julia Nolden on 18.06.2016.
 */
public class User extends Object{
    //name muss global eindeutig sein
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
