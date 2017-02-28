package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Booking;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Income;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;

import java.util.ArrayList;

/**
 * Adapterklasse zum Anzeigen der Listeneintraege fuer Einnahmen in Listen (ListView).
 * Hier wird eine "Loeschen" Schaltflaeche pro Eintrag bereitgestellt
 * <p>
 * Grundlage: http://stackoverflow.com/questions/17525886/listview-with-add-and-delete-buttons-in-each-row-in-android
 */
public class ListItemIncomeAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Income> list = new ArrayList<Income>();
    private Context context;
    private TripCalculationActivity tripCalculationActivity;
    private Trip trip;
    private Income incomeItem;


    public ListItemIncomeAdapter(ArrayList<Income> list, Context context, TripCalculationActivity tripCalculationActivity, Trip trip) {
        this.list = list;
        this.context = context;
        this.tripCalculationActivity = tripCalculationActivity;
        this.trip = trip;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_trip_calculation_listitem, null);
        }

        // Textviews zum Anzeigen des Eintrags
        TextView itemText = (TextView) view.findViewById(R.id.list_item_booking_title);
        TextView textPayFrom = (TextView) view.findViewById(R.id.list_item_text_payfrom);
        TextView textPayTo = (TextView) view.findViewById(R.id.list_item_text_payto);
        // Einnahme Item zum Anzeigen
        incomeItem = list.get(position);
        itemText.setText(String.format("%-14s %-10s", incomeItem.getTitle(), Utils.DATE_FORMATTER_SHORT.format(incomeItem.getDate()))
                + " " + Utils.formatDecimal(incomeItem.getAmount()) + "€ ");
        textPayFrom.setVisibility(View.GONE);
        textPayTo.setVisibility(View.GONE);
        // "Loeschen" Schaltflaeche an Logik anbinden
        ImageButton buttonDelete = (ImageButton) view.findViewById(R.id.delete_btn);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Income income = list.get(position);
                // Loescht gewuenschte Einnahme aus Anzeige und aus dem Reiseobjekt (Trip)
               GlobalStorageSingleton.getInstance().removeTripBooking(trip, income);
               // list.remove(position);
                notifyDataSetChanged();
                // Aktualisiere Berechnung in Activity
                tripCalculationActivity.calculateTripBookings();
            }
        });
        return view;
    }
}
