import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.*;



public class ProcessGUI extends JFrame implements MyObserver{
    Process tmpP;
     JLabel tmpL;
    public void update(){tmpL.setText("Name: " +tmpP.name+" / Cost: $"+tmpP.calcCost());}
    public ProcessGUI(Process a,WholeSystem z) {
        tmpP=a;
        setTitle("Process: "+a.name);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        a.addObserver(this);
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        

        JLabel nameLabel = new JLabel("Name: " +a.name+" / Cost: $"+a.calcCost());
        tmpL= nameLabel;
        JLabel TimeLabel = new JLabel("Time(Days): ");
        JSpinner TimeSpinner = new JSpinner(new SpinnerNumberModel(a.time, 1, Integer.MAX_VALUE, 1));
        JButton TimeButton = new JButton("Set Time");

        JLabel list1Label = new JLabel("Required Materials:");

        JLabel MatDescLabel = new JLabel("Not Provided: ✘       Provided: ✓");
        JList<RequiredMaterialRessources> list1 = new JList<>(a.requiredMaterialRessources);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton removeMaterialButton = new JButton(" - ");
        JButton addMaterialButton = new JButton(" + ");
        JButton assignMaterialButton = new JButton(" ☜ ");

        removeMaterialButton.setMargin(new Insets(0, 0, 0, 0));
        addMaterialButton.setMargin(new Insets(0, 0, 0, 0));
        assignMaterialButton.setMargin(new Insets(0, 0, 0, 0));

        removeMaterialButton.setToolTipText("Remove Selected Material");
        addMaterialButton.setToolTipText("Add Required Material");
        assignMaterialButton.setToolTipText("Assign To Selected Material");

        JLabel list2Label = new JLabel("Required Employees:");
  
        JList<RequiredEmployee> list2 = new JList<>(a.requiredEmployees);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton removeEmployeeButton = new JButton(" - ");
        JButton addEmployeeButton = new JButton(" + ");
        JButton assignEmployeeButton = new JButton(" ☜ ");

        removeEmployeeButton.setMargin(new Insets(0, 0, 0, 0));
        addEmployeeButton.setMargin(new Insets(0, 0, 0, 0));
        assignEmployeeButton.setMargin(new Insets(0, 0, 0, 0));

        removeEmployeeButton.setToolTipText("Remove Selected Employee");
        addEmployeeButton.setToolTipText("Add Required Employee");
        assignEmployeeButton.setToolTipText("Assign To Selected Employee");

        JLabel list3Label = new JLabel("Required Logistics:");
        JList<RequiredMaterialRessources> list3 = new JList<>(a.requiredLogistics);
        list3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton removeLogisticButton = new JButton(" - ");
        JButton addLogisticButton = new JButton(" + ");
        JButton assignLogisticButton = new JButton(" ☜ ");

        removeLogisticButton.setMargin(new Insets(0, 0, 0, 0));
        addLogisticButton.setMargin(new Insets(0, 0, 0, 0));
        assignLogisticButton.setMargin(new Insets(0, 0, 0, 0));

        removeLogisticButton.setToolTipText("Remove Selected logistic");
        addLogisticButton.setToolTipText("Add Required Logistic");
        assignLogisticButton.setToolTipText("Assign To Selected Logistic");

    
        //#region Button Functionality

        TimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                a.time= (Integer)TimeSpinner.getValue();
                a.notifyObservers();a.parent.update();
                WarningPopUp.createWarningPopUp("Time Changed!");
            }
        });
        removeMaterialButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(list1.getSelectedValue() !=null)
                {   if(list1.getSelectedValue().provided){WarningPopUp.createWarningPopUp(list1.getSelectedValue().removeAssignedMaterialRessource());}
                    a.requiredMaterialRessources.remove(list1.getSelectedIndex());  
                    a.notifyObservers();a.parent.update();
                }
            }
        });

        removeEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list2.getSelectedValue() !=null)
                {
                    a.removeAssignedEmployee(list2.getSelectedIndex());
                }
            }
        });
        
        addMaterialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateMaterialResourceStack.createMaterialResourceStackWindow(a,null,0,z);
            }
        });

        addEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateRequiredEmployee.CreateRequiredEmployeeWindow(a,z);
            }
        });
        
        assignMaterialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list1.getSelectedValue()!=null&&!list1.getSelectedValue().provided)
                {
                boolean found=false;
                
                for(int i=0;i<a.parent.parent.parent.globalStockMaterialRessources.size();i++)
                {if(a.parent.parent.parent.globalStockMaterialRessources.get(i).obj==list1.getSelectedValue().required.obj)
                {WarningPopUp.createWarningPopUp(a.requiredMaterialRessources.get(list1.getSelectedIndex()).assignMaterialRessource(a.parent.parent.parent.globalStockMaterialRessources.get(i)));
                found=true;
                a.notifyObservers();a.parent.update();a.update();}
                
                }
                if(!found)WarningPopUp.createWarningPopUp("Could Not Find Material in Stock!");
                }
            }
        });
        
        assignEmployeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list2.getSelectedValue()!=null&&list2.getSelectedValue().employee==null)
                {
                AssignRequiredEmployee.AssignRequiredEmployeeWindow(list2.getSelectedValue(),a,z);
                }
            }
        });

        removeLogisticButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if(list3.getSelectedValue() !=null)
                {   if(list3.getSelectedValue().provided){WarningPopUp.createWarningPopUp(list3.getSelectedValue().removeAssignedMaterialRessource());}
                    a.requiredLogistics.remove(list3.getSelectedIndex());  
                    a.notifyObservers();a.parent.update();
                }
            }
        });

        assignLogisticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list3.getSelectedValue()!=null&&!list3.getSelectedValue().provided)
                {
                boolean found=false;
                
                for(int i=0;i<a.parent.parent.parent.globalAvailableLogistics.size();i++)
                {if(a.parent.parent.parent.globalAvailableLogistics.get(i).obj==list3.getSelectedValue().required.obj)
                {WarningPopUp.createWarningPopUp(a.requiredLogistics.get(list3.getSelectedIndex()).assignMaterialRessource(a.parent.parent.parent.globalAvailableLogistics.get(i)));
                found=true;}
                a.notifyObservers();a.parent.update();a.update();
                }
                if(!found)WarningPopUp.createWarningPopUp("Could Not Find Logistic in Stock!");
                }
            }
        });
        
        addLogisticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateLogisticStack.CreateLogisticStackWindow(a,null,0,z);
            }
        });

        //#endregion

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(nameLabel)
                .addComponent(TimeLabel)
                .addComponent(MatDescLabel)
                .addComponent(list1Label)
                .addComponent(list2Label)
                .addComponent(list3Label));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(TimeSpinner)
                .addComponent(removeMaterialButton)
                .addComponent(removeEmployeeButton)
                .addComponent(removeLogisticButton));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(TimeButton)         
                .addComponent(list1)
                .addComponent(list2)
                .addComponent(list3));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(addMaterialButton)
                .addComponent(addEmployeeButton)
                .addComponent(addLogisticButton));
                hGroup.addGroup(layout.createParallelGroup()
                .addComponent(assignMaterialButton)
                .addComponent(assignEmployeeButton)
                .addComponent(assignLogisticButton));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(nameLabel));
                vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(TimeLabel)
                .addComponent(TimeSpinner)
                .addComponent(TimeButton));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(MatDescLabel));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(list1Label)
                .addComponent(removeMaterialButton)
                .addComponent(list1)
                .addComponent(addMaterialButton)
                .addComponent(assignMaterialButton));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(list2Label)
                .addComponent(removeEmployeeButton)
                .addComponent(list2)
                .addComponent(addEmployeeButton)
                .addComponent(assignEmployeeButton));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        .addComponent(list3Label)
        .addComponent(removeLogisticButton)
        .addComponent(list3)
        .addComponent(addLogisticButton)
        .addComponent(assignLogisticButton));
        layout.setVerticalGroup(vGroup);

        setSize(550, 400);
        add(panel);
       // pack();
        setLocationRelativeTo(null);

        
    }

    public static void createProcessWindow(Process a,WholeSystem z)
    {SwingUtilities.invokeLater(() -> { ProcessGUI processGUI = new ProcessGUI(a,z);processGUI.setVisible(true);});}
}


