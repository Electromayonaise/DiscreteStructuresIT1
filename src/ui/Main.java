package ui;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Task Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1750, 800);

        JPanel containerPanel = new JPanel(new CardLayout());

        GeneralMenuPanel generalMenuPanel = new GeneralMenuPanel(containerPanel);
        containerPanel.add(generalMenuPanel, "GeneralMenu");

        TasksPanel tasksPanel = new TasksPanel(containerPanel);
        containerPanel.add(tasksPanel, "TasksPanel");

        frame.getContentPane().add(containerPanel);
        frame.setVisible(true);
    }
}

