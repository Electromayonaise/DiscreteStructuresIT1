package model;
import java.util.Iterator;
import java.util.function.Consumer;

public class ArrayList<T> implements Iterable<T>{

    private T[] elements;
    private int size;


    public ArrayList() {
        this.elements = (T[]) new Object[1];
        this.size = 0;
    }
    public boolean set(int i, T element){
        boolean flag=false;
        if(i<size){
            elements[i]=element;
            flag=true;
        }
        return flag;
    }

    public void add(T element) {
        if (size == elements.length) {
            increaseSize();
        }
        elements[size] = element;
        size++;
    }

    public T get(int index) {
        return elements[index];
    }

    public int size() {
        return size;
    }

    private void increaseSize() {
        int newCapacity = elements.length * 2;
        T []arr=(T[]) new Object[newCapacity];
        for (int i = 0; i < elements.length; i++) {
            arr[i]=elements[i];
        }
        elements=arr;



    }
    public boolean remove(int i) {
        boolean flag = false;
        if (i >= 0 && i < size) { // Check if i is within the valid range
            int j = i + 1;
            while (j < size) {
                elements[j - 1] = elements[j];
                j++;
            }
            elements[size - 1] = null; // Set the last element to null
            size--;
            flag = true;
        }
        return flag;
    }
    @Override
    public String toString(){
        String str="";
        for (int i = 0; i < size; i++) {
            str += (elements[i] ).toString();
            str+=" ";
        }
        return str;
    }

    public boolean contains(T element){
        boolean flag=false;
        for (int i = 0; i < size&&!flag; i++) {
            if(elements[i].equals(element)){
                flag=true;
            }
        }
        return flag;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                return elements[currentIndex++];
            }
        };
    }
}
