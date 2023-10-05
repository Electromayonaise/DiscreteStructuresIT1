package ui;

import javax.swing.*;
import java.awt.*;


public class GeneralMenuPanel extends BasePanel {

    public GeneralMenuPanel(JPanel containerPanel) {
        super(containerPanel);
        initUI();
    }

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

        // Create and style buttons with a softer red color scheme
        JButton tasksButton = createStyledButton("Tasks", new Color(255, 175, 175));
        tasksButton.addActionListener(e -> showPanel("TasksPanel"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(tasksButton, gbc);

        JButton closeButton = createStyledButton("Close Program", new Color(255, 175, 175));
        closeButton.addActionListener(e -> System.exit(0));
        gbc.gridy = 1;
        buttonPanel.add(closeButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 30));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        // Set a larger preferred size
        button.setPreferredSize(new Dimension(300, 60));
        return button;
    }

    // Helper method to show a specific panel
    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) containerPanel.getLayout();
        cardLayout.show(containerPanel, panelName);
    }
}






