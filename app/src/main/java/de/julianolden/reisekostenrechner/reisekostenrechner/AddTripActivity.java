package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Expense;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Income;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.User;

import java.util.ArrayList;

/**
 * Created by Julia Nolden on 16.05.2016.
 */
public class AddTripActivity extends AppCompatActivity {

    private EditText editTextTripName, editTextUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        editTextTripName = (EditText) findViewById(R.id.editTextTripName);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
    }

    public void addTrip(View view) {
        GlobalStorageSingleton.getInstance().getTrips().add(
                new Trip(
                        editTextTripName.getText().toString(),
                        new User(editTextTripName.getText().toString()),
                        new ArrayList<User>(),
                        new ArrayList<Expense>(),
                        new ArrayList<Income>()
                )
        );
        finish();
    }
}
