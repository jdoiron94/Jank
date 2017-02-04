package me.jdoiron.widget.table;

import javafx.event.Event;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

/**
 * @param <S> Object representing each row
 * @param <T> Type of object stored in each cell
 * @author James D
 * @since 02/12/2015
 */
public class EditCell<S, T> extends TableCell<S, T> {

    // Text field for editing
    private final TextField textField = new TextField();

    // Converter for converting the text in the text field to the user type, and vice-versa:
    private final StringConverter<T> converter;

    /**
     * Extends TableCell to allow canceled cell commits to co-exist with committed ones.
     *
     * @param converter Converts the text in the text field to the user type and vice-versa
     */
    public EditCell(StringConverter<T> converter) {
        this.converter = converter;
        itemProperty().addListener((obx, oldItem, newItem) -> {
            if (newItem == null) {
                setText(null);
            } else {
                setText(converter.toString(newItem));
            }
        });
        setGraphic(textField);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        textField.setOnAction(evt -> commitEdit(this.converter.fromString(textField.getText())));
        textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                commitEdit(this.converter.fromString(textField.getText()));
            }
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                textField.setText(converter.toString(getItem()));
                cancelEdit();
                event.consume();
            }
        });
    }

    /**
     * Convenience converter that does nothing (converts Strings to themselves and vice-versa...).
     */
    public static final StringConverter<String> IDENTITY_CONVERTER = new StringConverter<String>() {

        @Override
        public String toString(String object) {
            return object;
        }

        @Override
        public String fromString(String string) {
            return string;
        }
    };

    /**
     * Convenience method for creating an EditCell for a String value.
     *
     * @param <S> Object representing each row
     * @return The EditCell for a String value
     */
    public static <S> EditCell<S, String> createStringEditCell() {
        return new EditCell<>(IDENTITY_CONVERTER);
    }


    // set the text of the text field and display the graphic
    @Override
    public void startEdit() {
        super.startEdit();
        textField.setText(converter.toString(getItem()));
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        textField.requestFocus();
    }

    // revert to text display
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    // commits the edit. Update property if possible and revert to text display
    @Override
    public void commitEdit(T item) {
        // This block is necessary to support commit on losing focus, because the baked-in mechanism
        // sets our editing state to false before we can intercept the loss of focus.
        // The default commitEdit(...) method simply bails if we are not editing...
        if (item != null && !isEditing() && !item.equals(getItem())) {
            TableView<S> table = getTableView();
            if (table != null) {
                TableColumn<S, T> column = getTableColumn();
                CellEditEvent<S, T> event = new CellEditEvent<>(table, new TablePosition<>(table, getIndex(), column),
                        TableColumn.editCommitEvent(), item);
                Event.fireEvent(column, event);
            }
        }
        super.commitEdit(item);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }
}
