package guiCalendar.factory;

import javafx.scene.control.ComboBox;

public class IntComboBoxFactory {
    public static ComboBox<Integer> getIntComboBox(int minVal, int maxVal) throws IllegalArgumentException {
        if(minVal < 0 || maxVal < minVal)
            throw new IllegalArgumentException("minVal must be 0 or greater and minVal < maxVal");

        ComboBox<Integer> comboBox = new ComboBox<>();

        // add elements
        for(int i = minVal; i <= maxVal; i++)
            comboBox.getItems().add(i);

        // add listener
        comboBox.getSelectionModel().selectedItemProperty().addListener(observable -> {
            comboBox.setStyle(null);
        });

        return comboBox;
    }
}
