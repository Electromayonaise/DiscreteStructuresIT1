package model;



/**
 * Represents a task with a title, description, and priority level.
 */
public class Task implements HeapKeyProvider, Cloneable {

    private String title;
    private String description;
    private int priorityLevel;

    /**
     * Constructs a Task with the specified title, description, and priority level.
     *
     * @param title         The title of the task.
     * @param description   The description of the task.
     * @param priorityLevel The priority level of the task.
     */
    public Task(String title, String description, int priorityLevel) {
        this.title = title;
        this.description = description;
        this.priorityLevel = priorityLevel;
    }

    /**
     * Gets the title of the task.
     *
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the task.
     *
     * @param title The new title of the task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description The new description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the priority level of the task.
     *
     * @return The priority level of the task.
     */
    public int getPriorityLevel() {
        return priorityLevel;
    }

    /**
     * Sets the priority level of the task.
     *
     * @param priorityLevel The new priority level of the task.
     */
    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    /**
     * Gets the attributes of the task as a list (title, description, priority level).
     *
     * @return The attributes of the task.
     */
    public ArrayList<String> getAttributes() {
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(title);
        attributes.add(description);
        attributes.add(String.valueOf(priorityLevel));
        return attributes;
    }

    /**
     * Gets the priority level of the task for heap operations.
     *
     * @return The priority level of the task.
     */
    @Override
    public int getHeapKey() {
        return priorityLevel;
    }

    /**
     * Sets the priority level of the task for heap operations.
     *
     * @param newKey The new priority level.
     */
    @Override
    public void setHeapKey(int newKey) {
        setPriorityLevel(newKey);
    }

    /**
     * Generates a hash value based on the title of the task.
     *
     * @return The hash value.
     */
    public String hash() {
        long key = getTitle().hashCode();
        return Long.toString(key);
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priorityLevel=" + priorityLevel +
                '}';
    }
}
