package test;


import org.junit.jupiter.api.Test;
import model.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Queue interface.
 */
public class QueueTest {
    private Queue<Integer> queue;
    private final int NUM_ELEMENTS =100;

    public void setUpEmptyQueue(){ // Cr
        queue=new DoublyLinkedList<>();
    }

    public void setUpQueueWithElements(){
        setUpEmptyQueue();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            queue.enqueue(i);
        }
    }




    @Test

    public void testDequeue(){
        setUpQueueWithElements();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            assertEquals(i,queue.dequeue());
        }
    }

    @Test
    public void testFront(){
        setUpEmptyQueue();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            queue.enqueue(i);
            assertEquals(queue.front(),0);
        }

    }



    @Test

    public void testSize(){
        setUpQueueWithElements();
        int size= NUM_ELEMENTS;
        while(size>=0){
            assertEquals(size,queue.size());
            queue.dequeue();
            size--;
        }
        queue.dequeue();
        assertEquals(0,queue.size());

    }
    @Test
    public void testEmpty(){
        setUpQueueWithElements();
        int size=NUM_ELEMENTS;
        while(size>=0){
            if(size!=0){
                assertFalse(queue.empty());
            }else{
                assertTrue(queue.empty());
            }
            queue.dequeue();
            size--;

        }
    }

}
