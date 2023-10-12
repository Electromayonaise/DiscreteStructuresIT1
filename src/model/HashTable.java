package model;

/**
 * A simple implementation of a hash table using open addressing and linked lists for collision resolution.
 *
 * @param <T> the type of the keys
 * @param <U> the type of the values
 */
public class HashTable<T, U> {

    /** The number of slots in the hash table. */
    private static final int NUM_SLOTS = 1069;

    /** The current size of the hash table. */
    private int size;

    /** An array of linked lists to store key-value pairs. */
    private DoublyLinkedList<Pair<T, U>>[] arr;

    /**
     * Constructs an empty hash table with a default number of slots.
     */
    public HashTable() {
        arr = (DoublyLinkedList<Pair<T, U>>[]) new DoublyLinkedList[NUM_SLOTS];
        size = 0;
    }

    /**
     * Computes the slot index for a given key using its hash code.
     *
     * @param key the key
     * @return the slot index
     */
    private int getSlot(T key) {
        long hashCode = key.hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return (int) (hashCode % NUM_SLOTS);
    }

    /**
     * Retrieves the linked list associated with the slot for a given key.
     *
     * @param key the key
     * @return the linked list
     */
    private DoublyLinkedList<Pair<T, U>> getLinkedListOnSlot(T key) {
        int index = getSlot(key);
        if (arr[index] == null) {
            arr[index] = new DoublyLinkedList<>();
        }
        return arr[index];
    }

    /**
     * Adds a key-value pair to the hash table.
     *
     * @param key   the key
     * @param value the value
     */
    public void add(T key, U value) {
        DoublyLinkedList<Pair<T, U>> list = getLinkedListOnSlot(key);

        // BiPredicate to compare the key of a pair with the key's hash code
        BiPredicate<Pair<T, U>, T> equals = (pair, k) -> (pair.getFirst().hashCode()) == (k.hashCode());

        // If an element with the same key exists, remove it
        boolean flag = list.removeFirstInstance(key, equals);
        if (!flag) {
            size++;
        }

        // Create a new pair and add it to the list
        Pair<T, U> newPair = new Pair<>(key, value);
        list.addLast(newPair);
    }

    /**
     * Removes a key-value pair from the hash table.
     *
     * @param key the key
     * @return true if the key was found and removed, false otherwise
     */
    public boolean remove(T key) {
        int slot = getSlot(key);
        boolean flag = false;

        // BiPredicate to compare the key of a pair with the key's hash code
        BiPredicate<Pair<T, U>, T> equals = (pair, k) -> (pair.getFirst().hashCode()) == (k.hashCode());

        // Remove the pair with the specified key
        if (arr[slot] != null && arr[slot].removeFirstInstance(key, equals)) {
            flag = true;
            size--;

            // If the linked list becomes empty, set the slot to null
            if (arr[slot].empty()) {
                arr[slot] = null;
            }
        }
        return flag;
    }

    /**
     * Retrieves the value associated with a given key.
     *
     * @param key the key
     * @return the value associated with the key, or null if not found
     */
    public U get(T key) {
        U value = null;
        int slot = getSlot(key);

        // BiPredicate to compare the key of a pair with the key's hash code
        BiPredicate<Pair<T, U>, T> equals = (pair, k) -> (pair.getFirst().hashCode()) == (k.hashCode());

        // Find the pair with the specified key
        if (arr[slot] != null) {
            Pair<T, U> pair = arr[slot].getFirstInstance(key, equals);
            value = (pair != null) ? pair.getSecond() : null;
        }
        return value;
    }

    /**
     * Checks if the hash table contains a given key.
     *
     * @param key the key
     * @return true if the key is found in the hash table, false otherwise
     */
    public boolean containsKey(T key) {
        return get(key) != null;
    }

    /**
     * Gets the current size of the hash table.
     *
     * @return the size of the hash table
     */
    public int size() {
        return size;
    }
}
