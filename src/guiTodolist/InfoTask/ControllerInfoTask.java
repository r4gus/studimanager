package guiTodolist.InfoTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logging.MyLogger;
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
    public Label labelDescription;

    @FXML
    public Label labelDate;

    @FXML
    public Label labelNotes;

    @FXML
    public Label labelPriority;

    @FXML
    public ImageView imageViewStatus;

    @FXML
    public VBox vBoxUncompletedTask;

    @FXML
    public VBox vBoxCompletedTask;

    @FXML
    public VBox vBoxFileAttachment;

    @FXML
    public Button ButtonCloseInfo;

    private Task task;

    private static final String NoENTRY = "N.A. (kein Eintrag vorhanden)";

    private String filepathHigh = "guiTodolist/Task/Icons/icons8-hohe-prio-48.png";
    private String filepathMiddle = "guiTodolist/Task/Icons/icons8-mittlere-prio-48.png";
    private String filepathLow = "guiTodolist/Task/Icons/icons8-niedrige-prio-48.png";

    public ControllerInfoTask(Task task) {

        this.task = task;
    }


    /**
     * Called to initialize a controller after its root element has been completely processed
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        if(task.getPriority() != null){
            labelPriority.setText(task.getPriority());
        }else {
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

            Label labelCheckBox = new Label(itemChecklist.getChecklistTaskName());
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
        Stage stage = (Stage) this.ButtonCloseInfo.getScene().getWindow();
        stage.close();
        MyLogger.LOGGER.exiting(getClass().toString(), "ButtonCloseInfo");
    }
}
