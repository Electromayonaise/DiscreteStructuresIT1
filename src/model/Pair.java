package model;

/**
 * A simple pair class representing a key-value pair.
 *
 * @param <T> the type of the first element (key)
 * @param <U> the type of the second element (value)
 */
public class Pair<T, U> {

    /** The first element (key) of the pair. */
    private T first;

    /** The second element (value) of the pair. */
    private U second;

    /**
     * Constructs a new pair with the given key and value.
     *
     * @param first  the first element (key) of the pair
     * @param second the second element (value) of the pair
     */
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the first element (key) of the pair.
     *
     * @return the first element (key) of the pair
     */
    public T getFirst() {
        return first;
    }

    /**
     * Sets the first element (key) of the pair.
     *
     * @param first the new value for the first element (key)
     */
    public void setFirst(T first) {
        this.first = first;
    }

    /**
     * Gets the second element (value) of the pair.
     *
     * @return the second element (value) of the pair
     */
    public U getSecond() {
        return second;
    }

    /**
     * Sets the second element (value) of the pair.
     *
     * @param second the new value for the second element (value)
     */
    public void setSecond(U second) {
        this.second = second;
    }
}
