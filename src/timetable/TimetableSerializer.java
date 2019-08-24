package timetable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Serializer for the {@link Timetable} class that facilitates the {@code Jackson}(JasonParser).
 * @author David Sugar
 */
public class TimetableSerializer extends StdSerializer<Timetable> {

    protected TimetableSerializer(Class<Timetable> t) {
        super(t);
    }

    /**
     * Custom serializer Method for the {@code Timetable} class that is used by the {@link Timetable#store(String)} method to
     * save a Timetable as a {@code Json} file.
     *
     * @param timetable Object to serialize
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    public void serialize(Timetable timetable, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException
    {
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
        jsonGenerator.writeEndObject();

    }
}
