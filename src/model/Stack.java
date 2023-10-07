package model;

public interface Stack<T>{
    void push(T newValue);
    T top();
    T pop();
    int size();
    boolean empty();
}
