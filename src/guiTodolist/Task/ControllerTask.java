package guiTodolist.Task;

import input.elements.textfield.AlphaNumTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logging.MyLogger;
import todolist.Task;
import todolist.TaskCheckListItem;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * The <code>ControllerTask</code> object represents the controller of the Gui CreateTask.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */


public class ControllerTask implements Initializable {

    @FXML
    public AnchorPane anchorPaneCreateTask;

    @FXML
    public AlphaNumTextField textFieldHeadingTask;

    @FXML
    public TextArea textAreaDescription;
    @FXML
    public TextArea textAreaNotes;

    @FXML
    public DatePicker datePickerDueDate;

    @FXML
    public ListView listViewChecklist;
    @FXML
    public AlphaNumTextField textFieldChecklistNewEntry;

    @FXML
    public ListView listViewFileAttachment;
    @FXML
    public ComboBox comboboxPriority;

    @FXML
    public Button buttonAddChecklistEntry;
    @FXML
    public Button buttonDeleteChecklistEntry;
    @FXML
    public Button buttonAddFileAttachment;
    @FXML
    public Button buttonDeleteFileAttachment;

    @FXML
    public Button buttonCreateTask;


    private ObservableList<CheckBox> itemsChecklist = FXCollections.observableArrayList();
    private ObservableList<String> itemsFilesList = FXCollections.observableArrayList();
    private ObservableList<String> itemsPriority = FXCollections.observableArrayList();

    private final String filepathAddIcon = "guiTodolist/Task/Icons/icons8-hinzufuegen-48.png";
    private final String filepathDeleteIcon = "guiTodolist/Task/Icons/icons8-unwiederuflich-loeschen-48.png";

    private ArrayList<TaskCheckListItem> taskCheckListItems = new ArrayList<>();
    private ArrayList<File> taskFiles = new ArrayList<>();

    private VBoxTasklist vboxTaskList;
    private Task currentTask;
    private VBoxTask vBoxTask;


    public ControllerTask(VBoxTasklist vboxTasklist) {

        this.vboxTaskList = vboxTasklist;
    }

    public ControllerTask(VBoxTask vBoxTask, VBoxTasklist vBoxTasklist) {

        this(vBoxTasklist);
        this.vBoxTask = vBoxTask;
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
        listViewFileAttachment.setItems(itemsFilesList);
        itemsPriority.addAll("Hoch", "Mittel", "Niedrig");
        comboboxPriority.setItems(itemsPriority);
        comboboxPriority.getSelectionModel().select(2);

        initButtonAddPicture(buttonAddChecklistEntry, filepathAddIcon);
        initButtonAddPicture(buttonDeleteChecklistEntry, filepathDeleteIcon);
        initButtonAddPicture(buttonAddFileAttachment, filepathAddIcon);
        initButtonAddPicture(buttonDeleteFileAttachment, filepathDeleteIcon);

        // Load information into Window.
        if (this.vBoxTask != null) {
            this.currentTask = vBoxTask.getTask();
            initLeftSide();
            initRightSide();
            loadFileAttachments();
        }
    }


    /**
     * load Information into Gui
     */

    private void initLeftSide() {

        if (this.currentTask.getProjectTitle() != null)
            textFieldHeadingTask.setText(this.currentTask.getProjectTitle());
        if (this.currentTask.getProjectDescription() != null)
            textAreaDescription.setText(this.currentTask.getProjectDescription());
        if (this.currentTask.getDeadline() != null)
            datePickerDueDate.setValue(currentTask.getDeadline());
        if (this.currentTask.getNotes() != null)                //Notes
            textAreaNotes.setText(this.currentTask.getNotes());
    }


    /**
     * load Information into Gui
     */

    private void initRightSide() {

        String prio = "";
        if (this.currentTask.getPriority() != null)
            prio = this.currentTask.getPriority();
        switch (prio) {
            case "Hoch":
                comboboxPriority.getSelectionModel().select(0);
                ;
                break;
            case "Mittel":
                comboboxPriority.getSelectionModel().select(1);
                ;
                break;
            case "Niedrig":
                comboboxPriority.getSelectionModel().select(2);
                ;
                break;
        }

        if (this.currentTask.getItemsChecklist() != null) {
            for (TaskCheckListItem taskCheckListItem : this.currentTask.getItemsChecklist()) {

                CheckBox checkBoxItem = new CheckBox(taskCheckListItem.getChecklistTaskName());
                checkBoxItem.setSelected(taskCheckListItem.isChecklistTaskCompleted());
                itemsChecklist.add(checkBoxItem);
                taskCheckListItems.add(taskCheckListItem);
            }
        }
    }


    /**
     * load Information into Gui
     */

    private void loadFileAttachments() {

        for (File file : this.currentTask.getFileArrayList()) {

            this.itemsFilesList.add(file.getName());
            this.taskFiles.add(file);
        }
    }


    /**
     * Adds a new entry to the checklist
     */

    public void addEntryToChecklist() {

        MyLogger.LOGGER.entering(getClass().toString(), "addEntryToChecklist");

        if (textFieldChecklistNewEntry.getText().trim().isEmpty())
        {
            textFieldChecklistNewEntry.showError("Textfeld darf nicht leer sein");
            return;
        }
        CheckBox checkBoxItem = new CheckBox(textFieldChecklistNewEntry.getText());
        itemsChecklist.add(checkBoxItem);
        TaskCheckListItem taskCheckListItem = new TaskCheckListItem(textFieldChecklistNewEntry.getText());
        taskCheckListItems.add(taskCheckListItem);
        textFieldChecklistNewEntry.clear();

        MyLogger.LOGGER.exiting(getClass().toString(), "addEntryToChecklist");
    }

    /**
     * Removes a new entry to the checklist
     */

    public void deleteEntryToChecklist() {

        MyLogger.LOGGER.entering(getClass().toString(), "deleteEntryToChecklist");          /* Exception Handling  */
        if(listViewChecklist.getSelectionModel().getSelectedIndex() == -1 )
            return;
        int index = listViewChecklist.getSelectionModel().getSelectedIndex();
        itemsChecklist.remove(index);
        taskCheckListItems.remove(index);
        MyLogger.LOGGER.exiting(getClass().toString(), "deleteEntryToChecklist");
    }


    /**
     * Adds a new file attachment to a list of file.
     */

    public void AddFileAttachmentToTask() {

        MyLogger.LOGGER.entering(getClass().toString(), "AddFileAttachmentToTask");         /* Exception Handling  */

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei für Aufgabe auswählen");


        List<File> files = fileChooser.showOpenMultipleDialog(null);
        for (File file : files) {

            this.itemsFilesList.add(file.getName());
            this.taskFiles.add(file);
        }
        MyLogger.LOGGER.exiting(getClass().toString(), "AddFileAttachmentToTask");
    }

    /**
     * Removes a new entry to the checklist
     */

    public void deleteFileAttachmentToTask() {                                                          /* Exception Handling  */

        MyLogger.LOGGER.entering(getClass().toString(), "deleteFileAttachmentToTask");
        if(listViewChecklist.getSelectionModel().getSelectedIndex() == -1 )
            return;
        int index = listViewFileAttachment.getSelectionModel().getSelectedIndex();
        this.itemsFilesList.remove(index);
        this.taskFiles.remove(index);

        MyLogger.LOGGER.exiting(getClass().toString(), "deleteFileAttachmentToTask");
    }


    /**
     * add image to Button.
     */

    private void initButtonAddPicture(Button button, String filepathAddIcon) {                          /* Exception Handling  */

        MyLogger.LOGGER.entering(getClass().toString(), "initButtonAddPicture");
        ImageView imageView = new ImageView(new Image(filepathAddIcon));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        button.setMaxWidth(20);
        button.setGraphic(imageView);
        MyLogger.LOGGER.exiting(getClass().toString(), "initButtonAddPicture");
    }


    private void updateVBoxTask() {

        MyLogger.LOGGER.entering(getClass().toString(), "updateVBoxTask");
        vboxTaskList.getChildren().remove(this.vBoxTask);     /* remove VBox From ToDoList */
        vboxTaskList.deleteVBoxTask(this.vBoxTask);
        MyLogger.LOGGER.exiting(getClass().toString(), "updateVBoxTask");
    }


    /**
     * This method creates/ updates a task object as well as the corresponding Gui Task object.
     * Furthermore, different references and IDs are exchanged in order to be able
     * to create one of the tasks belonging to the corresponding task list or the objects behind it.
     */

    public void createTask() {

        MyLogger.LOGGER.entering(getClass().toString(), "createTask");
        if (textFieldHeadingTask.getText().trim().isEmpty()) {
            textFieldHeadingTask.showError("Textfeld dar nicht leer sein");
            return;
        }
        if (vBoxTask != null) {
            updateVBoxTask();
            this.vboxTaskList.getTaskList().deleteTask(currentTask);   /* Object Task is removed to TaskList */
        }
        this.currentTask = createTaskObjekt();               /* Object Task is created */
        this.vboxTaskList.getTaskList().addTask(currentTask);   /* Object Task is add to TaskList */
        VBoxTask vBoxNewTask = createNewGuiElemnts();               /* VboxTask will be created  */
        vBoxNewTask.setTaskID(this.currentTask.getTaskId());            /*  Add TaskID from Object */
        currentTask.setTaskListId(vboxTaskList.getTaskListID());            /* Add TaskList-ID to Object from taskList */
        vboxTaskList.getChildren().add(vBoxNewTask);
        vboxTaskList.getvBoxTaskArrayList().add(vBoxNewTask);                              /* Add VboxTask to VboxTaskList  */
        Stage stage = (Stage) this.buttonCreateTask.getScene().getWindow();
        stage.close();
        MyLogger.LOGGER.exiting(getClass().toString(), "createTask");
    }

    /**
     * This method creates the Gui object task and all associated functions,
     * e.g. the drag and drop feature.
     */

    public VBoxTask createNewGuiElemnts() {

        MyLogger.LOGGER.entering(getClass().toString(), "createNewGuiElemnts");
        VBoxTask vBoxTask = new VBoxTask(currentTask, vboxTaskList);
        addEventDragDetected(vBoxTask, this.currentTask);
        this.vboxTaskList.setMargin(vBoxTask, new Insets(5, 10, 5, 10));
        MyLogger.LOGGER.exiting(getClass().toString(), "createNewGuiElemnts", vBoxTask);
        return vBoxTask;

    }

    /**
     * This method allows Gui Task to drag as user from one ToDoList to another ToDoList.
     *
     * @param vBoxTask Gui object to add the new event to.
     * @param task     Associated object behind the Gui object
     */

    public void addEventDragDetected(VBoxTask vBoxTask, Task task) {

        MyLogger.LOGGER.entering(getClass().toString(), "addEventDragDetected", new Object[]{vBoxTask, task});
        vBoxTask.setOnDragDetected(mouseEvent -> {
            Dragboard dragboard = vBoxTask.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent clipboardContent = new ClipboardContent();
            DataFormat dataFormat = DataFormat.lookupMimeType("VBox");
            if (dataFormat == null) {
                dataFormat = new DataFormat("VBox");
            }
            clipboardContent.put(dataFormat, task);
            dragboard.setContent(clipboardContent);
        });
        MyLogger.LOGGER.exiting(getClass().toString(), "addEventDragDetected");
    }


    /**
     * Generates the task object based on the user input from the Gui.
     * If nothing is entered in a field, the property of the object is set to null.
     */

    private Task createTaskObjekt() {

        MyLogger.LOGGER.entering(getClass().toString(), "createTaskObjekt");
        Task task = new Task(textFieldHeadingTask.getText().trim().isEmpty() ? null : textFieldHeadingTask.getText());
        task.setProjectDescription(((textAreaDescription.getText().trim().isEmpty() ? null : textAreaDescription.getText())));
        task.setDone(false);
        task.setNotes(textAreaNotes.getText().trim().isEmpty() ? null : textAreaNotes.getText());
        task.setPriority(comboboxPriority.getSelectionModel().getSelectedItem().toString());

        if (listViewChecklist.getItems().size() > 0) {
            ObservableList observableList = listViewChecklist.getItems();
            ArrayList<TaskCheckListItem> arrayList = new ArrayList<>();
            for (Object object : observableList) {
                CheckBox checkBox = (CheckBox) object;
                TaskCheckListItem taskCheckListItem = new TaskCheckListItem(checkBox.getText());
                if (checkBox.isSelected()) {
                    taskCheckListItem.setChecklistTaskCompleted(true);
                }
                arrayList.add(taskCheckListItem);
            }
            task.setItemsChecklist(arrayList);
        }
        if (taskFiles.size() > 0) {
            task.setFileArrayList(this.taskFiles);
        }
        task.setDeadline(datePickerDueDate.getValue());
        MyLogger.LOGGER.exiting(getClass().toString(), "createTaskObjekt", task);
        return task;
    }


}




