package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public boolean modifyTaskPriority(String title, int newPriority){
        boolean flag=false;
        Task task= table.get(title);
        int prevPriority=task.getPriorityLevel();

        if(prevPriority==0 &&newPriority>0){
            removeFromQueue(title);
            task.setPriorityLevel(newPriority);
            addTask(task);
            flag=true;
        }else if(prevPriority>0 &&newPriority==0){
            removeFromHeap(title);
            task.setPriorityLevel(newPriority);
            addTask(task);
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

    public boolean modifyTaskTitle(String title,String newTitle){
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
    public void modifyTaskDescription(String title){

    }
    public void modifyTask(String title,int option,int newValue){

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
            }else{
                queue.enqueue(newTask);
            }
            flag=true;

        }
        return flag;

    }
    public boolean addTask(Task newTask){
        boolean flag=false;
        /* title was added before */
        if(!table.containsKey(newTask.getTitle())){
            table.add(newTask.getTitle(), newTask);
            if(newTask.getPriorityLevel()>0){
                priorityQueue.insert(newTask);
            }else{
                queue.enqueue(newTask);
            }
            flag=true;

        }
        return flag;

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
