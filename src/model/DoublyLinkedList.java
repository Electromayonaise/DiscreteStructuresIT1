package model;

public class DoublyLinkedList <T> implements Queue<T>, Stack<T>{
    private Node<T> head;
    private Node<T> tail;

    private int size;

    public DoublyLinkedList() {
        size=0;
    }


    public void addFirst (T newValue){
        Node <T>newNode=new Node<>(newValue);
        if(head==null){
            head=newNode;
            tail=newNode;
            head.setNext(tail);
            tail.setPrev(head);
        }else{
            newNode.setNext(head);
            head.setPrev(newNode);
            head=newNode;
        }
        size++;


    }
    public void addLast(T newValue){
        Node <T>newNode=new Node<>(newValue);
        if(head==null){
            head=newNode;
            tail=newNode;
            head.setNext(tail);
            tail.setPrev(head);
        }else{
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail=newNode;
        }
        size++;
    }

    public T removeFirst(){
        T valueToDelete;
        if(size==1){
            valueToDelete= head.getValue();
            head=null;
            tail=null;
            size--;
        }else if(size>1){
            valueToDelete=head.getValue();
            head=head.getNext();
            head.getPrev().setNext(null);
            head.setPrev(null);

            size--;
        }else{
            valueToDelete=null;
        }


        return valueToDelete;
    }

    public T removeLast(){
        T valueToDelete;
        if(size==1){
            valueToDelete= tail.getValue();
            head=null;
            tail=null;

            size--;
        }else if(size>1){
            valueToDelete= tail.getValue();
            tail=tail.getPrev();
            tail.getNext().setPrev(null);
            tail.setNext(null);

            size--;
        }else{
            valueToDelete=null;
        }
        return valueToDelete;
    }

    public T getFirst(){
        return (head==null)?null:head.getValue();
    }

    public T getLast(){
        return (tail==null)?null:tail.getValue();
    }


    //********* Common methods******************//
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty(){
        return size==0;
    }




    //************** QUEUE IMPLEMENTATION**************//
    @Override
    public void enqueue(T newValue){
        addLast(newValue);
    }

    @Override
    public T front(){
        return getFirst();
    }

    @Override
    public T dequeue(){
        return removeFirst();
    }



    /******STACK IMPLEMENTATION******/

    @Override
    public void push(T newValue) {
        addLast(newValue);
    }

    public T top(){
        return getLast();
    }

    @Override
    public T pop() {
        return removeLast();
    }
}
