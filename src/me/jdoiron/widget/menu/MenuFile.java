package me.jdoiron.widget.menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.jdoiron.widget.table.Row;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class MenuFile {

    private final TableView<Row> table;
    private final Stage stage;

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
    private final MenuItem itemSaveAs = new MenuItem("Save As");
    private final MenuItem itemSaveAll = new MenuItem("Save All");
    private final MenuItem itemExit = new MenuItem("Exit");

    /**
     * Represents the File menu.
     *
     * @param table The TableView representing the table
     */
    public MenuFile(TableView<Row> table, Stage stage) {
        this.table = table;
        this.stage = stage;
        itemOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        itemSaveAll.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        itemExit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
        menuNew.setDisable(true);
        menuNew.getItems().addAll(itemFile, itemExistingFile);
        menuExport.getItems().addAll(itemExcel, itemCSV);
        itemClose.setDisable(true);
        itemOpen.setOnAction(e -> open());
        itemSettings.setDisable(true);
        itemSaveAs.setOnAction(e -> saveAs());
        itemExit.setOnAction(e -> System.exit(0));
        menu.getItems().addAll(menuNew, menuExport, itemOpen, itemClose, itemSettings, itemSaveAs, itemSaveAll, itemExit);
    }

    /**
     * Opens the specified workbook and loads it into the active window.
     */
    private void open() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Jank Workbook");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Jank Files", "*.jnk"));
        File path = chooser.showOpenDialog(stage);
        if (path != null) {
            try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path));
                 ObjectInputStream ois = new ObjectInputStream(inputStream)) {
                List<Row> rows = (ArrayList<Row>) ois.readObject();
                ObservableList<Row> observable = FXCollections.observableList(rows);
                table.setItems(observable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Saves the current workbook to the selected path.
     */
    private void saveAs() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Jank Workbook");
        chooser.setInitialFileName("Workbook");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jank Files (*.jnk)", "*.jnk"));
        File path = chooser.showSaveDialog(stage);
        if (path != null) {
            try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
                 ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
                ObservableList<Row> rows = table.getItems();
                oos.writeObject(new ArrayList<>(rows));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
