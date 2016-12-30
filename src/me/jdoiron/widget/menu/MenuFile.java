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
public class MenuFile {

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

    public MenuFile() {
        itemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        itemSaveAll.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        itemExit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
        menuNew.getItems().addAll(itemFile, itemExistingFile);
        menuExport.getItems().addAll(itemExcel, itemCSV);
        menu.getItems().addAll(menuNew, menuExport, itemOpen, itemClose, itemSettings, itemSaveAll, itemExit);
    }
}
