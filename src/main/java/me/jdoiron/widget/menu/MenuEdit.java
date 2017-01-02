package me.jdoiron.widget.menu;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import me.jdoiron.widget.table.Row;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class MenuEdit {

    private final TableView<Row> table;

    protected final Menu menu = new Menu("Edit");
    private final Menu menuInsertRow = new Menu("Insert Row");

    private final MenuItem itemCut = new MenuItem("Cut");
    private final MenuItem itemCopy = new MenuItem("Copy");
    private final MenuItem itemPaste = new MenuItem("Paste");
    private final MenuItem itemDelete = new MenuItem("Delete");
    private final MenuItem itemAbove = new MenuItem("Above");
    private final MenuItem itemBelow = new MenuItem("Below");
    private final MenuItem itemDeleteRow = new MenuItem("Delete Row");

    /**
     * Represents the Edit menu.
     *
     * @param table The TableView representing the table
     */
    public MenuEdit(TableView<Row> table) {
        this.table = table;
        itemCut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        itemCopy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        itemPaste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        itemDelete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        itemCut.setOnAction(e -> copyText(true));
        itemCopy.setOnAction(e -> copyText(false));
        itemPaste.setOnAction(e -> pasteText());
        itemDelete.setOnAction(e -> deleteText(null, null));
        menuInsertRow.setDisable(true);
        menuInsertRow.getItems().addAll(itemAbove, itemBelow);
        itemDeleteRow.setOnAction(e -> deleteRow());
        menu.getItems().addAll(itemCut, itemCopy, itemPaste, itemDelete, menuInsertRow, itemDeleteRow);
    }

    /**
     * Copies the text from the currently selected cell and optionally deletes the cell's text.
     *
     * @param delete Whether or not to delete the text from the cell after it has been copied
     */
    private void copyText(boolean delete) {
        TablePosition cell = getSelectedCell();
        if (cell != null) {
            Row row = table.getItems().get(cell.getRow());
            String column = table.getColumns().get(cell.getColumn()).getText();
            setClipboardContents((String) cell.getTableColumn().getCellObservableValue(row).getValue());
            if (delete) {
                deleteText(row, column);
            }
        }
    }

    /**
     * Pastes the user's clipboard text into the actively selected cell.
     */
    private void pasteText() {
        TablePosition cell = getSelectedCell();
        if (cell != null) {
            Row row = table.getItems().get(cell.getRow());
            String column = table.getColumns().get(cell.getColumn()).getText();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Clipboard clipboard = toolkit.getSystemClipboard();
            try {
                String result = (String) clipboard.getData(DataFlavor.stringFlavor);
                setText(row, column, result);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Deletes the text from the actively selected cell.
     *
     * @param row    The Row object containing the actively selected cell's data
     * @param column The column's name of the selected cell
     */
    private void deleteText(Row row, String column) {
        if (row == null && column == null) {
            TablePosition cell = getSelectedCell();
            if (cell != null) {
                row = table.getItems().get(cell.getRow());
                column = table.getColumns().get(cell.getColumn()).getText();
            }
        }
        if (row != null && column != null) {
            setText(row, column, null);
        }
    }

    /**
     * Deletes the selected row based on the selected cell. If there is only one row when the delete action is
     * processed, the table will be preserved. Otherwise, the entire row will be deleted.
     */
    private void deleteRow() {
        Row row = table.getSelectionModel().getSelectedItem();
        if (table.getItems().size() > 1) {
            table.getItems().remove(row);
        } else {
            String[] columns = {"Row", "Confirm", "Date", "Description", "Credit", "Debit", "Balance"};
            for (String col : columns) {
                deleteText(row, col);
            }
        }
    }

    /**
     * Sets the actively selected cell's text.
     *
     * @param row    The Row object containing the actively selected cell's data
     * @param column The column's name of the selected cell
     * @param text   The text to be set
     */
    private void setText(Row row, String column, String text) {
        StringProperty property = getColumnProperty(row, column);
        property.set(text);
    }

    /**
     * Returns the actively selected cell.
     *
     * @return The selected cell, should it exist
     */
    private TablePosition getSelectedCell() {
        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
        return selectedCells != null && selectedCells.size() > 0 ? selectedCells.get(0) : null;
    }

    /**
     * Sets the user's clipboard contents.
     *
     * @param text The text to be set
     */
    private void setClipboardContents(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /**
     * Returns the StringProperty associated with the selected column type.
     *
     * @param row    The Row object containing the actively selected cell's data
     * @param column The column's name of the selected cell
     * @return The StringProperty for the selected column type
     */
    private StringProperty getColumnProperty(Row row, String column) {
        switch (column) {
            case "Row":
                return row.rowProperty();
            case "Confirm":
                return row.confirmProperty();
            case "Date":
                return row.dateProperty();
            case "Description":
                return row.descriptionProperty();
            case "Credit":
                return row.creditProperty();
            case "Debit":
                return row.debitProperty();
        }
        return row.balanceProperty();
    }
}
