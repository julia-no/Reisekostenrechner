package de.julianolden.reisekostenrechner.reisekostenrechner.objects;

import java.util.Date;
import java.util.List;

/**
 * Created by Julia Nolden on 18.06.2016.
 */
public class Income {
    private final String title;
    private final Date date;
    private final float amount;
    private final Category category;

    public Income(String title, Date date, float amount, Category category) {
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
