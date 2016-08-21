package de.julianolden.reisekostenrechner.reisekostenrechner.objects;

import java.util.List;

/**
 * Created by Julia Nolden on 18.06.2016.
 */
public class Trip {

    private final String name;
    private final User owner;
    private final List<User> participants;
    private final List<Expense> expenses;
    private final List<Income> incomes;


    public Trip(String name, User owner, List<User> participants, List<Expense> expenses, List<Income> incomes) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public String toString() {
        return name;
    }
}
