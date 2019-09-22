package input.elements;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public interface IError {
    public default void showError(Control c, String msg) {
        c.setStyle("-fx-border-color: red");
        Tooltip tooltip = new Tooltip(msg);
        tooltip.setShowDelay(Duration.millis(0));
        c.setTooltip(tooltip);

        c.getTooltip().setOnShowing(s -> {
            Bounds bounds = c.localToScreen(c.getBoundsInLocal());
            c.getTooltip().setX(bounds.getMaxX());
            c.getTooltip().setY(bounds.getMinY() - 8);
        });

    }
}
