package timetable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * Deserializer for the {@link Timetable} class that facilitates the {@code Jackson}(JasonParser).
 * @author David Sugar
 */
public class TimetableDeserializer extends StdDeserializer<Timetable> {

    public TimetableDeserializer(Class<Timetable> vc) {
        super(vc);
    }

    /**
     * Custom deserializer Method for the {@code Timetable} class that is used by the {@link Timetable#load(String)} method to
     * retrieve data from a {@code Jason} file to build a {@code Timetable} object.
     *
     * @param jsonParser
     * @param deserializationContext
     * @return Timetable object on success, null otherwise.
     * @throws IOException
     * @throws JsonProcessingException
     */
    public Timetable deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException
    {
        Timetable timetable = null;

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        /*
        ---------------- CREATE TIMETABLE OBJECT -----------------------
         */
        final int semester      = node.get("semester").asInt();
        final int days          = node.get("days").asInt();
        final int unitsPerDays  = node.get("unitsPerDay").asInt();

        JsonNode begin = node.get("begin");
        final int begin_h = begin.get("hour").asInt();
        final int begin_m = begin.get("minute").asInt();

        JsonNode lunch = node.get("lunch");
        final int lunch_h = lunch.get("hour").asInt();
        final int lunch_m = lunch.get("minute").asInt();

        final long durationInMin = node.get("durationInMin").asLong();
        final long breakTime = node.get("breakTime").asLong();
        final long lunchTime = node.get("lunchTime").asLong();

        timetable = new Timetable(  unitsPerDays,
                                    semester,
                                    begin_h,
                                    begin_m,
                                    durationInMin,
                                    breakTime,
                                    lunchTime,
                                    lunch_h,
                                    lunch_m,
                                    days);

        /*
        ---------------- ADD LECTURES ----------------------------------
         */

        JsonNode units          = node.get("units");

        JsonNode next;
        int i = 0; // index
        int j = 0; // offset

        for(final JsonNode row: units) {                        // iterate over rows
            for(final JsonNode unit: row) {                     // iterate over each unit of a row

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

                }

                j += 1;
            }
            j = 0;
            i += 1;
        }

        return timetable;
    }
}
