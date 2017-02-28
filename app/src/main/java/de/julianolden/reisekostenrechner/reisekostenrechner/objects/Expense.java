package de.julianolden.reisekostenrechner.reisekostenrechner.objects;

import java.util.Date;
import java.util.List;

/**
 * Created by Julia Nolden on 18.06.2016.
 */
public class Expense extends Booking {
    private final User payer;
    private final List<User> recipients;

    public Expense(String title, Date date, float amount, Category category, User payer, List<User> recipients) {
        super(title,date,amount,category);
        this.payer = payer;
        this.recipients = recipients;
    }

    public User getPayer() {
        return payer;
    }

    public List<User> getRecipients() {
        return recipients;
    }
}
