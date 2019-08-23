package timetable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;

/**
 * @author David Sugar
 */
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

        /*
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
        */

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        /*
        ---------------- CREATE TIMETABLE OBJECT -----------------------
         */
        final int semester      = node.get("semester").asInt();
        final int days          = node.get("days").asInt();
        final int unitsPerDays  = node.get("unitsPerDay").asInt();

        timetable = new Timetable(unitsPerDays, semester, days);

        /*
        ---------------- ADD LECTURES ----------------------------------
         */

        JsonNode units          = node.get("units");

        JsonNode next;
        int i = 0; // index
        int j = 0; // offset

        for(final JsonNode row: units) {                        // iterate over rows
            for(final JsonNode unit: row) {                     // iterate over each unit of a row
                System.out.println("[" + i + "][" + j + "]:");

                final JsonNode lectures  = unit.get("container");   // get the lectures of each unit

                for(final JsonNode lecture: lectures) {             // iterate over each lecture
                    /*
                    ------------------ ADD LECTURE ----------------------------------------
                     */
                    String      title       = null;
                    Facility    facility    = null;
                    Lecturer    lecturer    = null;
                    boolean     elective;
                    Lecture     newLecture = null;

                    title       = lecture.get("title").asText();
                    elective    = lecture.get("elective").asBoolean();

                    /*
                    ############## FACILITY ##############
                     */
                    JsonNode lectureFacility   = lecture.get("facility");
                    if(!lectureFacility.isNull()) {
                        facility = timetable.newFacility(lectureFacility.get("building").asText(),
                                                lectureFacility.get("room").asText(),
                                                lectureFacility.get("street").asText(),
                                                lectureFacility.get("zipcode").asText(),
                                                lectureFacility.get("city").asText());
                    }

                    /*
                    ############### LECTURER #############
                     */
                    JsonNode lectureLecturer   = lecture.get("lecturer");
                    if(!lectureLecturer.isNull()) {
                        JsonNode lecturerFacility = lectureLecturer.get("facility");
                        Facility f = null;

                        if(!lecturerFacility.isNull()) {
                            f = timetable.newFacility(lecturerFacility.get("building").asText(),
                                    lecturerFacility.get("room").asText(),
                                    lecturerFacility.get("street").asText(),
                                    lecturerFacility.get("zipcode").asText(),
                                    lecturerFacility.get("city").asText());
                        }

                        lecturer = timetable.newLecturer(lectureLecturer.get("firstName").asText(),
                                                lectureLecturer.get("lastName").asText(),
                                                lectureLecturer.get("email").asText(),
                                                f);
                    }

                    /*
                    ################## LECTURE ########################
                     */
                    newLecture = timetable.newLecture(title, facility, lecturer, elective, null);

                    /*
                    ################### NOTES ##########################
                     */
                    JsonNode lectureNotes      = lecture.get("notes").get("container");
                    for(JsonNode n: lectureNotes) {     // iterate over all existing notes
                        newLecture.addNote(new Note(n.get("title").asText(),
                                                    n.get("body").asText(),
                                                    null,
                                                    n.get("important").asBoolean()));
                    }

                    /*
                    ######################### ADD LECTURE TO TIMETABLE ###############
                     */
                    timetable.addLecture(i, j, newLecture);

                    /*
                    System.out.println("    " + newLecture + ":" +
                            "\n         facility: " + facility + "\n        lecturer:" + lecturer +
                            "\n         elective: " + elective +
                            "\n         notes:" + newLecture.getNotes());
                     */
                }

                j += 1;
            }
            j = 0;
            i += 1;
        }

        return timetable;
    }
}
