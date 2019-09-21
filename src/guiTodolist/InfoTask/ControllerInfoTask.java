package guiTodolist.InfoTask;

import guiTodolist.Task.ControllerTask;
import guiTodolist.Task.VBoxTask;
import guiTodolist.Task.VBoxTasklist;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import sample.Main;
import todolist.Task;
import todolist.TaskCheckListItem;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The <code>ControllerInfoTask</code> object represents the controller of the Gui InfoTask.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */

public class ControllerInfoTask implements Initializable {

    @FXML
    public Label labelHeading;

    @FXML
    public Label labelDescriptionTitle;

    @FXML
    public Label labelDescription;

    @FXML
    public Label labelDateTitle;
    @FXML
    public Label labelDate;

    @FXML
    public Label labelNotesTitle;
    @FXML
    public Label labelNotes;

    @FXML
    public Label labelPriority;

    @FXML
    public ImageView imageViewStatus;

    @FXML
    public Label labelPriorityTitle;

    @FXML
    public Label labelChecklistTitle;
    @FXML
    public VBox vBoxUncompletedTask;
    @FXML
    public Label labelOpenTasks;
    @FXML
    public Label labelCompletedTasks;

    @FXML
    public VBox vBoxCompletedTask;

    @FXML
    public Label labelFileAttachmentsTitle;
    @FXML
    public VBox vBoxFileAttachment;

    @FXML
    public Button buttonCloseInfo;

    @FXML
    public Button buttonEditUsertask;

    private Task task;
    private VBoxTask vBoxTask;
    private VBoxTasklist vBoxTasklist;

    private static final String NoENTRY = "N.A. (kein Eintrag vorhanden)";

    private String filepathHigh = "guiTodolist/Task/Icons/icons8-hohe-prio-48.png";
    private String filepathMiddle = "guiTodolist/Task/Icons/icons8-mittlere-prio-48.png";
    private String filepathLow = "guiTodolist/Task/Icons/icons8-niedrige-prio-48.png";

    public ControllerInfoTask(Task task) {

        this.task = task;

    }

    public ControllerInfoTask(Task task, VBoxTask vBoxTask, VBoxTasklist vBoxTasklist){

        this(task);
        this.vBoxTask = vBoxTask;
        this.vBoxTasklist = vBoxTasklist;
    }


    /**
     * Called to initialize a controller after its root element has been completely processed
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelDescriptionTitle.setText(Main.getBundle().getString("Description"));
        labelDateTitle.setText(Main.getBundle().getString("dueDate"));
        labelNotesTitle.setText(Main.getBundle().getString("Notification"));
        labelPriorityTitle.setText(Main.getBundle().getString("Priority"));
        labelChecklistTitle.setText(Main.getBundle().getString("Checklist"));
        labelFileAttachmentsTitle.setText(Main.getBundle().getString("FileAttachments"));
        labelOpenTasks.setText(Main.getBundle().getString("OpenTasks"));
        labelCompletedTasks.setText(Main.getBundle().getString("CompletedTasks"));

          buttonEditUsertask.setText(Main.getBundle().getString("EditTask"));
          buttonCloseInfo.setText(Main.getBundle().getString("CloseInfo"));

        inizializeTextLabels();
        initializeChecklist();
        initializeFileAttachment();
        initializePriority();
    }


    /**
     * the method creates dynamically Gui elements based on a task object.
     */

    private void inizializeTextLabels() {

        MyLogger.LOGGER.entering(getClass().toString(), "inizializeTextLabels");
        if (task.getProjectTitle() != null) {
            labelHeading.setText(task.getProjectTitle());
        } else {
            labelHeading.setText(NoENTRY);
        }
        if (task.getProjectDescription() != null) {
            labelDescription.setText(task.getProjectDescription());
        } else {
            labelDescription.setText(NoENTRY);
        }
        if (task.getDeadline() != null) {
            labelDate.setText(task.getDeadline().toString());
        } else {
            labelDate.setText(NoENTRY);
        }
        if (task.getNotes() != null) {
            labelNotes.setText(task.getNotes());
        } else {
            labelNotes.setText(NoENTRY);
        }
        if (task.getPriority() != null) {
            labelPriority.setText(task.getPriority());
        } else {
            labelPriority.setText(NoENTRY);
        }
        MyLogger.LOGGER.exiting(getClass().toString(), "inizializeTextLabels");
    }


    /**
     * the method creates dynamically Gui elements based on a task object.
     */

    private void initializeChecklist() {

        MyLogger.LOGGER.entering(getClass().toString(), "initializeChecklist");
        for (TaskCheckListItem itemChecklist : task.getItemsChecklist()) {

            Label labelCheckBox = new Label("o " + itemChecklist.getChecklistTaskName());
            labelCheckBox.getStyleClass().add("label-h3");
            labelCheckBox.setWrapText(false);

            if (itemChecklist.isChecklistTaskCompleted())
                vBoxCompletedTask.getChildren().add(labelCheckBox);
            else
                vBoxUncompletedTask.getChildren().add(labelCheckBox);
        }
        MyLogger.LOGGER.exiting(getClass().toString(), "initializeChecklist");
    }


    /**
     * the method creates dynamically Gui elements based on a task object.
     */

    private void initializeFileAttachment() {

        MyLogger.LOGGER.entering(getClass().toString(), "initializeFileAttachment");
        for (File file : this.task.getFileArrayList()) {

            Hyperlink hyperlinkFileAttachment = new Hyperlink(file.getName());
            hyperlinkFileAttachment.getStyleClass().add("label-h3");
            vBoxFileAttachment.getChildren().add(hyperlinkFileAttachment);
            hyperlinkFileAttachment.setOnAction(ActionEvent -> {
                try {
                    Desktop.getDesktop().open(file);

                } catch (IOException ex) {                   //Fehlerbehandlung.... User informieren,dass datei... nicht mehr existiert...

                }
            });
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "initializeFileAttachment");
    }


    /**
     * the method creates dynamically Gui elements based on a task object.
     */

    private void initializePriority() {

        MyLogger.LOGGER.entering(getClass().toString(), "initializePriority");
        String filepath;
        if (task.getPriority() == "Hoch") {
            filepath = filepathHigh;
        } else if (task.getPriority() == "Mittel") {
            filepath = filepathMiddle;
        } else {
            filepath = filepathLow;
        }
        Image image = new Image(filepath);
        imageViewStatus.setImage(image);

        MyLogger.LOGGER.exiting(getClass().toString(), "initializePriority");
    }


    /**
     * This method contains the logic for the button "ButtonCloseInfo".
     * The button closes the window.
     */

    @FXML
    public void ButtonCloseInfo() {

        MyLogger.LOGGER.entering(getClass().toString(), "ButtonCloseInfo");
        Stage stage = (Stage) this.buttonCloseInfo.getScene().getWindow();
        stage.close();
        MyLogger.LOGGER.exiting(getClass().toString(), "ButtonCloseInfo");
    }

    /**
     * This method contains the logic for the button "buttonEditTask".
     * The button loads a new Window
     */

    @FXML
    public void buttonEditTask() {

        MyLogger.LOGGER.entering(getClass().toString(), "buttonEditTask");

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Task/layout_Task.fxml"));
            ControllerTask controllerTask = new ControllerTask(this.vBoxTask, this.vBoxTasklist);
            fxmlLoader.setController(controllerTask);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Window");
            stage.show();

        } catch (IOException ioException) {

        }
        Stage stage = (Stage) this.buttonEditUsertask.getScene().getWindow();
        stage.close();

        MyLogger.LOGGER.exiting(getClass().toString(), "buttonEditTask");
    }
}
