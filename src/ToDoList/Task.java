package ToDoList;

/**
 * The <code>Task</code> object the Task object represents a task of a user.
 * This task can be assigned to different responsible persons e.g. "in progress".
 *
 * @author Lukas Mendel
 */

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Task {

    private static int currentTaskId = 0;
    private String projectTitle;
    private int taskId;
    private String projectDescription;
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
        this.currentTaskId++;

    }

    public Task(String projectTitle, String projectDescription, int projectStatus, boolean isDone, LocalDate projectStart, LocalTime projectDuration, LocalDate deadline, boolean remindMe, LocalDateTime remindTime) {
        this.projectTitle = projectTitle;
        this.taskId = currentTaskId;
        this.projectDescription = projectDescription;
        this.projectStatus = projectStatus;
        this.isDone = isDone;
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

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
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

    public int getTaskId() {
        return taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskId == task.taskId &&
                isDone == task.isDone &&
                remindMe == task.remindMe &&
                Objects.equals(projectTitle, task.projectTitle) &&
                Objects.equals(projectDescription, task.projectDescription) &&
                Objects.equals(projectStatus, task.projectStatus) &&
                Objects.equals(projectStart, task.projectStart) &&
                Objects.equals(projectDuration, task.projectDuration) &&
                Objects.equals(deadline, task.deadline) &&
                Objects.equals(remindTime, task.remindTime);
    }
}
