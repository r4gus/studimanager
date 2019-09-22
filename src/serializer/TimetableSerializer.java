package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import exam.ExamList;
import timetable.Timetable;
import todolist.TaskListCollection;

import java.io.IOException;

/**
 * Serializer for the {@link Timetable} class that facilitates the {@code Jackson}(JasonParser).
 * @author David Sugar
 */
public class TimetableSerializer extends StdSerializer<TimetableObjectCollection> {

    protected TimetableSerializer(Class<TimetableObjectCollection> t) {
        super(t);
    }

    /**
     * Custom serializer Method for the {@code Timetable} class that is used by the {@link Timetable#store(String)} method to
     * save a Timetable as a {@code Json} file.
     *
     * @param timetableObjectCollection Object to serialize
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    public void serialize(TimetableObjectCollection timetableObjectCollection, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException
    {
        Timetable timetable = timetableObjectCollection.getTimetable();
        TaskListCollection taskListCollection = timetableObjectCollection.getTaskListCollection();
        ExamList examList = timetableObjectCollection.getExamList();

        /* ##################### SERIALIZE TIMETABLE OBJECT ########################################### */
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("semester", timetable.getSemester());
        jsonGenerator.writeNumberField("days", timetable.getDays());
        jsonGenerator.writeNumberField("unitsPerDay", timetable.getUnitsPerDay());
        jsonGenerator.writeObjectField("begin", timetable.getDEFAULT_BEGIN());
        jsonGenerator.writeObjectField("lunch", timetable.getDEFAULT_LUNCH_AT());
        jsonGenerator.writeNumberField("durationInMin", timetable.getDEFAULT_DURATION_M());
        jsonGenerator.writeNumberField("breakTime", timetable.getDEFAULT_BREAK_TIME());
        jsonGenerator.writeNumberField("lunchTime", timetable.getDEFAULT_LUNCH_TIME());
        jsonGenerator.writeObjectField("units", timetable.getUnit());


        /* ##################### SERIALIZE Exams OBJECT ########################################### */
        jsonGenerator.writeObjectField("exams", examList.getExams());

        /* ##################### SERIALIZE CanBanBoard ############################################ */
        jsonGenerator.writeObjectField("toDoList", taskListCollection.getTaskLists());
        jsonGenerator.writeEndObject();

    }
}
