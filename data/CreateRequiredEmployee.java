import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CreateRequiredEmployee extends JFrame{



    // Constructor to set up the GUI
    public CreateRequiredEmployee(DefaultListModel<Job> a,Process b) {
        // Create the frame
        setTitle("Create Required Employee");
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
        JLabel resourceLabel = new JLabel("Available Jobs:");
        Job[] materials = new Job[a.size()];
        for(int i=0;i<a.size();i++){materials[i]=a.get(i);}
        JComboBox<Job> resourceDropdown = new JComboBox<>(materials);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(resourceLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(resourceDropdown, gbc);



        JButton createMat = new JButton("Create");

        createMat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(resourceDropdown.getSelectedItem()!=null)
               {
                b.addRequiredEmployee(new RequiredEmployee((Job)resourceDropdown.getSelectedItem(),b));
                WarningPopUp.createWarningPopUp("Created Required Job Successfuly!");
                frame.dispose();
               }
               else{WarningPopUp.createWarningPopUp("No Job Selected!");}
    
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BELOW_BASELINE;
        panel.add(createMat, gbc);


        // Add the panel to the frame
        this.add(panel);


    }

    public static void CreateRequiredEmployeeWindow(Process a,WholeSystem z)
    {SwingUtilities.invokeLater(() -> { CreateRequiredEmployee frame = new CreateRequiredEmployee(z.globalJobs,a);frame.setVisible(true);});}
}
