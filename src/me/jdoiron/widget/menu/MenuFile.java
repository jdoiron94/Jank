package me.jdoiron.widget.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import me.jdoiron.widget.table.Row;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class MenuFile {

    private final TableView<Row> table;

    protected final Menu menu = new Menu("File");
    private final Menu menuNew = new Menu("New");
    private final Menu menuExport = new Menu("Export");

    private final MenuItem itemFile = new MenuItem("File");
    private final MenuItem itemExistingFile = new MenuItem("File from Existing Sources");
    private final MenuItem itemExcel = new MenuItem("Excel");
    private final MenuItem itemCSV = new MenuItem("CSV");
    private final MenuItem itemOpen = new MenuItem("Open");
    private final MenuItem itemClose = new MenuItem("Close");
    private final MenuItem itemSettings = new MenuItem("Settings");
    private final MenuItem itemSaveAll = new MenuItem("Save All");
    private final MenuItem itemExit = new MenuItem("Exit");

    /**
     * Represents the File menu.
     *
     * @param table The TableView representing the table
     */
    public MenuFile(TableView<Row> table) {
        this.table = table;
        itemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        itemSaveAll.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        itemExit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
        menuNew.setDisable(true);
        menuNew.getItems().addAll(itemFile, itemExistingFile);
        menuExport.getItems().addAll(itemExcel, itemCSV);
        itemClose.setDisable(true);
        itemSettings.setDisable(true);
        menu.getItems().addAll(menuNew, menuExport, itemOpen, itemClose, itemSettings, itemSaveAll, itemExit);
    }
}
