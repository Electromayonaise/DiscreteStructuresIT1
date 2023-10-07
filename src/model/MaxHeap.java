package model;


public class MaxHeap<T extends HeapKeyProvider > implements PriorityQueue<T> {
    private ArrayList<T> list;
    private int size;

    public MaxHeap(ArrayList<T> list) {
        this.list = list;
        size = list.size();
        buildMaxHeap();
    }

    public MaxHeap() {
        list = new ArrayList<>();
        size = 0;
    }

    public void buildMaxHeap() {
        for (int i = size / 2 - 1; i >= 0; i--) {
            maxHeapify(i);
        }

    }

    public boolean modifyKey(int i,int newKey){
        return (increaseKey(i,newKey)||decreaseKey(i,newKey));
    }

    public boolean increaseKey(int i, int newKey) {
        boolean flag = false;
        if (i<size &&list.get(i).getHeapKey() <= newKey) {
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

    public boolean decreaseKey(int i,int newKey){
        boolean flag=false;
        if( i<size&&list.get(i).getHeapKey()>=newKey){
            list.get(i).setHeapKey(newKey);
            maxHeapify(i);
            flag=true;

        }
        return flag;
    }
    @Override
    public T extractMax(){
        T max=null;
        if(size>0){
            max= list.get(0);
            int lastIndex=list.size()-1;
            swap(0,lastIndex);
            list.remove(lastIndex);
            size--;
            maxHeapify(0);
        }
        return max;
    }


    public void insert(T newElement){
        list.add(newElement);
        size++;
        increaseKey(size-1, newElement.getHeapKey());
    }

    public int size(){
        return size;
    }

    private void maxHeapify(int currentIndex) {
        boolean flag = true;
        while (currentIndex < size && flag) {
            int leftChildIndex = getLeftChildIndex(currentIndex); //compute the left son index
            int rightChildIndex = getRightChildIndex(currentIndex); //compute right son index
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

    public T max() {
        return (size > 0) ? list.get(0) : null;
    }

    public boolean empty(){
        return size==0;
    }

    private int getLeftChildIndex(int i) {
        return i * 2 + 1;
    }

    private int getRightChildIndex(int i) {
        return getLeftChildIndex(i) + 1;
    }

    private int getFatherIndex(int i) {
        return (i - 1) / 2;
    }

    private T getLeftChild(int i) {
        int leftChildIndex = getLeftChildIndex(i);
        return (leftChildIndex < size) ? list.get(leftChildIndex) : null;
    }

    private T getRightChild(int i) {
        int rightChildIndex = getRightChildIndex(i);
        return (rightChildIndex < size) ? list.get(rightChildIndex) : null;
    }

    private void swap(int i, int j) {
        T element1 = list.get(i);
        T element2 = list.get(j);
        list.set(j, element1);
        list.set(i, element2);
    }




}
