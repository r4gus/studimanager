package guiTodolist.InfoTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logging.MyLogger;
import todolist.Task;
import todolist.TaskCheckListItem;

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
    public VBox vBoxUncompletedTask;

    @FXML
    public VBox vBoxCompletedTask;

    @FXML
    public Button ButtonCloseInfo;

    private Task task;

    private static final String NoENTRY = "N.A. (kein Eintrag vorhanden)";


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
        MyLogger.LOGGER.exiting(getClass().toString(), "inizializeTextLabels");
    }


    /**
     * the method creates dynamically Gui elements based on a task object.
     */

    private void initializeChecklist() {

        MyLogger.LOGGER.entering(getClass().toString(), "initializeChecklist");
        for (TaskCheckListItem t : task.getItemsChecklist()) {

            Label labelCheckBox = new Label(t.getChecklistTaskName());
            labelCheckBox.setWrapText(false);

            if (t.isChecklistTaskCompleted())
                vBoxCompletedTask.getChildren().add(labelCheckBox);
            else
                vBoxUncompletedTask.getChildren().add(labelCheckBox);
        }
        MyLogger.LOGGER.exiting(getClass().toString(), "initializeChecklist");
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