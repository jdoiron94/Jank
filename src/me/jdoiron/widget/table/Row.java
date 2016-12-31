package me.jdoiron.widget.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Jacob
 * @since 12/31/2016
 */
public class Row {

    private final StringProperty row;
    private final StringProperty confirm;
    private final StringProperty date;
    private final StringProperty description;
    private final StringProperty credit;
    private final StringProperty debit;
    private final StringProperty balance;

    /**
     * Represents a row in a TableView.
     *
     * @param row         The row number of the row
     * @param confirm     Whether or not the row has been confirmed in the user's statement
     * @param date        The date of the entry
     * @param description A short description of the row entry
     * @param credit      The amount credited to the user
     * @param debit       The amount debited from the user
     * @param balance     The active balance up through this row
     */
    public Row(String row, String confirm, String date, String description, String credit, String debit,
               String balance) {
        this.row = new SimpleStringProperty(row);
        this.confirm = new SimpleStringProperty(confirm);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
        this.credit = new SimpleStringProperty(credit);
        this.debit = new SimpleStringProperty(debit);
        this.balance = new SimpleStringProperty(balance);
    }

    /**
     * Returns the row number property.
     *
     * @return The row number property
     */
    public StringProperty rowProperty() {
        return row;
    }

    /**
     * Returns the row number.
     *
     * @return The row number
     */
    public String getRow() {
        return row.get();
    }

    /**
     * Returns the confirmation property.
     *
     * @return The confirmation property
     */
    public StringProperty confirmProperty() {
        return confirm;
    }

    /**
     * Returns the confirmation value.
     *
     * @return The confirmation value
     */
    public String getConfirm() {
        return confirm.get();
    }

    /**
     * Returns the date property.
     *
     * @return The date property
     */
    public StringProperty dateProperty() {
        return date;
    }

    /**
     * Returns the date value.
     *
     * @return The date value
     */
    public String getDate() {
        return date.get();
    }

    /**
     * Returns the description property.
     *
     * @return The description property
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * Returns the description value.
     *
     * @return The description value
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Returns the credit property.
     *
     * @return The credit property
     */
    public StringProperty creditProperty() {
        return credit;
    }

    /**
     * Returns the credit value.
     *
     * @return The credit value
     */
    public String getCredit() {
        return credit.get();
    }

    /**
     * Returns the debit property.
     *
     * @return The debit property
     */
    public StringProperty debitProperty() {
        return debit;
    }

    /**
     * Returns the debit amount.
     *
     * @return The debit amount
     */
    public String getDebit() {
        return debit.get();
    }

    /**
     * Returns the balance property.
     *
     * @return The balance property
     */
    public StringProperty balanceProperty() {
        return balance;
    }

    /**
     * Returns the balance amount.
     *
     * @return The balance amount
     */
    public String getBalance() {
        return balance.get();
    }
}
