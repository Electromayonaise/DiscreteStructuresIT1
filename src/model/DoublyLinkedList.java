package model;

import java.util.Iterator;

public class DoublyLinkedList <T> implements Queue<T>, Stack<T>, Iterable<T>{
    private Node<T> head;
    private Node<T> tail;

    private int size;

    public DoublyLinkedList() {
        size=0;
    }

    public void addLast(T newValue){
        Node <T>newNode=new Node<>(newValue);
        if(head==null){
            head=newNode;

        }else{
            tail.setNext(newNode);
            newNode.setPrev(tail);

        }
        tail=newNode;
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
    private <U>Node<T> getFirstNodeWithInstance(U object,BiPredicate<T,U> equals ){
        Node<T> firstNode=null;
        Node <T> current=head;
        boolean flag=false;
        while(current!=null&&!flag){
            if(equals.test(current.getValue(),object)){
                firstNode= current;
                flag=true;
            }
            current=current.getNext();
        }
        return firstNode;
    }







    public <U>T getFirstInstance(U object,BiPredicate<T,U> equals ){
        T value=null;
        Node<T> node= getFirstNodeWithInstance(object,equals);
        if(node!=null){
            value=node.getValue();
        }
        return value;
    }

    public <U>boolean removeFirstInstance(U object, BiPredicate<T,U>equals){
        Node<T> nodeToDelete=getFirstNodeWithInstance(object,equals);
        boolean removed=false;
        if(nodeToDelete!=null) {
            if (nodeToDelete.getPrev() != null) {
                nodeToDelete.getPrev().setNext(nodeToDelete.getNext());

            } else {
                head = nodeToDelete.getNext();
            }
            if (nodeToDelete.getNext() != null) {
                nodeToDelete.getNext().setPrev(nodeToDelete.getPrev());

            } else {
                tail = nodeToDelete.getPrev();
            }
            nodeToDelete.setPrev(null);
            nodeToDelete.setNext(null);
            size--;
            removed=true;
        }


        return removed;
    }


    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }
    private class MyIterator implements Iterator<T> {
        private Node<T> currentNode;
        int currentIndex;
        MyIterator(){
            currentIndex=-1;

        }
        @Override
        public boolean hasNext() {
            return currentIndex<size-1;
        }
        @Override
        public T next() {
            if (hasNext()) {
                T valueToReturn;
                if(currentIndex==-1){
                    valueToReturn=head.getValue();
                    currentNode=head;
                }else{
                    valueToReturn=currentNode.getNext().getValue();
                    currentNode=currentNode.getNext();
                }
                currentIndex++;
                return valueToReturn;
            } else {
                throw new java.util.NoSuchElementException();
            }
        }
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
