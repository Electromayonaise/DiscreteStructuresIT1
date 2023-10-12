package ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * A panel representing the general menu of the task manager application.
 */
public class GeneralMenuPanel extends BasePanel {

    /**
     * Constructs a GeneralMenuPanel.
     *
     * @param containerPanel the container panel for managing card layout
     */
    public GeneralMenuPanel(JPanel containerPanel) {
        super(containerPanel);
        initUI();
    }

    /**
     * Initializes the user interface components.
     */
    private void initUI() {
        setLayout(new BorderLayout());
        Color myRed = new Color(255, 175, 175);

        // Create a JLabel for the title
        JLabel titleLabel = new JLabel("TASK MANAGER");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(myRed); // Soft red color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add some padding
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create a button for tasks
        JButton tasksButton = createStyledButton("Tasks", myRed);
        tasksButton.addActionListener(e -> showPanel());
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(tasksButton, gbc);

        // Create a button for closing the program
        JButton closeButton = createStyledButton("Close Program", myRed);
        closeButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 1;
        buttonPanel.add(closeButton, gbc);

        // Create a hyperlink-like button for Documentation
        JButton documentationButton = createStyledButton("Documentation", myRed);
        documentationButton.setContentAreaFilled(false); // Make the button transparent
        documentationButton.setBorderPainted(false); // Remove the border
        documentationButton.setForeground(myRed); // Set text color to myRed
        documentationButton.addActionListener(e -> {
            // Open the documentation link in the default web browser
            try {
                URI documentationURI = new URI("https://docs.google.com/document/d/1JMBJPXm9Ap0g7WcMd4UwYlNqtwQpDWPY8Scmv6L1Xis/edit?usp=sharing");
                Desktop.getDesktop().browse(documentationURI);
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Move the button to the bottom right
        buttonPanel.add(documentationButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a styled JButton with specific text and background color.
     *
     * @param text   the text to be displayed on the button
     * @param bgColor the background color of the button
     * @return the styled JButton
     */
    protected JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 60));
        return button;
    }

    /**
     * Helper method to show a specific panel using card layout.
     */
    private void showPanel() {
        CardLayout cardLayout = (CardLayout) containerPanel.getLayout();
        cardLayout.show(containerPanel, "TasksPanel");
    }
}
