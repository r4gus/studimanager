package input.elements.textfield;

import input.elements.IError;
import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public abstract class TemplateTextField extends TextField implements IError {

    protected TemplateTextField() {
        this.textProperty().addListener(observable -> {
            this.setStyle(null);
            this.setTooltip(null);
        });
    }

    public void showError(String msg) {
        this.showError(this, msg);
    }
}
