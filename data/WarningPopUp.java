import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class WarningPopUp extends JFrame {
    
    public WarningPopUp(String message) {
        setTitle("Warning");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setVerticalAlignment(JLabel.CENTER);
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the popup when OK button is clicked
            }
        });
        
        panel.add(messageLabel, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);
        
        add(panel);
    }

    public static void createWarningPopUp(String a){SwingUtilities.invokeLater(() -> {
        WarningPopUp popup = new WarningPopUp(a);
        popup.setVisible(true);
    });}
}
