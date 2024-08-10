import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.*;


public class ProjectGUI extends JFrame implements MyObserver {
        Project q;
        JLabel w;
        public void update(){w.setText("Name: " +q.name+" / Cost: $"+q.calcCost()+" / Project Time: "+q.calcTotalTime()+" Days");}

public ProjectGUI(Project a,WholeSystem z)
{
     setTitle("Project: "+a.name);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        a.addObserver(this);
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        

        JLabel nameLabel = new JLabel("Name: " +a.name+" / Cost: $"+a.calcCost()+" / Project Time: "+a.calcTotalTime()+" Days");
        q=a;w=nameLabel;
     
        //#region List GUI
        JLabel list1Label = new JLabel("Tasks:");
        JLabel MatDescLabel = new JLabel("InComplete: ✘       Complete: ✓");
        JList<Task> list1 = new JList<>(a.tasks);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton removeTaskButton = new JButton(" - ");
        JButton addTaskButton = new JButton(" + ");
        JButton assignTaskButton = new JButton(" ✎ ");

        removeTaskButton.setMargin(new Insets(0, 0, 0, 0));
        addTaskButton.setMargin(new Insets(0, 0, 0, 0));
        assignTaskButton.setMargin(new Insets(0, 0, 0, 0));

        removeTaskButton.setToolTipText("Remove Selected Task");
        addTaskButton.setToolTipText("Add Task");
        assignTaskButton.setToolTipText("Edit Selected Task");
        //#endregion

        //#region Button Functions
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               TaskCreate.createTaskCreatorWindow(a);
              // a.notifyObservers();a.parent.update();a.update();
            }
        });
        removeTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list1.getSelectedValue()!=null)
               WarningPopUp.createWarningPopUp(a.removeTask(list1.getSelectedIndex()));
               a.notifyObservers();
            }
        });
        assignTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list1.getSelectedValue()!=null)
               TaskGUI.createTaskWindow(list1.getSelectedValue(),z);
            }
        });
        //#endregion

        //#region ALignement
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(nameLabel)
                .addComponent(MatDescLabel)
                .addComponent(list1Label));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(removeTaskButton));
        hGroup.addGroup(layout.createParallelGroup()         
                .addComponent(list1));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(addTaskButton));
                hGroup.addGroup(layout.createParallelGroup()
                .addComponent(assignTaskButton));
        layout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(nameLabel));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(MatDescLabel));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(list1Label)
                .addComponent(removeTaskButton)
                .addComponent(list1)
                .addComponent(addTaskButton)
                .addComponent(assignTaskButton));
        layout.setVerticalGroup(vGroup);
        //#endregion

        setSize(500, 200);
        add(panel);
       // pack();
        setLocationRelativeTo(null);
    }
    public static void createProjectWindow(Project a,WholeSystem z)
    {SwingUtilities.invokeLater(() -> { ProjectGUI pjGUI = new ProjectGUI(a,z);pjGUI.setVisible(true);});}
}
