package serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import exam.Exam;
import exam.ExamList;
import timetable.*;
import todolist.Task;
import todolist.TaskCheckListItem;
import todolist.TaskList;
import todolist.TaskListCollection;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Deserializer for the {@link Timetable} class that facilitates the {@code Jackson}(JasonParser).
 * @author David Sugar
 */
public class TimetableDeserializer extends StdDeserializer<TimetableObjectCollection> {

    public TimetableDeserializer(Class<TimetableObjectCollection> vc) {
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
    public TimetableObjectCollection deserialize(JsonParser jsonParser,
                                 DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException
    {
        Timetable timetable = null;
        TaskListCollection taskListCollection = null;
        ExamList examList = null;

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
                    Facility facility    = null;
                    Lecturer lecturer    = null;
                    boolean     elective;
                    Lecture newLecture = null;

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

                    /* to serialize notes: uncomment this block and remove @JsonIgnore annotation in Lecture
                    ################### NOTES ##########################
                    JsonNode lectureNotes      = lecture.get("notes").get("container");
                    for(JsonNode n: lectureNotes) {     // iterate over all existing notes
                        newLecture.addNote(new Note(n.get("title").asText(),
                                                    n.get("body").asText(),
                                                    null,
                                                    n.get("important").asBoolean()));
                    }
                     */

                    /*
                    ######################### ADD LECTURE TO TIMETABLE ###############
                     */
                    timetable.addLecture(i, j, newLecture);

                }

                final JsonNode head = unit.get("head");
                if(!head.isNull()) {
/*
                    ------------------ ADD HEAD LECTURE ----------------------------------------
                     */
                    String      title       = null;
                    Facility facility    = null;
                    Lecturer lecturer    = null;
                    boolean     elective;
                    Lecture newLecture = null;

                    title       = head.get("title").asText();
                    elective    = head.get("elective").asBoolean();

                    /*
                    ############## FACILITY ##############
                     */
                    JsonNode lectureFacility   = head.get("facility");
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
                    JsonNode lectureLecturer   = head.get("lecturer");
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
                    timetable.getUnit()[i][j].setHead(newLecture);

                }

                j += 1;
            }
            j = 0;
            i += 1;
        }

        /*
        ------------------------------- DESERIALIZE EXAMS --------------------------------------
         */
        examList = new ExamList();

        for(JsonNode exam: node.get("exams")) {
            examList.addExam(new Exam(exam.get("subjectNumber").asText(),
                    exam.get("technicalName").asText(),
                    exam.get("semester").asText(),
                    exam.get("date").asText(),
                    exam.get("begin").asText(),
                    exam.get("duration").asText(),
                    exam.get("building").asText(),
                    exam.get("roomNumber").asText(),
                    exam.get("trialNumber").asText(),
                    exam.get("mark").asText(),
                    exam.get("modulMark").asText(),
                    exam.get("insisted").asBoolean(),
                    exam.get("currentExam").asBoolean()
                    ));
        }

        /*
        ----------------------------- DESERIALIZE Can-Ban Board ---------------------------
         */
        taskListCollection = new TaskListCollection();

        for(JsonNode section: node.get("toDoList")) {
            TaskList taskList = new TaskList();
            taskList.setHeading(section.get("heading").asText());

            for(JsonNode task: section.get("tasks")) { // iterate over each task of a task list

                /* ##################################################################
                                    GET ALL REQUIRED ELEMENTS FOR EACH TASK
                 ######################################################################*/
                String projectTitle = task.get("projectTitle").asText();
                int taskId = task.get("taskId").asInt();
                int taskListId = task.get("taskListId").asInt();

                String projectDescription = task.get("projectDescription").asText();
                if(projectDescription.equals("null")) projectDescription = null;

                String notes = task.get("notes").asText();
                if(notes.equals("null")) notes = null;

                String priority = task.get("priority").asText();

                /* ###################### ITEMS CHECK LIST ############# */
                ArrayList<TaskCheckListItem> itemsChecklist = new ArrayList<>();
                for(JsonNode item: task.get("itemsChecklist")) {
                    itemsChecklist.add(new TaskCheckListItem(
                            item.get("checklistTaskName").asText(),
                            item.get("checkListTaskID").asInt(),
                            item.get("checklistTaskCompleted").asBoolean()
                    ));
                }

                /* ###################### FILES ########################### */
                ArrayList<File> fileArrayList = new ArrayList<>();
                for(JsonNode file: task.get("fileArrayList")) {
                    fileArrayList.add(new File(file.asText()));
                }

                int projectStatus = task.get("projectStatus").asInt();

                /* ###################### DATES ########################## */
                JsonNode time;

                LocalDate projectStart = null;
                time = task.get("projectStart");
                if(!time.isNull()) {
                    projectStart = LocalDate.of(
                            time.get("year").asInt(),
                            time.get("monthValue").asInt(),
                            time.get("dayOfMonth").asInt()
                    );
                }

                LocalTime projectDuration = null;

                LocalDate deadline = null;
                time = task.get("deadline");
                if(!time.isNull()) {
                    deadline = LocalDate.of(
                            time.get("year").asInt(),
                            time.get("monthValue").asInt(),
                            time.get("dayOfMonth").asInt()
                    );
                }


                LocalDateTime remindTime = null;

                boolean remindMe = task.get("remindMe").asBoolean();

                boolean done = task.get("done").asBoolean();

                /* #######################################################
                                CREATE TASK OBJECT
                 ########################################################### */
                Task x = new Task(projectTitle,
                        projectDescription,
                        priority,
                        notes,
                        itemsChecklist,
                        fileArrayList,
                        projectStatus,
                        projectStart,
                        projectDuration,
                        deadline,
                        remindMe,
                        remindTime);

                x.setTaskListId(taskListId);
                x.setTaskId(taskId);
                x.setDone(done);

                taskList.addTask(x);
            }

            taskListCollection.add(taskList);
        }

        return new TimetableObjectCollection(timetable, examList, taskListCollection);
    }
}
