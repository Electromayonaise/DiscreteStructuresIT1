package model;

/**
 * An interface representing a priority queue.
 *
 * @param <T> the type of elements in the priority queue
 */
public interface PriorityQueue<T> {

    /**
     * Inserts a new element into the priority queue.
     *
     * @param newElement the new element to insert
     */
    void insert(T newElement);

    /**
     * Extracts the maximum element from the priority queue.
     *
     * @return the maximum element in the priority queue
     */
    T extractMax();

    /**
     * Gets the maximum element from the priority queue without removing it.
     *
     * @return the maximum element in the priority queue
     */
    T max();

    /**
     * Modifies the key of the element at the specified index in the priority queue.
     *
     * @param i      the index of the element to modify
     * @param newKey the new key for the element
     * @return true if the modification is successful, false otherwise
     */
    boolean modifyKey(int i, int newKey);
}
