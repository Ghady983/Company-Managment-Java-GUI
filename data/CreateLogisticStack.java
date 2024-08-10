import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CreateLogisticStack extends JFrame{



    // Constructor to set up the GUI
    public CreateLogisticStack(DefaultListModel<MaterialRessource> a,Process b,Enterprise d,int c) {
        // Create the frame
        setTitle("Create Required Logistic");
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
        JLabel resourceLabel = new JLabel("Logistics:");
        MaterialRessource[] materials = new MaterialRessource[a.size()];
        for(int i=0;i<a.size();i++){materials[i]=a.get(i);}
        JComboBox<MaterialRessource> resourceDropdown = new JComboBox<>(materials);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(resourceLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(resourceDropdown, gbc);

        // Add the quantity label and number input field
        JLabel quantityLabel = new JLabel("Quantity:");
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        quantitySpinner.setValue((Integer)5);

        JButton createMat = new JButton("Create");

        createMat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(resourceDropdown.getSelectedItem()!=null)
                {
                if(c==0)
                {
                if((Integer)quantitySpinner.getValue()>0)
                {
                b.addRequiredLogistic(new RequiredMaterialRessources(new Stack((Integer)quantitySpinner.getValue(),resourceDropdown.getSelectedItem())));
                WarningPopUp.createWarningPopUp("Created Logistic Successfuly!");
                frame.dispose();
                }
                else{WarningPopUp.createWarningPopUp("Quantity Must Be At Least 1!");}
                }

                else
                {
                    if((Integer)quantitySpinner.getValue()>0)
                    { 
                    d.addOntoExistingLogisticStack(new Stack((Integer)quantitySpinner.getValue(),resourceDropdown.getSelectedItem()));
                    WarningPopUp.createWarningPopUp("Created Logistic Successfuly!");
                    frame.dispose();
                    }
                    else{WarningPopUp.createWarningPopUp("Quantity Must Be At Least 1!");}
                }
                }
                else{WarningPopUp.createWarningPopUp("No Logistic Selected!");}

            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(quantityLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(quantitySpinner, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BELOW_BASELINE;
        panel.add(createMat, gbc);


        // Add the panel to the frame
        this.add(panel);


    }

    public static void CreateLogisticStackWindow(Process a,Enterprise c,int b,WholeSystem z)
    {SwingUtilities.invokeLater(() -> { CreateLogisticStack frame = new CreateLogisticStack(z.globalLogistics,a,c,b);frame.setVisible(true);});}
}

