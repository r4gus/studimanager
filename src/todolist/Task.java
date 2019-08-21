package todolist;

/**
 * The <code>Task</code> object the Task object represents a task of a user.
 * This task can be assigned to different responsible lists e.g. "in progress".
 *
 * @author Lukas Mendel
 */

import logging.MyLogger;

import java.io.File;
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
    private String notes;
    private String priority;
    private ArrayList<TaskCheckListItem> itemsChecklist = new ArrayList<>();
    private ArrayList<File> fileArrayList = new ArrayList<>();
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

    /**
     * constructor
     */

    public Task(String projectTitle, String projectDescription, String priority , String notes, ArrayList<TaskCheckListItem> itemsChecklist , ArrayList<File> files , int projectStatus, LocalDate projectStart, LocalTime projectDuration, LocalDate deadline, boolean remindMe, LocalDateTime remindTime) {

        MyLogger.LOGGER.entering(getClass().toString(), "Task", new Object[]{projectTitle, projectDescription, notes, itemsChecklist , projectStatus, projectStart, projectDuration, deadline, remindMe, remindTime});
        this.projectTitle = projectTitle;
        this.taskId = currentTaskId;
        this.projectDescription = projectDescription;
        this.priority = priority;
        this.notes = notes;
        this.itemsChecklist = itemsChecklist;
        this.fileArrayList = files;
        this.projectStatus = projectStatus;
        this.isDone = false;
        this.projectStart = projectStart;
        this.projectDuration = projectDuration;
        this.deadline = deadline;
        this.remindMe = remindMe;
        this.remindTime = remindTime;
        this.currentTaskId++;
        MyLogger.LOGGER.exiting(getClass().toString(), "Task");
    }


    /**
     * getter and setter of the different parameters
     */

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

    public String getPriority() { return priority; }

    public void setPriority(String priority) { this.priority = priority; }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<TaskCheckListItem> getItemsChecklist() {
        return itemsChecklist;
    }

    public void setItemsChecklist(ArrayList<TaskCheckListItem> itemsChecklist) {
        this.itemsChecklist = itemsChecklist;
    }

    public ArrayList<File> getFileArrayList() { return fileArrayList; }

    public void setFileArrayList(ArrayList<File> fileArrayList) { this.fileArrayList = fileArrayList; }

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
