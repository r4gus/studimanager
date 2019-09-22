package input.elements.textarea;

import input.elements.IError;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class TextArea extends javafx.scene.control.TextArea implements IError {
    protected TextArea() {
        this.textProperty().addListener(observable -> {
            this.setStyle(null);
            this.setTooltip(null);
        });
    }

    public void showError(String msg) {
        this.showError(this, msg);
    }
}
