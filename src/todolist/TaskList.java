package todolist;

/**
 * The <code>TaskList</code> object represents a list of tasks created by the user.
 * It contains all basic operations to insert or delete a Task.
 *
 * @author Lukas Mendel
 */

import custom_exceptions.UserException;
import logging.MyLogger;
import java.util.ArrayList;
import java.util.logging.Level;


public class TaskList {

    private String heading;
    private ArrayList<Task> tasks = new ArrayList<>();

    //Sonstoge Eigenschagter Der TodoListe...

    public TaskList() {

    }


    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * the method adds an element Task to the arrayList in Tasks
     *
     * @param task Element Task which should be added to the ArrayList
     */

    public void addTask(Task task) {

        MyLogger.LOGGER.entering(getClass().toString(), "addTask", new Object[]{task});
        tasks.add(task);
        MyLogger.LOGGER.exiting(getClass().toString(), "addTask");
    }


    /**
     * the method deletes an element Task from the arrayList in TaskList.
     *
     * @param task Object which should be deleted from ArrayList
     */

    public void deleteTask(Task task) {

        MyLogger.LOGGER.entering(getClass().toString(), "deleteTask", new Object[]{task});
        this.tasks.remove(task);

        MyLogger.LOGGER.exiting(getClass().toString(), "deleteTask");

    }

    /**
     * the method returns the length of the arraylist.
     */

    public int size() {

        MyLogger.LOGGER.entering(getClass().toString(), "size");
        int size = tasks.size();
        MyLogger.LOGGER.exiting(getClass().toString(), "size", size);
        return size;
    }


    /**
     * the method iterates over the individual elements of the array list and checks certain parameters is set to true.
     * If yes, the element is added to the returning list.
     *
     * @param parameter  0  TasktoDo / "1" = in progress / "2" = Done / "3" = can be deleted
     */

    public ArrayList<Task> getExamWithSpecalProperties(int parameter) {

        MyLogger.LOGGER.entering(getClass().toString(), "getExamWithSpecalProperties", new Object[]{parameter});
        ArrayList<Task> taskList = new ArrayList<>();
        for (Task e : tasks) {

            if(e.getProjectStatus() == parameter)
            {
                taskList.add(e);
            }
        }
        MyLogger.LOGGER.exiting(getClass().toString(), "getExamWithSpecalProperties", taskList);
        return taskList;
    }

    /**
     * the method returns a list of all elements in the Array
     *
     */

    public ArrayList<Task> getTasks() {

        MyLogger.LOGGER.entering(getClass().toString(), "getTasks");
        MyLogger.LOGGER.exiting(getClass().toString(), "getTasks", tasks);
        return this.tasks;
    }

    /**
     * the method sets a list of all elements in the Array
     *
     */

    public void setTasks(ArrayList<Task> arrayList) {

        MyLogger.LOGGER.entering(getClass().toString(), "setTasks", new Object[]{tasks});
        tasks = arrayList;
        MyLogger.LOGGER.exiting(getClass().toString(), "setTasks");

    }


}
