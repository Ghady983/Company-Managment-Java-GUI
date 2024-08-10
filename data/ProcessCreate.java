import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProcessCreate extends JFrame {
    
    public ProcessCreate(Task a, WholeSystem z) {
        // Set up the frame
        JFrame frame=this;
        setTitle("Process Creator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create a panel with a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
        
        // Add the label
        JLabel label = new JLabel("Process Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label, constraints);
        
        // Add the text field
        JTextField textField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(textField, constraints);
        
        // Add the button
        JButton button = new JButton("Create Process");
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(button, constraints);
        
        // Add the panel to the frame
        add(panel);
        
        // Add button click event
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField.getText().length()>0)
                {
                    Process s= a.createProcess(textField.getText());
                    ProcessGUI.createProcessWindow(s,z);
                WarningPopUp.createWarningPopUp("Process: "+s.name+" Created!");
                frame.dispose();
                }
                else{WarningPopUp.createWarningPopUp("Process must have a name!");}
            }
        });
        
        // Make the frame visible
        setVisible(true);
    }
    
    public static void createProcessCreatorWindow(Task a,WholeSystem z)
    {SwingUtilities.invokeLater(() -> { ProcessCreate pc = new ProcessCreate(a,z);pc.setVisible(true);});}
}
