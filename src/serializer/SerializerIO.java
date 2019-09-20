package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import exam.ExamList;
import logging.MyLogger;
import timetable.Timetable;
import todolist.TaskListCollection;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Class for de-/ serializing the user data.
 * @author David Sugar
 */
public class SerializerIO {
    /**
     * Calls a custom serializer {@link TimetableSerializer#serialize(TimetableObjectCollection, JsonGenerator, SerializerProvider)}
     * method to store a {@code Timetable} objects data in a Json file at the specified location.
     * @param path Destination
     * @throws IOException Either because a problem occurred while parsing the specified object or because the file
     * couldn't be opened.
     */
    public static void storeJson(String path, Timetable timetable,
                                 TaskListCollection taskListCollection,
                                 ExamList examList) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        TimetableSerializer timetableSerializer = new TimetableSerializer(TimetableObjectCollection.class);

        SimpleModule module = new SimpleModule("TimetableSerializer",
                new Version(0, 1, 0, null, null, null));
        module.addSerializer(TimetableObjectCollection.class, timetableSerializer);

        objectMapper.registerModule(module);

        try (FileOutputStream fout = new FileOutputStream(path)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fout, new TimetableObjectCollection(timetable, examList, taskListCollection));
        } catch (JsonProcessingException exc) {
            MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        } catch (IOException e) {
            MyLogger.LOGGER.log(Level.WARNING, e.getMessage() + "\nSpecified Path: " + path);
            throw e;
        }

    }

    /**
     * Calls a custom deserializer {@link TimetableDeserializer#deserialize(JsonParser, DeserializationContext)}
     * method to retrieve data from a json file.
     * @param path Path to the Json file
     * @return Timetable object on success, null otherwise
     * @throws IOException if the specified file couldn't be found or because of an parsing error
     */
    public static TimetableObjectCollection loadJson(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TimetableObjectCollection timetable = null;

        SimpleModule module = new SimpleModule("TimetableDeserializer",
                new Version(0, 1, 0, null, null, null));
        module.addDeserializer(TimetableObjectCollection.class, new TimetableDeserializer(TimetableObjectCollection.class));

        objectMapper.registerModule(module);

        try(FileInputStream fin = new FileInputStream(path)) {
            timetable = objectMapper.readValue(fin, TimetableObjectCollection.class);
        } catch (JsonProcessingException exc) {
            MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        } catch (IOException exc) {
            MyLogger.LOGGER.log(Level.WARNING, exc.getMessage() + "\nSpecified Path: " + path);
            throw exc;
        }

        return timetable;
    }
}
