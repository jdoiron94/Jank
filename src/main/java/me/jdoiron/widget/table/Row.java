package me.jdoiron.widget.table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Jacob
 * @since 12/31/2016
 */
public class Row implements Serializable {

    private static final long serialVersionUID = 0L;

    private transient StringProperty row;
    private transient StringProperty confirm;
    private transient StringProperty date;
    private transient StringProperty description;
    private transient StringProperty credit;
    private transient StringProperty debit;
    private transient StringProperty balance;

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
     * Sets the row number.
     *
     * @param row The row number
     */
    public void setRow(String row) {
        this.row = new SimpleStringProperty(row);
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

    /**
     * Sets the balance amount
     *
     * @param balance The balance amount
     */
    public void setBalance(String balance) {
        this.balance = new SimpleStringProperty(balance);
    }

    /**
     * Writes the Row object to an ObjectOutputStream.
     *
     * @param stream The stream to write
     * @throws Exception Thrown if there is a problem writing to the stream
     */
    private void writeObject(ObjectOutputStream stream) throws Exception {
        stream.defaultWriteObject();
        stream.writeObject(getRow());
        stream.writeObject(getConfirm());
        stream.writeObject(getDate());
        stream.writeObject(getDescription());
        stream.writeObject(getCredit());
        stream.writeObject(getDebit());
        stream.writeObject(getBalance());
    }

    /**
     * Reads in an ObjectInputStream to create a Row object
     *
     * @param stream The stream to read
     * @throws Exception Thrown if there is a problem reading from the stream
     */
    private void readObject(ObjectInputStream stream) throws Exception {
        stream.defaultReadObject();
        this.row = new SimpleStringProperty((String) stream.readObject());
        this.confirm = new SimpleStringProperty((String) stream.readObject());
        this.date = new SimpleStringProperty((String) stream.readObject());
        this.description = new SimpleStringProperty((String) stream.readObject());
        this.credit = new SimpleStringProperty((String) stream.readObject());
        this.debit = new SimpleStringProperty((String) stream.readObject());
        this.balance = new SimpleStringProperty((String) stream.readObject());
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s, %s", getRow(), getConfirm(), getDate(), getDescription(),
                getCredit(), getDebit(), getBalance());
    }
}
