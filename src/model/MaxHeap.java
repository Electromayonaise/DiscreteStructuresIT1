package model;



public class MaxHeap<T extends HeapKeyProvider> implements PriorityQueue<T> {
    private ArrayList<T> list;
    private int size;

    /**
     * Constructs a MaxHeap from an existing list.
     *
     * @param list The ArrayList to be converted into a MaxHeap.
     */
    public MaxHeap(ArrayList<T> list) {
        this.list = list;
        size = list.size();
        buildMaxHeap();
    }

    /**
     * Constructs an empty MaxHeap.
     */
    public MaxHeap() {
        list = new ArrayList<>();
        size = 0;
    }


    /**
     * Builds a MaxHeap from the current list.
     */
    public void buildMaxHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    /**
     * Gets the elements of the MaxHeap as an ArrayList.
     *
     * @return The ArrayList containing the elements of the MaxHeap.
     */
    public ArrayList<T> getElements() {
        return list;
    }

    /**
     * Modifies the key of the element at index i.
     *
     * @param i      The index of the element to be modified.
     * @param newKey The new key value.
     * @return True if the modification is successful, false otherwise.
     */
    public boolean modifyKey(int i, int newKey) {
        return (increaseKey(i, newKey) || decreaseKey(i, newKey));
    }

    /**
     * Increases the key of the element at index i.
     *
     * @param i      The index of the element to be modified.
     * @param newKey The new key value.
     * @return True if the increase is successful, false otherwise.
     */
    public boolean increaseKey(int i, int newKey) {
        boolean flag = false;
        if (i < size && list.get(i).getHeapKey() <= newKey) {
            int currentIndex = i;
            int fatherIndex = getFatherIndex(currentIndex);
            T current = list.get(currentIndex);
            T father = list.get(fatherIndex);
            current.setHeapKey(newKey);
            while (currentIndex > 0 && father.getHeapKey() < current.getHeapKey()) {
                swap(currentIndex, fatherIndex);
                currentIndex = fatherIndex;
                fatherIndex = getFatherIndex(currentIndex);
                current = list.get(currentIndex);
                father = list.get(fatherIndex);
            }
            flag = true;
        }
        return flag;
    }

    /**
     * Decreases the key of the element at index i.
     *
     * @param i      The index of the element to be modified.
     * @param newKey The new key value.
     * @return True if the decrease is successful, false otherwise.
     */
    public boolean decreaseKey(int i, int newKey) {
        boolean flag = false;
        if (i < size && list.get(i).getHeapKey() >= newKey) {
            list.get(i).setHeapKey(newKey);
            maxHeapify(i);
            flag = true;
        }
        return flag;
    }

    /**
     * Extracts and returns the maximum element from the MaxHeap.
     *
     * @return The maximum element, or null if the heap is empty.
     */
    @Override
    public T extractMax() {
        T max = null;
        if (size > 0) {
            max = list.get(0);
            int lastIndex = list.size() - 1;
            swap(0, lastIndex);
            list.remove(lastIndex);
            size--;
            maxHeapify(0);
        }
        return max;
    }

    /**
     * Inserts a new element into the MaxHeap.
     *
     * @param newElement The new element to be inserted.
     */
    public void insert(T newElement) {
        list.add(newElement);
        size++;
        increaseKey(size - 1, newElement.getHeapKey());
    }

    /**
     * Gets the size of the MaxHeap.
     *
     * @return The number of elements in the MaxHeap.
     */
    public int size() {
        return size;
    }

    // Private Methods

    /**
     * Maintains the max-heap property from the given index.
     *
     * @param currentIndex The index from which to start maintaining the max-heap property.
     */
    private void maxHeapify(int currentIndex) {
        boolean flag = true;
        while (currentIndex < size && flag) {
            int leftChildIndex = getLeftChildIndex(currentIndex);
            int rightChildIndex = getRightChildIndex(currentIndex);
            T father = list.get(currentIndex);
            T leftChild = getLeftChild(currentIndex);
            T rightChild = getRightChild(currentIndex);
            int largestIndex = currentIndex;

            if (leftChildIndex < size && leftChild.getHeapKey() > father.getHeapKey()) {
                largestIndex = leftChildIndex;
            }
            if (rightChildIndex < size && rightChild.getHeapKey() > list.get(largestIndex).getHeapKey()) {
                largestIndex = rightChildIndex;
            }
            if (largestIndex == currentIndex) {
                flag = false;
            } else {
                swap(largestIndex, currentIndex);
            }
            currentIndex = largestIndex;
        }
    }

    /**
     * Gets the maximum element in the MaxHeap.
     *
     * @return The maximum element, or null if the heap is empty.
     */
    public T max() {
        return (size > 0) ? list.get(0) : null;
    }

    /**
     * Checks if the MaxHeap is empty.
     *
     * @return True if the MaxHeap is empty, false otherwise.
     */
    public boolean empty() {
        return size == 0;
    }

    /**
     * Calculates the index of the left child of a given index.
     *
     * @param i The index of the parent.
     * @return The index of the left child.
     */
    private int getLeftChildIndex(int i) {
        return i * 2 + 1;
    }

    /**
     * Calculates the index of the right child of a given index.
     *
     * @param i The index of the parent.
     * @return The index of the right child.
     */
    private int getRightChildIndex(int i) {
        return getLeftChildIndex(i) + 1;
    }

    /**
     * Calculates the index of the parent of a given index.
     *
     * @param i The index of the child.
     * @return The index of the parent.
     */
    private int getFatherIndex(int i) {
        return (i - 1) / 2;
    }

    /**
     * Gets the left child of a given index.
     *
     * @param i The index of the parent.
     * @return The left child.
     */
    private T getLeftChild(int i) {
        int leftChildIndex = getLeftChildIndex(i);
        return (leftChildIndex < size) ? list.get(leftChildIndex) : null;
    }

    /**
     * Gets the right child of a given index.
     *
     * @param i The index of the parent.
     * @return The right child.
     */
    private T getRightChild(int i) {
        int rightChildIndex = getRightChildIndex(i);
        return (rightChildIndex < size) ? list.get(rightChildIndex) : null;
    }

    /**
     * Swaps two elements in the MaxHeap.
     *
     * @param i The index of the first element.
     * @param j The index of the second element.
     */
    private void swap(int i, int j) {
        T element1 = list.get(i);
        T element2 = list.get(j);
        list.set(j, element1);
        list.set(i, element2);
    }

    /**
     * Removes the element at a given index.
     *
     * @param i The index of the element to be removed.
     * @return True if the removal is successful, false otherwise.
     */
    public boolean remove(int i) {
        boolean flag = false;
        if (i < list.size() && list.remove(i)) {
            size--;
            flag = true;
            buildMaxHeap();
        }
        return flag;
    }
}
