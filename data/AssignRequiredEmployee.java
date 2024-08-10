
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AssignRequiredEmployee extends JFrame{



    // Constructor to set up the GUI
    public AssignRequiredEmployee(DefaultListModel<Employee> a,RequiredEmployee b,Process c) {
        // Create the frame
        setTitle("Assign Employee");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        JFrame frame =this;
        // Create a panel for the main content
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add the material resource label and dropdown box
        JLabel resourceLabel = new JLabel("Available Employees:");
        Employee[] materials = new Employee[a.size()];
        for(int i=0;i<a.size();i++){materials[i]=a.get(i);}
        JComboBox<Employee> resourceDropdown = new JComboBox<>(materials);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(resourceLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(resourceDropdown, gbc);



        JButton createMat = new JButton("Assign");

        createMat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(resourceDropdown.getSelectedItem()!=null)
                {
                if(!c.parent.parent.parent.checkifEmployeeAlreadyInEnterprise((Employee)resourceDropdown.getSelectedItem()))
                {
                WarningPopUp.createWarningPopUp(b.assignEmployee((Employee)resourceDropdown.getSelectedItem()));
                frame.dispose();
                }
                else{WarningPopUp.createWarningPopUp("Employee Already Assigned!");}
                }
                else{WarningPopUp.createWarningPopUp("No Employee Selected!");}
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BELOW_BASELINE;
        panel.add(createMat, gbc);


        // Add the panel to the frame
        
        this.add(panel);


    }

    public static void AssignRequiredEmployeeWindow(RequiredEmployee a,Process b,WholeSystem z)
    {SwingUtilities.invokeLater(() -> { AssignRequiredEmployee frame = new AssignRequiredEmployee(z.globalAvailableEmployees,a,b);frame.setVisible(true);});}
}
