package model;



public class HashTable<T,U > {
    private static final int NUM_SLOTS=1069;
    private int size;
    private DoublyLinkedList<Pair<T,U>>[] arr;
    public HashTable(){
        arr=( DoublyLinkedList<Pair<T,U>> []) new DoublyLinkedList[NUM_SLOTS];
        size=0;
    }
    private int getSlot(T key){
        long hashCode=key.hashCode();
        if(hashCode<0){
            hashCode=-hashCode;
        }
        return (int) hashCode % NUM_SLOTS;
    }
    private DoublyLinkedList<Pair<T,U>> getLinkedListOnSlot(T key){
        int index=getSlot(key);
        if(arr[index]==null){
            arr[index]=new DoublyLinkedList<>();
        }
        return arr[index];

    }
    public void add(T key, U value){
        DoublyLinkedList<Pair<T,U>> list=getLinkedListOnSlot(key);


        //The biPredicate compares the key of any
        // pair with the key's hash code that gas given as parameter.
        BiPredicate<Pair<T,U>,T> equals=(t,u)-> ((t.getFirst()).hashCode() )==(u.hashCode());


        //If one element of the list's pair has the same hashCode that my key, I remove it
        boolean flag=list.removeFirstInstance(key,equals);
        if(!flag){
            size++;
        }

        //then I create the pair
        Pair<T,U> newPair=new Pair<>(key,value);
        //and I add it to my list.
        list.addLast(newPair);
    }
    public boolean remove(T key){
        int slot=getSlot(key);
        boolean flag=false;
        BiPredicate<Pair<T,U>,T> equals=(t,u)-> ((t.getFirst()).hashCode() )==(u.hashCode());
        if(arr[slot]!=null&& arr[slot].removeFirstInstance(key,equals)){
            flag=true;
            size--;
            if(arr[slot].empty()){
                arr[slot]=null;
            }
        }



        return flag;

    }

    public U get(T key){
        U value=null;
        int slot=getSlot(key);
        BiPredicate<Pair<T,U>,T> equals=(t,u)-> ((t.getFirst()).hashCode() )==(u.hashCode());
        if(arr[slot]!=null ){
            Pair<T,U> pair = arr[slot].getFirstInstance(key,equals);
            value=pair!=null? pair.getSecond() : null;
        }
        return value;
    }
    public boolean containsKey(T key){
        return get(key)!=null;
    }

    public int size(){
        return size;
    }



}
