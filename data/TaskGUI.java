import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.*;


public class TaskGUI extends JFrame implements MyObserver {
        Task q;
        JLabel w;
        public void update(){w.setText("Name: " +q.name+" / Cost: $"+q.calcCost()+" / Task Time: "+q.calcTotalTime()+" Days");}
public TaskGUI(Task a,WholeSystem z)
{
     setTitle("Task: "+a.name);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        a.addObserver(this);
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        q=a;

        JLabel nameLabel = new JLabel("Name: " +a.name+" / Cost: $"+a.calcCost()+" / Task Time: "+a.calcTotalTime()+" Days");
        w=nameLabel;
     
        //#region List GUI
        JLabel list1Label = new JLabel("Processes:");
        JLabel MatDescLabel = new JLabel("InComplete: ✘       Complete: ✓");
        JList<Process> list1 = new JList<>(a.processes);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JButton removeProcessButton = new JButton(" - ");
        JButton addProcessButton = new JButton(" + ");
        JButton assignProcessButton = new JButton(" ✎ ");

        removeProcessButton.setMargin(new Insets(0, 0, 0, 0));
        addProcessButton.setMargin(new Insets(0, 0, 0, 0));
        assignProcessButton.setMargin(new Insets(0, 0, 0, 0));

        removeProcessButton.setToolTipText("Remove Selected Process");
        addProcessButton.setToolTipText("Add Process");
        assignProcessButton.setToolTipText("Edit Selected Process");
        //#endregion

        //#region Button Functions
        addProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               ProcessCreate.createProcessCreatorWindow(a,z);
              // a.notifyObservers();a.parent.update();a.update();
            }
        });
        removeProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list1.getSelectedValue()!=null)
               WarningPopUp.createWarningPopUp(a.removeProcess(list1.getSelectedIndex()));
               a.notifyObservers();
            }
        });
        assignProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(list1.getSelectedValue()!=null)
               ProcessGUI.createProcessWindow(list1.getSelectedValue(),z);
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
                .addComponent(removeProcessButton));
        hGroup.addGroup(layout.createParallelGroup()         
                .addComponent(list1));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(addProcessButton));
                hGroup.addGroup(layout.createParallelGroup()
                .addComponent(assignProcessButton));
        layout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(nameLabel));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(MatDescLabel));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(list1Label)
                .addComponent(removeProcessButton)
                .addComponent(list1)
                .addComponent(addProcessButton)
                .addComponent(assignProcessButton));
        layout.setVerticalGroup(vGroup);
        //#endregion

        setSize(500, 200);
        add(panel);
       // pack();
        setLocationRelativeTo(null);
    }
    public static void createTaskWindow(Task a,WholeSystem z)
    {SwingUtilities.invokeLater(() -> { TaskGUI taskGUI = new TaskGUI(a,z);taskGUI.setVisible(true);});}
}
