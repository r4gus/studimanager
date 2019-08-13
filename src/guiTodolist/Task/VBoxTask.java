package guiTodolist.Task;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private VBoxTasklist vBoxTasklist;

    public VBoxTask(Task task, VBoxTasklist vBoxTasklist) {

        this.task = task;
        this.vBoxTasklist = vBoxTasklist;
        initVBoxTask();
    }

    /**
     * getter and setter methods of the variables
     */

    public int getTaskID() {
        return taskID;
    }           // identisch mit Projekt status...

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskListID() {
        return taskListID;
    }       //Variable und getter + Setter derzeit nicht in verwendung

    public void setTaskListID(int taskListID) {
        this.taskListID = taskListID;
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    public void initVBoxTask() {

        this.generateBasicVbox();
        this.taskID = this.task.getTaskId();

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

        /* Insert basic information about Task */
        VBox vBox = generateBasicInformationBox();
        this.getChildren().add(vBox);
        HBox hBoxStatusElements = new HBox();
        createNewInformationtoobar(this, hBoxStatusElements);
        addEventDragDetected(this, this.task);
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private VBox generateBasicInformationBox() {

        VBox vBoxbasicInformation = new VBox();
        this.setMargin(vBoxbasicInformation, new Insets(8, 0, 10, 0));
        vBoxbasicInformation.setSpacing(5);
        HBox hBoxPriority = generateHBoxPrio("Priorität: ", "guiTodolist/Task/Icons/icons8-mittlere-prio-48.png");
        HBox hBoxDate = null;
        HBox hBoxProgressbar = null;
        if (task.getDeadline() != null) {
            hBoxDate = generateHboxDate("Fälligkeitsdatum: ");
        }
        if (task.getItemsChecklist() != null) {
            hBoxProgressbar = generateProgressBar();
        }
        if (hBoxDate == null) {
            vBoxbasicInformation.getChildren().addAll(hBoxPriority, hBoxProgressbar);
        } else if (hBoxProgressbar == null) {
            vBoxbasicInformation.getChildren().addAll(hBoxPriority, hBoxDate);
        } else if (hBoxDate == null && hBoxProgressbar == null) {
            vBoxbasicInformation.getChildren().addAll(hBoxPriority);
        } else {
            vBoxbasicInformation.getChildren().addAll(hBoxPriority, hBoxDate, hBoxProgressbar);
        }
        return vBoxbasicInformation;
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private HBox generateProgressBar(){

        int anzhahlAufgaben =  task.getItemsChecklist().size();
        String verhältnis = "Checklist 0 /" + anzhahlAufgaben;
        HBox hBox = hBox(verhältnis);
        hBox.setSpacing(37);
        ProgressBar progressBarList = new ProgressBar();
        progressBarList.setProgress(0);
        progressBarList.setPrefWidth(93);
        hBox.getChildren().add(progressBarList);
        return  hBox;
    }


    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private HBox hBox(String labelname) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        Label label = new Label(labelname);
        label.setPadding(new Insets(2, 10, 2, 10));
        hBox.setMargin(label, new Insets(0, 0, 0, 10));
        hBox.getChildren().add(label);
        return hBox;
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private HBox generateHBoxPrio(String labelname, String filepath) {
        HBox hBox = hBox(labelname);
        hBox.setSpacing(95);
        Image image = new Image(filepath);
        ImageView imageViewPrio = new ImageView();
        imageViewPrio.setFitWidth(24);
        imageViewPrio.setFitHeight(24);
        imageViewPrio.setImage(image);
        hBox.getChildren().addAll(imageViewPrio);
        return hBox;
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private HBox generateHboxDate(String labelname) {
        HBox hBox = hBox(labelname);
        Label labelDatum = new Label(task.getDeadline().toString());
        labelDatum.setPadding(new Insets(2, 10, 2, 10));
        hBox.getChildren().add(labelDatum);
        return hBox;
    }


    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private void createNewInformationtoobar(VBox vBoxTask, HBox hBoxStatusElements) {

        vBoxTask.setMargin(hBoxStatusElements, new Insets(5, 10, 5, 10));
        hBoxStatusElements.setAlignment(Pos.CENTER);
        hBoxStatusElements.setSpacing(10);

        Button buttonDelete = new Button(" - "); // Delete mit Icon
        addEventToDeleteButton(buttonDelete);
        Button buttonDetails = new Button(" i "); // mit Image

        ImageView imageViewDeadline = generateImageviewIcons("guiTodolist/Task/Icons/icons8-Deadline-48.png");
        ImageView imageViewFiles = generateImageviewIcons("guiTodolist/Task/Icons/icons8-dokumente-48.png");
        ImageView imageViewChecklist = generateImageviewIcons("guiTodolist/Task/Icons/icons8-aufgabenliste-48.png");
        ImageView imageViewNotes = generateImageviewIcons("guiTodolist/Task/Icons/icons8-bemerkungen-48.png");

        createNewInformationtoobarVisibleCheck(imageViewDeadline, imageViewFiles, imageViewChecklist, imageViewNotes);
        hBoxStatusElements.getChildren().addAll(buttonDelete, buttonDetails, imageViewDeadline, imageViewFiles, imageViewChecklist, imageViewNotes);
        this.getChildren().add(hBoxStatusElements);
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private void addEventToDeleteButton(Button buttonDelete){

        buttonDelete.setOnAction(actionEvent -> {

            this.vBoxTasklist.getChildren().remove(this);
        });
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private ImageView generateImageviewIcons(String filepath) {
        Image image = new Image(filepath);
        ImageView iv1 = new ImageView();
        iv1.setFitWidth(36);
        iv1.setFitHeight(36);
        iv1.setImage(image);
        return iv1;
    }

    /**
     * generates a Task  Object with different Information. If no specifications were made, a zero reference is assigned to the object.
     */

    private void createNewInformationtoobarVisibleCheck(ImageView deadline, ImageView fileAttachment, ImageView checklist, ImageView notes) {

        if (this.task.getDeadline() == null) {
            deadline.setVisible(false);
        }
        if (this.task.getNotes() == null) {
            notes.setVisible(false);
        }
        if (this.task.getItemsChecklist().size() == 0) {
            checklist.setVisible(false);
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
