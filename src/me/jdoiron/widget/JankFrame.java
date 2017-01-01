package me.jdoiron.widget;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.jdoiron.widget.menu.MenuBar;
import me.jdoiron.widget.table.EditCell;
import me.jdoiron.widget.table.Row;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class JankFrame extends Application {

    private static final String CENTER_STYLE = "-fx-alignment: CENTER;";

    public static void main(String... args) {
        launch(args);
    }

    /**
     * Creates a new TableColumn with a specified title, styling, preferred cell width, and associated StringProperty.
     *
     * @param title    The title of the column
     * @param style    The styling of the column
     * @param width    The preferred cell width of the column
     * @param property The associated StringProperty
     * @param <T>      The type of object each row will contain
     * @return The created TableColumn
     */
    private <T> TableColumn<T, String> createColumn(String title, String style, double width,
                                                    Function<T, StringProperty> property) {
        TableColumn<T, String> column = new TableColumn<>(title);
        column.setStyle(style);
        column.setPrefWidth(width);
        column.setCellValueFactory(cell -> property.apply(cell.getValue()));
        column.setCellFactory(col -> EditCell.createStringEditCell());
        return column;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Workbook - Jank");
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 1024, 768);

        TableView<Row> table = new TableView<>();
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setEditable(true);

        TableColumn columnRow = createColumn("Row", CENTER_STYLE, 50, Row::rowProperty);
        TableColumn columnConfirm = createColumn("Confirm", CENTER_STYLE, 75, Row::confirmProperty);
        TableColumn columnDate = createColumn("Date", CENTER_STYLE, 150, Row::dateProperty);
        TableColumn columnDesc = createColumn("Description", CENTER_STYLE, 440, Row::descriptionProperty);
        TableColumn columnCredit = createColumn("Credit", CENTER_STYLE, 100, Row::creditProperty);
        TableColumn columnDebit = createColumn("Debit", CENTER_STYLE, 100, Row::debitProperty);
        TableColumn columnBalance = createColumn("Balance", CENTER_STYLE, 100, Row::balanceProperty);

        columnBalance.setEditable(false);

        table.getColumns().addAll(columnRow, columnConfirm, columnDate, columnDesc, columnCredit, columnDebit,
                columnBalance);

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        String date = String.format("%02d/%02d/%04d", localDate.getMonthValue(), localDate.getDayOfMonth(),
                localDate.getYear());

        table.getItems().addAll(
                new Row("1", "X", date, "Initial balance", "5.00", "",
                        "5.00")
        );

        table.setOnKeyPressed(event -> {
            TablePosition<Row, String> pos = table.getFocusModel().getFocusedCell();
            if (pos != null) {
                table.edit(pos.getRow(), pos.getTableColumn());
            }
        });

        ScrollPane scrollTable = new ScrollPane(table);
        scrollTable.setFitToHeight(true);
        scrollTable.setFitToWidth(true);
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        vbox.getChildren().addAll(new MenuBar(table, primaryStage).bar(), scrollTable);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
