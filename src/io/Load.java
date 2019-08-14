package io;

import timetable.Timetable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.*;

/**
 * This Class is used to load an serialized {@link Timetable}, {@link exam.Exam} or {@link todolist.Task} objects using it's
 * own thread. It can be passed to an {@link java.util.concurrent.ExecutorService} where the {@link #call()} method
 * is invoked.
 *
 * @author David Sugar
 * @param <T> Object-Type to load
 */
public class Load<T> implements Callable<T> {

    private String path;

    /**
     * Constructor
     * @param path File to get the data from
     */
    public Load(String path) {
        if(path == null)
            throw new IllegalArgumentException("Path must not be null");

        this.path = path;
    }

    /**
     * Loads and deserializes an object 'T' from a file specified in {@link #path}. Then return this object.
     * @return Object of type T
     * @throws Exception Throws an exception if either the file or the class couldn't be found.
     */
    public T call() throws Exception {
        T t = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.path))) {
            t = (T) ois.readObject();
        } catch (IOException | ClassNotFoundException exc) {
            System.err.println(exc.getMessage());
        }

        return t;
    }
}
