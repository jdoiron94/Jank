package me.jdoiron.widget;

import java.util.function.Function;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class TableViewEditOnType extends Application {


    @Override
    public void start(Stage primaryStage) {

        TableView<Person> table = new TableView<>();

        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setEditable(true);

        table.getColumns().add(createColumn("First Name", Person::firstNameProperty));
        table.getColumns().add(createColumn("Last Name", Person::lastNameProperty));
        table.getColumns().add(createColumn("Email", Person::emailProperty));

        table.getItems().add(
                new Person("Jacob", "Smith", "jacob.smith@example.com")
        );

        table.setOnKeyPressed(event -> {
            TablePosition<Person, ?> pos = table.getFocusModel().getFocusedCell() ;
            if (pos != null && event.getCode().isLetterKey()) {
                table.edit(pos.getRow(), pos.getTableColumn());
            }
        });

        Button showDataButton = new Button("Debug data");
        showDataButton.setOnAction(event -> table.getItems().stream()
                .map(p -> String.format("%s %s", p.getFirstName(), p.getLastName()))
                .forEach(System.out::println));

        Scene scene = new Scene(new BorderPane(table, null, null, showDataButton, null), 880, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private <T> TableColumn<T, String> createColumn(String title, Function<T, StringProperty> property) {
        TableColumn<T, String> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> {
            Person p = (Person) cellData.getValue();
            System.out.println(p.getFirstName());
            return property.apply(cellData.getValue());
        });
        col.setCellFactory(column -> EditCell.createStringEditCell());
        return col ;
    }

    public static class Person {
        private final StringProperty firstName = new SimpleStringProperty();
        private final StringProperty lastName = new SimpleStringProperty();
        private final StringProperty email = new SimpleStringProperty();

        public Person(String firstName, String lastName, String email) {
            setFirstName(firstName);
            setLastName(lastName);
            setEmail(email);
        }

        public final StringProperty firstNameProperty() {
            return this.firstName;
        }

        public final java.lang.String getFirstName() {
            return this.firstNameProperty().get();
        }

        public final void setFirstName(final java.lang.String firstName) {
            this.firstNameProperty().set(firstName);
        }

        public final StringProperty lastNameProperty() {
            return this.lastName;
        }

        public final java.lang.String getLastName() {
            return this.lastNameProperty().get();
        }

        public final void setLastName(final java.lang.String lastName) {
            this.lastNameProperty().set(lastName);
        }

        public final StringProperty emailProperty() {
            return this.email;
        }

        public final java.lang.String getEmail() {
            return this.emailProperty().get();
        }

        public final void setEmail(final java.lang.String email) {
            this.emailProperty().set(email);
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}