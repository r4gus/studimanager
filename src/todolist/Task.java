package todolist;

/**
 * The <code>Task</code> object the Task object represents a task of a user.
 * This task can be assigned to different responsible lists e.g. "in progress".
 *
 * @author Lukas Mendel
 */

import guiTodolist.Task.VBoxTask;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;


public class Task implements Serializable {

    private static int currentTaskId = 0;

    private String projectTitle;
    private int taskId;
    private int taskListId;
    private String projectDescription;
    private ArrayList<String> notes = new ArrayList<>();
    private ArrayList<String> itemsChecklist = new ArrayList<>();
    private int projectStatus;
    private boolean isDone;
    private LocalDate projectStart;
    private LocalTime projectDuration;
    private LocalDate deadline;
    private boolean remindMe;
    private LocalDateTime remindTime;

    public Task(String title) {
        this.projectTitle = title;
        this.taskId = currentTaskId;
        this.isDone =false;
        this.currentTaskId++;

    }

    public Task(String projectTitle, String projectDescription, ArrayList<String> notes, ArrayList<String> itemsChecklist , int projectStatus, LocalDate projectStart, LocalTime projectDuration, LocalDate deadline, boolean remindMe, LocalDateTime remindTime) {
        this.projectTitle = projectTitle;
        this.taskId = currentTaskId;
        this.projectDescription = projectDescription;
        this.notes = notes;
        this.itemsChecklist = itemsChecklist;
        this.projectStatus = projectStatus;
        this.isDone = false;
        this.projectStart = projectStart;
        this.projectDuration = projectDuration;
        this.deadline = deadline;
        this.remindMe = remindMe;
        this.remindTime = remindTime;
        this.currentTaskId++;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskListId() {
        return taskListId;
    }

    public void setTaskListId(int taskListId) {
        this.taskListId = taskListId;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public ArrayList<String> getItemsChecklist() {
        return itemsChecklist;
    }

    public void setItemsChecklist(ArrayList<String> itemsChecklist) {
        this.itemsChecklist = itemsChecklist;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDate getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(LocalDate projectStart) {
        this.projectStart = projectStart;
    }

    public LocalTime getProjectDuration() {
        return projectDuration;
    }

    public void setProjectDuration(LocalTime projectDuration) {
        this.projectDuration = projectDuration;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isRemindMe() {
        return remindMe;
    }

    public void setRemindMe(boolean remindMe) {
        this.remindMe = remindMe;
    }

    public LocalDateTime getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(LocalDateTime remindTime) {
        this.remindTime = remindTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "projectTitle='" + projectTitle + '\'' +
                ", taskId=" + taskId +
                ", projectDescription='" + projectDescription + '\'' +
                ", notes=" + notes +
                ", itemsChecklist=" + itemsChecklist +
                ", projectStatus=" + projectStatus +
                ", isDone=" + isDone +
                ", projectStart=" + projectStart +
                ", projectDuration=" + projectDuration +
                ", deadline=" + deadline +
                ", remindMe=" + remindMe +
                ", remindTime=" + remindTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId &&
                projectStatus == task.projectStatus &&
                isDone == task.isDone &&
                remindMe == task.remindMe &&
                Objects.equals(projectTitle, task.projectTitle) &&
                Objects.equals(projectDescription, task.projectDescription) &&
                Objects.equals(notes, task.notes) &&
                Objects.equals(itemsChecklist, task.itemsChecklist) &&
                Objects.equals(projectStart, task.projectStart) &&
                Objects.equals(projectDuration, task.projectDuration) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(remindTime, task.remindTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectTitle, taskId, projectDescription, notes, itemsChecklist, projectStatus, isDone, projectStart, projectDuration, deadline, remindMe, remindTime);
    }
}
