package io;

import timetable.Timetable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

/**
 * This Class is used to serialize {@link Timetable}, {@link exam.Exam} and {@link todolist.Task} objects each using it's
 * own thread. It can be passed to an {@link java.util.concurrent.ExecutorService} where the {@link #call()} method
 * is invoked.
 *
 * @author David Sugar
 * @param <T> Object-Type to serialize
 */
public class Store<T> implements Callable<Boolean> {

    private String path;
    private T obj;

    /**
     * Constructor
     * @param obj Object to store
     * @param path Place to store the object at
     * @throws IllegalArgumentException Throws an exception if one of the parameters is null.
     */
    public Store(T obj, String path) throws IllegalArgumentException {
        if( obj == null || path == null )
            throw new IllegalArgumentException("Arguments must not be null");

        this.path = path;
        this.obj = obj;
    }

    /**
     *  Takes an Object 'T' {@link #obj}, serializes it and stores it at the location
     *  specified in {@link #path}.
     *
     * @return TRUE on success, FALSE otherwise
     */
    @Override
    public Boolean call() throws Exception {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.path))) {
            oos.writeObject(this.obj);
        } catch (IOException exc) {
            System.err.println(exc.getMessage());
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

}
