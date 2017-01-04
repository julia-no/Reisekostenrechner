package de.julianolden.reisekostenrechner.reisekostenrechner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Expense;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.Trip;
import de.julianolden.reisekostenrechner.reisekostenrechner.objects.User;

/**
 * Created by Julia Nolden on 29.05.2016.
 */
public class TripCalculationActivity extends AppCompatActivity {
    private String tripName;
    private Trip choosenTrip;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_calculation);
        textView = (TextView) findViewById(R.id.listview_trips);
        textView.setMovementMethod(new ScrollingMovementMethod());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tripName = bundle.getString("tripName");
            choosenTrip = GlobalStorageSingleton.getInstance().findTripByTitle(tripName);
            setTitle(tripName);
        } else {
            // Fehler, Reisenamen wurde nicht uebergeben
            tripName = null;
            setTitle("Fehler: Keine Reise ausgewählt!");
        }
        if (choosenTrip != null) {
            User userToCalculateFor = choosenTrip.getOwner();
            String outputText = "__Kostenübersicht für " + userToCalculateFor.getName() + "__\n\n";
            double sumOwnExpense = 0;
            // Durchlaufe fuer alle Teilnehmer...
            for (User currentUser : choosenTrip.getParticipants()) {
                double sumFromOther = 0;
                double sumForOther = 0;
                // Finde alle Ausgaben von ausgewaehltem Teilnehmer...
                for (Expense currentExpense : choosenTrip.getExpenses()) {
                    // Summiere alle Ausgaben anteilig, die fuer userToCalculateFor bezahlt wurden...
                    if ((currentExpense.getPayer().equals(currentUser)
                            && currentExpense.getRecipients().contains(userToCalculateFor))) {
                        sumFromOther += currentExpense.getAmount() / currentExpense.getRecipients().size();
                    }
                    // Summiere alle Ausgaben, die von userToCalculateFor bezahlt wurden...
                    if ((currentExpense.getPayer().equals(userToCalculateFor)
                            && currentExpense.getRecipients().contains(currentUser))) {
                        sumForOther += currentExpense.getAmount() / currentExpense.getRecipients().size();
                    }
                }
                double totalAmount = sumForOther - sumFromOther;
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
            outputText += "\n" + userToCalculateFor.getName() + " hat insgesamt   " + sumOwnExpense + " €   ausgegeben \n";

            outputText += "\n\n__Liste der Ausgaben__\n";
            for (Expense currentExpense : choosenTrip.getExpenses()) {
                outputText = outputText + "\n" +
                        Utils.DATE_TEXT_VIEW_FORMATTER.format(currentExpense.getDate()) + "   -   " +
                        currentExpense.getAmount() + "€   -   " +
                        "'" + currentExpense.getTitle() + "'\n" +
                        "Kategorie '" + currentExpense.getCategory() + "'\n" +
                        "bezahlt von: " +
                        currentExpense.getPayer() + "\n" +
                        "bezahlt für:  " + Utils.getListAsStrings(currentExpense.getRecipients(), ", ") +
                        "\n";

            }

            textView.setText(outputText);
        }
    }


}
