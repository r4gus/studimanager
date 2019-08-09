package guiTodolist.Task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerTask implements Initializable {

    @FXML
    public TextArea textAreaDescription ;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listViewChecklist.setItems(itemsChecklist);
    }


    public void addEntryToChecklist(){

        itemsChecklist.add( "o " + textFieldChecklistNewEntry.getText());
        textFieldChecklistNewEntry.clear();
    }

    public void deleteEntryToChecklist(){

        itemsChecklist.remove(listViewChecklist.getSelectionModel().getSelectedItem());
    }


    public void createTask(){

        Stage stage = (Stage) this.buttonCreateTask.getScene().getWindow();
        stage.close();
    }
}
