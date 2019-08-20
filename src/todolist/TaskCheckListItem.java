package todolist;

import java.io.Serializable;
import java.util.Objects;


/**
 * The <code>TaskCheckListItem</code> object represents an entry in the ToDoList of a task.
 * This object can have 2 states. Task completed and task unfinished.
 *
 * @author Lukas Mendel
 */

public class TaskCheckListItem implements Serializable {

    private String checklistTaskName;
    private int checkListTaskID;
    private boolean checklistTaskCompleted;

    private static int checkListTaskIDCounter = 0;


    /**
     * constructor
     */

    public TaskCheckListItem(String checklistTaskName) {
        this.checklistTaskName = checklistTaskName;
        this.checklistTaskCompleted = false;
        this.checkListTaskID = checkListTaskIDCounter;
        checkListTaskIDCounter++;
    }

    /**
     * getter and setter of the different parameters
     */

    public String getChecklistTaskName() {
        return checklistTaskName;
    }

    public void setChecklistTaskName(String checklistTaskName) {
        this.checklistTaskName = checklistTaskName;
    }

    public int getCheckListTaskID() {
        return checkListTaskID;
    }

    public void setCheckListTaskID(int checkListTaskID) {
        this.checkListTaskID = checkListTaskID;
    }

    public boolean isChecklistTaskCompleted() {
        return checklistTaskCompleted;
    }

    public void setChecklistTaskCompleted(boolean checklistTaskCompleted) {
        this.checklistTaskCompleted = checklistTaskCompleted;
    }

    @Override
    public String toString() {
        return "TaskCheckListItem{" +
                "checklistTaskName='" + checklistTaskName + '\'' +
                ", checkListTaskID=" + checkListTaskID +
                ", checklistTaskCompleted=" + checklistTaskCompleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskCheckListItem that = (TaskCheckListItem) o;
        return checkListTaskID == that.checkListTaskID &&
                checklistTaskCompleted == that.checklistTaskCompleted &&
                Objects.equals(checklistTaskName, that.checklistTaskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checklistTaskName, checkListTaskID, checklistTaskCompleted);
    }
}
