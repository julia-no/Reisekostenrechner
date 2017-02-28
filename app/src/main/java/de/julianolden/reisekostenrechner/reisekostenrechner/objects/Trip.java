package de.julianolden.reisekostenrechner.reisekostenrechner.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julia Nolden on 18.06.2016.
 */
public class Trip {

    private final String title;
    private final User owner;
    private final ArrayList<User> participants;
    private final ArrayList<Expense> expenses;
    private final ArrayList<Income> incomes;


    public Trip(String title, User owner, ArrayList<User> participants, ArrayList<Expense> expenses, ArrayList<Income> incomes) {
        this.title = title;
        this.owner = owner;
        this.participants = participants;
        this.expenses = expenses;
        this.incomes = incomes;
    }


    public List<User> getParticipants() {
        return participants;
    }

    public User getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public String toString() {
        return title;
    }
}
