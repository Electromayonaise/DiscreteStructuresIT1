package model;

/**
 * The HeapKeyProvider interface provides a way for objects to expose and modify their heap key.
 * Objects implementing this interface can be used in a heap data structure.
 */
public interface HeapKeyProvider {

    /**
     * Gets the heap key of the object.
     *
     * @return The heap key.
     */
    int getHeapKey();

    /**
     * Sets a new heap key for the object.
     *
     * @param newKey The new heap key to be set.
     */
    void setHeapKey(int newKey);
}

