package guiTodolist;

import custom_exceptions.UserException;
import guiTodolist.Task.VBoxTasklist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logging.MyLogger;
import todolist.TaskList;
import todolist.TaskListCollection;

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

    private final String filepathAddIcon = "guiTodolist/Task/Icons/icons8-hinzufuegen-48.png";

    // Muss später wieder auf Null gesetzt werden, Wenn Objekt geliefert wird...!!!!
    private static TaskListCollection taskListCollection = new TaskListCollection();

    /**
     * Called to initialize a controller after its root element has been completely processed
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initButtonEdit();
    }


    /**
     * add image to Button.
     */

    private void initButtonEdit(){

        MyLogger.LOGGER.entering(getClass().toString(), "initButtonEdit");
        ImageView imageView = new ImageView(new Image(this.filepathAddIcon));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        this.buttonEditCanBan.setMaxWidth(20);
        this.buttonEditCanBan.setGraphic(imageView);
        MyLogger.LOGGER.exiting(getClass().toString(), "initButtonEdit");
    }


    /**
     * generates a ToDoList with different functions e.g. adding a task etc.
     */

    public void createNewSection() {

        MyLogger.LOGGER.entering(getClass().toString(), "createNewSection");
        try {
            if (textFieldHeaderToDoList.getText().trim().isEmpty()) {
                throw new UserException("Info", "Bitte geben Sie einen Titel für die neue Tasklist ein");
            }

        } catch (UserException e) {
            /* Exception logs automatically and creates InfoWindow For User */
            return;
        }
        TaskList taskList = new TaskList();
        taskList.setHeading(textFieldHeaderToDoList.getText());
        this.taskListCollection.add(taskList);
        textFieldHeaderToDoList.clear();
        new VBoxTasklist(this.taskListCollection ,taskList, this.hboxToDoLists);
        MyLogger.LOGGER.exiting(getClass().toString(), "createNewSection");
    }

    public static TaskListCollection getTaskListCollection() {
        return taskListCollection;
    }

    public static void setTaskListCollection(TaskListCollection taskListCollection) {
        ControllerTodolist.taskListCollection = taskListCollection;
    }
}
