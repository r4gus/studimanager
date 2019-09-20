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

    /**
     * Store the config data in a {@code Json} file at the specified {@code path}.
     * @param path Path to store the Json file at.
     * @throws IOException if the file can't be processed or the give file doesn't exist.
     */
    public void storeJson(String path) throws IOException {
        MyLogger.LOGGER.entering(getClass().toString(), "storeJson", path);

        ObjectMapper objectMapper = new ObjectMapper();

        try (FileOutputStream fout = new FileOutputStream(path)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fout, this);
        } catch (JsonProcessingException exc) {
            MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        } catch (IOException exc) {
            MyLogger.LOGGER.log(Level.WARNING, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "storeJson");
    }
}
