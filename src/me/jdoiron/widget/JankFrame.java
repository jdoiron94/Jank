package me.jdoiron.widget;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.jdoiron.widget.menu.MenuBar;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class JankFrame extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Jank");
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 1024, 768);
        vbox.getChildren().add(new MenuBar().bar());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
