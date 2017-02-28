package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Expense;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Income;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia Nolden on 16.05.2016.
 */
//Klasse AddTripActivity mit erweiterung um die Mutterklasse AppCompatActivity
public class AddTripActivity extends AppCompatActivity {

    //Deklaration der Variablen
    private EditText editTextTripName, editTextUserName, editTextNewParticipant;
    private ListView listViewParticipants;

    private List<User> tripUsers = new ArrayList<>();
    private ArrayAdapter adapterParticipants;

    @Override
    //Initialisierung der Activity activity_add_Trip
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        editTextTripName = (EditText) findViewById(R.id.editTextTripName);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextNewParticipant = (EditText) findViewById(R.id.editTextNewParticipant);
        listViewParticipants = (ListView) findViewById(R.id.listViewParticipants);
        // Userauswahl fuer Bezahlenden anzeigen
        adapterParticipants = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1
        );
        listViewParticipants.setAdapter(adapterParticipants);
    }

    public void addTrip(View view) {
        User owner = new User(editTextUserName.getText().toString());
        ArrayList<User> participantsWithOwner = new ArrayList<>();
        participantsWithOwner.add(owner);
        participantsWithOwner.addAll(tripUsers);
        GlobalStorageSingleton storage = GlobalStorageSingleton.getInstance();
        storage.addTrip(
                new Trip(
                        editTextTripName.getText().toString(),
                        owner,
                        participantsWithOwner,
                        new ArrayList<Expense>(),
                        new ArrayList<Income>()
                )
        );
        storage.saveDataToStorage();
        finish();
    }

    public void addUser(View view) {
        User user = new User(editTextNewParticipant.getText().toString());
        tripUsers.add(user);
        adapterParticipants.add(user);
        editTextNewParticipant.setText("");
    }
}
