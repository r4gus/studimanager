package guiTodolist;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The <code>ControllerTodoList</code> object represents the controller of the Gui ToDoList.
 * This is one of the tabs of the TabPane.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */

public class ControllerTodolist implements Initializable {

    @FXML
    public AnchorPane anchorPaneToDoList;
    @FXML
    public Button buttonEditCanBan;

    @FXML
    public HBox hboxToDoLists;


    /**
     * Called to initialize a controller after its root element has been completely processed
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createNewSection();
    }



    public void createNewSection()
    {
        VBox vBoxList = new VBox(10);
        vBoxList.setMinWidth(100);
        vBoxList.setMinHeight(111);



        vBoxList.getStyleClass().add("vBox");
        hboxToDoLists.getChildren().addAll(vBoxList );

    }

}
