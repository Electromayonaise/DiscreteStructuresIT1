package model;

public class Pair <T,U >{
    private T first;
    private U second;

    Pair(T key, U second){
        this.first =key;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public U getSecond() {
        return second;
    }

    public void setSecond(U second) {
        this.second = second;
    }
}
