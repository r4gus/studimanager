package timetable;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author David Sugar
 */
public class TimetableSerializer extends StdSerializer<Timetable> {

    protected TimetableSerializer(Class<Timetable> t) {
        super(t);
    }

    public void serialize(Timetable timetable, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("semester", timetable.getSemester());
        jsonGenerator.writeNumberField("days", timetable.getDays());
        jsonGenerator.writeNumberField("unitsPerDay", timetable.getUnitsPerDay());
        jsonGenerator.writeObjectField("units", timetable.getUnit());
        jsonGenerator.writeEndObject();

    }
}
