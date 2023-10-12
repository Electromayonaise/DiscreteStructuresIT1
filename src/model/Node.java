package model;

/**
 * A simple node class for a doubly linked list.
 *
 * @param <T> the type of the value stored in the node
 */
public class Node<T> {

    /** The value stored in the node. */
    private T value;

    /** The reference to the next node in the list. */
    private Node<T> next;

    /** The reference to the previous node in the list. */
    private Node<T> prev;

    /**
     * Constructs a new node with the given value.
     *
     * @param value the value to be stored in the node
     */
    public Node(T value) {
        this.value = value;
    }

    /**
     * Gets the value stored in the node.
     *
     * @return the value stored in the node
     */
    public T getValue() {
        return value;
    }

    /**
     * Sets the value stored in the node.
     *
     * @param value the new value to be stored in the node
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * Gets the reference to the next node in the list.
     *
     * @return the next node in the list
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Sets the reference to the next node in the list.
     *
     * @param next the next node in the list
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Gets the reference to the previous node in the list.
     *
     * @return the previous node in the list
     */
    public Node<T> getPrev() {
        return prev;
    }

    /**
     * Sets the reference to the previous node in the list.
     *
     * @param prev the previous node in the list
     */
    public void setPrev(Node<T> prev) {
        this.prev = prev;
    }
}
