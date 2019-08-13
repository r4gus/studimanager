package guiTodolist.Task;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import todolist.Task;
import todolist.TaskList;

import java.io.IOException;

/**
 * The <code>ControllerTask</code> object represents the controller of the Gui CreateTask.
 * This is one of the tabs of the TabPane.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */

public class VBoxTasklist extends VBox {


    private HBox hboxToDoLists;
    private TaskList taskList;
    private int taskListID;

    private static int currentTaskListId = 0;

    private static final String pathControllerTask = "layout_Task.fxml";


    public VBoxTasklist(TaskList taskList, HBox hboxToDoLists) {

        this.taskList = taskList;
        this.hboxToDoLists = hboxToDoLists;
        initVboxTasklist();

        this.taskListID = currentTaskListId;
        currentTaskListId++;
    }

    public int getTaskListID() {
        return taskListID;
    }

    public void setTaskListID(int taskListID) {
        this.taskListID = taskListID;
    }

    private void initVboxTasklist() {

        createNewSection();
        addSetOnDragOverEvent(this);
        generateHboxHeading(this);
    }


    /**
     * The method creates an event by loading a corresponding window. This event is assigned to an item from the context menu.
     */

    private void createNewSection() {

        addSetOnDragOverEvent(this);
        this.setPrefWidth(300);
        this.setMinWidth(300);
        hboxToDoLists.setMargin(this, new Insets(10, 10, 10, 10));


        HBox hBoxHeading = generateHboxHeading(this);
        this.getChildren().add(hBoxHeading);

        this.getStyleClass().add("vBox");
        this.setMargin(hBoxHeading, new Insets(10, 10, 10, 10));
        hboxToDoLists.getChildren().add(this);

    }

    /**
     * The method creates an event by loading a corresponding window. This event is assigned to an item from the context menu.
     *
     * @param vBoxToDoList ...
     */

    private void addSetOnDragOverEvent(VBoxTasklist vBoxToDoList) {

        vBoxToDoList.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {

                dragEvent.acceptTransferModes(TransferMode.ANY);
            }
        });

        vBoxToDoList.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {

                Dragboard dragboard = dragEvent.getDragboard();

                Object o = dragboard.getContent(DataFormat.lookupMimeType("VBox"));
                Task task = (Task) o;

                /* alter Task von alter Liste finden    */
                VBoxTasklist vBoxTasklistOLD = findTasklistwithID(task.getTaskListId());
                VBoxTask vBoxTaskOLD = findVBoxTaskWithID(task.getTaskId(), vBoxTasklistOLD);
                vBoxTasklistOLD.getChildren().remove(vBoxTaskOLD);

                VBoxTask vBoxTask = new VBoxTask(task, vBoxToDoList);
                vBoxToDoList.getChildren().add(vBoxTask);
                task.setTaskListId(vBoxToDoList.getTaskListID());
                vBoxToDoList.setMargin(vBoxTask, new Insets(5, 10, 5, 10));
            }
        });
    }

    private VBoxTasklist findTasklistwithID(int taskListID) {

        for (Node node : this.hboxToDoLists.getChildren()) {
            if (node instanceof VBoxTasklist) {
                VBoxTasklist vBoxTasklist = (VBoxTasklist) node;
                if (vBoxTasklist.getTaskListID() == taskListID) {
                    return vBoxTasklist;
                }
            }
        }
        return null;        //Exception Handling
    }

    private VBoxTask findVBoxTaskWithID(int taskID, VBoxTasklist vBoxTasklist) {

        for (Node node : vBoxTasklist.getChildren()) {

            if (node instanceof VBoxTask) {
                VBoxTask vBoxTask = (VBoxTask) node;
                if (vBoxTask.getTaskID() == taskID) {
                    return vBoxTask;
                }
            }
        }
        return null;
    }


    /**
     * The method creates an event by loading a corresponding window. This event is assigned to an item from the context menu.
     *
     * @param TodoList ...
     */

    private HBox generateHboxHeading(VBox TodoList) {
        /*  add List controls and heading */
        HBox hBoxHeading = new HBox();
        Label labelHeading = new Label(this.taskList.getHeading());
        VBox vBoxTest = new VBox();
        vBoxTest.getChildren().addAll(labelHeading);
        vBoxTest.setAlignment(Pos.CENTER);
        labelHeading.setPadding(new Insets(2, 10, 2, 10));
        Button buttonEditList = new Button("...");
        generateHboxEditButton(buttonEditList, TodoList);

        /* Berechnung der Centralen Possition muss noch verbessert werden!!  */
        int factor = 18;
        if (this.taskList.getHeading().length() > 8)
            factor = 10;
        double centerHeading = ((300 - (this.taskList.getHeading().length()) * factor) - 10) / 2;
        hBoxHeading.setMargin(labelHeading, new Insets(0, centerHeading, 0, 0));

        hBoxHeading.getChildren().addAll(labelHeading, buttonEditList);
        hBoxHeading.setAlignment(Pos.CENTER_RIGHT);

        return hBoxHeading;
    }


    /**
     * The method creates an event by loading a corresponding window. This event is assigned to an item from the context menu.
     *
     * @param buttonEditToDoList The parameter contains the corresponding element to which the event is to be assigned.
     * @param TodoList           ...
     */

    private void generateHboxEditButton(Button buttonEditToDoList, VBox TodoList) {

        ContextMenu contextMenuEditTask = new ContextMenu();

        MenuItem menuItemNewTask = new MenuItem("Neue Aufgabe");
        generateAddTaskFunction(menuItemNewTask, TodoList);
        MenuItem b = new MenuItem("Liste bearbeiten");
        Menu menuItemSort = new Menu("Sortieren nach:");
        MenuItem subMmenuItemSortDate = new MenuItem("Nach Fälligkeitsdatum");
        MenuItem subMmenuItemSortAlphabet = new MenuItem("Alphabetisch");
        sortTasksAlphabeticalFunction(subMmenuItemSortAlphabet, TodoList);
        MenuItem subMmenuItemSortbla = new MenuItem("...");
        menuItemSort.getItems().addAll(subMmenuItemSortDate, subMmenuItemSortAlphabet, subMmenuItemSortbla);
        MenuItem menuItemDeleteList = new MenuItem("Liste löschen");
        generateDeleteFunction(menuItemDeleteList, TodoList);
        MenuItem e = new MenuItem("...");
        contextMenuEditTask.getItems().addAll(menuItemNewTask, b, menuItemSort, menuItemDeleteList, e);
        buttonEditToDoList.setContextMenu(contextMenuEditTask);

    }


    /**
     * The method creates an event by loading a corresponding window. This event is assigned to an item from the context menu.
     *
     * @param menuItem The parameter contains the corresponding element to which the event is to be assigned.
     * @param TodoList ...
     */

    private void generateDeleteFunction(MenuItem menuItem, VBox TodoList) {
        menuItem.setOnAction(actionEvent -> {

            hboxToDoLists.getChildren().remove(TodoList);
        });
    }


    /**
     * The method creates an event by loading a corresponding window. This event is assigned to an item from the context menu.
     *
     * @param menuItem The parameter contains the corresponding element to which the event is to be assigned.
     * @param TodoList ...
     */

    private void sortTasksAlphabeticalFunction(MenuItem menuItem, VBox TodoList) {
        menuItem.setOnAction(actionEvent -> {


        });
    }

    /**
     * The method creates an event by loading a corresponding window. This event is assigned to an item from the context menu.
     *
     * @param menuItem The parameter contains the corresponding element to which the event is to be assigned.
     * @param ToDoList ...
     */

    private void generateAddTaskFunction(MenuItem menuItem, VBox ToDoList) {
        menuItem.setOnAction(actionEvent -> {

            ControllerTask controllerTask = new ControllerTask(this);
            loadNewWindow("Aufgabe", pathControllerTask, controllerTask);

        });
    }


    /**
     * The method loads a new window according to the specified parameters.
     *
     * @param title    Title of the new window
     * @param fxmlPath File path to the required fxml file.
     */

    private void loadNewWindow(String title, String fxmlPath, Object Controller) {

        try {
            //Load second scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fxmlLoader.setController(Controller);
            Parent root = fxmlLoader.load();

            //Show scene 2 (edit Task) in new window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}
