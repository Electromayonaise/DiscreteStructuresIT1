package ui;

import javax.swing.*;
import java.awt.*;

import model.ArrayList;
import model.Controller;

/**
 * The TasksPanel class represents a panel for managing tasks in the Task Manager application.
 * It provides features for displaying, adding, modifying, and deleting tasks.
 */
public class TasksPanel extends BasePanel {
    private final Controller controller;

    private final Color myRed = new Color(255, 175, 175);
    private final Color TASKCOLOR = new Color(90, 90, 90);

    private JPanel displayPanel;

    private final CardLayout cardLayout = new CardLayout();
    private DefaultListModel<String> taskListModel;
    private DefaultListModel<String> taskNameListModel; // List to store task names
    private boolean displayByPriority = true; // Track the current display mode

    /**
     * Constructs a TasksPanel with a reference to the container panel.
     *
     * @param containerPanel The container panel that holds this TasksPanel.
     */
    public TasksPanel(JPanel containerPanel) {
        super(containerPanel);
        controller = Controller.getInstance();
        initUI();
    }

    /**
     * Initializes the UI components of the TasksPanel.
     */
    private void initUI() {
        setLayout(new BorderLayout()); // Use BorderLayout for better element placement
        setOpaque(false); // Make the panel transparent

        // Create a title label with red color and centered
        JLabel titleLabel = new JLabel("Tasks Panel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(myRed);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH); // Place the title at the top

        // Your content here
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout()); // Use BorderLayout to display tasks as a list
        contentPanel.setOpaque(false); // Make the content panel transparent

        // Create a panel for the left column
        JPanel leftColumnPanel = new JPanel(new BorderLayout());
        leftColumnPanel.setOpaque(false);

        // Create a button to switch display type
        JButton toggleDisplayButton = createStyledButton("Change visualization", myRed);
        toggleDisplayButton.addActionListener(e -> {
            displayByPriority = !displayByPriority; // Toggle the display mode
            refreshDisplay(); // Update the displayed tasks
        });

        // Add the "Toggle Display" button to the left column panel
        leftColumnPanel.add(toggleDisplayButton, BorderLayout.NORTH); // Add the button to the left column

        // Add the leftColumnPanel to the main panel
        add(leftColumnPanel, BorderLayout.WEST);

        // Create a panel for displaying tasks (by priority or arrival order)
        displayPanel = new JPanel(cardLayout);
        displayPanel.setOpaque(false);

        // Create a panel for displaying tasks by priority
        JPanel priorityPanel = new JPanel(new BorderLayout());
        JLabel priorityLabel = new JLabel("Priority");
        priorityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priorityLabel.setForeground(myRed);
        priorityPanel.add(priorityLabel, BorderLayout.NORTH);
        taskListModel = new DefaultListModel<>();
        taskNameListModel = new DefaultListModel<>(); // Initialize task name list model
        updateTaskListModels(); // Update the list models with task data
        JList<String> priorityList = new JList<>(taskNameListModel);
        priorityList.setFont(new Font("Arial", Font.PLAIN, 16));
        displayPanel.add(priorityPanel, "Priority");

        // Create a panel for displaying tasks by arrival order
        JPanel arrivalPanel = new JPanel(new BorderLayout());
        JLabel arrivalLabel = new JLabel("Arrival Order");
        arrivalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        arrivalLabel.setForeground(myRed);
        arrivalPanel.add(arrivalLabel, BorderLayout.NORTH);
        JList<String> arrivalList = new JList<>(taskNameListModel); // Use the same task name list model
        arrivalList.setFont(new Font("Arial", Font.PLAIN, 16));
        displayPanel.add(arrivalPanel, "Arrival");

        // Add the displayPanel to the left column
        leftColumnPanel.add(displayPanel, BorderLayout.CENTER);

        // Create a panel for the right column (buttons)
        JPanel rightColumnPanel = new JPanel(new GridLayout(0, 2));
        rightColumnPanel.setOpaque(false);

        JList<String> taskList = new JList<>(taskNameListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 16));
        taskList.setForeground(TASKCOLOR); // Set text color
        JScrollPane scrollPane = new JScrollPane(taskList);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add a selection listener to handle item click events
        taskList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Handle the task selection, e.g., show further info
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedTaskName = taskNameListModel.getElementAt(selectedIndex);
                    showTaskInfo(selectedTaskName);
                }
            }
        });

        add(contentPanel, BorderLayout.CENTER); // Place the content in the center

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2)); // 2 columns for modify and delete buttons
        buttonPanel.setOpaque(false);

        // Modify button
        JButton modifyButton = createStyledButton("Modify Task", myRed);
        modifyButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedTaskName = taskNameListModel.getElementAt(selectedIndex);
                String[] options = {"Modify Name", "Modify Priority", "Modify Description"};
                int choice = JOptionPane.showOptionDialog(TasksPanel.this, "What would you like to modify?", "Modify Task", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if (choice == 0) {
                    // Modify Name
                    String modifiedName = JOptionPane.showInputDialog("Modify Task Name", selectedTaskName);
                    if (modifiedName != null && !modifiedName.isEmpty()) {
                        // Update the task name in the controller
                        controller.modifyTask(selectedTaskName, 1, modifiedName);
                        // Update the task name in the taskNameListModel
                        taskNameListModel.setElementAt(modifiedName, selectedIndex);
                    }
                } else if (choice == 1) {
                    // Modify Priority
                    JSlider slider = getjSlider();
                    JOptionPane.showMessageDialog(TasksPanel.this, slider, "Select new Task Priority", JOptionPane.QUESTION_MESSAGE);
                    String modifiedPriority = String.valueOf(slider.getValue());
                    // Update the task priority in the controller
                    controller.modifyTask(selectedTaskName, 3, modifiedPriority);
                    // No need to update the task name in the taskNameListModel for priority modification
                } else if (choice == 2) {
                    // Modify Description
                    String modifiedDescription = JOptionPane.showInputDialog("Modify Task Description", selectedTaskName);
                    if (modifiedDescription != null) {
                        // Update the task description in the controller
                        controller.modifyTask(selectedTaskName, 2, modifiedDescription);
                        // No need to update the task name in the taskNameListModel for description modification
                    }
                }
                JOptionPane.showMessageDialog(TasksPanel.this, "Task modified successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Refresh the display panel to update the displayed tasks
                refreshDisplay();
            } else {
                JOptionPane.showMessageDialog(TasksPanel.this, "Please select a task first.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete button
        JButton deleteButton = createStyledButton("Delete Task", myRed);
        deleteButton.addActionListener(e -> {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedTaskName = taskNameListModel.getElementAt(selectedIndex);
                // Remove the task from the list models
                taskNameListModel.removeElementAt(selectedIndex);
                taskListModel.removeElementAt(selectedIndex);
                // Remove the task from the controller
                controller.removeTask(selectedTaskName);
                JOptionPane.showMessageDialog(TasksPanel.this, "Task deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(TasksPanel.this, "Please select a task first.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        // Create a panel for the "Add Task" button
        JPanel addTaskPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addTaskPanel.setOpaque(false);

        // Add Task button
        JButton addButton = createStyledButton("Add Task", myRed);
        addButton.addActionListener(e -> {
            String taskName = JOptionPane.showInputDialog("Enter Task Name");
            if (taskName != null && !taskName.isEmpty()) {
                JSlider slider = getjSlider();
                JOptionPane.showMessageDialog(TasksPanel.this, slider, "Select Task Priority", JOptionPane.QUESTION_MESSAGE);
                int taskPriority = slider.getValue();
                String taskDescription = JOptionPane.showInputDialog("Enter Task Description");
                if (taskDescription != null) {
                    boolean success = controller.addTask(taskName, taskDescription, taskPriority);
                    if (success) {
                        refreshDisplay();
                        JOptionPane.showMessageDialog(TasksPanel.this, "Task added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(TasksPanel.this, "Task with the same name already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        addTaskPanel.add(addButton);

        // Create a panel for the "Return to Menu" button
        JPanel returnButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        returnButtonPanel.setOpaque(false);

        // Return to Menu button
        JButton returnButton = getReturnButton();

        returnButtonPanel.add(returnButton);

        // Create a container panel for all buttons
        JPanel buttonsContainer = new JPanel(new BorderLayout());
        buttonsContainer.setOpaque(false);

        // Add the button panels to the wrapper panel
        buttonsContainer.add(buttonPanel, BorderLayout.NORTH);
        buttonsContainer.add(addTaskPanel, BorderLayout.CENTER);
        buttonsContainer.add(returnButtonPanel, BorderLayout.SOUTH);

        // Add the buttons container to the south
        add(buttonsContainer, BorderLayout.SOUTH);

        // Button to undo the last action
        JButton undoButton = createStyledButton("Undo", myRed);
        undoButton.addActionListener(e -> {

            boolean undo = controller.undo();
            if(undo){
                refreshDisplay();
                JOptionPane.showMessageDialog(TasksPanel.this, "Undo successful.", "Success", JOptionPane.INFORMATION_MESSAGE);

            }else{
                JOptionPane.showMessageDialog(TasksPanel.this, "No undo possible", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add the undo button to the left column panel
        leftColumnPanel.add(undoButton, BorderLayout.SOUTH);
    }

    /**
     * Creates and returns a styled JSlider for selecting task priorities.
     *
     * @return A JSlider with specific styling.
     */
    private JSlider getjSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 3, 1);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setOpaque(false);
        slider.setForeground(myRed);
        slider.setBackground(Color.WHITE);
        slider.setPreferredSize(new Dimension(300, 100));
        slider.setFont(new Font("Arial", Font.BOLD, 16));
        slider.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        return slider;
    }

    /**
     * Updates the task list models based on the current display mode.
     */
    private void updateTaskListModels() {
        taskNameListModel.clear();
        taskListModel.clear();

        // Get the tasks from the controller based on the current display mode
        ArrayList<ArrayList<String>> tasks;
        if (displayByPriority) {
            tasks = controller.getPrioritizedTasksAttributes();

        } else {
            tasks = controller.getNonPrioritizedTasksAttributes();
        }

        for (ArrayList<String> task : tasks) {
            String taskName = task.get(0);
            taskNameListModel.addElement(taskName); // Only add task name to the list

            String taskDetails = task.get(1) + " Priority: " + task.get(2);
            taskListModel.addElement(taskDetails);
        }

    }


    /**
     * Fetches tasks based on the specified display mode (priority or arrival order).
     *
     * @param byPriority True if tasks should be fetched by priority, false for arrival order.
     * @return A list of tasks with their attributes.
     */
    private ArrayList<ArrayList<String>> fetchTasks(boolean byPriority) {
        if (byPriority) {
            return controller.getPrioritizedTasksAttributes();
        } else {
            return controller.getNonPrioritizedTasksAttributes();
        }
    }

    /**
     * Refreshes the display panel to update the displayed tasks.
     */
    private void refreshDisplay() {
        cardLayout.show(displayPanel, displayByPriority ? "Priority" : "Arrival");
        updateTaskListModels();
    }

    /**
     * Shows detailed information about a selected task.
     *
     * @param selectedTaskName The name of the selected task.
     */
    private void showTaskInfo(String selectedTaskName) {
        ArrayList<ArrayList<String>> tasks = fetchTasks(displayByPriority);
        for (ArrayList<String> task : tasks) {
            if (task.get(0).equals(selectedTaskName)) {
                String taskDetails = "Task Name: " + task.get(0) + "\n"
                        + "Task Description: " + task.get(1) + "\n"
                        + "Task Priority: " + task.get(2);
                JOptionPane.showMessageDialog(this, taskDetails, "Task Info", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }

}