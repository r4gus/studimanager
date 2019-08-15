package guiTodolist.InfoTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import todolist.Task;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInfoTask implements Initializable {

    @FXML
    public Label labelHeading;

    @FXML
    public Label labelDescription;

    @FXML
    public Label labelDate;

    @FXML
    public Label labelNotes;


    private Task task;


    public ControllerInfoTask(Task task){

        this.task = task;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        labelHeading.setText(task.getProjectTitle());
        labelDescription.setText(task.getProjectDescription());
        labelDate.setText(task.getDeadline().toString());
        labelNotes.setText(task.getNotes().toString());

    }
}
