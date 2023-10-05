package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TasksPanel extends BasePanel {

    private final Color myRed = new Color(255, 175, 175);
    private final Color TASKCOLOR = new Color(90, 90, 90);

    private JPanel displayPanel; // Panel for displaying tasks (by priority or arrival order)

    private CardLayout cardLayout = new CardLayout();
    private DefaultListModel<String> taskListModel;
    private List<String> tasks; // List to store the displayed tasks
    private boolean displayByPriority = true; // Track the current display mode

    public TasksPanel(JPanel containerPanel) {
        super(containerPanel);
        tasks = fetchTasks(true); // Default to display tasks by priority
        initUI();
    }

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
        JButton toggleDisplayButton = createStyledButton("Change visualization", myRed, Color.WHITE);
        toggleDisplayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayByPriority = !displayByPriority; // Toggle the display mode
                tasks = fetchTasks(displayByPriority); // Fetch tasks based on the new display mode
                refreshDisplay(); // Update the displayed tasks
            }
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
        for (String task : tasks) {
            taskListModel.addElement(task);
        }
        JList<String> priorityList = new JList<>(taskListModel);
        priorityList.setFont(new Font("Arial", Font.PLAIN, 16));
        priorityList.setFont(new Font("Arial", Font.PLAIN, 16));
        displayPanel.add(priorityPanel, "Priority");

        // Create a panel for displaying tasks by arrival order
        JPanel arrivalPanel = new JPanel(new BorderLayout());
        JLabel arrivalLabel = new JLabel("Arrival Order");
        arrivalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        arrivalLabel.setForeground(myRed);
        arrivalPanel.add(arrivalLabel, BorderLayout.NORTH);
        DefaultListModel<String> arrivalListModel = new DefaultListModel<>();
        for (String task : tasks) {
            arrivalListModel.addElement(task);
        }
        JList<String> arrivalList = new JList<>(arrivalListModel);
        arrivalList.setFont(new Font("Arial", Font.PLAIN, 16));
        displayPanel.add(arrivalPanel, "Arrival");

        // Add the displayPanel to the left column
        leftColumnPanel.add(displayPanel, BorderLayout.CENTER);

        // Create a panel for the right column (buttons)
        JPanel rightColumnPanel = new JPanel(new GridLayout(0, 2));
        rightColumnPanel.setOpaque(false);

        JList<String> taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 16));
        taskList.setForeground(TASKCOLOR); // Set text color
        JScrollPane scrollPane = new JScrollPane(taskList);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Add a selection listener to handle item click events
        taskList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Handle the task selection, e.g., show further info
                String selectedTask = taskList.getSelectedValue();
                // make it so that the user can't select a task that has been deleted
                if (selectedTask == null) {
                    return;
                }
                showTaskInfo(selectedTask);
            }
        });

        add(contentPanel, BorderLayout.CENTER); // Place the content in the center

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2)); // 2 columns for modify and delete buttons
        buttonPanel.setOpaque(false);

        // Modify button
        JButton modifyButton = createStyledButton("Modify Task", myRed, Color.WHITE);
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedTask = taskListModel.getElementAt(selectedIndex);
                    String[] options = {"Modify Name", "Modify Priority"};
                    int choice = JOptionPane.showOptionDialog(TasksPanel.this, "What would you like to modify?", "Modify Task", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                    if (choice == 0) {
                        // Modify Name
                        String modifiedName = JOptionPane.showInputDialog("Modify Task Name", selectedTask.split(":")[1].trim());
                        if (modifiedName != null) {
                            taskListModel.setElementAt("Task " + (selectedIndex + 1) + ": " + modifiedName, selectedIndex);
                        }
                    } else if (choice == 1) {
                        // Modify Priority
                        String modifiedPriority = JOptionPane.showInputDialog("Modify Task Priority", selectedTask.split(":")[0].trim());
                        if (modifiedPriority != null) {
                            taskListModel.setElementAt(modifiedPriority + ": " + selectedTask.split(":")[1].trim(), selectedIndex);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(TasksPanel.this, "Please select a task first.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete button
        JButton deleteButton = createStyledButton("Delete Task", myRed, Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex);
                    // Remove the selection of the user deleted task, so that a null message from the selection listener is not triggered
                    taskList.clearSelection();
                    // send a message of the successful deletion
                    JOptionPane.showMessageDialog(TasksPanel.this, "Task deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(TasksPanel.this, "Please select a task first.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add buttons to the button panel
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        // Create a panel for the "Add Task" button
        JPanel addTaskPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addTaskPanel.setOpaque(false);

        // Add Task button
        JButton addButton = createStyledButton("Add Task", myRed, Color.WHITE);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = JOptionPane.showInputDialog("Enter Task Name");
                if (taskName != null) {
                    String taskPriority = JOptionPane.showInputDialog("Enter Task Priority");
                    if (taskPriority != null) {
                        String newTask = taskPriority + ": " + taskName;
                        taskListModel.addElement(newTask);
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
    }

    // Fetch tasks based on user choice (priority or arrival order)
    private List<String> fetchTasks(boolean byPriority) {
        if (byPriority) {
            // Replace this with your code to fetch tasks by priority from your data source
            // For example, you can use a database query or read from a file.
            // Return a List<String> containing the tasks by priority.
            // For now, let's return some sample priority tasks.
            return List.of("Task 1: Sample Priority Task 1", "Task 2: Sample Priority Task 2", "Task 3: Sample Priority Task 3");
        } else {
            // Replace this with your code to fetch tasks by arrival order from your data source
            // For example, you can use a database query or read from a file.
            // Return a List<String> containing the tasks by arrival order.
            // For now, let's return some sample arrival order tasks.
            return List.of("Task 1: Sample Arrival Order Task 1", "Task 2: Sample Arrival Order Task 2", "Task 3: Sample Arrival Order Task 3");
        }
    }

    // Refresh the display panel to update the displayed tasks
    private void refreshDisplay() {
        cardLayout.show(displayPanel, displayByPriority ? "Priority" : "Arrival");
        taskListModel.clear();
        for (String task : tasks) {
            taskListModel.addElement(task);
        }
    }

    // Example method to show further info (replace with your logic)
    private void showTaskInfo(String task) {
        // Replace this with your logic to display further task info
        JOptionPane.showMessageDialog(this, "Task selected: " + task + "\n" + "Priority", "Task Info", JOptionPane.INFORMATION_MESSAGE);
    }
}

