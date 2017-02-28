package de.julianolden.reisekostenrechner.reisekostenrechner.objects;

import java.util.Date;

/**
 * Created by Julia Nolden on 28.02.17.
 * <p>
 * Mutterklasse fuer Buchungen "Expense" und "Income". Durch die Vererbung koennen hier Gemeinsamkeiten wie Titel, Amount etc.
 * ohne redundanten Code genutzt werden.
 */
public class Booking {

    private final String title;
    private final Date date;
    private final float amount;
    private final Category category;

    public Booking(String title, Date date, float amount, Category category) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public Category getCategory() {
        return category;
    }
}
