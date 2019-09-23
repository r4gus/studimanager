package todolist.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todolist.Task;
import todolist.TaskList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListTest {

    private TaskList taskList = new TaskList();
    private TaskList taskListtEmpty = new TaskList();
    private Task f1, f2, f3, f4 ,f5 , f6, f7, f8;

    @BeforeEach
    void setUp() {

        taskList = new TaskList();

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();

        f1 = new Task("Analysis");
        f2 = new Task("lineare Algebra");
        f3 = new Task("lineare Algebra");
   /*     f4 = new Task("Wahrscheinlichkeitstheorie", "WK berechnen", 0, false , localDate, localTime, localDate, true, localDateTime );
        f5 = new Task("Wahrscheinlichkeitstheorie", "WK berechnen_2", 1, false , localDate, localTime, localDate, true, localDateTime );
        f6 = new Task("Analysis", "Integrale", 1, false , localDate, localTime, localDate, true, localDateTime );
        f7 = new Task("Analysis", "Integrale berechnen", 2, false , localDate, localTime, localDate, true, localDateTime );
        f8 = new Task("lineare Algebra", "MAtrix berechnen", 3, false , localDate, localTime, localDate, true, localDateTime );
    */

        try {
            taskList.addTask(f1);
            taskList.addTask(f2);
            taskList.addTask(f3);
            taskList.addTask(f4);
            taskList.addTask(f5);
            taskList.addTask(f6);
            taskList.addTask(f7);


        } catch (Exception e) {

        }

    }

    @Test
    void deleteObjektFromArrayList() {

        assertEquals(7, taskList.size());
        //taskList.deleteTask(1);
        assertEquals(6, taskList.size());
    }

    @Test
    void addObjektToArrayList() {

        assertEquals(7, taskList.size());
        try {
            taskList.addTask(f8);
        } catch (Exception e) {

        }
        assertEquals(8, taskList.size());
    }

    @Test
    void addExistingObjektFromArrayList() {

        assertEquals(7, taskList.size());
        try {
            taskList.addTask(f7);
        }catch (Exception e)
        {
            assertTrue(true);
        }
    }


    @Test
    void deleteObjektFromEmtyArrayList() {

        assertEquals(0, taskListtEmpty.size());
        try {
           // taskList.deleteTask(0);
        }catch (IllegalArgumentException e)
        {
            assertTrue(true);
        }
    }

    @Test
    void getToDoTasks() {

        ArrayList<Task> taskArrayList;
        taskArrayList = taskList.getExamWithSpecalProperties(0);
        assertEquals(4, taskArrayList.size());
    }

    @Test
    void getInProgressTasks() {

        ArrayList<Task> taskArrayList = new ArrayList<>();
        taskArrayList = taskList.getExamWithSpecalProperties(1);
        assertEquals(2, taskArrayList.size());
    }

    @Test
    void getDoneTasks() {

        ArrayList<Task> taskArrayList = new ArrayList<>();
        taskArrayList = taskList.getExamWithSpecalProperties(2);
        assertEquals(1, taskArrayList.size());
    }

    @Test
    void getDeletebaleTasks() {

        ArrayList<Task> taskArrayList = new ArrayList<>();
        taskArrayList = taskList.getExamWithSpecalProperties(3);
        assertEquals(0, taskArrayList.size());
    }

}