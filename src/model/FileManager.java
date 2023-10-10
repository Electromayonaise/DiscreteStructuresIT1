package model;

import com.google.gson.Gson;


import java.io.*;
import java.util.Arrays;


public class FileManager {

    private static FileManager instance;

    private File dataFolder;

    private File doublyFile;

    private File maxheapFile;

    private FileManager(){
        // la ruta absoluta del proyecto
        File projectDir = new File(System.getProperty("user.dir"));
        dataFolder = new File(projectDir+"/data");
        doublyFile = new File(dataFolder+"/doubly.json");
        maxheapFile = new File(dataFolder+"/maxheap.json");

    }

    public static FileManager getInstance(){

        if(instance == null){
            instance = new FileManager();
        }
        return instance;
    }

    private void createResources() throws IOException {
        if(!dataFolder.exists()){
            dataFolder.mkdir();
        }

        if(!doublyFile.exists()){
            doublyFile.createNewFile();
        }
        if(!maxheapFile.exists()){
            maxheapFile.createNewFile();
        }
    }

    public Controller loadToJson() throws IOException {

        createResources();

        // Cargar la queue y el maxheap
            DoublyLinkedList<Task> loadedQueue = loadDoublyLinkedListFromJSON(Task[].class);
            MaxHeap<Task> loadedHeap = loadMaxHeapFromJSON();

            // Crea un nuevo Controller
            Controller loadedController = new Controller();

            // Agrega la DoublyLinkedList y la MaxHeap al nuevo Controller
            loadedController.setQueue(loadedQueue);
            loadedController.setPriorityQueue(loadedHeap);

            // Agrega los elementos de la DoublyLinkedList a la HashTable
            HashTable<String,Task> loadedTable = new HashTable<>();

            for (Task task : loadedQueue) {
                loadedTable.add(task.getTitle(), task);
            }

            // Agrega los elementos de la MaxHeap a la HashTable
            for (Task task : loadedHeap.getElements()) {
                loadedTable.add(task.getTitle(), task);
            }

            // Agrega la HashTable al nuevo Controller
        loadedController.setTable(loadedTable);

            return loadedController;
    }


    public void saveDoublyLinkedListToJSON(DoublyLinkedList<?> list) throws IOException {
        createResources();

        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(doublyFile)) {
            Object[] array = list.toArray();
            String data = gson.toJson(array);
            writer.write(data);
            writer.flush();
            writer.close();
        }
    }

    public <T> DoublyLinkedList<T> loadDoublyLinkedListFromJSON(Class<T[]> elementType) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(doublyFile)) {
            T[] array = gson.fromJson(reader, elementType);
            DoublyLinkedList<T> newList = new DoublyLinkedList<>();
            if(array != null) {
                for (T value : array) {
                    newList.addLast(value);
                }
            }
            return newList;
        }
    }


    public void saveMaxHeapToJSON(MaxHeap<?> maxHeap) throws IOException {
        createResources();

        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(maxheapFile)) {
            // Convertir cada elemento individualmente a JSON
            for (Object element : maxHeap.getElements()) {
                String data = gson.toJson(element);
                writer.write(data + "\n");
            }
        }
    }

    public MaxHeap<Task> loadMaxHeapFromJSON() throws IOException {
        Gson gson = new Gson();

        MaxHeap<Task> loadedHeap = new MaxHeap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(maxheapFile))) {
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                Task task = gson.fromJson(jsonLine, Task.class);
                loadedHeap.insert(task);
            }
        }

        return loadedHeap;
    }

}