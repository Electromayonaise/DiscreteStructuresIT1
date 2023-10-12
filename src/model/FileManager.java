package model;

import com.google.gson.Gson;

import java.io.*;

/**
 * Manages the saving and loading of data to and from JSON files.
 */
public class FileManager {

    private static FileManager instance;

    private File dataFolder;

    private File doublyFile;

    private File maxheapFile;

    /**
     * Private constructor to ensure singleton pattern and initialize file paths.
     */
    private FileManager() {
        // Get the absolute path of the project
        File projectDir = new File(System.getProperty("user.dir"));
        dataFolder = new File(projectDir + "/data");
        doublyFile = new File(dataFolder + "/doubly.json");
        maxheapFile = new File(dataFolder + "/maxheap.json");
    }

    /**
     * Gets the singleton instance of the FileManager.
     *
     * @return The FileManager instance.
     */
    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    /**
     * Creates necessary resources (folders and files) if they do not exist.
     *
     * @throws IOException If an I/O error occurs.
     */
    private void createResources() throws IOException {
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        if (!doublyFile.exists()) {
            doublyFile.createNewFile();
        }
        if (!maxheapFile.exists()) {
            maxheapFile.createNewFile();
        }
    }

    /**
     * Loads the data from JSON files and constructs a new Controller instance.
     *
     * @return The loaded Controller instance.
     * @throws IOException If an I/O error occurs.
     */
    public Controller loadToJson() throws IOException {
        createResources();

        // Load the queue and the maxheap
        DoublyLinkedList<Task> loadedQueue = loadDoublyLinkedListFromJSON(Task[].class);
        MaxHeap<Task> loadedHeap = loadMaxHeapFromJSON();

        // Create a new Controller
        Controller loadedController = new Controller();

        // Add the DoublyLinkedList and the MaxHeap to the new Controller
        loadedController.setQueue(loadedQueue);
        loadedController.setPriorityQueue(loadedHeap);

        // Add the elements of the DoublyLinkedList to the HashTable
        HashTable<String, Task> loadedTable = new HashTable<>();

        for (Task task : loadedQueue) {
            loadedTable.add(task.getTitle(), task);
        }

        // Add the elements of the MaxHeap to the HashTable
        for (Task task : loadedHeap.getElements()) {
            loadedTable.add(task.getTitle(), task);
        }

        // Add the HashTable to the new Controller
        loadedController.setTable(loadedTable);

        return loadedController;
    }

    /**
     * Saves a DoublyLinkedList to a JSON file.
     *
     * @param list The DoublyLinkedList to be saved.
     * @throws IOException If an I/O error occurs.
     */
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

    /**
     * Loads a DoublyLinkedList from a JSON file.
     *
     * @param elementType The class type of the elements in the DoublyLinkedList.
     * @param <T>         The type of the elements in the DoublyLinkedList.
     * @return The loaded DoublyLinkedList.
     * @throws IOException If an I/O error occurs.
     */
    public <T> DoublyLinkedList<T> loadDoublyLinkedListFromJSON(Class<T[]> elementType) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(doublyFile)) {
            T[] array = gson.fromJson(reader, elementType);
            DoublyLinkedList<T> newList = new DoublyLinkedList<>();
            if (array != null) {
                for (T value : array) {
                    newList.addLast(value);
                }
            }
            return newList;
        }
    }

    /**
     * Saves a MaxHeap to a JSON file.
     *
     * @param maxHeap The MaxHeap to be saved.
     * @throws IOException If an I/O error occurs.
     */
    public void saveMaxHeapToJSON(MaxHeap<?> maxHeap) throws IOException {
        createResources();

        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter(maxheapFile)) {
            // Convert each element individually to JSON
            for (Object element : maxHeap.getElements()) {
                String data = gson.toJson(element);
                writer.write(data + "\n");
            }
        }
    }

    /**
     * Loads a MaxHeap from a JSON file.
     *
     * @return The loaded MaxHeap.
     * @throws IOException If an I/O error occurs.
     */
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
