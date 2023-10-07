package model;

import java.util.Arrays;

public class Task implements HeapKeyProvider{
    private String title;
    private String description;
    private int priorityLevel;

    public Task(String title, String description, int priorityLevel) {
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }
     
    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }
    @Override
    public String toString()   {
        return "title:"+title+ " description:"+description+" priorityLevel: "+priorityLevel;
    }


    public String[] getAttributes(){
        String[] attributes= new String[3];
        attributes[0]=title;
        attributes[1]=description;
        attributes[2]=priorityLevel+"";
        return Arrays.copyOf(attributes,attributes.length);
    }

    @Override
    public int getHeapKey() {
        return priorityLevel;
    }

    @Override
    public void setHeapKey(int newKey) {
        setPriorityLevel(newKey);
    }

    public String hash(){
        long key = getTitle().hashCode();
        return Long.toString(key);
    }


}
