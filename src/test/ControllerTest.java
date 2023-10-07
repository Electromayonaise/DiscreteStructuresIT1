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
        for (int i = 0; i < NUM; i++) {
            assertTrue(controller.removeTask("prioritizedTask"+i));
            assertFalse(controller.removeTask("prioritizedTask"+i));
            assertFalse(controller.removeTask("prioritizedTask"+i));

        }

    }



}
