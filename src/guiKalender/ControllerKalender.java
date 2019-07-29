package guiKalender;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerKalender implements Initializable {

    @FXML
    public AnchorPane tt_anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GridPane gridPane = new GridPane();

        javafx.scene.control.Button button1 = new javafx.scene.control.Button("Foo");
        javafx.scene.control.Button button2 = new javafx.scene.control.Button("Bar");
        javafx.scene.control.Button button3 = new javafx.scene.control.Button("Baz");
        javafx.scene.control.Button button4 = new javafx.scene.control.Button("Buz");
        javafx.scene.control.Button button5 = new javafx.scene.control.Button("Lol");

        gridPane.add(button1, 0, 0, 1,1);
        gridPane.add(button2, 1, 0, 1,1);
        gridPane.add(button3, 2, 0, 1,1);
        gridPane.add(button4, 0, 1, 1,1);
        gridPane.add(button5, 0, 2, 1,1);

        tt_anchorPane.getChildren().add(gridPane);
    }
}
