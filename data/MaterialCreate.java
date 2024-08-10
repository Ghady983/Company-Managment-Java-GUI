import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MaterialCreate extends JFrame {
    
    public MaterialCreate(WholeSystem z) {
        // Set up the frame
        JFrame frame=this;
        setTitle("Material Creator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create a panel with a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
        
        // Add the label
        JLabel label = new JLabel("Material Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(label, constraints);

        // Add the text field
        JTextField textField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(textField, constraints);

        JLabel quantityLabel = new JLabel("Price:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(quantityLabel,constraints);
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(2, 1, Integer.MAX_VALUE, 1));
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(quantitySpinner,constraints);
        
        // Add the button
        JButton button = new JButton("Create Material");
        constraints.gridx = 1;
        constraints.gridy = 2;
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
                    z.globalMaterialRessources.addElement(new MaterialRessource(textField.getText(),(Integer)quantitySpinner.getValue()));
                WarningPopUp.createWarningPopUp("Material "+textField.getText()+" Created!");
                frame.dispose();
                }
                else{WarningPopUp.createWarningPopUp("Material must have a name!");}
            }
        });
        
        // Make the frame visible
        setVisible(true);
    }
    
    public static void createMaterialCreatorWindow(WholeSystem z)
    {SwingUtilities.invokeLater(() -> { MaterialCreate pc = new MaterialCreate(z);pc.setVisible(true);});}
}
