package test;

import model.DoublyLinkedList;
import model.BiPredicate;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the DoublyLinkedList class.
 */
public class DoublyLinkedListTest {
    private DoublyLinkedList<Integer> list;
    private final int NUM_ELEMENTS =40;

    public void setUpEmptyList(){ // Cr
        list=new DoublyLinkedList<>();
    }

    public void setUpListWithElements(){
        setUpEmptyList();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            list.addLast(i);
        }

    }
    @Test
    public void testGetFirstInstanceNull(){
        setUpEmptyList();
        BiPredicate<Integer,String> equals= (t,u)->t.toString().equals(u);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            Integer element=list.getFirstInstance(i+"",equals);
            assertNull(element);
        }

    }
    @Test
    public void testGetFirstInstance(){
        setUpListWithElements();
        BiPredicate<Integer,String> equals= (t,u)->t.toString().equals(u);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            Integer element=list.getFirstInstance(i+"",equals);
            assertEquals(element,i);
        }

    }
    @Test

    public void testDeleteFirstInstance(){
        setUpListWithElements();
        BiPredicate<Integer,String> equals= (t,u)->t.toString().equals(u);
        Integer element;
        boolean flag;
        for (int i = NUM_ELEMENTS-1; i >= 0; i=i-2) {
            element=list.getFirstInstance(i+"",equals);
            assertEquals(element,i);
            flag= list.removeFirstInstance(i+"",equals);
            assertTrue(flag);
            flag= list.removeFirstInstance(i+"",equals);
            assertFalse(flag);
            element=list.getFirstInstance(i+"",equals);
            assertNull(element);

        }
        for (int i = NUM_ELEMENTS-2; i >= 0; i=i-2) {
            element=list.getFirstInstance(i+"",equals);
            assertEquals(element,i);
            flag= list.removeFirstInstance(i+"",equals);
            assertTrue(flag);
            flag= list.removeFirstInstance(i+"",equals);
            assertFalse(flag);
            element=list.getFirstInstance(i+"",equals);
            assertNull(element);

        }

    }
    @Test
    public void testForEachListWithElements(){
        setUpListWithElements();
        int i=0;
        for(Integer x: list){
            assertEquals(i,x);
            i++;
        }
    }
    @Test
    public void testForEachListWithNoElements(){
        setUpEmptyList();
        for(Integer x: list){
            //the list is empty
            //this line should be unreachable
            assertEquals(x,1012);
        }
    }









}
