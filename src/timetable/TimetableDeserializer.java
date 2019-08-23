package timetable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TimetableDeserializer extends StdDeserializer<Timetable> {

    public TimetableDeserializer(Class<Timetable> vc) {
        super(vc);
    }

    @Override
    public Timetable deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException
    {
        Timetable timetable = null;

        while (!jsonParser.isClosed()) {
            JsonToken jsonToken = jsonParser.nextToken();

            if (JsonToken.FIELD_NAME.equals(jsonToken)) {
                String fieldName = jsonParser.getCurrentName();
                System.out.println(fieldName);

                jsonToken = jsonParser.nextToken();

                if ("semester".equals(fieldName)) {
                    System.out.println(" - " + jsonParser.getValueAsInt());
                } else if ("days".equals(fieldName)) {
                    System.out.println(" - " + jsonParser.getValueAsInt());
                } else if ("unitsPerDay".equals(fieldName)) {
                    System.out.println(" - " + jsonParser.getValueAsInt());
                } else if ("units".equals(fieldName)) {
                    System.out.println(" fuck fuck fuck ");
                }
            }
        }

        return timetable;
    }
}
