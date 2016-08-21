package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Expense;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Income;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.User;

import java.util.ArrayList;

import static android.R.attr.value;

/**
 * Created by Julia Nolden on 16.05.2016.
 */
public class MainActivity extends AppCompatActivity {
    private ListView listViewTrips;
    private ArrayAdapter<Trip> adapterTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewTrips = (ListView) findViewById(R.id.listview_trips);
        adapterTrips = new ArrayAdapter<Trip>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1
        );
        listViewTrips.setAdapter(adapterTrips);
        listViewTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Ermittle aktuell angeklickten Listeneintrag (Reise) und
                // uebergebe Namen der Reise
                chooseTrip(parent.getItemAtPosition(position).toString());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateListViewTrips();
    }

    public void openAddTripActivity(View button) {
        //oeffne addTripActivity
        Intent myIntent = new Intent(MainActivity.this, AddTripActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    /**
     * Aktualisiert die Listenansicht mit den global gespeicherten Reisen
     */
    public void updateListViewTrips() {
        adapterTrips.clear();
        adapterTrips.addAll(GlobalStorageSingleton.getInstance().getTrips());
    }

    /**
     * oeffnet die tripMenueActivity bei Klicken einer Reise aus der ListView
     *
     * @param tripName Name der ausgewählten Reise
     */
    public void chooseTrip(String tripName) {
        //oeffne tripMenueActivity
        Intent tripMenueIntent = new Intent(MainActivity.this, TripMenueActivity.class);
        Bundle parameterChoosenTrip = new Bundle();
        parameterChoosenTrip.putString("tripName", tripName);
        tripMenueIntent.putExtras(parameterChoosenTrip);
        MainActivity.this.startActivity(tripMenueIntent);

    }

}
