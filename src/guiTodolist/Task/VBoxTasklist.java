package guiTodolist.Task;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import todolist.Task;
import todolist.TaskList;

import java.io.IOException;

/**
 * The <code>ControllerTask</code> object represents the controller of the Gui CreateTask.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */

public class VBoxTasklist extends VBox {


    private HBox hBoxToDoLists;
    private TaskList taskList;
    private int taskListID;

    private static int currentTaskListId = 0;

    private static final String pathControllerTask = "layout_Task.fxml";


    public VBoxTasklist(TaskList taskList, HBox hBoxToDoLists) {

        this.taskList = taskList;
        this.hBoxToDoLists = hBoxToDoLists;
        initVBoxTaskList();

        this.taskListID = currentTaskListId;
        currentTaskListId++;
    }

    public int getTaskListID() {
        return taskListID;
    }

    public void setTaskListID(int taskListID) {
        this.taskListID = taskListID;
    }


    /**
     * Generates a graphic element task-list, which can contain graphic tasks.
     */

    private void initVBoxTaskList() {

        MyLogger.LOGGER.entering(getClass().toString(), "initVBoxTaskList");
        createNewSection();
        addSetOnDragOverEvent(this);
        generateHBoxHeading(this);
        MyLogger.LOGGER.exiting(getClass().toString(), "initVBoxTaskList");
    }


    /**
     * Generates the Vbox with the corresponding elements and events.
     */

    private void createNewSection() {

        MyLogger.LOGGER.entering(getClass().toString(), "createNewSection");
        addSetOnDragOverEvent(this);
        this.setPrefWidth(300);
        this.setMinWidth(300);
        hBoxToDoLists.setMargin(this, new Insets(10, 10, 10, 10));

        HBox hBoxHeading = generateHBoxHeading(this);
        this.getChildren().add(hBoxHeading);

        this.getStyleClass().add("vBox");
        this.setMargin(hBoxHeading, new Insets(10, 10, 10, 10));
        hBoxToDoLists.getChildren().add(this);
        MyLogger.LOGGER.exiting(getClass().toString(), "createNewSection");
    }

    /**
     * The method creates an event by loading a corresponding window. This event is assigned to an item from the context menu.
     *
     * @param vBoxToDoList The object to which the event is to be assigned
     */

    private void addSetOnDragOverEvent(VBoxTasklist vBoxToDoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "addSetOnDragOverEvent", new Object[]{vBoxToDoList});
        vBoxToDoList.setOnDragOver(dragEvent -> dragEvent.acceptTransferModes(TransferMode.ANY));

        vBoxToDoList.setOnDragDropped(dragEvent -> {

            Dragboard dragboard = dragEvent.getDragboard();
            Object o = dragboard.getContent(DataFormat.lookupMimeType("VBox"));
            Task task = null;                   /*  Exception einbauen und handeln... */
            if (o instanceof Task) {
                task = (Task) o;
            }
            /* alter Task von alter Liste finden    */
            VBoxTasklist vBoxTasklistOLD = findTasklistwithID(task.getTaskListId());
            VBoxTask vBoxTaskOLD = findVBoxTaskWithID(task.getTaskId(), vBoxTasklistOLD);
            vBoxTasklistOLD.getChildren().remove(vBoxTaskOLD);

            VBoxTask vBoxTask = new VBoxTask(task, vBoxToDoList);
            vBoxToDoList.getChildren().add(vBoxTask);
            task.setTaskListId(vBoxToDoList.getTaskListID());
            vBoxToDoList.setMargin(vBoxTask, new Insets(5, 10, 5, 10));
        });
        MyLogger.LOGGER.exiting(getClass().toString(), "addSetOnDragOverEvent");
    }


    /**
     * Finds the object VBoxTaskList in a list of objects by means of an ID.
     *
     * @param taskListID Object with corresponding ID.
     */

    private VBoxTasklist findTasklistwithID(int taskListID) {

        MyLogger.LOGGER.entering(getClass().toString(), "findTasklistwithID", new Object[]{taskListID});
        for (Node node : this.hBoxToDoLists.getChildren()) {
            if (node instanceof VBoxTasklist) {
                VBoxTasklist vBoxTasklist = (VBoxTasklist) node;
                if (vBoxTasklist.getTaskListID() == taskListID) {
                    MyLogger.LOGGER.exiting(getClass().toString(), "findTasklistwithID", vBoxTasklist);
                    return vBoxTasklist;
                }
            }
        }
        return null;        //Exception Handling
    }

    /**
     * Finds the object VBoxTaskList in a list of objects by means of an ID.
     *
     * @param taskID       Object with corresponding ID.
     * @param vBoxTaskList Task list with task objects that also contain the desired object.
     */


    private VBoxTask findVBoxTaskWithID(int taskID, VBoxTasklist vBoxTaskList) {

        MyLogger.LOGGER.entering(getClass().toString(), "findVBoxTaskWithID", new Object[]{taskID, vBoxTaskList});
        for (Node node : vBoxTaskList.getChildren()) {

            if (node instanceof VBoxTask) {
                VBoxTask vBoxTask = (VBoxTask) node;
                if (vBoxTask.getTaskID() == taskID) {
                    MyLogger.LOGGER.exiting(getClass().toString(), "findVBoxTaskWithID", vBoxTask);
                    return vBoxTask;
                }
            }
        }
        return null;            //Exception Handling
    }


    /**
     * Generates the heading of the GUI element.
     *
     * @param todoList Gui object to which the heading should be added.
     */

    private HBox generateHBoxHeading(VBox todoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "generateHBoxHeading", new Object[]{todoList});
        HBox hBoxHeading = new HBox();                                      /*  add List controls and heading */
        Label labelHeading = new Label(this.taskList.getHeading());
        VBox vBoxTest = new VBox();
        vBoxTest.getChildren().addAll(labelHeading);
        vBoxTest.setAlignment(Pos.CENTER);
        labelHeading.setPadding(new Insets(2, 10, 2, 10));
        Button buttonEditList = new Button("...");
        generateHBoxEditButton(buttonEditList, todoList);

        int factor = 18;                                    /* Berechnung der zentralen Possition muss noch verbessert werden!!  */
        if (this.taskList.getHeading().length() > 8)
            factor = 10;
        double centerHeading = ((300 - (this.taskList.getHeading().length()) * factor) - 10) / 2;
        hBoxHeading.setMargin(labelHeading, new Insets(0, centerHeading, 0, 0));

        hBoxHeading.getChildren().addAll(labelHeading, buttonEditList);
        hBoxHeading.setAlignment(Pos.CENTER_RIGHT);

        MyLogger.LOGGER.exiting(getClass().toString(), "generateHBoxHeading", hBoxHeading);
        return hBoxHeading;
    }


    /**
     * The method generates a context menu which is added to a button.
     * The user can then edit his task list. E.g. add new tasks.
     *
     * @param buttonEditToDoList The parameter contains the corresponding element to which the event is to be assigned.
     * @param todoList           List to which the button with context menu should be added.
     */

    private void generateHBoxEditButton(Button buttonEditToDoList, VBox todoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "generateHBoxEditButton", new Object[]{buttonEditToDoList, todoList});
        ContextMenu contextMenuEditTask = new ContextMenu();

        MenuItem menuItemNewTask = new MenuItem("Neue Aufgabe");
        generateAddTaskFunction(menuItemNewTask, todoList);
        MenuItem b = new MenuItem("Liste bearbeiten");
        Menu menuItemSort = new Menu("Sortieren nach:");
        MenuItem subMmenuItemSortDate = new MenuItem("Nach Fälligkeitsdatum");
        MenuItem subMmenuItemSortAlphabet = new MenuItem("Alphabetisch");
        sortTasksAlphabeticalFunction(subMmenuItemSortAlphabet, todoList);
        MenuItem subMmenuItemSortbla = new MenuItem("...");
        menuItemSort.getItems().addAll(subMmenuItemSortDate, subMmenuItemSortAlphabet, subMmenuItemSortbla);
        MenuItem menuItemDeleteList = new MenuItem("Liste löschen");
        generateDeleteFunction(menuItemDeleteList, todoList);
        MenuItem e = new MenuItem("...");
        contextMenuEditTask.getItems().addAll(menuItemNewTask, b, menuItemSort, menuItemDeleteList, e);
        buttonEditToDoList.setContextMenu(contextMenuEditTask);
        MyLogger.LOGGER.exiting(getClass().toString(), "generateHBoxEditButton");
    }


    /**
     * This event allows you to delete task lists.
     *
     * @param menuItem The parameter contains the corresponding element to which the event is to be assigned.
     * @param todoList Element to be deleted.
     */

    private void generateDeleteFunction(MenuItem menuItem, VBox todoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "generateDeleteFunction", new Object[]{menuItem, todoList});
        menuItem.setOnAction(actionEvent -> {

            hBoxToDoLists.getChildren().remove(todoList);
        });
        MyLogger.LOGGER.exiting(getClass().toString(), "generateDeleteFunction");
    }


    /**
     * This event allows you to sort task lists.
     *
     * @param menuItem The parameter contains the corresponding element to which the event is to be assigned.
     * @param todoList Task list in which the tasks are to be re-sorted.
     */

    private void sortTasksAlphabeticalFunction(MenuItem menuItem, VBox todoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "sortTasksAlphabeticalFunction", new Object[]{menuItem, todoList});
        menuItem.setOnAction(actionEvent -> {


        });
        MyLogger.LOGGER.exiting(getClass().toString(), "sortTasksAlphabeticalFunction");
    }

    /**
     * This event allows you to add tasks.
     *
     * @param menuItem The parameter contains the corresponding element to which the event is to be assigned.
     * @param toDoList Task list in which the tasks should be added.
     */

    private void generateAddTaskFunction(MenuItem menuItem, VBox toDoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "generateAddTaskFunction", new Object[]{menuItem, toDoList});
        menuItem.setOnAction(actionEvent -> {

            ControllerTask controllerTask = new ControllerTask(this);
            loadNewWindow("Aufgabe", pathControllerTask, controllerTask);

        });
        MyLogger.LOGGER.exiting(getClass().toString(), "generateAddTaskFunction");
    }


    /**
     * The method loads a new window according to the specified parameters.
     *
     * @param title    Title of the new window
     * @param fxmlPath File path to the required fxml file.
     */

    private void loadNewWindow(String title, String fxmlPath, Object controller) {

        MyLogger.LOGGER.entering(getClass().toString(), "loadNewWindow", new Object[]{title, fxmlPath, controller});
        try {
            //Load second scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fxmlLoader.setController(controller);
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
        MyLogger.LOGGER.exiting(getClass().toString(), "loadNewWindow");
    }

}
