package todolist;

import java.util.ArrayList;

public class TaskListCollection {
    ArrayList<TaskList> taskLists = new ArrayList<>();

    public void add(TaskList taskList) {
        taskLists.add(taskList);
    }

    public boolean remove(TaskList taskList) {
        return taskLists.remove(taskList);
    }

    public ArrayList<TaskList> getTaskLists() {
        return taskLists;
    }
}
