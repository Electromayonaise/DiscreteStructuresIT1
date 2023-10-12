package test;

import model.MaxHeap;
import model.Task;
import org.junit.jupiter.api.Test;

import model.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the MaxHeap class.
 */
public class MaxHeapTest {
    private MaxHeap<Task> heap;
    public final int NUM_ELEMENTS=100;

    public void setUpEmptyHeap(){
        heap=new MaxHeap<>();
    }
    public void setUpHeapWithElements(){
        ArrayList<Task> list= new ArrayList<>();
        for (int i = 49; i >=25 ; i--) {
            list.add(new Task("title"+i,"description"+i,i));
        }
        for(int i=0;i<25;i++){
            list.add(new Task("title"+i,"description"+i,i));
        }
        for (int i = NUM_ELEMENTS-1; i >=75 ; i--) {
            list.add(new Task("title"+i,"description"+i,i));
        }
        for (int i = 74; i >=50 ; i--) {
            list.add(new Task("title"+i,"description"+i,i));
        }




        heap=new MaxHeap<>(list);

    }



    @Test

    public void testIncreaseKey(){
        setUpHeapWithElements();
        heap.buildMaxHeap();
        assertTrue(heap.increaseKey(99,500));
        assertEquals(heap.max().getHeapKey(),500);

    }
    @Test
    public void testDecreaseKey(){
        setUpHeapWithElements();

        assertTrue(heap.decreaseKey(0,-1));
        while(heap.size()!=1){
            heap.extractMax();
        }
        assertEquals(heap.extractMax().getHeapKey(),-1);

    }
    @Test

    public void testHeapSort(){
        setUpHeapWithElements();
        for (int i = NUM_ELEMENTS-1; i >=0; i--) {
            System.out.println(heap.extractMax().getHeapKey());
            //assertEquals(heap.extractMax().getHeapKey(),i);
        }
    }
    @Test

    public void testInsert(){
        setUpEmptyHeap();
        for (int i = 49; i >=25 ; i--) {
            heap.insert(new Task("title"+i,"description"+i,i));
        }
        for(int i=0;i<25;i++){
            heap.insert(new Task("title"+i,"description"+i,i));
        }
        for (int i = NUM_ELEMENTS-1; i >=75 ; i--) {
            heap.insert(new Task("title"+i,"description"+i,i));
        }
        for (int i = 74; i >=50 ; i--) {
            heap.insert(new Task("title"+i,"description"+i,i));
        }
        for (int i = NUM_ELEMENTS-1; i >=0; i--) {
            assertEquals(heap.extractMax().getHeapKey(),i);
        }
    }
    @Test
    public void modifyKey(){
        setUpHeapWithElements();
        assertTrue(heap.modifyKey(99,500));
        assertEquals(heap.max().getHeapKey(),500);
        assertTrue(heap.modifyKey(0,-1));
        while(heap.size()!=1){
            heap.extractMax();
        }
        assertEquals(heap.extractMax().getHeapKey(),-1);

    }
}
