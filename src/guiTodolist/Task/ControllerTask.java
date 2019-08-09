package guiTodolist.Task;

import guiTodolist.ControllerTodolist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
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


    public ControllerTask(){

    }

    public ControllerTask(ControllerTodolist controllerTodolist){

        this.controllerTodolist = controllerTodolist;
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
     * generates a ToDoList with different functions e.g. adding a task etc.
     */

    public void createTask() {

        VBox vBoxnewTask = createNewGuiElemnts();
        /* add new Task  */
        //Controller ToDoList brauch entsprechende Methoden zum hinzufügen / entfernen von Tasks auf HboxTask Listen halter element.
        Stage stage = (Stage) this.buttonCreateTask.getScene().getWindow();
        stage.close();
    }

    /**
     * generates a ToDoList with different functions e.g. adding a task etc.
     */

    public VBox createNewGuiElemnts()
    {

        VBox vBoxTask = new VBox();
        vBoxTask.getStyleClass().add("vBox");

        Label labelHeading = new Label(textFieldHeadingTask.getText());

        vBoxTask.getChildren().addAll(labelHeading );

        return vBoxTask;
    }

}
