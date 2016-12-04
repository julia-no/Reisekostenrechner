package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;

import java.util.List;

/**
 * Created by Julia Nolden on 29.05.2016.
 */
public class TripMenueActivity extends AppCompatActivity {
    private String tripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_menue);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tripName = bundle.getString("tripName");
        } else {
            // Fehler, Reisenamen wurde nicht uebergeben
            tripName = "NA";
        }
        setTitle(tripName);
    }

    public void openAddExpenseActivity(View view) {
        Intent openAddTripExpenseIntent = new Intent(TripMenueActivity.this, AddExpenseActivity.class);
        Bundle parameterChoosenTrip = new Bundle();
        parameterChoosenTrip.putString("tripName", tripName);
        openAddTripExpenseIntent.putExtras(parameterChoosenTrip);
        TripMenueActivity.this.startActivity(openAddTripExpenseIntent);
    }

    public void openAddIncomeActivity(View view) {
        Intent openAddTripIncomeIntent = new Intent(TripMenueActivity.this, AddIncomeActivity.class);
        Bundle parameterChoosenTrip = new Bundle();
        parameterChoosenTrip.putString("tripName", tripName);
        openAddTripIncomeIntent.putExtras(parameterChoosenTrip);
        TripMenueActivity.this.startActivity(openAddTripIncomeIntent);
    }

    public void deleteTrip(View view) {
        Trip tripToDelete = GlobalStorageSingleton.getInstance().findTripByTitle(tripName);
        if (tripToDelete != null) {
            GlobalStorageSingleton.getInstance().removeTrip(tripToDelete);
            Toast.makeText(view.getContext(), "Trip mit Titel '" + tripName + "' wurde gelöscht.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(view.getContext(), "Fehler: Trip mit Titel '" + tripName + "' existiert nicht, Löschen gescheitert.", Toast.LENGTH_LONG).show();
        }
    }
}
