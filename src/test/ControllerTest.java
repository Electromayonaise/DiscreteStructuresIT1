import org.junit.jupiter.api.Test;
import model.Controller;

import model.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller controller;

    private static final int NUM=50;

    void setUp1EmptyController(){
        controller=Controller.getInstance();

    }
    void setUp2ControllerWithTasks(){
        setUp1EmptyController();
        for (int i = 0; i < NUM; i++) {
            assertTrue(controller.addTask("nonPrioritizedTask"+i,"description"+i,0));
            assertFalse(controller.addTask("nonPrioritizedTask"+i,"description"+i,0));
            assertFalse(controller.addTask("nonPrioritizedTask"+i,"description"+i,0));
        }
        for (int i = 0; i < NUM; i++) {
            assertTrue(controller.addTask("prioritizedTask"+i,"description"+i,i));
            assertFalse(controller.addTask("prioritizedTask"+i,"description"+i,i));
            assertFalse(controller.addTask("prioritizedTask"+i,"description"+i,i));
        }
    }
    @Test
    void addTasks(){
        setUp1EmptyController();

        for (int i = 0; i < NUM; i++) {
            assertTrue(controller.addTask("nonPrioritizedTask"+i,"description"+i,0));
            assertFalse(controller.addTask("nonPrioritizedTask"+i,"description"+i,0));
            assertFalse(controller.addTask("nonPrioritizedTask"+i,"description"+i,0));
        }
        for (int i = 0; i < NUM; i++) {
            assertTrue(controller.addTask("prioritizedTask"+i,"description"+i,i));
            assertFalse(controller.addTask("prioritizedTask"+i,"description"+i,i));
            assertFalse(controller.addTask("prioritizedTask"+i,"description"+i,i));
        }
        System.out.println("A");
        ArrayList<ArrayList<String>> prioritizedTasks=controller.getPrioritizedTasksAttributes();
        ArrayList<ArrayList<String>> nonPrioritizedTasks= controller.getNonPrioritizedTasksAttributes();

        System.out.println(prioritizedTasks+"");

        System.out.println(nonPrioritizedTasks);

    }
    @Test
    void removeTasks(){
        setUp2ControllerWithTasks();
        for (int i = 0; i < NUM; i++) {
            assertTrue(controller.removeTask("nonPrioritizedTask"+i));
            assertFalse(controller.removeTask("nonPrioritizedTask"+i));
            assertFalse(controller.removeTask("nonPrioritizedTask"+i));

        }
        ArrayList<ArrayList<String>> prioritizedTasks=controller.getPrioritizedTasksAttributes();
        ArrayList<ArrayList<String>> nonPrioritizedTasks= controller.getNonPrioritizedTasksAttributes();

        System.out.println(prioritizedTasks+"");
        System.out.println(nonPrioritizedTasks);
        for (int i = 0; i < NUM; i++) {
            assertTrue(controller.removeTask("prioritizedTask"+i));
            assertFalse(controller.removeTask("prioritizedTask"+i));
            assertFalse(controller.removeTask("prioritizedTask"+i));

        }

    }
    @Test

    void modifyTaskDescription(){
        setUp2ControllerWithTasks();
        assertTrue(controller.modifyTask("prioritizedTask1",2,"asasa"));
        assertTrue(controller.modifyTask("nonPrioritizedTask0",2,"asasa"));
        assertTrue(controller.modifyTask("nonPrioritizedTask0",2,"esassa"));
        printTasks();
    }

    @Test
    void modifyPriorityLevelOfTask(){
        setUp2ControllerWithTasks();
        //change status->decrease priority-> increase priority
        assertTrue(controller.modifyTask("nonPrioritizedTask0",3,"1000"));
        printTasks();
        assertTrue(controller.modifyTask("nonPrioritizedTask0",3,"0"));
        printTasks();
        assertTrue(controller.modifyTask("nonPrioritizedTask0",3,"10000"));
        printTasks();
        System.out.println("     ");
        assertTrue(controller.modifyTask("nonPrioritizedTask0",3,"1"));
        printTasks();
        System.out.println("     ");
        assertTrue(controller.modifyTask("prioritizedTask49",3,"1"));
        System.out.println("     ");
        printTasks();
    }
    @Test

    void modifyTaskTitle(){
        setUp2ControllerWithTasks();
        //It must be possible to modify the task's title of an added Task
        assertTrue(controller.modifyTask("nonPrioritizedTask0",1,"a"));
        //Now there is no a task with title nonPrioritizedTask0. So modifyTask should be false
        assertFalse(controller.modifyTask("nonPrioritizedTask0",1,"a"));
        //It is not possible to use a title that other task has.
        assertFalse(controller.modifyTask("nonPrioritizedTask3", 1, "a"));

        assertFalse(controller.modifyTask("notAddedTask",1,"aasas"));
        assertTrue(controller.modifyTask("prioritizedTask30",1,"test"));
        printTasks();

    }
    void printTasks(){
        ArrayList<ArrayList<String>> prioritizedTasks=controller.getPrioritizedTasksAttributes();
        ArrayList<ArrayList<String>> nonPrioritizedTasks= controller.getNonPrioritizedTasksAttributes();

        System.out.println(prioritizedTasks+"");
        System.out.println(nonPrioritizedTasks);
    }


}
