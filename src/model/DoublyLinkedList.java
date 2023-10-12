package model;

import java.util.Iterator;

/**
 * A generic Doubly Linked List implementation that can be used as a Queue or a Stack.
 *
 * @param <T> The type of elements stored in the list.
 */
public class DoublyLinkedList<T> implements Queue<T>, Stack<T>, Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Constructs an empty Doubly Linked List.
     */
    public DoublyLinkedList() {
        size = 0;
    }

    /**
     * Converts the linked list to an array.
     *
     * @return An array representation of the linked list.
     */
    public Object[] toArray() {
        Object[] array = new Object[size];
        int index = 0;
        for (T value : this) {
            array[index] = value;
            index++;
        }
        return array;
    }

    /**
     * Adds a new element to the end of the list.
     *
     * @param newValue The new element to be added.
     */
    public void addLast(T newValue) {
        Node<T> newNode = new Node<>(newValue);
        if (head == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
        }
        tail = newNode;
        size++;
    }

    /**
     * Removes and returns the first element in the list.
     *
     * @return The first element in the list, or null if the list is empty.
     */
    public T removeFirst() {
        T valueToDelete;
        if (size == 1) {
            valueToDelete = head.getValue();
            head = null;
            tail = null;
            size--;
        } else if (size > 1) {
            valueToDelete = head.getValue();
            head = head.getNext();
            head.getPrev().setNext(null);
            head.setPrev(null);
            size--;
        } else {
            valueToDelete = null;
        }
        return valueToDelete;
    }

    /**
     * Removes and returns the last element in the list.
     *
     * @return The last element in the list, or null if the list is empty.
     */
    public T removeLast() {
        T valueToDelete;
        if (size == 1) {
            valueToDelete = tail.getValue();
            head = null;
            tail = null;
            size--;
        } else if (size > 1) {
            valueToDelete = tail.getValue();
            tail = tail.getPrev();
            tail.getNext().setPrev(null);
            tail.setNext(null);
            size--;
        } else {
            valueToDelete = null;
        }
        return valueToDelete;
    }

    /**
     * Gets the value of the first element in the list.
     *
     * @return The value of the first element, or null if the list is empty.
     */
    public T getFirst() {
        return (head == null) ? null : head.getValue();
    }

    /**
     * Gets the value of the last element in the list.
     *
     * @return The value of the last element, or null if the list is empty.
     */
    public T getLast() {
        return (tail == null) ? null : tail.getValue();
    }

    /**
     * Gets the first node in the list that matches a given condition.
     *
     * @param object The object to match against.
     * @param equals The condition to check.
     * @param <U>    The type of the object to match.
     * @return The first node that matches the condition, or null if not found.
     */
    private <U> Node<T> getFirstNodeWithInstance(U object, BiPredicate<T, U> equals) {
        Node<T> firstNode = null;
        Node<T> current = head;
        boolean flag = false;
        while (current != null && !flag) {
            if (equals.test(current.getValue(), object)) {
                firstNode = current;
                flag = true;
            }
            current = current.getNext();
        }
        return firstNode;
    }

    /**
     * Gets the first instance in the list that matches a given condition.
     *
     * @param object The object to match against.
     * @param equals The condition to check.
     * @param <U>    The type of the object to match.
     * @return The first instance that matches the condition, or null if not found.
     */
    public <U> T getFirstInstance(U object, BiPredicate<T, U> equals) {
        T value = null;
        Node<T> node = getFirstNodeWithInstance(object, equals);
        if (node != null) {
            value = node.getValue();
        }
        return value;
    }

    /**
     * Removes the first instance in the list that matches a given condition.
     *
     * @param object The object to match against.
     * @param equals The condition to check.
     * @param <U>    The type of the object to match.
     * @return True if an instance was removed, false otherwise.
     */
    public <U> boolean removeFirstInstance(U object, BiPredicate<T, U> equals) {
        Node<T> nodeToDelete = getFirstNodeWithInstance(object, equals);
        boolean removed = false;
        if (nodeToDelete != null) {
            if (nodeToDelete.getPrev() != null) {
                nodeToDelete.getPrev().setNext(nodeToDelete.getNext());
            } else {
                head = nodeToDelete.getNext();
            }
            if (nodeToDelete.getNext() != null) {
                nodeToDelete.getNext().setPrev(nodeToDelete.getPrev());
            } else {
                tail = nodeToDelete.getPrev();
            }
            nodeToDelete.setPrev(null);
            nodeToDelete.setNext(null);
            size--;
            removed = true;
        }
        return removed;
    }

    /**
     * Provides an iterator for iterating over the elements of the linked list.
     *
     * @return An iterator for the linked list.
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    /**
     * Private inner class representing the iterator for the linked list.
     */
    private class MyIterator implements Iterator<T> {
        private Node<T> currentNode;
        int currentIndex;

        MyIterator() {
            currentIndex = -1;
        }

        /**
         * Checks if there is a next element in the iteration.
         *
         * @return True if there is a next element, false otherwise.
         */
        @Override
        public boolean hasNext() {
            return currentIndex < size - 1;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return The next element in the iteration.
         * @throws java.util.NoSuchElementException If there is no next element.
         */
        @Override
        public T next() {
            if (hasNext()) {
                T valueToReturn;
                if (currentIndex == -1) {
                    valueToReturn = head.getValue();
                    currentNode = head;
                } else {
                    valueToReturn = currentNode.getNext().getValue();
                    currentNode = currentNode.getNext();
                }
                currentIndex++;
                return valueToReturn;
            } else {
                throw new java.util.NoSuchElementException();
            }
        }
    }


    //********* Common methods******************//

    /**
     * Gets the size of the linked list.
     *
     * @return The number of elements in the linked list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the linked list is empty.
     *
     * @return True if the linked list is empty, false otherwise.
     */
    @Override
    public boolean empty() {
        return size == 0;
    }

    //************** QUEUE IMPLEMENTATION**************//

    /**
     * Enqueues a new element to the end of the linked list.
     *
     * @param newValue The new element to be enqueued.
     */
    @Override
    public void enqueue(T newValue) {
        addLast(newValue);
    }

    /**
     * Gets the value of the first element in the linked list (front of the queue).
     *
     * @return The value of the first element, or null if the list is empty.
     */
    @Override
    public T front() {
        return getFirst();
    }

    /**
     * Dequeues and returns the first element in the linked list (removes from the front of the queue).
     *
     * @return The first element in the linked list, or null if the list is empty.
     */
    @Override
    public T dequeue() {
        return removeFirst();
    }

    /******STACK IMPLEMENTATION******/

    /**
     * Pushes a new element onto the end of the linked list (top of the stack).
     *
     * @param newValue The new element to be pushed.
     */
    @Override
    public void push(T newValue) {
        addLast(newValue);
    }

    /**
     * Gets the value of the last element in the linked list (top of the stack).
     *
     * @return The value of the last element, or null if the list is empty.
     */
    public T top() {
        return getLast();
    }

    /**
     * Pops and returns the last element in the linked list (removes from the top of the stack).
     *
     * @return The last element in the linked list, or null if the list is empty.
     */
    @Override
    public T pop() {
        return removeLast();
    }
}
