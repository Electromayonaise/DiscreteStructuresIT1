package model;

/**
 * An interface representing a queue.
 *
 * @param <T> the type of elements in the queue
 */
public interface Queue<T> {

    /**
     * Enqueues a new element into the queue.
     *
     * @param newValue the new element to enqueue
     */
    void enqueue(T newValue);

    /**
     * Gets the front element of the queue without removing it.
     *
     * @return the front element of the queue
     */
    T front();

    /**
     * Dequeues and returns the front element of the queue.
     *
     * @return the dequeued element
     */
    T dequeue();

    /**
     * Gets the size of the queue.
     *
     * @return the size of the queue
     */
    int size();

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    boolean empty();
}
