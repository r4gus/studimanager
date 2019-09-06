package message;

import javafx.stage.Window;

public class Alert {
    public static void showAlert(javafx.scene.control.Alert.AlertType alertType, Window owner, String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
