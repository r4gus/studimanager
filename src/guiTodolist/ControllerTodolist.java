package guiTodolist;

import guiTodolist.Task.VBoxTask;
import guiTodolist.Task.VBoxTasklist;
import input.elements.textfield.AlphaNumTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import logging.MyLogger;
import Main.Main;
import todolist.Task;
import todolist.TaskList;
import todolist.TaskListCollection;

import java.net.URL;
import java.util.ArrayList;
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
    public Button buttonEditBoard;
    @FXML
    public Button buttonEditCanBan;
    @FXML
    public AlphaNumTextField textFieldHeaderToDoList;

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
        textFieldHeaderToDoList.setPromptText(Main.getBundle().getString("listDesignation"));
        if (taskListCollection.getTaskLists().size() > 0) {
            initStoredDataForGui();
        }
    }


    /**
     * init Graphical Objects from Json File
     */

    private void initStoredDataForGui() {            //Muss noch fertiggestellt werden

        for (TaskList taskList : this.taskListCollection.getTaskLists()) {

           VBoxTasklist vBoxTasklist =  new VBoxTasklist(this.taskListCollection, taskList, this.hboxToDoLists);
            ArrayList<VBoxTask> arrayListVBoxes = new ArrayList<>();
            for (Task task : taskList.getTasks()) {
                VBoxTask vBoxTask = new VBoxTask(task, vBoxTasklist);
                vBoxTasklist.setMargin(vBoxTask, new Insets(5, 10, 5, 10));
                vBoxTasklist.getChildren().add(vBoxTask);
                arrayListVBoxes.add(vBoxTask);
            }
            vBoxTasklist.setvBoxTaskArrayList(arrayListVBoxes);
        }
    }


    /**
     * add image to Button.
     */

    private void initButtonEdit() {

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

        if(textFieldHeaderToDoList.getText().trim().length() == 0)
        {
            textFieldHeaderToDoList.showError("Textfeld darf nicht leer sein");
            return;
        }
        MyLogger.LOGGER.entering(getClass().toString(), "createNewSection");
        TaskList taskList = new TaskList();
        taskList.setHeading(textFieldHeaderToDoList.getText());
        this.taskListCollection.add(taskList);
        textFieldHeaderToDoList.clear();
        new VBoxTasklist(this.taskListCollection, taskList, this.hboxToDoLists);
        MyLogger.LOGGER.exiting(getClass().toString(), "createNewSection");
    }

    public static TaskListCollection getTaskListCollection() {
        return taskListCollection;
    }

    public static void setTaskListCollection(TaskListCollection taskListCollection) {
        ControllerTodolist.taskListCollection = taskListCollection;
    }
}
