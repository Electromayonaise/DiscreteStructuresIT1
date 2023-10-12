package model;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * The ArrayList class represents a dynamic array that can grow or shrink in size.
 *
 * @param <T> The type of elements stored in the array.
 */
public class ArrayList<T> implements Iterable<T> {

    private T[] elements;
    private int size;

    /**
     * Constructs an empty ArrayList with an initial capacity of 1.
     */
    public ArrayList() {
        this.elements = (T[]) new Object[1];
        this.size = 0;
    }

    /**
     * Sets the element at the specified index in the ArrayList.
     *
     * @param i       The index at which the specified element is to be set.
     * @param element The element to be set.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean set(int i, T element) {
        boolean flag = false;
        if (i < size) {
            elements[i] = element;
            flag = true;
        }
        return flag;
    }

    /**
     * Adds the specified element to the end of the ArrayList.
     *
     * @param element The element to be added.
     */
    public void add(T element) {
        if (size == elements.length) {
            increaseSize();
        }
        elements[size] = element;
        size++;
    }

    /**
     * Retrieves the element at the specified index from the ArrayList.
     *
     * @param index The index of the element to be retrieved.
     * @return The element at the specified index.
     */
    public T get(int index) {
        return elements[index];
    }

    /**
     * Returns the current size of the ArrayList.
     *
     * @return The size of the ArrayList.
     */
    public int size() {
        return size;
    }

    /**
     * Removes the element at the specified index from the ArrayList.
     *
     * @param i The index of the element to be removed.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean remove(int i) {
        boolean flag = false;
        if (i >= 0 && i < size) {
            int j = i + 1;
            while (j < size) {
                elements[j - 1] = elements[j];
                j++;
            }
            elements[size - 1] = null;
            size--;
            flag = true;
        }
        return flag;
    }

    /**
     * Returns a string representation of the ArrayList.
     *
     * @return A string representation of the ArrayList.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size; i++) {
            str.append(elements[i]);
            str.append(" ");
        }
        return str.toString();
    }

    /**
     * Checks if the ArrayList contains the specified element.
     *
     * @param element The element to be checked for existence in the ArrayList.
     * @return true if the ArrayList contains the element, false otherwise.
     */
    public boolean contains(T element) {
        boolean flag = false;
        for (int i = 0; i < size && !flag; i++) {
            if (elements[i].equals(element)) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Increases the size of the internal array when needed.
     */
    private void increaseSize() {
        int newCapacity = elements.length * 2;
        T[] arr = (T[]) new Object[newCapacity];
        System.arraycopy(elements, 0, arr, 0, elements.length);
        elements = arr;
    }

    /**
     * Returns an iterator over the elements in the ArrayList.
     *
     * @return An iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                return elements[currentIndex++];
            }
        };
    }

    /**
     * Performs the given action for each element of the ArrayList until all elements have been processed
     * or the action throws an exception.
     *
     * @param action The action to be performed for each element.
     */
    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }
}
