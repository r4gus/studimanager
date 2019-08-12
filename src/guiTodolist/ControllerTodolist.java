package guiTodolist;

import guiTodolist.Task.VBoxTasklist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import todolist.TaskList;

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

    private static final String pathControllerTask = "Task/layout_Task.fxml";

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

        TaskList taskList = new TaskList();
        taskList.setHeading(textFieldHeaderToDoList.getText());
        textFieldHeaderToDoList.clear();
        new VBoxTasklist(taskList, this.hboxToDoLists);
    }

}
