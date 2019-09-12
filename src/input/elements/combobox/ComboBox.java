package input.elements.combobox;

import input.elements.IError;

public class ComboBox<T> extends javafx.scene.control.ComboBox<T> implements IError {
    public ComboBox() {
        this.getSelectionModel().selectedItemProperty().addListener(observable -> {
            this.setStyle(null);
            this.setTooltip(null);
        });
    }

    public void showError(String msg) {
        this.showError(this, msg);
    }
}
