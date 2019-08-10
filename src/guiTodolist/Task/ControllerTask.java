package guiTodolist.Task;

import guiTodolist.ControllerTodolist;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    private VBox vboxTodoList;

    public ControllerTask(){

    }

    public ControllerTask(ControllerTodolist controllerTodolist, VBox vboxTodoList){

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
     * generates a ToDoList with different functions e.g. adding a task etc.
     */

    public void createTask() {


        VBox vBoxnewTask = createNewGuiElemnts();
        /* add new Task  */
        vboxTodoList.getChildren().add(vBoxnewTask);


        Stage stage = (Stage) this.buttonCreateTask.getScene().getWindow();
        stage.close();
    }

    /**
     * generates a Task with different functions e.g. show basic information.
     */

    public VBox createNewGuiElemnts()
    {

        VBox vBoxTask = new VBox();
        vBoxTask.getStyleClass().add("vBoxTask");
        vBoxTask.setAlignment(Pos.CENTER);
        vboxTodoList.setMargin(vBoxTask, new Insets(5, 10, 5, 10));

        Label labelHeading = new Label(textFieldHeadingTask.getText());
        labelHeading.setPadding(new Insets(2, 10, 2, 10));
        vBoxTask.setMargin(labelHeading, new Insets(5, 10, 5, 10));

        /*  Hbox with Different options to show user */
        HBox hBoxStatusElements = new HBox();
        createNewInformationtoobar(vBoxTask ,hBoxStatusElements);

        vBoxTask.getChildren().addAll(labelHeading, hBoxStatusElements );

        return vBoxTask;
    }

    /**
     * generates info-toolbar a Task with different functions e.g. show basic information.
     *
     * @param vBoxTask              The element contains all elements with events belonging to the task of a ToDolist.
     * @param hBoxStatusElements    Elements in the Hbox inform the user about general things, e.g. whether the task has a file attachment or not.
     */

    private void createNewInformationtoobar(VBox vBoxTask , HBox hBoxStatusElements){

        vBoxTask.setMargin(hBoxStatusElements, new Insets(5, 10, 5, 10));
        hBoxStatusElements.setAlignment(Pos.CENTER);
        hBoxStatusElements.setSpacing(10);
        Button buttonEdit = new Button("...");
        Button buttonDetails = new Button(" + ");
        Button buttonDeadline = new Button("L");
        Button buttonFileAttachment = new Button("F");
        Button buttonNotes = new Button("N");
        hBoxStatusElements.getChildren().addAll(buttonEdit, buttonDetails, buttonDeadline, buttonFileAttachment, buttonNotes);
    }


}
