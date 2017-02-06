package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Julia Nolden on 29.05.2016.
 */
public class AddIncomeActivity extends AppCompatActivity {

    private Button buttonDatepicker, buttonSaveIncome;
    private Spinner spinnerCategory;
    private TextView txtTitle;
    private EditText editTextTitle, editTextAmount;

    private Trip choosenTrip;
    private Calendar choosenIncomeDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        buttonDatepicker = (Button) findViewById(R.id.buttonDatepicker);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        buttonSaveIncome = (Button) findViewById(R.id.buttonSaveIncome);
        // Ausgewaehltes Trip Objekt per uebergebenen Titel ermitteln
        Bundle bundle = getIntent().getExtras();
        String tripName;
        if (bundle != null) {
            tripName = bundle.getString("tripName");
            txtTitle.setText("Einnahme für '" + tripName + "' hinzufügen");
            choosenTrip = GlobalStorageSingleton.getInstance().findTripByTitle(tripName);
        } else {
            // Fehler, Reisenamen wurde nicht uebergeben
            tripName = null;
            txtTitle.setText("Fehler: Keine Reise ausgewählt!");
        }
        if (choosenTrip != null) {
            // Kategorien anzeigen
            ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1
            );
            adapterCategories.addAll(GlobalStorageSingleton.getInstance().getCategories());
            spinnerCategory.setAdapter(adapterCategories);
        } else {
            Toast.makeText(getApplicationContext(), "Fehler: Keine Reise gefunden!", Toast.LENGTH_LONG).show();
        }
    }

    public void addIncome(View view) {
        // TODO Pruefe Eingaben auf Gueltigkeit
        float amount;
        if (choosenTrip != null) {
            try {
                amount = Float.parseFloat(editTextAmount.getText().toString());
            } catch (NumberFormatException ex) {
                //TODO Fehlerbehandlung
                return;
            }
            if (choosenIncomeDate != null && editTextTitle.getText().length() > 0) {
                choosenTrip.getIncomes().add(
                        new Income(
                                editTextTitle.getText().toString(),
                                choosenIncomeDate.getTime(),
                                amount,
                                (Category) spinnerCategory.getItemAtPosition(spinnerCategory.getSelectedItemPosition())
                        ));
            }
            GlobalStorageSingleton.getInstance().saveDataToStorage();
        } else {
            Toast.makeText(getApplicationContext(), "Fehler: Keine Reise gefunden!", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    /**
     * Zeigt einen Dialog zur Auswahl des Ausgabedatums an
     */
    public void showDatePicker(View view) {
        LayoutInflater inflater = getLayoutInflater();
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View customView = inflater.inflate(R.layout.activity_date_picker, null);
        dialogBuilder.setView(customView);
        final DatePicker datePicker = (DatePicker) customView.findViewById(R.id.activity_date_picker_picker);
        final TextView dateTextView = (TextView) customView.findViewById(R.id.activity_date_picker_text);
        final SimpleDateFormat dateTextViewFormatter = new SimpleDateFormat(Utils.DEFAULT_DATE_FORMAT_DATEPICKER, Locale.GERMANY);
        final SimpleDateFormat formatter = new SimpleDateFormat(Utils.DEFAULT_DATE_FORMAT, Locale.GERMANY);
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
                SimpleDateFormat formatter = new SimpleDateFormat(Utils.DEFAULT_DATE_FORMAT, Locale.GERMANY);
                Calendar choosen = Calendar.getInstance();
                choosen.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                //Speichern zum Anlegen eines Income Objekts
                choosenIncomeDate = choosen;
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
        dialog.show(); // Öffnet den Datepicker mit den zuvor eingerichteten Elementen

    }
}
