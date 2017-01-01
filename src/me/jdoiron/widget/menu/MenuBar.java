package me.jdoiron.widget.menu;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import me.jdoiron.widget.table.Row;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class MenuBar {

    private final javafx.scene.control.MenuBar bar = new javafx.scene.control.MenuBar();

    /**
     * Represents a MenuBar with all of the menus and sub-menus attached.
     *
     * @param table The TableView representing the table
     */
    public MenuBar(TableView<Row> table, Stage stage) {
        MenuFile menuFile = new MenuFile(table, stage);
        MenuEdit menuEdit = new MenuEdit(table);
        MenuHelp menuHelp = new MenuHelp();
        bar.getMenus().addAll(menuFile.menu, menuEdit.menu, menuHelp.menu);
    }

    /**
     * Returns the instantiated MenuBar with all of its menus added.
     *
     * @return The created MenuBar
     */
    public javafx.scene.control.MenuBar bar() {
        return bar;
    }
}
