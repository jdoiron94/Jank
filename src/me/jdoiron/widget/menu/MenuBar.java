package me.jdoiron.widget.menu;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class MenuBar {

    private final javafx.scene.control.MenuBar bar = new javafx.scene.control.MenuBar();

    public MenuBar() {
        MenuFile menuFile = new MenuFile();
        MenuEdit menuEdit = new MenuEdit();
        MenuHelp menuHelp = new MenuHelp();
        bar.getMenus().addAll(menuFile.menu, menuEdit.menu, menuHelp.menu);
    }

    public javafx.scene.control.MenuBar bar() {
        return bar;
    }
}
