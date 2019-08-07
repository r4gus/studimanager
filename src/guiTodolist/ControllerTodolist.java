package guiTodolist;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    public TextField textFieldHeaderToDoList;

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

    }


    /**
     * generates a ToDoList with different functions e.g. adding a task etc.
     */

    public void createNewSection() {
        VBox vBoxList = new VBox(10);
        vBoxList.setPrefWidth(300);
        vBoxList.setMinWidth(300);
        hboxToDoLists.setMargin(vBoxList, new Insets(10, 10, 10, 10));


        HBox hBoxHeading = generateHboxHeading();
        vBoxList.getChildren().add(hBoxHeading);

        vBoxList.getStyleClass().add("vBox");
        vBoxList.setMargin(hBoxHeading, new Insets(10, 10, 10, 10));
        hboxToDoLists.getChildren().add(vBoxList);

    }

    /**
     * Generates the overwriting of the ToDoList and the button with various functions.
     */

    public HBox generateHboxHeading() {
        /*  add List controls and heading */
        HBox hBoxHeading = new HBox();
        Label labelHeading = new Label(textFieldHeaderToDoList.getText());
        VBox vBoxTest = new VBox();
        vBoxTest.getChildren().addAll(labelHeading);
        vBoxTest.setAlignment(Pos.CENTER);
        labelHeading.setPadding(new Insets(2, 10, 2, 10));
        Button buttonEditList = new Button("...");
        generateHboxEditButton(buttonEditList);

        /* Berechnung der Centralen Possition muss noch verbessert werden!!  */
        int factor = 18;
        if (textFieldHeaderToDoList.getText().length() > 8)
            factor = 10;
        double centerHeading = ((300 - (textFieldHeaderToDoList.getText().length()) * factor) - 10) / 2;
        hBoxHeading.setMargin(labelHeading, new Insets(0, centerHeading, 0, 0));

        textFieldHeaderToDoList.clear();
        hBoxHeading.getChildren().addAll(labelHeading, buttonEditList);
        hBoxHeading.setAlignment(Pos.CENTER_RIGHT);

        return hBoxHeading;
    }

    /**
     * generates the functionality of the ToDoList button.
     */

    public void generateHboxEditButton(Button buttonEditToDoList) {

        ContextMenu contextMenuEditTask = new ContextMenu();
        MenuItem menuItemNewTask = new MenuItem("Neue Aufgabe");
        MenuItem b = new MenuItem("Option 2");
        Menu menuItemSort = new Menu("Sortieren nach:");
            MenuItem subMmenuItemSortDate = new MenuItem("Nach Fälligkeitsdatum");
            MenuItem subMmenuItemSortAlphabet = new MenuItem("Alphabetisch");
            MenuItem subMmenuItemSortbla = new MenuItem("...");
            menuItemSort.getItems().addAll(subMmenuItemSortDate, subMmenuItemSortAlphabet, subMmenuItemSortbla);
        MenuItem d = new MenuItem("Option 4");
        MenuItem e = new MenuItem("Option 5");
        contextMenuEditTask.getItems().addAll(menuItemNewTask,b,menuItemSort,d,e);
        buttonEditToDoList.setContextMenu(contextMenuEditTask);



    }


}
