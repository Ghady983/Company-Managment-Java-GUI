import javax.swing.*;
import java.awt.*;

public class SimulateGUI extends JFrame {

    // Constructor to set up the GUI
    public SimulateGUI(String a) {
        // Set the title of the window
        setTitle("Simulation");

        // Set the layout for the frame
        setLayout(new BorderLayout());

        // Create a JLabel
        JLabel label = new JLabel("InComplete: ✘       Complete: ✓");

        // Create a JTextArea
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setText(a);
        textArea.setEditable(false);

        // Create a JScrollPane to make the JTextArea scrollable
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Add the label to the top of the frame
        add(label, BorderLayout.NORTH);

        // Add the scrollPane (which contains the textArea) to the center of the frame
        add(scrollPane, BorderLayout.CENTER);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Pack the components within the frame
        pack();

        // Set the frame to be visible
        setSize(550, 400);
        setVisible(true);
    }

    // Main method to run the application
  
}