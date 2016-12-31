package me.jdoiron.widget;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.jdoiron.widget.menu.MenuBar;

import java.time.LocalDate;
import java.util.function.Function;

/**
 * @author Jacob
 * @since 12/30/2016
 */
public class JankFrame extends Application {

    public static void main(String... args) {
        launch(args);
    }

    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cell -> property.apply(cell.getValue()));
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Jank");
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 1024, 768);

        TableView<Row> table = new TableView<>();
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setEditable(true);
        
        table.getColumns().add(createColumn("Row", Row::rowProperty));
        table.getColumns().add(createColumn("Confirm", Row::confirmProperty));
        table.getColumns().add(createColumn("Date", Row::dateProperty));
        table.getColumns().add(createColumn("Description", Row::descriptionProperty));
        table.getColumns().add(createColumn("Credit", Row::creditProperty));
        table.getColumns().add(createColumn("Debit", Row::debitProperty));
        table.getColumns().add(createColumn("Balance", Row::balanceProperty));

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        String date = String.format("%02d/%02d/%04d", localDate.getMonthValue(), localDate.getDayOfMonth(),
                localDate.getYear());

        table.getItems().addAll(
                new Row("1", "X", date, "Initial balance", "5.00", "", "5.0")
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

        vbox.getChildren().addAll(new MenuBar().bar(), scrollTable);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static class Row {

        private final StringProperty row;
        private final StringProperty confirm;
        private final StringProperty date;
        private final StringProperty description;
        private final StringProperty credit;
        private final StringProperty debit;
        private final StringProperty balance;

        public Row(String row, String confirm, String date, String description, String credit, String debit, String balance) {
            this.row = new SimpleStringProperty(row);
            this.confirm = new SimpleStringProperty(confirm);
            this.date = new SimpleStringProperty(date);
            this.description = new SimpleStringProperty(description);
            this.credit = new SimpleStringProperty(credit);
            this.debit = new SimpleStringProperty(debit);
            this.balance = new SimpleStringProperty(balance);
        }

        public StringProperty rowProperty() {
            return row;
        }

        public String getRow() {
            return row.get();
        }

        public StringProperty confirmProperty() {
            return confirm;
        }

        public String getConfirm() {
            return confirm.get();
        }

        public StringProperty dateProperty() {
            return date;
        }

        public String getDate() {
            return date.get();
        }

        public StringProperty descriptionProperty() {
            return description;
        }

        public String getDescription() {
            return description.get();
        }

        public StringProperty creditProperty() {
            return credit;
        }

        public String getCredit() {
            return credit.get();
        }

        public StringProperty debitProperty() {
            return debit;
        }

        public String getDebit() {
            return debit.get();
        }

        public StringProperty balanceProperty() {
            return balance;
        }

        public String getBalance() {
            return balance.get();
        }
    }
}
