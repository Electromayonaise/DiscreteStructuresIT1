package model;

/**
 * An interface representing a stack.
 *
 * @param <T> the type of elements in the stack
 */
public interface Stack<T> {

    /**
     * Pushes a new element onto the stack.
     *
     * @param newValue the new element to push
     */
    void push(T newValue);

    /**
     * Gets the top element of the stack without removing it.
     *
     * @return the top element of the stack
     */
    T top();

    /**
     * Pops and returns the top element of the stack.
     *
     * @return the popped element
     */
    T pop();

    /**
     * Gets the size of the stack.
     *
     * @return the size of the stack
     */
    int size();

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise
     */
    boolean empty();
}
