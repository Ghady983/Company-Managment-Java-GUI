import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class EnterpriseGUI extends JFrame implements MyObserver{
    Enterprise q;
    JLabel w;
    public void update(){w.setText(q.name+" / Projects Cost: $"+q.calcCost());}

    public  EnterpriseGUI(Enterprise a,WholeSystem z) {
        // Create the frame
        setTitle("Enterprise: "+a.name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        a.addObserver(this);
        // Create the components for the first line
        JLabel label1 = new JLabel(a.name+" / Projects Cost: $"+a.calcCost());
        JLabel label2 = new JLabel("InComplete: ✘       Complete: ✓");
        JButton button1 = new JButton("Edit Store");
        JButton simulate = new JButton("Simulate()");
        button1.setMargin(new Insets(0, 0, 0, 0));

        q=a;w=label1;
        // Create components for the additional lines
        JLabel label3 = new JLabel("Projects:");
        JButton button4 = new JButton(" - ");button4.setMargin(new Insets(0, 0, 0, 0));
        JList<Project> list1 = new JList<>(a.projects);
        JButton button5 = new JButton(" + "); button5.setMargin(new Insets(0, 0, 0, 0));
        JButton assignButton = new JButton(" ✎ "); assignButton.setMargin(new Insets(0, 0, 0, 0));

        JLabel label4 = new JLabel("Stock Materials:");
        JList<Stack<MaterialRessource>> list2 = new JList<>(a.globalStockMaterialRessources);
        JButton button7 = new JButton(" + "); button7.setMargin(new Insets(0, 0, 0, 0));

        JLabel label5 = new JLabel("Stock Logistics:");
        JList<Stack<MaterialRessource>> list3 = new JList<>(a.globalAvailableLogistics);
        JButton button9 = new JButton(" + "); button9.setMargin(new Insets(0, 0, 0, 0));

        JButton button10 = new JButton("Save Enterprise"); button9.setMargin(new Insets(0, 0, 0, 0));

        //#region Buttons Function
        
        simulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                new SimulateGUI(a.simulate());
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
              EditStore.createEditStoreWindow(z);
            }
        });
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               if(list1.getSelectedValue()!=null){ProjectGUI.createProjectWindow(list1.getSelectedValue(),z);}
            }
        });
        
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               if(list1.getSelectedValue()!=null){a.removeProject(list1.getSelectedIndex()); a.notifyObservers();a.update();}
            }
        });
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               ProjectCreate.createProjectCreatorWindow(a);// a.notifyObservers();a.update();
            }
        });

        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               CreateMaterialResourceStack.createMaterialResourceStackWindow(null,a,1,z); 
            }
        });

        button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               CreateLogisticStack.CreateLogisticStackWindow(null,a,1,z); 
            }
        });
        
        button10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    FileOutputStream fos = new FileOutputStream("ProjectSave.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    // write object to file
                    oos.writeObject(z);
                    WarningPopUp.createWarningPopUp("Saved Enterprise Succesfull!");
                    // closing resources
                    oos.close();
                    fos.close();
                } catch (IOException k) {WarningPopUp.createWarningPopUp("Didn't Save Correctly!");
                    k.printStackTrace();
                }
            }
        });
        //#endregion

        //#region LAYOUT

        // Create a container and set its layout
        Container container = getContentPane();
        GroupLayout layout = new GroupLayout(container);
        container.setLayout(layout);

        // Automatically add gaps between components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Create horizontal groups
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(label1)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(label2)
                    .addComponent(button1)
                    .addComponent(button10)
                    .addComponent(simulate))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(label3)
                    .addComponent(button4)
                    .addComponent(list1)
                    .addComponent(button5).addComponent(assignButton))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(label4)
                    .addComponent(list2)
                    .addComponent(button7))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(label5)
                    .addComponent(list3)
                    .addComponent(button9))
                    
        );

        // Create vertical groups
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label1)
                    .addComponent(button1)
                    .addComponent(button10)
                    .addComponent(simulate))
                 .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label2))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label3)
                    .addComponent(button4)
                    .addComponent(list1)
                    .addComponent(button5)
                    .addComponent(assignButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label4)
                    .addComponent(list2)
                    .addComponent(button7))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label5)
                    .addComponent(list3)
                    .addComponent(button9))
        );
        //#endregion
      
        // Set the frame size and make it visible
       // pack();
       setSize(550, 400);
        setVisible(true);
    }
    public static void createEnterpriseWindow(Enterprise a,WholeSystem z)
    {SwingUtilities.invokeLater(() -> { EnterpriseGUI comp = new EnterpriseGUI(a,z);comp.setVisible(true);});}
}
