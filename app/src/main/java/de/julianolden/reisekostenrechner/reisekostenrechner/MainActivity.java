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
public class MainActivity extends AppCompatActivity { //Klasse MainActivity mit erweiterung um die Mutterklasse AppCompatActivity aber wisoooo???
    private ListView listViewTrips; // Globale Klassenvariable vom Typ Listenansicht (ListView),
    private ArrayAdapter<Trip> adapterTrips; //Listen Modell fuer verfuegbare Reisen

    @Override
    protected void onCreate(Bundle savedInstanceState) {//Initialisierung der Activity activity_main
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewTrips = (ListView) findViewById(R.id.listview_trips); //zugriff auf die Listview der Activity?
        adapterTrips = new ArrayAdapter<Trip>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1
        );
        listViewTrips.setAdapter(adapterTrips);
        listViewTrips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseTrip(parent.getItemAtPosition(position).toString());// Ermittle aktuell angeklickten Listeneintrag (Reise) und uebergebe Namen der Reise
            }
        });
    }

    @Override
    protected void onStart() {//update der ListViewTrips bei Start der Activity
        super.onStart();
        updateListViewTrips();
    }

    public void openAddTripActivity(View button) {//oeffne addTripActivity
        Intent myIntent = new Intent(MainActivity.this, AddTripActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void updateListViewTrips() {//Aktualisiert die Listenansicht mit den global gespeicherten Reisen
        adapterTrips.clear();
        adapterTrips.addAll(GlobalStorageSingleton.getInstance().getTrips());
    }

    public void chooseTrip(String tripName) { //oeffnet die tripMenueActivity bei Klicken einer Reise aus der ListView @param tripName Name der ausgewählten Reise
        Intent tripMenueIntent = new Intent(MainActivity.this, TripMenueActivity.class); //oeffne tripMenueActivity
        Bundle parameterChoosenTrip = new Bundle();
        parameterChoosenTrip.putString("tripName", tripName);
        tripMenueIntent.putExtras(parameterChoosenTrip);
        MainActivity.this.startActivity(tripMenueIntent);

    }

}
