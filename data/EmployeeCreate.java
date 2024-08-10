import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeCreate extends JFrame {
    
    public EmployeeCreate(WholeSystem z) {
        // Set up the frame
        JFrame frame=this;
        setTitle("Employee Creator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create a panel with a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
        
        // Add the label
        JLabel label = new JLabel("Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label, constraints);

        // Add the text field
        JTextField textField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(textField, constraints);

        JLabel label2 = new JLabel("Last Name:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(label2, constraints);
        JTextField textField2 = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(textField2, constraints);

        JLabel quantityLabel = new JLabel("Hours Of Work:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(quantityLabel,constraints);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(5, 5, Integer.MAX_VALUE, 1));
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(quantitySpinner,constraints);
        JLabel label3 = new JLabel("Job:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(label3, constraints);
        JList<Job> list = new JList<>(z.globalJobs);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(list, constraints);
        
        // Add the button
        JButton button = new JButton("Create Employee");
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(button, constraints);
        
        // Add the panel to the frame
        add(panel);
        
        // Add button click event
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list.getSelectedValue()!=null)
                {
                if(textField.getText().length()>0 || textField2.getText().length()>0)
                {
                    z.globalAvailableEmployees.addElement(new Employee(textField.getText(),textField2.getText(),(Integer)quantitySpinner.getValue(),list.getSelectedValue()));
                WarningPopUp.createWarningPopUp("Employee "+textField.getText()+" Created!");
                frame.dispose();
                }
                else{WarningPopUp.createWarningPopUp("Employee must have a name!");}
                }
                else{WarningPopUp.createWarningPopUp("Employee must have a Job!");}
            }
        });
        
        // Make the frame visible
        setSize(400, 300);
        setVisible(true);
    }
    
    public static void createEmployeeCreatorWindow(WholeSystem z)
    {SwingUtilities.invokeLater(() -> { EmployeeCreate pc = new EmployeeCreate(z);pc.setVisible(true);});}
}
