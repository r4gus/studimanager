package guiTodolist.Task;

import guiTodolist.ControllerTodolist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import todolist.Task;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;



/**
 * The <code>ControllerTask</code> object represents the controller of the Gui CreateTask.
 * This is one of the tabs of the TabPane.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */


public class ControllerTask implements Initializable {

    @FXML
    public AnchorPane anchorPaneCreateTask;

    @FXML
    public TextField textFieldHeadingTask;

    @FXML
    public TextArea textAreaDescription;
    @FXML
    public TextArea textAreaNotes;

    @FXML
    public DatePicker datePickerDueDate;

    @FXML
    public ListView listViewChecklist;
    @FXML
    public TextField textFieldChecklistNewEntry;

    @FXML
    public ListView listViewFileAttachment;
    @FXML
    public TextField textFieldNewFileEntry;

    @FXML
    public Button buttonCreateTask;


    private ObservableList<String> itemsChecklist = FXCollections.observableArrayList();

    private ControllerTodolist controllerTodolist;
    private VBoxTasklist vboxTodoList;
    private Task currentTask;


    public ControllerTask(VBoxTasklist vboxTasklist){

        this.vboxTodoList = vboxTasklist;
    }

    public ControllerTask(ControllerTodolist controllerTodolist, VBoxTasklist vboxTodoList) {

        this.controllerTodolist = controllerTodolist;
        this.vboxTodoList = vboxTodoList;
    }


    /**
     * Called to initialize a controller after its root element has been completely processed
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listViewChecklist.setItems(itemsChecklist);
    }


    /**
     * generates a ToDoList with different functions e.g. adding a task etc.
     */

    public void addEntryToChecklist() {

        itemsChecklist.add("o " + textFieldChecklistNewEntry.getText());
        textFieldChecklistNewEntry.clear();
    }

    /**
     * generates a ToDoList with different functions e.g. adding a task etc.
     */

    public void deleteEntryToChecklist() {

        itemsChecklist.remove(listViewChecklist.getSelectionModel().getSelectedItem());
    }


    /**
     * generates a ToDoList with different functions e.g. adding a task etc.
     */

    public void AddFileAttachmentToTask() {

        String filename = textFieldNewFileEntry.getText();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei für Aufgabe auswählen");
        Stage stageCreateTask = (Stage) anchorPaneCreateTask.getScene().getWindow();

        File file = fileChooser.showOpenDialog(stageCreateTask);

        //....

    }


    /**
     * generates a Task with user-information e.g. task-heading.
     */

    public void createTask() {

        this.currentTask = createTaskObjekt();                  /* Object Task is created */
        VBoxTask vBoxnewTask = createNewGuiElemnts();               /* VboxTask will be created  */
        vBoxnewTask.setTaskID(this.currentTask.getTaskId());            /*  Add TaskID from Object */
        currentTask.setTaskListId(vboxTodoList.getTaskListID());            /* Add TaskList-ID to Object from taskList */
        vboxTodoList.getChildren().add(vBoxnewTask);
        Stage stage = (Stage) this.buttonCreateTask.getScene().getWindow();
        stage.close();
    }

    /**
     * generates a Task with different functions e.g. show basic information.
     */

    public VBoxTask createNewGuiElemnts() {

        VBoxTask vBoxTask = new VBoxTask(currentTask);
        addEventDragDetected(vBoxTask, this.currentTask);

        this.vboxTodoList.setMargin(vBoxTask, new Insets(5, 10, 5, 10));

        return vBoxTask;
    }

    /**
     * Gives the object the function to be moved on the surface.
     */

    public void addEventDragDetected(VBoxTask vBoxTask, Task task ){

        vBoxTask.setOnDragDetected(mouseEvent -> {
            Dragboard dragboard = vBoxTask.startDragAndDrop(TransferMode.ANY);
            ClipboardContent clipboardContent = new ClipboardContent();
            DataFormat dataFormat = new DataFormat("VBox");   // name an die ID des Objekts Binden...
            clipboardContent.put(dataFormat, task);
            dragboard.setContent(clipboardContent);
        });

    }


    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private Task createTaskObjekt() {

        Task task = new Task(textFieldHeadingTask.getText().trim().isEmpty() ? null : textFieldHeadingTask.getText());
        task.setProjectDescription(((textAreaDescription.getText().trim().isEmpty() ? null : textAreaDescription.getText())));
        task.setProjectStatus(0);
        task.setDone(false);
        task.setNotes(textAreaNotes.getText().trim().isEmpty() ? null : new ArrayList<> (Arrays.asList(textAreaNotes.getText().split("\n"))) );
        //task.setItemsChecklist(listViewChecklist.getItems().isEmpty() ? null : listViewChecklist.getItems());
        task.setDeadline(datePickerDueDate.getValue());

        return task;
    }


}
