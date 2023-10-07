package model;

public interface PriorityQueue<T> {
    void insert(T newElement);
    T extractMax();
    T max();
    boolean modifyKey(int i,int newKey);

}
