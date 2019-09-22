package serializer;

import exam.ExamList;
import timetable.Timetable;
import todolist.TaskListCollection;

/**
 * This object wraps all for the application necessary objects into one for de-/ serialization.
 */
public class TimetableObjectCollection {
    private Timetable timetable;
    private TaskListCollection taskListCollection;
    private ExamList examList;

    public TimetableObjectCollection(Timetable timetable, ExamList examList, TaskListCollection taskListCollection) {
        this.timetable = timetable;
        this.taskListCollection = taskListCollection;
        this.examList = examList;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public TaskListCollection getTaskListCollection() {
        return taskListCollection;
    }

    public void setTaskListCollection(TaskListCollection taskListCollection) {
        this.taskListCollection = taskListCollection;
    }

    public ExamList getExamList() {
        return examList;
    }

    public void setExamList(ExamList examList) {
        this.examList = examList;
    }
}
