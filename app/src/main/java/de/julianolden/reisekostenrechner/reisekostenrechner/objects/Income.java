package de.julianolden.reisekostenrechner.reisekostenrechner.objects;

import java.util.Date;
import java.util.List;

/**
 * Created by Julia Nolden on 18.06.2016.
 */
public class Income extends Booking{

    public Income(String title, Date date, float amount, Category category) {
        super(title, date, amount, category);
    }
}
