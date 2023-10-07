package model;

public class ArrayList<T> {

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
    public boolean remove(int i){
        boolean flag=false;
        if(i>=0 && i<size) {
            int j = i + 1;
            while (j < size) {
                elements[j-1] = elements[j ];
                j++;
            }
            elements[size] = null;
            size--;
            flag=true;
        }
        return flag;
    }


}
