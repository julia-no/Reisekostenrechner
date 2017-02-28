package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ListView;
import android.widget.TextView;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.*;

import java.util.ArrayList;

/**
 * Created by Julia Nolden on 29.05.2016.
 */
public class TripCalculationActivity extends AppCompatActivity {
    private String tripName;
    private Trip choosenTrip;
    private TextView textViewCalculation;
    private TextView textViewTitle;
    private ListView listViewExpenses;
    private ListView listViewIncomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_calculation);
        textViewCalculation = (TextView) findViewById(R.id.listview_trips);
        textViewTitle = (TextView) findViewById(R.id.text_calculation_title);
        textViewCalculation.setMovementMethod(new ScrollingMovementMethod());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Zeige Inhalte wenn Reise uebergeben wurde
            tripName = bundle.getString("tripName");
            choosenTrip = GlobalStorageSingleton.getInstance().findTripByTitle(tripName);
            setTitle("Kostenübersicht " + tripName);
            textViewTitle.setText("Kostenübersicht für " + choosenTrip.getOwner().getName());
            calculateTripBookings();
            listViewExpenses = (ListView) findViewById(R.id.listview_expenses);
            ArrayList<Expense> expenses = choosenTrip.getExpenses();
            ListItemExpenseAdapter adapterExpenses = new ListItemExpenseAdapter(expenses, this, this, choosenTrip);
            listViewExpenses.setAdapter(adapterExpenses);
            listViewIncomes = (ListView) findViewById(R.id.listview_incomes);
            ArrayList<Income> incomes = choosenTrip.getIncomes();
            ListItemIncomeAdapter adapterIncomes = new ListItemIncomeAdapter(incomes, this, this, choosenTrip);
            listViewIncomes.setAdapter(adapterIncomes);
        } else {
            // Fehler, Reisenamen wurde nicht uebergeben
            tripName = null;
            setTitle("Fehler: Keine Reise ausgewählt!");
        }
    }

    /**
     * Berechnet Ein-/Ausgabebilanz der Teilnehmer und zeigt diese in einem Textfeld der Oberflaeche an
     */
    public void calculateTripBookings() {
        if (choosenTrip != null) {
            User userToCalculateFor = choosenTrip.getOwner();
            String outputText = "";
            double sumOwnExpense = 0;
            // Durchlaufe fuer alle Teilnehmer...
            double sumIncome = 0;
            // Finde alle Einnahmen...
            for (Income currentIcome : choosenTrip.getIncomes()) {
                // Summiere alle Ausgaben anteilig, die fuer userToCalculateFor bezahlt wurden...
                sumIncome += currentIcome.getAmount();
            }
            for (User currentUser : choosenTrip.getParticipants()) {
                double sumExpenseFromOther = 0;
                double sumExpenseForOther = 0;
                // Finde alle Ausgaben von ausgewaehltem Teilnehmer...
                for (Expense currentExpense : choosenTrip.getExpenses()) {
                    // Summiere alle Ausgaben anteilig, die fuer userToCalculateFor bezahlt wurden...
                    if ((currentExpense.getPayer().equals(currentUser)
                            && currentExpense.getRecipients().contains(userToCalculateFor))) {
                        sumExpenseFromOther += currentExpense.getAmount() / currentExpense.getRecipients().size();
                    }
                    // Summiere alle Ausgaben, die von userToCalculateFor bezahlt wurden...
                    if ((currentExpense.getPayer().equals(userToCalculateFor)
                            && currentExpense.getRecipients().contains(currentUser))) {
                        sumExpenseForOther += currentExpense.getAmount() / currentExpense.getRecipients().size();
                    }
                }
                double totalAmount = sumExpenseForOther - sumExpenseFromOther;
                if (totalAmount < 0) {
                    outputText += currentUser.getName() + " bekommt von " + userToCalculateFor.getName() + ":   " + totalAmount + " € \n";
                } else if (totalAmount > 0) {
                    outputText += currentUser.getName() + " schuldet " + userToCalculateFor.getName() + ":   " + totalAmount + " € \n";
                }
            }
            for (Expense currentExpense : choosenTrip.getExpenses()) {
                // Eigene Ausgaben summiert
                if ((currentExpense.getRecipients().contains(userToCalculateFor))) {
                    sumOwnExpense += currentExpense.getAmount() / currentExpense.getRecipients().size();
                }
            }
            // Einnahmen abziehen
            sumOwnExpense -= sumIncome;
            // Ausgabe
            outputText += "\n" + userToCalculateFor.getName() + " hat insgesamt" +
                    (sumOwnExpense < 0 ?
                            "    " + Math.abs(sumOwnExpense) + " €    verdient" :
                            "    " + Math.abs(sumOwnExpense) + " €   ausgegeben") + "\n";

            textViewCalculation.setText(outputText);
        }
    }


}
