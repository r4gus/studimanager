package guiTodolist.Task;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
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
import sample.Main;
import todolist.Task;
import todolist.TaskList;
import todolist.TaskListCollection;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The <code>ControllerTask</code> object represents the controller of the Gui CreateTask.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */

public class VBoxTasklist extends VBox {


    private HBox hBoxToDoLists;
    private TaskListCollection taskListCollection;
    private TaskList taskList;
    private int taskListID;
    private ArrayList<VBoxTask> vBoxTaskArrayList = new ArrayList<>();

    private static int currentTaskListId = 0;

    private static final String pathControllerTask = "layout_Task.fxml";


    public VBoxTasklist(TaskListCollection taskListCollection, TaskList taskList, HBox hBoxToDoLists) {

        this.taskListCollection = taskListCollection;
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

    public TaskList getTaskList() {
        return taskList;
    }

    public ArrayList<VBoxTask> getvBoxTaskArrayList() {
        return vBoxTaskArrayList;
    }

    public void setvBoxTaskArrayList(ArrayList<VBoxTask> vBoxTaskArrayList) {
        this.vBoxTaskArrayList = vBoxTaskArrayList;
    }

    public void addVBoxTask(VBoxTask vBoxTask) {

        this.vBoxTaskArrayList.add(vBoxTask);
    }

    public void deleteVBoxTask(VBoxTask vBoxTask) {
        this.vBoxTaskArrayList.remove(vBoxTask);
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
        this.setPrefWidth(350);
        this.setMinWidth(350);
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
            /* Vergleichen Ob Task bereits vorhanden ist */
            for (Task taskCompare : this.taskList.getTasks()) {

                if (task.equals(taskCompare)) {
                    return;
                }
            }

            /* alter Task von alter Liste finden    */
            VBoxTasklist vBoxTasklistOLD = findTaskListWithID(task.getTaskListId());
            VBoxTask vBoxTaskOLD = findVBoxTaskWithID(task.getTaskId(), vBoxTasklistOLD);
            vBoxTasklistOLD.getChildren().remove(vBoxTaskOLD);
            vBoxTasklistOLD.getTaskList().deleteTask(vBoxTaskOLD.getTask());        /*  Delete old Task from old TaskList */
            vBoxTasklistOLD.deleteVBoxTask(vBoxTaskOLD);                            /* Delete VBox from VBoxTaskArrayList in old VBoxTaskList */

            VBoxTask vBoxTask = new VBoxTask(task, vBoxToDoList);
            this.vBoxTaskArrayList.add(vBoxTask);                   /* add VBoxArrayList new Vboxtask */
            this.taskList.addTask(task);                            /*  add new Task to new TaskList */
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

    private VBoxTasklist findTaskListWithID(int taskListID) {

        MyLogger.LOGGER.entering(getClass().toString(), "findTaskListWithID", new Object[]{taskListID});
        for (Node node : this.hBoxToDoLists.getChildren()) {
            if (node instanceof VBoxTasklist) {
                VBoxTasklist vBoxTasklist = (VBoxTasklist) node;
                if (vBoxTasklist.getTaskListID() == taskListID) {
                    MyLogger.LOGGER.exiting(getClass().toString(), "findTaskListWithID", vBoxTasklist);
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
        labelHeading.getStyleClass().add("label-h1");
        VBox vBoxHeading = new VBox();
        vBoxHeading.getChildren().addAll(labelHeading);
        vBoxHeading.setAlignment(Pos.CENTER);
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
        generateContextMenuItems(contextMenuEditTask, todoList);

        buttonEditToDoList.setOnAction(actionEvent -> {
            contextMenuEditTask.show(buttonEditToDoList, Side.BOTTOM, -45, 5);
        });
        MyLogger.LOGGER.exiting(getClass().toString(), "generateHBoxEditButton");
    }


    /**
     * The method generates a context menu.
     * The user can then edit his task list. E.g. add new tasks.
     *
     * @param contextMenuEditTask Context menu to which the individual entries with events are to be added
     * @param todoList            List to which the button with context menu should be added.
     */

    private void generateContextMenuItems(ContextMenu contextMenuEditTask, VBox todoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "generateContextMenuItems", new Object[]{contextMenuEditTask, todoList});
        MenuItem menuItemNewTask = new MenuItem(Main.getBundle().getString("newTask"));
        generateAddTaskFunction(menuItemNewTask, todoList);
        MenuItem menuItemEditList = new MenuItem(Main.getBundle().getString("EditList"));
        Menu menuItemSort = new Menu(Main.getBundle().getString("SortBy"));
        MenuItem subMmenuItemSortDate = new MenuItem(Main.getBundle().getString("byDueDate"));
        MenuItem subMmenuItemSortAlphabet = new MenuItem(Main.getBundle().getString("Alphabetical"));
        MenuItem subMmenuItemSortPriority = new MenuItem(Main.getBundle().getString("byPriority"));
        sortTasksAfterDateFunction(subMmenuItemSortDate, todoList);
        sortTasksAlphabeticalFunction(subMmenuItemSortAlphabet, todoList);
        sortTasksAfterPriorityFunction(subMmenuItemSortPriority, todoList);
        menuItemSort.getItems().addAll(subMmenuItemSortDate, subMmenuItemSortAlphabet, subMmenuItemSortPriority);
        MenuItem menuItemDeleteList = new MenuItem(Main.getBundle().getString("DeleteList"));
        generateDeleteFunction(menuItemDeleteList, todoList);
        contextMenuEditTask.getItems().addAll(menuItemNewTask, menuItemEditList, menuItemSort, menuItemDeleteList);
        MyLogger.LOGGER.exiting(getClass().toString(), "generateContextMenuItems");
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

            this.taskListCollection.remove(this.taskList);
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

    private void sortTasksAfterDateFunction(MenuItem menuItem, VBox todoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "sortTasksAfterDateFunction", new Object[]{menuItem, todoList});
        menuItem.setOnAction(actionEvent -> {

            ArrayList<Task> unsortedTaskList = this.taskList.getTasks();
            ArrayList<VBoxTask> unsortedVBoxList = this.vBoxTaskArrayList;
            ArrayList<LocalDate> unsortedTaskDates = new ArrayList<>();

            for (Task task : unsortedTaskList) {
                if(task.getDeadline() != null)
                unsortedTaskDates.add(task.getDeadline());
            }
            Collections.sort(unsortedTaskDates);

            ArrayList<VBoxTask> sortedVBoxList = new ArrayList<>();
            ArrayList<Task> sortedTaskList = new ArrayList<>();

            for (int p = 0; p < unsortedTaskDates.size(); p++) {
                for (int i = 0; i < unsortedTaskList.size(); i++) {

                    if(unsortedTaskDates.get(p).equals(unsortedTaskList.get(i).getDeadline()))
                    {
                        sortedTaskList.add(unsortedTaskList.get(i));
                        sortedVBoxList.add(unsortedVBoxList.get(i));
                    }
                }
            }
            int counter = 0;
            for (Task task: unsortedTaskList) {
                if(task.getDeadline() == null)
                {
                    sortedTaskList.add(unsortedTaskList.get(counter));
                    sortedVBoxList.add(unsortedVBoxList.get(counter));
                }
                counter++;
            }

            this.taskList.setTasks(sortedTaskList);
            this.vBoxTaskArrayList = sortedVBoxList;
            this.getChildren().removeAll(sortedVBoxList);
            for (VBoxTask vBoxTask : sortedVBoxList) {
                this.getChildren().add(vBoxTask);
            }
        });
        MyLogger.LOGGER.exiting(getClass().toString(), "sortTasksAfterDateFunction");
    }


    /**
     * This event allows you to sort task lists.
     *
     * @param menuItem The parameter contains the corresponding element to which the event is to be assigned.
     * @param todoList Task list in which the tasks are to be re-sorted.
     */

    private void sortTasksAfterPriorityFunction(MenuItem menuItem, VBox todoList) {

        MyLogger.LOGGER.entering(getClass().toString(), "sortTasksAfterPriorityFunction", new Object[]{menuItem, todoList});
        menuItem.setOnAction(actionEvent -> {

            ArrayList<Task> unsortedTaskList = this.taskList.getTasks();
            ArrayList<VBoxTask> unsortedVBoxList = this.vBoxTaskArrayList;
            ArrayList<String> unsortedTaskTitles = new ArrayList<>();

            for (Task task : unsortedTaskList) {
                unsortedTaskTitles.add(task.getProjectTitle());
            }

            ArrayList<VBoxTask> sortedVBoxList = new ArrayList<>();
            ArrayList<Task> sortedTaskList = new ArrayList<>();

            for (int p = 0; p < 3; p++) {
                for (int i = 0; i < unsortedTaskList.size(); i++) {
                    if (p == 0) {
                        if (unsortedTaskList.get(i).getPriority().equals("Hoch")) {
                            sortedTaskList.add(unsortedTaskList.get(i));
                            sortedVBoxList.add(unsortedVBoxList.get(i));
                        }
                    }
                    if (p == 1) {
                        if (unsortedTaskList.get(i).getPriority().equals("Mittel")) {
                            sortedTaskList.add(unsortedTaskList.get(i));
                            sortedVBoxList.add(unsortedVBoxList.get(i));
                        }
                    }
                    if (p == 2) {
                        if (unsortedTaskList.get(i).getPriority().equals("Niedrig")) {
                            sortedTaskList.add(unsortedTaskList.get(i));
                            sortedVBoxList.add(unsortedVBoxList.get(i));
                        }
                    }
                }
            }
            this.taskList.setTasks(sortedTaskList);
            this.vBoxTaskArrayList = sortedVBoxList;
            this.getChildren().removeAll(sortedVBoxList);
            for (VBoxTask vBoxTask : sortedVBoxList) {
                this.getChildren().add(vBoxTask);
            }
        });
        MyLogger.LOGGER.exiting(getClass().toString(), "sortTasksAfterPriorityFunction");
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

            ArrayList<Task> unsortedTaskList = this.taskList.getTasks();
            ArrayList<VBoxTask> unsortedVBoxList = this.vBoxTaskArrayList;
            ArrayList<String> unsortedTaskTitles = new ArrayList<>();

            for (Task task : unsortedTaskList) {
                unsortedTaskTitles.add(task.getProjectTitle());
            }
            Collections.sort(unsortedTaskTitles);

            ArrayList<VBoxTask> sortedVBoxList = new ArrayList<>();
            ArrayList<Task> sortedTaskList = new ArrayList<>();
            for (String projectTitle : unsortedTaskTitles) {

                int index = findTaskObject(unsortedTaskList, projectTitle);
                sortedVBoxList.add(unsortedVBoxList.get(index));
                sortedTaskList.add(unsortedTaskList.get(index));
            }

            this.taskList.setTasks(sortedTaskList);
            this.vBoxTaskArrayList = sortedVBoxList;
            this.getChildren().removeAll(sortedVBoxList);
            for (VBoxTask vBoxTask : sortedVBoxList) {
                this.getChildren().add(vBoxTask);
            }


        });
        MyLogger.LOGGER.exiting(getClass().toString(), "sortTasksAlphabeticalFunction");
    }


    /**
     * This method find an Object in an ArrayList and returns index
     *
     * @param list         which you will find in the List.
     * @param projectTitle list of tasks.
     */

    private int findTaskObject(ArrayList<Task> list, String projectTitle) {

        MyLogger.LOGGER.entering(getClass().toString(), "findTaskObject", new Object[]{list, projectTitle});
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProjectTitle().equals(projectTitle)) {
                index = i;
                break;
            }
        }
        MyLogger.LOGGER.exiting(getClass().toString(), "findTaskObject", index);
        return index;
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
