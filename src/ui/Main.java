package ui;

import javax.swing.*;
import java.awt.*;

/**
 * The Main class serves as the entry point for the Task Manager application.
 * It initializes the graphical user interface (GUI) and launches the application.
 */
public class Main {

    /**
     * The main method of the application.
     * It invokes the GUI creation and display using SwingUtilities.invokeLater().
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    /**
     * Creates and displays the main GUI for the Task Manager application.
     * Initializes a JFrame, sets its properties, and adds panels for different
     * sections of the application, such as the general menu and tasks panel.
     */
    private static void createAndShowGUI() {
        // Create the main JFrame
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1750, 800);

        // Create a container panel with CardLayout for managing different panels
        JPanel containerPanel = new JPanel(new CardLayout());

        // Create and add the GeneralMenuPanel to the container
        GeneralMenuPanel generalMenuPanel = new GeneralMenuPanel(containerPanel);
        containerPanel.add(generalMenuPanel, "GeneralMenu");

        // Create and add the TasksPanel to the container
        TasksPanel tasksPanel = new TasksPanel(containerPanel);
        containerPanel.add(tasksPanel, "TasksPanel");

        // Add the container panel to the JFrame's content pane
        frame.getContentPane().add(containerPanel);

        // Make the JFrame visible
        frame.setVisible(true);
    }
}
