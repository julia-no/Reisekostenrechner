package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Category;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Expense;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.User;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Julia Nolden on 29.05.2016.
 */
public class AddExpenseActivity extends AppCompatActivity {

    private Button buttonDatepicker, buttonRecipients, buttonSaveExpense;
    private Spinner spinnerCategory, spinnerPayer;
    private ListView listRecipients;
    private TextView txtTitle;
    private EditText editTextTitle, editTextAmount;

    private Trip choosenTrip;
    private Calendar choosenExpenseDate;
    private List<User> choosenRecipients = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        buttonDatepicker = (Button) findViewById(R.id.buttonDatepicker);
        buttonRecipients = (Button) findViewById(R.id.buttonRecipientsChoice);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        spinnerPayer = (Spinner) findViewById(R.id.spinnerPayer);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        buttonSaveExpense = (Button) findViewById(R.id.buttonSaveExpense);
        // Ausgewaehltes Trip Objekt per uebergebenen Titel ermitteln
        Bundle bundle = getIntent().getExtras();
        String tripName;
        if (bundle != null) {
            tripName = bundle.getString("tripName");
            txtTitle.setText("Ausgabe für '" + tripName + "' hinzufügen");
            choosenTrip = GlobalStorageSingleton.getInstance().findTripByTitle(tripName);
        } else {
            // Fehler, Reisenamen wurde nicht uebergeben
            tripName = null;
            txtTitle.setText("Fehler");
            Toast.makeText(getApplicationContext(), "Fehler: Keine Reisetitel gewählt!", Toast.LENGTH_LONG).show();
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
            // Userauswahl fuer Bezahlenden anzeigen
            ArrayAdapter<User> adapterUsers = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1
            );
            adapterUsers.addAll(choosenTrip.getParticipants());
            spinnerPayer.setAdapter(adapterUsers);
        } else {
            Toast.makeText(getApplicationContext(), "Fehler: Keine Reise gefunden!", Toast.LENGTH_LONG).show();
        }
    }

    public void showRecipientsChoice(View view) {
        final List<User> choosenRecipientsDialog = new ArrayList<>();
        String[] array = Utils.getListAsStringList(choosenTrip.getParticipants()).toArray(new String[choosenTrip.getParticipants().size()]);
        final boolean[] isSelectedArray = new boolean[choosenTrip.getParticipants().size()];
        AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenseActivity.this);
        builder.setTitle("Empfänger auswählen")
                .setMultiChoiceItems(array, isSelectedArray,
                        new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    choosenRecipientsDialog.add(choosenTrip.getParticipants().get(which));
                                } else {
                                    choosenRecipientsDialog.remove(choosenTrip.getParticipants().get(which));
                                }
                            }
                        })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String choosenRecipientsSumStr = "";
                        // Ermittle alle von Benutzer gewaehlten Teilnehmer
                        for (User user : choosenRecipientsDialog) {
                            choosenRecipientsSumStr += (!choosenRecipientsSumStr.isEmpty() ? ", " : "") + user.getName();
                        }
                        buttonRecipients.setText(choosenRecipientsSumStr);
                        choosenRecipients = choosenRecipientsDialog;
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void addExpense(View view) {
        // TODO Pruefe Eingaben auf Gueltigkeit
        float amount;
        if (choosenTrip != null) {
            try {
                amount = Float.parseFloat(editTextAmount.getText().toString());
            } catch (NumberFormatException ex) {
                Toast.makeText(getApplicationContext(), "Fehler: Der Betrag ist ungültig, nur Zahlen sind erlaubt!", Toast.LENGTH_LONG).show();
                return;
            }
            if (choosenExpenseDate != null && editTextTitle.getText().length() > 0) {
                choosenTrip.getExpenses().add(
                        new Expense(
                                editTextTitle.getText().toString(),
                                choosenExpenseDate.getTime(),
                                amount,
                                (Category) spinnerCategory.getItemAtPosition(spinnerCategory.getSelectedItemPosition()),
                                (User) spinnerPayer.getItemAtPosition(spinnerPayer.getSelectedItemPosition()),
                                choosenRecipients
                        ));
                GlobalStorageSingleton.getInstance().saveDataToStorage();
            }
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

        // View settings
        dialogBuilder.setTitle("Datum wählen");
        Calendar choosenDate = Calendar.getInstance();
        int year = choosenDate.get(Calendar.YEAR);
        int month = choosenDate.get(Calendar.MONTH);
        int day = choosenDate.get(Calendar.DAY_OF_MONTH);
        try {
            Date choosenDateFromUI = Utils.DATE_FORMATTER.parse(buttonDatepicker.getText().toString());
            choosenDate.setTime(choosenDateFromUI);
            year = choosenDate.get(Calendar.YEAR);
            month = choosenDate.get(Calendar.MONTH);
            day = choosenDate.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar dateToDisplay = Calendar.getInstance();
        dateToDisplay.set(year, month, day);
        dateTextView.setText(Utils.DATE_TEXT_VIEW_FORMATTER.format(dateToDisplay.getTime()));
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
                dateTextView.setText(Utils.DATE_TEXT_VIEW_FORMATTER.format(choosenDate.getTime()));
            }
        });
        // Finish
        dialog.show(); // Öffnet den Datepicker mit den zuvor eingerichteten Elementen

    }
}
