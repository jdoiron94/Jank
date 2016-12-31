package me.jdoiron.widget.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class MenuEdit {

    protected final Menu menu = new Menu("Edit");
    private final Menu menuInsertRow  = new Menu("Insert Row");

    private final MenuItem itemCut = new MenuItem("Cut");
    private final MenuItem itemCopy = new MenuItem("Copy");
    private final MenuItem itemPaste = new MenuItem("Paste");
    private final MenuItem itemDelete = new MenuItem("Delete");
    private final MenuItem itemAbove = new MenuItem("Above");
    private final MenuItem itemBelow = new MenuItem("Below");
    private final MenuItem itemDeleteRow = new MenuItem("Delete Row");

    public MenuEdit() {
        itemCut.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        itemCopy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        itemPaste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        itemDelete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        menuInsertRow.setDisable(true);
        menuInsertRow.getItems().addAll(itemAbove, itemBelow);
        itemDeleteRow.setDisable(true);
        menu.getItems().addAll(itemCut, itemCopy, itemPaste, itemDelete, menuInsertRow, itemDeleteRow);
    }
}
