package model;

public interface Queue<T>{
    void enqueue(T newValue);
    T front();
    T dequeue();
    int size();

    boolean empty();
}
