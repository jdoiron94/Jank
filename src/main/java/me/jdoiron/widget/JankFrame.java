package me.jdoiron.widget;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.jdoiron.widget.menu.MenuBar;
import me.jdoiron.widget.table.EditCell;
import me.jdoiron.widget.table.Row;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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

        columnRow.setEditable(false);
        columnBalance.setEditable(false);

        table.getColumns().addAll(columnRow, columnConfirm, columnDate, columnDesc, columnCredit, columnDebit,
                columnBalance);

        LocalDate localDate = LocalDate.now();
        String dateToday = String.format("%02d/%02d/%04d", localDate.getMonthValue(), localDate.getDayOfMonth(),
                localDate.getYear());

        table.getItems().addAll(
                new Row("1", "X", dateToday, "Initial balance", "5.00", "",
                        "5.00")
        );

        table.setOnKeyPressed(event -> {
            System.out.println("HEY");
            String text = event.getText();
            TablePosition<Row, String> pos = table.getFocusModel().getFocusedCell();
            if (pos != null && StringUtils.isNotEmpty(text) &&
                    (StringUtils.isAlphanumericSpace(text) || StringUtils.isAsciiPrintable(text))) {
                table.edit(pos.getRow(), pos.getTableColumn());
            }
        });

        table.setOnKeyReleased(e -> {
            TablePosition<Row, String> cell = getSelectedCell(table);
            if (cell != null && cell.getTableColumn().getText().equals("Row")) {
                System.out.println(cell.getRow());
            }
        });

        ScrollPane scrollTable = new ScrollPane(table);
        scrollTable.setFitToHeight(true);
        scrollTable.setFitToWidth(true);
        scrollTable.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        CheckBox boxConfirm = new CheckBox("Confirm");

        DatePicker fieldDate = new DatePicker();
        fieldDate.setPromptText("Date");
        TextField fieldDesc = new TextField();
        fieldDesc.setPromptText("Description");
        TextField fieldCredit = new TextField();
        fieldCredit.setPromptText("Credit");
        TextField fieldDebit = new TextField();
        fieldDebit.setPromptText("Debit");

        Button buttonSubmit = new Button("Submit");
        buttonSubmit.setOnAction(e -> {
            String row = Integer.toString(table.getItems().size() + 1);
            String confirm = boxConfirm.isSelected() ? "X" : "";
            String date = fieldDate.getEditor().getText();
            String description = fieldDesc.getText();
            String credit = StringUtils.isBlank(fieldCredit.getText()) ? "" : fieldCredit.getText().replace(',', '.');
            String debit = StringUtils.isBlank(fieldDebit.getText()) ? "" : fieldDebit.getText().replace(',', '.');
            if (StringUtils.isNotBlank(date) && StringUtils.isNotBlank(description)
                    && (NumberUtils.isParsable(credit) || NumberUtils.isParsable(debit))) {
                System.out.println(table.getItems().size());
                String prevBalance = table.getItems().get(table.getItems().size() - 1).getBalance();
                if (prevBalance == null) {
                    prevBalance = "0.00";
                    //row = "1";
                }
                double newCredit = StringUtils.isBlank(credit)
                        || !NumberUtils.isParsable(credit) ? 0.0 : Double.parseDouble(credit);
                double newDebit = StringUtils.isBlank(debit)
                        || !NumberUtils.isParsable(debit) ? 0.0 : Double.parseDouble(debit);
                String formattedCredit = StringUtils.isBlank(credit)
                        || !NumberUtils.isParsable(credit) ? "" : String.format("%.02f", Double.parseDouble(credit));
                String formattedDebit = StringUtils.isBlank(debit)
                        || !NumberUtils.isParsable(debit) ? "" : String.format("%.02f", Double.parseDouble(debit));
                System.out.println(prevBalance);
                String newBalance = String.format("%.02f", Double.parseDouble(prevBalance) + newCredit - newDebit);
                table.getItems().add(
                        new Row(row, confirm, date, description, formattedCredit, formattedDebit, newBalance)
                );
                boxConfirm.setSelected(false);
                fieldDate.getEditor().setText(null);
                fieldDesc.setText(null);
                fieldCredit.setText(null);
                fieldDebit.setText(null);
            }
        });

        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(20, 0, 0, 20));
        hbox.getChildren().addAll(boxConfirm, fieldDate, fieldDesc, fieldCredit, fieldDebit, buttonSubmit);

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(hbox, 0, 1, 1, 1);

        vbox.getChildren().addAll(new MenuBar(table, primaryStage).bar(), scrollTable, gridPane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(t -> System.out.println("closing"));
    }

    /**
     * Returns the actively selected cell.
     *
     * @param table The TableView representing the table
     * @return The selected cell, should it exist
     */
    private TablePosition<Row, String> getSelectedCell(TableView<Row> table) {
        ObservableList<TablePosition> selectedCells = table.getSelectionModel().getSelectedCells();
        return selectedCells != null && selectedCells.size() > 0 ? selectedCells.get(0) : null;
    }
}
