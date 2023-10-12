package test;


import model.DoublyLinkedList;
import model.Stack;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Stack interface.
 */
public class StackTest {
    private Stack<Integer> stack;
    private final int NUM_ELEMENTS =100;

    public void setUpEmptyStack(){ // Cr
        stack=new DoublyLinkedList<>();
    }

    public void setUpStackWithElements(){
        setUpEmptyStack();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            stack.push(i);
        }
    }




    @Test

    public void testPop(){
        setUpStackWithElements();
        for (int i = NUM_ELEMENTS-1; i >=0 ; i--) {
            assertEquals(i,stack.pop());
        }
    }

    @Test
    public void testTop(){
        setUpEmptyStack();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            stack.push(i);
            stack.push(i+1);
            assertEquals(stack.top(),i+1);
        }
    }



    @Test

    public void testSize(){
        setUpStackWithElements();
        int size= NUM_ELEMENTS;
        while(size>=0){
            assertEquals(size,stack.size());
            stack.pop();
            size--;
        }
        stack.pop();
        assertEquals(0,stack.size());

    }
    @Test
    public void testEmpty(){
        setUpStackWithElements();
        int size=NUM_ELEMENTS;
        while(size>=0){
            if(size!=0){
                assertFalse(stack.empty());
            }else{
                assertTrue(stack.empty());
            }
            stack.pop();
            size--;

        }
    }

}
