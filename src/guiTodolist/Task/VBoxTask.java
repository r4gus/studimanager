package guiTodolist.Task;

import guiTodolist.InfoTask.ControllerInfoTask;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import todolist.Task;

import java.awt.event.ActionEvent;
import java.io.IOException;

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

    /**
     * init method generates VBoxtask.
     */

    public void initVBoxTask() {

        this.generateBasicVbox();
        this.taskID = this.task.getTaskId();

    }


    /**
     * generates the required elements for the task.
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
        createNewInformationToolbar(this, hBoxStatusElements);
        addEventDragDetected(this, this.task);
    }

    /**
     * generates the middle part of the task of the Gui.
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
        if (task.getItemsChecklist().size() > 0) {
            hBoxProgressbar = generateProgressBar();
        }
        if (hBoxDate == null && hBoxProgressbar == null) {
            vBoxbasicInformation.getChildren().addAll(hBoxPriority);
        } else if (hBoxDate == null) {
            vBoxbasicInformation.getChildren().addAll(hBoxPriority, hBoxProgressbar);
        } else if (hBoxProgressbar == null) {
            vBoxbasicInformation.getChildren().addAll(hBoxPriority, hBoxDate);
        } else {
            vBoxbasicInformation.getChildren().addAll(hBoxPriority, hBoxDate, hBoxProgressbar);
        }
        return vBoxbasicInformation;
    }

    /**
     * Generates the progress display of the checklist.
     */

    private HBox generateProgressBar() {

        int anzhahlAufgaben = task.getItemsChecklist().size();
        String verhaeltnis = "Checklist 0 /" + anzhahlAufgaben;
        HBox hBox = hBox(verhaeltnis);
        hBox.setSpacing(37);
        ProgressBar progressBarList = new ProgressBar();
        progressBarList.setProgress(0);
        progressBarList.setPrefWidth(93);
        hBox.getChildren().add(progressBarList);
        return hBox;
    }


    /**
     * generates the Hbox for single elements of the middle part of the task
     *
     * @param labelname Text to be displayed.
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
     * generates the priority display of the task
     *
     * @param labelname Text to be displayed.
     * @param filepath  File path for the corresponding icon.
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
     * generates the display for due date.
     *
     * @param labelname Text to be displayed.
     */

    private HBox generateHboxDate(String labelname) {
        HBox hBox = hBox(labelname);
        Label labelDatum = new Label(task.getDeadline().toString());
        labelDatum.setPadding(new Insets(2, 10, 2, 10));
        hBox.getChildren().add(labelDatum);
        return hBox;
    }


    /**
     * generates the lower Gui part of the task.
     *
     * @param vBoxTask
     * @param hBoxStatusElements
     */

    private void createNewInformationToolbar(VBox vBoxTask, HBox hBoxStatusElements) {

        vBoxTask.setMargin(hBoxStatusElements, new Insets(5, 10, 5, 10));
        hBoxStatusElements.setAlignment(Pos.CENTER);
        hBoxStatusElements.setSpacing(10);

        Button buttonDelete = new Button(" - "); // Delete mit Icon
        addEventToDeleteButton(buttonDelete);
        Button buttonDetails = new Button(" i "); // mit Image
        addEventShowTaskInfo(buttonDetails);

        ImageView imageViewDeadline = generateImageviewIcons("guiTodolist/Task/Icons/icons8-Deadline-48.png");
        ImageView imageViewFiles = generateImageviewIcons("guiTodolist/Task/Icons/icons8-dokumente-48.png");
        ImageView imageViewChecklist = generateImageviewIcons("guiTodolist/Task/Icons/icons8-aufgabenliste-48.png");
        ImageView imageViewNotes = generateImageviewIcons("guiTodolist/Task/Icons/icons8-bemerkungen-48.png");

        createNewInformationtoobarVisibleCheck(imageViewDeadline, imageViewFiles, imageViewChecklist, imageViewNotes);
        hBoxStatusElements.getChildren().addAll(buttonDelete, buttonDetails, imageViewDeadline, imageViewFiles, imageViewChecklist, imageViewNotes);
        this.getChildren().add(hBoxStatusElements);
    }

    /**
     * allows you to delete the element.
     *
     * @param buttonDelete button to which the event should be added
     */

    private void addEventToDeleteButton(Button buttonDelete) {

        buttonDelete.setOnAction(actionEvent -> {

            this.vBoxTasklist.getChildren().remove(this);
        });
    }

    /**
     * Opens a new window with detailed information about the task.
     *
     * @param buttonInfo to which the event should be added
     */

    private void addEventShowTaskInfo(Button buttonInfo) {
        buttonInfo.setOnAction(actionEvent -> {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../InfoTask/layoutInfoTask.fxml"));
                ControllerInfoTask controllerInfoTask = new ControllerInfoTask(this.task);
                fxmlLoader.setController(controllerInfoTask);
                Parent root = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Task Informationen");
                stage.show();

            } catch (IOException ex) {

            }
        });
    }

    /**
     * Generates a new element icon which is displayed on the Gui.
     *
     * @param filepath File path for the corresponding icon.
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
     * method checks whether the elements on the Gui should be visible for the user.
     *
     * @param deadline       Image object containing an image.
     * @param fileAttachment Image object containing an image.
     * @param checklist      Image object containing an image.
     * @param notes          Image object containing an image.
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
     * Add events that allow drag and drop feature.
     *
     * @param vBoxTask element which the event to be added to.
     * @param task     The object belonging to the Gui object.
     */

    private void addEventDragDetected(VBoxTask vBoxTask, Task task) {

        vBoxTask.setOnDragDetected(mouseEvent -> {
            Dragboard dragboard = vBoxTask.startDragAndDrop(TransferMode.ANY);
            ClipboardContent clipboardContent = new ClipboardContent();
            DataFormat dataFormat = new DataFormat("VBox");   // name an die ID des Objekts Binden... + prüfen Ob Objekt bereits vorhanden Ist...
            clipboardContent.put(dataFormat, task);
            dragboard.setContent(clipboardContent);
        });

    }
}
