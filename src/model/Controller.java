package model;


import java.io.IOException;

/**
 * The Controller class manages tasks in the Task Manager application.
 * It handles task modification, removal, addition, and provides access to task attributes.
 */
public class Controller {
    // Singleton
    private transient static  Controller instance;
    private HashTable<String,Task> table;
    private DoublyLinkedList<Task> queue;

    private MaxHeap<Task>priorityQueue;

    private transient static final FileManager fileManager=FileManager.getInstance();

    private DoublyLinkedList<DoublyLinkedList<Task>> doublyStack;

    private DoublyLinkedList<MaxHeap<Task>> maxHeapStack;

    /**
     * Private constructor to enforce singleton pattern and initialize data structures.
     */
    protected Controller(){
        table=new HashTable<>();
        queue=new DoublyLinkedList<>();
        priorityQueue=new MaxHeap<>();

        doublyStack = new DoublyLinkedList<>();
        maxHeapStack = new DoublyLinkedList<>();

    }

    /**
     * Returns the singleton instance of the Controller class.
     *
     * @return The Controller instance.
     */
    public static Controller getInstance(){
        try{
            instance=fileManager.loadToJson();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(instance==null){
            instance=new Controller();
        }
        return instance;
    }

    /**
     * Undoes the last action by restoring the previous state of the controller from the stacks.
     *
     * @return True if the undo operation is successful, false if there's nothing to undo.
     */
    public boolean undo(){
        boolean stackThings = false;
        if(doublyStack.size() != 0){
            stackThings = true;
            loadFromStacks();
            saveData();
        }
        return stackThings;
    }

    /**
     * Saves the current state of the controller to the undo stacks.
     */
    public void saveToStacks(){

        // Cloning the Queue
        DoublyLinkedList<Task> QueueToClone = getQueue();
        DoublyLinkedList<Task> queue = new DoublyLinkedList<>();
        for(Task task : QueueToClone){
            Task newTask = new Task(task.getTitle(), task.getDescription(), task.getPriorityLevel());
            queue.addLast(newTask);
        }

        // Cloning the Heap
        ArrayList<Task> maxHeapToClone = getPriorityQueue().getElements();
        MaxHeap<Task> heap =  new MaxHeap<>();
        for(Task task : maxHeapToClone){
            Task newTask = new Task(task.getTitle(), task.getDescription(), task.getPriorityLevel());
            heap.insert(newTask);
        }

        doublyStack.push(queue);

        maxHeapStack.push(heap);

    }

    /**
     * Loads the previous state of the controller from the undo stacks.
     */
    public void loadFromStacks(){
        DoublyLinkedList<Task> loadedQueue = doublyStack.pop();
        MaxHeap<Task> loadedHeap =  maxHeapStack.pop();
        setQueue(loadedQueue);
        setPriorityQueue(loadedHeap);

        // Add the elements of the DoublyLinkedList to the HasTable
        HashTable<String,Task> loadedTable = new HashTable<>();

        for (Task task : loadedQueue) {
            loadedTable.add(task.getTitle(), task);
        }

        // Add the elements of the MaxHeap to the HasTable
        for (Task task : loadedHeap.getElements()) {
            loadedTable.add(task.getTitle(), task);
        }

        // Add the HashTable to the new Controller
        setTable(loadedTable);
    }

    /**
     * Modifies a task based on the provided options.
     *
     * @param title    The title of the task to modify.
     * @param option   The modification option (1 for title, 2 for description, 3 for priority).
     * @param newValue The new value for the modification.
     * @return True if the modification is successful, false otherwise.
     */
    public boolean modifyTask(String title, int option,String newValue ){
        boolean flag=false;
        if(table.containsKey(title)){
            saveToStacks();
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
                    break;
                default:
            }

        }
        saveData();
        return flag;
    }


    /**
     * Modifies the priority of a task.
     *
     * @param title       The title of the task to modify.
     * @param newPriority The new priority level to set.
     * @return True if the modification is successful, false otherwise.
     */
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
                    flag= priorityQueue.modifyKey(i,newPriority);
                    break;

                }
            }
        }



        return flag;
    }

    /**
     * Modifies the title of a task.
     *
     * @param title    The current title of the task.
     * @param newTitle The new title to set.
     * @return True if the modification is successful, false otherwise.
     */
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

    /**
     * Removes a task with the given title.
     *
     * @param title The title of the task to remove.
     * @return True if the task is removed successfully, false otherwise.
     */
    public boolean removeTask(String title){

        boolean flag=false;
        if(table.containsKey(title)){
            saveToStacks();
            Task taskToRemove=table.get(title);

            if(taskToRemove.getPriorityLevel()>0){
                flag=removeFromHeap(title);
            }else{
                flag=removeFromQueue(title);
            }
            table.remove(title);
        }

        saveData();

        return flag;

    }

    /**
     * Retrieves attributes of non-prioritized tasks.
     *
     * @return List of attributes for non-prioritized tasks.
     */
    public ArrayList<ArrayList<String>> getNonPrioritizedTasksAttributes(){

        ArrayList<ArrayList<String>> list=new ArrayList<>();
        for(Task task: (DoublyLinkedList<Task>) queue){
            list.add(task.getAttributes());
        }
        return list;
    }

    /**
     * Retrieves attributes of prioritized tasks.
     *
     * @return List of attributes for prioritized tasks.
     */
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

    /**
     * Saves data to JSON files using the FileManager.
     */
    public void saveData(){
        try {
            fileManager.saveMaxHeapToJSON(priorityQueue);
        } catch (IOException e) {
            System.out.println("Error al guardar");
            throw new RuntimeException(e);
        }

        try {
            fileManager.saveDoublyLinkedListToJSON(queue);
        } catch (IOException e) {
            System.out.println("Error al guardar");
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a new task with the given title, description, and priority level.
     *
     * @param title          The title of the task.
     * @param description    The description of the task.
     * @param priorityLevel  The priority level of the task.
     * @return True if the task is added successfully, false otherwise.
     */
    public boolean addTask(String title,String description, int priorityLevel){

        boolean flag=false;

        if(!table.containsKey(title)){
            saveToStacks();
            Task newTask=new Task(title,description,priorityLevel);
            table.add(newTask.getTitle(), newTask);
            if(priorityLevel>0){
                priorityQueue.insert(newTask);
            }else{
                queue.enqueue(newTask);
            }
	    saveData();


            flag=true;

        }
        return flag;

    }


    /**
     * Sorts tasks by priority level in descending order.
     *
     * @param tasks The list of tasks to be sorted.
     * @return A sorted list of tasks by priority level.
     */
    private ArrayList<Task> sortTasksByPriorityLevel(ArrayList<Task> tasks){
        ArrayList<Task>sortedTasks=new ArrayList<>();
        MaxHeap<Task> heap;
        heap = new MaxHeap<>(tasks);
        int n= heap.size();
        for (int i = n-1; i >=0; i--) {
            sortedTasks.add(heap.extractMax());
        }
        return sortedTasks;

    }

    /**
     * Copies a list of tasks.
     *
     * @param list The list of tasks to be copied.
     * @return A new list containing copied tasks.
     */
    private <T> ArrayList<T> copyTasks(ArrayList<T> list){
        ArrayList<T> copy=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            copy.add(list.get(i));
        }
        return copy;
    }

    /**
     * Removes a task with the given title from the heap.
     *
     * @param title The title of the task to be removed.
     * @return True if the task is removed from the heap successfully, false otherwise.
     */
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

    /**
     * Removes a task with the given title from the queue.
     *
     * @param title The title of the task to be removed.
     * @return True if the task is removed from the queue successfully, false otherwise.
     */
    private boolean removeFromQueue(String title){
        BiPredicate<Task,String> equals= (t,u)->t.getTitle().equals(u);
        return ((DoublyLinkedList<Task>) queue).removeFirstInstance(title,equals);

    }

    // Getters and Setters
    public void setTable(HashTable<String, Task> table) {
        this.table = table;
    }


    public void setQueue(DoublyLinkedList<Task> queue) {
        this.queue = queue;
    }


    public void setPriorityQueue(MaxHeap<Task> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    public DoublyLinkedList<Task> getQueue() {
        return queue;
    }

    public MaxHeap<Task> getPriorityQueue() {
        return priorityQueue;
    }
}
