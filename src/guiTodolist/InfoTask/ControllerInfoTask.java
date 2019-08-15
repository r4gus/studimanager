package guiTodolist.InfoTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import todolist.Task;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * The <code>ControllerInfoTask</code> object represents the controller of the Gui CreateTask.
 * This is one of the tabs of the TabPane.
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
        inizializeChecklist();

    }


    /**
     * Called to initialize a controller after its root element has been completely processed
     */

    private void inizializeTextLabels() {

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
    }


    /**
     * Called to initialize a controller after its root element has been completely processed
     */

    private void inizializeChecklist() {




    }

        /**
         * Called to initialize a controller after its root element has been completely processed
         */

    @FXML
    public void ButtonCloseInfo() {

        Stage stage = (Stage) this.ButtonCloseInfo.getScene().getWindow();
        stage.close();
    }
}
