import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectCreate extends JFrame {
    
    public ProjectCreate(Enterprise a) {
        // Set up the frame
        JFrame frame=this;
        setTitle("Project Creator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create a panel with a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
        
        // Add the label
        JLabel label = new JLabel("Project Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label, constraints);
        
        // Add the text field
        JTextField textField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(textField, constraints);
        
        // Add the button
        JButton button = new JButton("Create Project");
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
                WarningPopUp.createWarningPopUp(a.createProject(textField.getText()));
                frame.dispose();
                }
                else{WarningPopUp.createWarningPopUp("Project must have a name!");}
            }
        });
        
        // Make the frame visible
        setVisible(true);
    }
    
    public static void createProjectCreatorWindow(Enterprise a)
    {SwingUtilities.invokeLater(() -> { ProjectCreate pc = new ProjectCreate(a);pc.setVisible(true);});}
}

