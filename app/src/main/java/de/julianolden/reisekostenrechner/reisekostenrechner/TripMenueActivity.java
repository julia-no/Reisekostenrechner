package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
}
