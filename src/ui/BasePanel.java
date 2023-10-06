package ui;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {

    protected JPanel containerPanel;
    protected JButton returnButton; // Declare the button at the class level

    public BasePanel(JPanel containerPanel) {
        this.containerPanel = containerPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new GridBagLayout()); // Use GridBagLayout for better element placement
        setOpaque(false); // Make the panel transparent
        setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        returnButton = createStyledButton("Return to Menu", new Color(255, 175, 175));
        returnButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) containerPanel.getLayout();
            cardLayout.show(containerPanel, "GeneralMenu");
        });

        add(returnButton, gbc);
    }

    // Helper method to create styled buttons
    protected JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    // Allow other classes to access the returnButton
    public JButton getReturnButton() {
        return returnButton;
    }
}



