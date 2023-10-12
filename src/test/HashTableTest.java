package test;

import model.HashTable;

import model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the HashTable class.
 */
public class HashTableTest {
    private HashTable<String, Task> table;
    private final int NUM_ELEMENTS=1000;

    public void setUp1EmptyHashTable(){
        table=new HashTable<>();
    }

    public void setUp2HashTableWithElements(){
        setUp1EmptyHashTable();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            Task task= new Task("title"+i,"description+"+i,i);
            table.add(task.getTitle(), task);
        }


    }
    @Test
    public void nullTest(){
        setUp1EmptyHashTable();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            assertNull(table.get("title"+i));
        }

    }

    @Test
    public void getTest(){
       setUp2HashTableWithElements();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            assertEquals( (table.get("title"+i)) .getTitle(), "title"+i);
        }

    }
    @Test
    public void removeFalseTest(){
        setUp1EmptyHashTable();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            assertFalse(table.remove(i+""));
        }
    }
    @Test

    public void removeTrueTest(){
        setUp2HashTableWithElements();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            assertTrue(table.remove("title"+i));
            assertFalse(table.remove("title"+i));
            assertFalse(table.remove("title"+i));
        }

    }
    @Test
    public void overwriteElements(){
        setUp2HashTableWithElements();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            Task task= new Task("modifyTask"+i,"description+"+i,i);
            table.add("title"+i, task);
            assertEquals( (table.get("title"+i)) .getTitle(), "modifyTask"+i);


        }
    }

    @Test
    public void sizeOverwriteElements(){
        setUp2HashTableWithElements();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            Task task= new Task("modifyTask"+i,"description+"+i,i);
            table.add("title"+i, task);
            assertEquals(table.size(),NUM_ELEMENTS);
            assertEquals( (table.get("title"+i)) .getTitle(), "modifyTask"+i);


        }
    }

    @Test
    public void sizeTest(){
        setUp2HashTableWithElements();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            assertEquals(table.size(),NUM_ELEMENTS-i);
            table.remove("title"+i);
            table.remove("title"+i);
            table.remove("title"+i);
        }

    }



}
