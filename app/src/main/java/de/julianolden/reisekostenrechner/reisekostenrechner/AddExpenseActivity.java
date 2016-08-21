package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Category;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Expense;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Julia Nolden on 29.05.2016.
 */
public class AddExpenseActivity extends AppCompatActivity {

    private Button buttonDatepicker;
    private Spinner spinnerCategory, spinnerPayer, spinnerRecipients;
    private TextView txtTitle;
    private EditText editTextTile, editTextAmount;
    Trip choosenTrip;
    Calendar choosenExpenseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        editTextTile= (EditText) findViewById(R.id.editTextTitle);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        buttonDatepicker = (Button) findViewById(R.id.buttonDatepicker);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerPayer = (Spinner) findViewById(R.id.spinnerPayer);
        spinnerRecipients = (Spinner) findViewById(R.id.spinnerRecipients);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        // Ausgewaehltes Trip Objekt per uebergebenen Titel ermitteln
        Bundle bundle = getIntent().getExtras();
        String tripName;
        if (bundle != null) {
            tripName = bundle.getString("tripName");
            txtTitle.setText("Ausgabe für '" + tripName + "' hinzufügen");
            for (Trip trip : GlobalStorageSingleton.getInstance().getTrips()) {
                if (trip.equals(tripName)) {
                    choosenTrip = trip;
                    break;
                }
            }
        } else {
            // Fehler, Reisenamen wurde nicht uebergeben
            tripName = null;
            txtTitle.setText("Fehler");
        }
        // Kategorien anzeigen
        ArrayAdapter adapterCategories = new ArrayAdapter<Trip>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1
        );
        adapterCategories.addAll(GlobalStorageSingleton.getInstance().getCategories());
        spinnerCategory.setAdapter(adapterCategories);
        // Userauswahl fuer Bezahlenden anzeigen
        ArrayAdapter adapterUsers = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1
        );
        adapterCategories.addAll(GlobalStorageSingleton.getInstance().getUsers());
        spinnerPayer.setAdapter(adapterCategories);
        // Userauswahl fuer Empfaenger anzeigen
        ArrayAdapter adapterRecipients = new ArrayAdapter<User>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1
        );
        adapterRecipients.addAll(GlobalStorageSingleton.getInstance().getUsers());
        spinnerRecipients.setAdapter(adapterCategories);
    }

    public void addExpense(View view) {
        // TODO Pruefe Eingaben auf Gueltigkeit
        float amount;
        if (choosenTrip != null) {
            try {
                amount = Float.parseFloat(editTextAmount.getText().toString());
            } catch (NumberFormatException ex) {
                //TODO Fehlerbehandlung
                return;
            }
            if (choosenExpenseDate != null && editTextTile.getText().length() > 0) {
                choosenTrip.getExpenses().add(
                        new Expense(
                                editTextTile.getText().toString(),
                                choosenExpenseDate.getTime(),
                                amount,
                                (Category) spinnerCategory.getItemAtPosition(spinnerCategory.getSelectedItemPosition()),
                                (User) spinnerPayer.getItemAtPosition(spinnerPayer.getSelectedItemPosition()),
                                // FIXME Empfänger
                                new ArrayList<User>()
                        ));
            }
        }
        finish();
    }

    public void showDatePicker(View view) {
        LayoutInflater inflater = getLayoutInflater();
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View customView = inflater.inflate(R.layout.activity_date_picker, null);
        dialogBuilder.setView(customView);
        final DatePicker datePicker = (DatePicker) customView.findViewById(R.id.activity_date_picker_picker);
        final TextView dateTextView = (TextView) customView.findViewById(R.id.activity_date_picker_text);
        final SimpleDateFormat dateTextViewFormatter = new SimpleDateFormat(Utils.defaultDateFormatDatepicker, Locale.GERMANY);
        final SimpleDateFormat formatter = new SimpleDateFormat(Utils.defaultDateFormat, Locale.GERMANY);
        // View settings
        dialogBuilder.setTitle("Datum wählen");
        Calendar choosenDate = Calendar.getInstance();
        int year = choosenDate.get(Calendar.YEAR);
        int month = choosenDate.get(Calendar.MONTH);
        int day = choosenDate.get(Calendar.DAY_OF_MONTH);
        try {
            Date choosenDateFromUI = formatter.parse(buttonDatepicker.getText().toString());
            choosenDate.setTime(choosenDateFromUI);
            year = choosenDate.get(Calendar.YEAR);
            month = choosenDate.get(Calendar.MONTH);
            day = choosenDate.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar dateToDisplay = Calendar.getInstance();
        dateToDisplay.set(year, month, day);
        dateTextView.setText(dateTextViewFormatter.format(dateToDisplay.getTime()));
        // Buttons
        dialogBuilder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setPositiveButton("Wählen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Aktion bei Auswahl des Datums zur Übernahme in die Activity aus dem Dialog
                SimpleDateFormat formatter = new SimpleDateFormat(Utils.defaultDateFormat, Locale.GERMANY);
                Calendar choosen = Calendar.getInstance();
                choosen.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                //Speichern zum Anlegen eines Expense Objekts
                choosenExpenseDate = choosen;
                // Anzeige des gewaehlten Datums
                buttonDatepicker.setText(formatter.format(choosen.getTime()));
                dialog.dismiss();
            }
        });
        final AlertDialog dialog = dialogBuilder.create();
        // Datepicker
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Anzeige des aktuell ausgewählten Datums
                Calendar choosenDate = Calendar.getInstance();
                choosenDate.set(year, monthOfYear, dayOfMonth);
                dateTextView.setText(dateTextViewFormatter.format(choosenDate.getTime()));
            }
        });
        // Finish
        dialog.show();
    }
}
