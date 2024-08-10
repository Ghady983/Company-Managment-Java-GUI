import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditStore extends JFrame {

    public EditStore(WholeSystem z) {
        // Set the title and default close operation
        setTitle("Store Editor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create the main panel
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        // Automatically add gaps between components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Create the components
        JLabel firstLabel = new JLabel(":Store Edit:");

        JLabel[] labels = new JLabel[4];
        JButton[] buttons1 = new JButton[4];
        JList<MaterialRessource> list1 = new JList<>(z.globalMaterialRessources);
        JList<Job> list2 = new JList<>(z.globalJobs);
        JList<Employee> list3 = new JList<>(z.globalAvailableEmployees);
        JList<MaterialRessource> list4 = new JList<>(z.globalLogistics);
        JButton[] buttons2 = new JButton[4];

        JList[] lists = new JList[4];
        lists[0]=list1;lists[1]=list2;lists[2]=list3;lists[3]=list4;

        for (int i = 0; i < 4; i++) {
            buttons1[i] = new JButton(" - ");buttons1[i].setMargin(new Insets(0, 0, 0, 0));
            buttons2[i] = new JButton(" + ");buttons2[i].setMargin(new Insets(0, 0, 0, 0));
        }

        labels[0] = new JLabel("Raw Materials:");
        labels[1] = new JLabel("Jobs:");
        labels[2] = new JLabel("Employees:");
        labels[3] = new JLabel("Logistics:");

        //#region Button Functions
        buttons1[0].addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) 
        {if(list1.getSelectedValue()!=null)z.globalMaterialRessources.removeElement(list1.getSelectedValue());}});

        buttons2[0].addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) 
        {MaterialCreate.createMaterialCreatorWindow(z);}});
        //
        buttons1[1].addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) 
        {if(list2.getSelectedValue()!=null)z.globalJobs.removeElement(list2.getSelectedValue());}});
        buttons2[1].addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) 
        {JobCreate.createJobCreatorWindow(z);}});
        //
        buttons1[2].addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) 
        {if(list3.getSelectedValue()!=null)z.globalAvailableEmployees.removeElement(list3.getSelectedValue());}});
        buttons2[2].addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) 
        {EmployeeCreate.createEmployeeCreatorWindow(z);}});
        //
        buttons1[3].addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) 
        {if(list4.getSelectedValue()!=null)z.globalLogistics.removeElement(list4.getSelectedValue());}});
        buttons2[3].addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) 
        {LogisticCreate.createLogisticCreatorWindow(z);}});
        //#endregion

        //#region LAYOUT

        // Setup the horizontal group
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(firstLabel)
                .addComponent(labels[0])
                .addComponent(labels[1])
                .addComponent(labels[2])
                .addComponent(labels[3]));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(buttons1[0])
                .addComponent(buttons1[1])
                .addComponent(buttons1[2])
                .addComponent(buttons1[3]));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(list1)
                .addComponent(list2)
                .addComponent(list3)
                .addComponent(list4));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(buttons2[0])
                .addComponent(buttons2[1])
                .addComponent(buttons2[2])
                .addComponent(buttons2[3]));
        layout.setHorizontalGroup(hGroup);

        // Setup the vertical group

        
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(firstLabel));
        for (int i = 0; i < 4; i++) {
            vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(labels[i])
                    .addComponent(buttons1[i])
                    .addComponent(lists[i])
                    .addComponent(buttons2[i]));
        }
        layout.setVerticalGroup(vGroup);

        // Add the panel to the frame
        add(panel);

        //#endregion

        // Pack the components and set the frame to visible
        //pack();
        setSize(500, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void createEditStoreWindow(WholeSystem z)
    {SwingUtilities.invokeLater(() -> { EditStore e = new EditStore(z);e.setVisible(true);});}
}
