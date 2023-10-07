package model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class Controller {
    private static final Controller instance=new Controller();
    private HashTable<String,Task> table;
    private Queue<Task> queue;

    private PriorityQueue<Task>priorityQueue;


    private Controller(){
        table=new HashTable<>();
        queue=new DoublyLinkedList<>();
        priorityQueue=new MaxHeap<>();

    }
    public static Controller getInstance(){
        return instance;
    }
    public boolean modifyTask(String title, int option,String newValue ){
        boolean flag=false;
        if(table.containsKey(title)){
            switch (option) {
                case 1:
                    flag=modifyTaskTitle(title,newValue);
                    break;
                case 2:
                    table.get(title).setDescription(newValue);
                    flag=true;
                    break;
                case 3:
                    flag=modifyTaskPriority(title,Integer.parseInt(  newValue));
                default:


            }
        }
        return flag;
    }
    private boolean modifyTaskPriority(String title, int newPriority){
        boolean flag=false;
        Task task= table.get(title);
        int prevPriority=task.getPriorityLevel();

        if(prevPriority==0 &&newPriority>0){
            removeFromQueue(title);
            task.setPriorityLevel(newPriority);
            priorityQueue.insert(task);
            flag=true;
        }else if(prevPriority>0 &&newPriority==0){
            removeFromHeap(title);
            task.setPriorityLevel(newPriority);
            queue.enqueue(task);
            flag=true;
        }else if(prevPriority>0&&newPriority>0&&prevPriority!=newPriority){
            ArrayList<Task> heapTasks=((MaxHeap<Task>) priorityQueue).getElements();
            for(int i=0;i< heapTasks.size();i++){
                if(heapTasks.get(i) == task){
                    flag= ((MaxHeap<Task>) priorityQueue).modifyKey(i,newPriority);
                    break;

                }
            }
        }else{

        }
        return flag;


    }

    private boolean modifyTaskTitle(String title,String newTitle){
        boolean flag=false;
        Task task=table.get(title);
        table.remove(title);
        task.setTitle(newTitle);
        if(!table.containsKey(newTitle)){
            table.add(task.getTitle(),task);
            flag=true;
        }
        return flag;

    }


    public boolean removeTask(String title){
        boolean flag=false;
        if(table.containsKey(title)){
            Task taskToRemove=table.get(title);

            if(taskToRemove.getPriorityLevel()>0){
               flag=removeFromHeap(title);
            }else{
                flag=removeFromQueue(title);
            }
            table.remove(title);
        }
        return flag;

    }

    public ArrayList<ArrayList<String>> getNonPrioritizedTasksAttributes(){
        ArrayList<ArrayList<String>> list=new ArrayList<>();
        for(Task task: (DoublyLinkedList<Task>) queue){
            list.add(task.getAttributes());
        }
        return list;
    }
    public ArrayList<ArrayList<String>> getPrioritizedTasksAttributes(){
        ArrayList<ArrayList<String>> list=new ArrayList<>();

        ArrayList<Task> heapList=((MaxHeap<Task>)priorityQueue).getElements();
        ArrayList<Task> copy=  copyTasks(heapList);
        copy=sortTasksByPriorityLevel(copy);

        for (int i = 0; i < copy.size(); i++) {
            list.add(copy.get(i).getAttributes());

        }

        return list;
    }

    public boolean addTask(String title,String description, int priorityLevel){
        boolean flag=false;


        //title was added before

        if(!table.containsKey(title)){
            Task newTask=new Task(title,description,priorityLevel);
            table.add(newTask.getTitle(), newTask);
            if(priorityLevel>0){
                priorityQueue.insert(newTask);
                saveChangesToPrioJson();
            }else{
                queue.enqueue(newTask);
                saveChangesToNonPrioJson();
            }
            flag=true;

        }
        return flag;

    }

    public void saveChangesToPrioJson() {
        ArrayList<ArrayList<String>> prioritizedTasks = getPrioritizedTasksAttributes();
        String filePath = "src/data/prioritizedTasks.json";

        // Check if the directory exists, create it if not
        File directory = new File("src/data");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            File file = new File(filePath);

            // Check if the file exists, create it if not
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("[");
            for (int i = 0; i < prioritizedTasks.size(); i++) {
                bufferedWriter.write("[");
                for (int j = 0; j < prioritizedTasks.get(i).size(); j++) {
                    bufferedWriter.write("\"" + prioritizedTasks.get(i).get(j) + "\"");
                    if (j < prioritizedTasks.get(i).size() - 1) {
                        bufferedWriter.write(",");
                    }
                }
                bufferedWriter.write("]");
                if (i < prioritizedTasks.size() - 1) {
                    bufferedWriter.write(",");
                }
            }
            bufferedWriter.write("]");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveChangesToNonPrioJson() {
        ArrayList<ArrayList<String>> nonPrioritizedTasks = getNonPrioritizedTasksAttributes();
        String filePath = "src/data/nonPrioritizedTasks.json";

        // Check if the directory exists, create it if not
        File directory = new File("src/data");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            File file = new File(filePath);

            // Check if the file exists, create it if not
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("[");
            for (int i = 0; i < nonPrioritizedTasks.size(); i++) {
                bufferedWriter.write("[");
                for (int j = 0; j < nonPrioritizedTasks.get(i).size(); j++) {
                    bufferedWriter.write("\"" + nonPrioritizedTasks.get(i).get(j) + "\"");
                    if (j < nonPrioritizedTasks.get(i).size() - 1) {
                        bufferedWriter.write(",");
                    }
                }
                bufferedWriter.write("]");
                if (i < nonPrioritizedTasks.size() - 1) {
                    bufferedWriter.write(",");
                }
            }
            bufferedWriter.write("]");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




   private ArrayList<Task> sortTasksByPriorityLevel(ArrayList<Task> tasks){
        ArrayList<Task>sortedTasks=new ArrayList<>();
        MaxHeap<Task> heap=new MaxHeap<>(tasks);
        int n= heap.size();
       for (int i = n-1; i >=0; i--) {
           sortedTasks.add(heap.extractMax());
       }
       return sortedTasks;

   }
   private <T> ArrayList<T> copyTasks(ArrayList<T> list){
        ArrayList<T> copy=new ArrayList<>();
       for (int i = 0; i < list.size(); i++) {
           copy.add(list.get(i));
       }
       return copy;
   }

   private boolean removeFromHeap(String title){
       ArrayList<Task> heapTasks=((MaxHeap<Task>) priorityQueue).getElements();
       boolean flag=false;
       for (int i = 0; i < heapTasks.size()&&!flag; i++) {
           String taskTitle=heapTasks.get(i).getTitle();
           if(taskTitle.equals(title)){
               flag=((MaxHeap<Task>)priorityQueue).remove(i);
           }
       }
       return flag;
   }

   private boolean removeFromQueue(String title){
       BiPredicate<Task,String> equals= (t,u)->t.getTitle().equals(u);
       return ((DoublyLinkedList<Task>) queue).removeFirstInstance(title,equals);

   }






}
