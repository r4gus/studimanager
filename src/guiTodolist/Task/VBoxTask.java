package guiTodolist.Task;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import todolist.Task;

/**
 * The <code>VBoxTaskTask</code> object represents the controller of the Gui CreateTask.
 * This is one of the tabs of the TabPane.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */

public class VBoxTask extends VBox {

    private Task task;
    private int taskID;
    private int taskListID;

    public VBoxTask(Task task) {

        this.task = task;

        initVBoxTask();
    }

    /**
     * getter and setter methods of the variables
     */

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskListID() {
        return taskListID;
    }

    public void setTaskListID(int taskListID) {
        this.taskListID = taskListID;
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    public void initVBoxTask() {
        this.generateBasicVbox();

    }


    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private void generateBasicVbox() {

        this.getStyleClass().add("vBoxTask");
        this.setAlignment(Pos.CENTER);

        Label labelHeading = new Label(task.getProjectTitle());
        labelHeading.setPadding(new Insets(2, 10, 2, 10));
        this.setMargin(labelHeading, new Insets(5, 10, 5, 10));
        this.getChildren().add(labelHeading);

        HBox hBoxStatusElements = new HBox();
        createNewInformationtoobar(this, hBoxStatusElements);
        addEventDragDetected(this, this.task);
    }


    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private void createNewInformationtoobar(VBox vBoxTask, HBox hBoxStatusElements) {

        vBoxTask.setMargin(hBoxStatusElements, new Insets(5, 10, 5, 10));
        hBoxStatusElements.setAlignment(Pos.CENTER);
        hBoxStatusElements.setSpacing(10);

        Button buttonEdit = new Button("...");
        Button buttonDetails = new Button(" + ");
        Button buttonDeadline = new Button("L");
        Button buttonFileAttachment = new Button("F");
        Button buttonNotes = new Button("N");

        createNewInformationtoobarVisibleCheck(buttonDeadline, buttonFileAttachment, buttonNotes);
        hBoxStatusElements.getChildren().addAll(buttonEdit, buttonDetails, buttonDeadline, buttonFileAttachment, buttonNotes);
        this.getChildren().add(hBoxStatusElements);
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private void createNewInformationtoobarVisibleCheck(Button buttonDeadline, Button buttonFileAtt, Button buttonNotes) {

        if (this.task.getDeadline() == null) {
            buttonDeadline.setVisible(false);
        }
        if (this.task.getNotes() == null) {
            buttonNotes.setVisible(false);
        }

    }


    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    public void addEventDragDetected(VBoxTask vBoxTask, Task task) {

        vBoxTask.setOnDragDetected(mouseEvent -> {
            Dragboard dragboard = vBoxTask.startDragAndDrop(TransferMode.ANY);
            ClipboardContent clipboardContent = new ClipboardContent();
            DataFormat dataFormat = new DataFormat("VBox");   // name an die ID des Objekts Binden... + prüfen Ob Objekt bereits vorhanden Ist...
            clipboardContent.put(dataFormat, task);
            dragboard.setContent(clipboardContent);
        });

    }
}
