package todolist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import logging.MyLogger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

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
