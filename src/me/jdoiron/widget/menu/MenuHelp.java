package me.jdoiron.widget.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class MenuHelp {

    protected final Menu menu = new Menu("Help");

    private final MenuItem itemCheckUpdates = new MenuItem("Check for Updates");
    private final MenuItem itemAbout = new MenuItem("About");

    public MenuHelp() {
        itemCheckUpdates.setDisable(true);
        menu.getItems().addAll(itemCheckUpdates, itemAbout);
    }
}
