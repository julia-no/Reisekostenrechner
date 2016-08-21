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
    private final User payer;
    private final List<User> recipients;

    public Income(String title, Date date, float amount, Category category, User payer, List<User> recipients) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.payer = payer;
        this.recipients = recipients;
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

    public User getPayer() {
        return payer;
    }

    public List<User> getRecipients() {
        return recipients;
    }
}
