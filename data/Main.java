
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;


public class Main implements Serializable {

    
    
    public static void main(String[] args)
    {
        WholeSystem sys = new WholeSystem();
        try
        {
        FileInputStream fis = new FileInputStream("ProjectSave.txt");  
        ObjectInputStream ois = new ObjectInputStream(fis);  
        sys = (WholeSystem)ois.readObject();
        ois.close();  
        }
        catch (IOException k){k.printStackTrace();}
        catch (ClassNotFoundException h){h.printStackTrace();}
 
        sys.StartProgr();
       
       
    }

    
}
class WholeSystem implements Serializable
{
    public DefaultListModel<MaterialRessource> globalMaterialRessources;
    public DefaultListModel<Job> globalJobs;
    public DefaultListModel<MaterialRessource> globalLogistics;
    public DefaultListModel<Employee> globalAvailableEmployees;
    Enterprise c;
    public WholeSystem()
    {
        c= new Enterprise("OMEGA");globalMaterialRessources= new DefaultListModel<>();
        globalJobs = new DefaultListModel<>();globalLogistics = new DefaultListModel<>();
        globalAvailableEmployees = new DefaultListModel<>();
    }
    public void StartProgr(){EnterpriseGUI.createEnterpriseWindow(c,this);}

}
//#region Functionality

//#region Material Ressources Related
class Stack<E> implements Serializable
{
    E obj;int quantity; 
    //
    public Stack(int a,E b){quantity=a;obj=b;}
    public void changeQuantity(int a){quantity=a;}
    
    public String toString(){return obj+" x"+quantity;}
}

class MaterialRessource implements Serializable
{
    static int idinc=0;int id;String name; float price;
    //
    public MaterialRessource(String a,float b){id=idinc++;name=a;price=b;}
    public String toString(){return name+" $"+price;}
}

class RequiredMaterialRessources extends MyObservable implements Serializable
{
    Stack<MaterialRessource> required,takenfrom;boolean provided;
    //
    public RequiredMaterialRessources(Stack<MaterialRessource> a){required=a;}
    public String assignMaterialRessource(Stack<MaterialRessource> a)
    {   
        if(required.obj==a.obj&&required.quantity<=a.quantity&&!provided)
        {a.changeQuantity(a.quantity-required.quantity);takenfrom=a;provided=true;System.out.println(takenfrom);notifyObservers(); return"Assignement Successful!";}
        return "Could not Assign Item!";
    }
    public String removeAssignedMaterialRessource()
    {
        if(takenfrom!=null && provided ==true)
        {takenfrom.changeQuantity(takenfrom.quantity+required.quantity);System.out.println(takenfrom);takenfrom=null;provided=false;return "Removing Assigned Item Complete!";}
        return "There is Nothing To Remove!";
    }
    public float cost(){return required.quantity*required.obj.price;}
    public String toString(){String extra ="";if(provided){extra=" ✓";}else{extra=" ✘";}return required.obj.name+" x"+required.quantity+extra;}
}


//#endregion

//#region Human Ressources Related
class Job implements Serializable
{
    static int idinc=0;int id;String name;float perHourSalary;
    //
    public Job(String a,float b){id=idinc++;name=a;perHourSalary=b;}
   // public void changePerHourSalary(float a){perHourSalary=a;}

    public String toString(){return name;}
}

class Employee implements Serializable
{
    static int idinc=0;int id;String name,lastName;Job job;float hoursOfWork;boolean assigned;
    //
    public Employee(String a,String b,float c){id=idinc++;name=a;lastName=b;hoursOfWork=c;assigned=false;}
    public Employee(String a,String b,float c,Job j){id=idinc++;name=a;lastName=b;hoursOfWork=c;assigned=false;assignJob(j);}
    public Employee assignJob(Job a){job=a;return this;}
   // public void changeHoursOfWork(float a){hoursOfWork=a;}
    public float salary(){return hoursOfWork*job.perHourSalary;}

    public String toString(){String e="";if(assigned){e="✒ ";}return e+name+" "+lastName+" / Job: "+job+" / HOF: "+hoursOfWork;}
}

class RequiredEmployee extends MyObservable implements Serializable
{
    Process parent;Job required; Employee employee;
    //
    public RequiredEmployee(Job a,Process b){required=a;parent=b;}
    public String assignEmployee(Employee a)
    {
        if(a.job==required){employee=a;a.assigned=true;parent.parent.parent.parent.EnterpriseAvailableEmployees.add(a);notifyObservers();return ("Employee "+a.name+" "+a.lastName+" Successfully Assigned!");}
        return "Could Not Assign Employee: "+a.name+" "+a.lastName+"!"; 
    }

    public String toString()
    {String extra ="";if(employee!=null){extra=" ✓";}else{extra=" ✘";}return required.name+extra;}
}
//#endregion

//#region Tasks/Project Related
class Enterprise extends MyObservable implements Serializable,MyObserver
{
    String name;DefaultListModel<Project> projects;
    public String createProject(String a){projects.addElement(new Project(this, a));return"Project "+a+" Created!";}
    public String removeProject(int a)
    {String b = projects.get(a).name; 
       
            Project g =projects.get(a);
            for(int j=0;j<g.tasks.size();j++)
            {g.removeTask(j);}
        
    projects.remove(a);notifyObservers();return"Project: "+b+" Removed Successfully!";
    }
    public DefaultListModel<Stack<MaterialRessource>> globalAvailableLogistics;
    public DefaultListModel<Stack<MaterialRessource>> globalStockMaterialRessources;
    public  ArrayList<Employee> EnterpriseAvailableEmployees;
    public Enterprise(String a){name=a;globalAvailableLogistics = new DefaultListModel<>();globalStockMaterialRessources= new DefaultListModel<>();EnterpriseAvailableEmployees = new ArrayList<>();projects = new DefaultListModel<>();}
    public void addOntoExistingMaterialStack(Stack<MaterialRessource> a)
    {
        for(int i=0;i<globalStockMaterialRessources.size();i++){if(a.obj==globalStockMaterialRessources.get(i).obj){globalStockMaterialRessources.get(i).quantity+=a.quantity;update();return;}}
        globalStockMaterialRessources.addElement(a);
    }
    public void addOntoExistingLogisticStack(Stack<MaterialRessource> a)
    {
        for(int i=0;i<globalAvailableLogistics.size();i++){if(a.obj==globalAvailableLogistics.get(i).obj){globalAvailableLogistics.get(i).quantity+=a.quantity;update();return;}}
        globalAvailableLogistics.addElement(a);
    }
    public boolean checkifEmployeeAlreadyInEnterprise(Employee a){for(Employee e:EnterpriseAvailableEmployees){if(e==a)return true;}return false;}
    public int checkIndexOfEmployeeAlreadyInEnterprise(Employee a){for(int i=0;i<EnterpriseAvailableEmployees.size();i++){if(EnterpriseAvailableEmployees.get(i)==a)return i;}return -1;}
    public String simulate()
    {
        String[] ind= {"   ","      ","          "};
        String str="";
        for(int i=0;i<projects.size();i++)
        {
            Project a= projects.get(i);str+=a.toString()+"\n";
            if(a.checkIfMissing())
            {
                str+=ind[0]+".{"+"\n";
                for(int j=0;j<a.tasks.size();j++)
                {
                    Task b= a.tasks.get(j);str+=ind[0]+b.toString()+"\n";
                    if(b.checkIfMissing())
                    {
                        str+=ind[1]+"..{"+"\n";
                        for(int k=0;k<b.processes.size();k++)
                        {
                           Process c = b.processes.get(k);str+=ind[1]+c.toString()+"\n";
                           if(c.checkIfMissing())
                           {    str+=ind[2]+"...{"+"\n"+ind[2]+"Material Ressources Missing:"+"\n";
                                for(int q=0;q<c.requiredMaterialRessources.size();q++)
                                {if(!c.requiredMaterialRessources.get(q).provided)str+=ind[2]+c.requiredMaterialRessources.get(q)+"\n";}
                                str+=ind[2]+"----"+"\n"+ind[2]+"Logisitics Missing:"+"\n";
                                for(int q=0;q<c.requiredLogistics.size();q++)
                                {if(!c.requiredLogistics.get(q).provided)str+=ind[2]+c.requiredLogistics.get(q)+"\n";}
                                str+=ind[2]+"----"+"\n"+ind[2]+"Jobs Missing:"+"\n";
                                for(int q=0;q<c.requiredEmployees.size();q++)
                                {if(c.requiredEmployees.get(q).employee==null)str+=ind[2]+c.requiredEmployees.get(q).required.name+" ✘\n";}
                                str+=ind[2]+"}..."+"\n";
                            }  
                        }
                        str+=ind[1]+"}.."+"\n";
                    }
                }
                str+=ind[0]+"}."+"\n";
            }
        }

        return str;
    }
    public void update()
    {System.out.println("Enterprise: "+name);
       if(projects.size()>0) projects.set(0,projects.get(0));
       if(globalStockMaterialRessources.size()>0)globalStockMaterialRessources.set(0,globalStockMaterialRessources.get(0));
       if(globalAvailableLogistics.size()>0)globalAvailableLogistics.set(0,globalAvailableLogistics.get(0));
        notifyObservers();
    }
    public float calcCost(){float sum=0;for(int i=0;i<projects.size();i++){sum+=projects.get(i).calcCost();}return sum;}
}

class Project extends MyObservable implements Serializable,MyObserver
{
    Enterprise parent;static int idinc=0;int id;String name;DefaultListModel<Task> tasks;
    public Project(Enterprise a,String b){id=idinc++;parent=a;name=b;tasks=new DefaultListModel<>();addObserver(a);}
    public String createTask(String a){tasks.addElement(new Task(this, a));notifyObservers();return"Task "+a+" Created!";}
    public String removeTask(int a)
    {String b = tasks.get(a).name; 
        
            Task g =tasks.get(a);
            for(int j=0;j<g.processes.size();j++)
            {g.removeProcess(j);}
        
    tasks.remove(a);notifyObservers();return"Task: "+b+" Removed Successfully!";
    }
    public int calcTotalTime(){int sum=0;for(int i=0;i<tasks.size();i++){sum+=tasks.get(i).calcTotalTime();}return sum;}
    public float calcCost(){float sum=0;for(int i=0;i<tasks.size();i++){sum+=tasks.get(i).calcCost();}return sum;}
    public void update(){System.out.println("Project: "+name);tasks.set(0,tasks.get(0));notifyObservers();parent.update();}
    public boolean checkIfMissing(){for(int i=0;i<tasks.size();i++){if(tasks.get(i).checkIfMissing())return true;} return false;}
    public String toString(){String e="";if(checkIfMissing()){e=" ✘";}else{e=" ✓";}return "Project: "+name+e;}

}

class Task extends MyObservable implements Serializable,MyObserver
{
    Project parent;static int idinc=0;int id;String name;DefaultListModel<Process> processes;
    public Task(Project a,String b){id=idinc++;parent=a;name=b;processes=new DefaultListModel<>();addObserver(a);}
    public Process createProcess(String a){Process tmp = new Process(this, a) ;processes.addElement(tmp);notifyObservers();parent.update();return tmp;}
    public String removeProcess(int a)
    {String b = processes.get(a).name; 
 
        Process h= processes.get(a);
        for(int j=0;j<h.requiredMaterialRessources.size();j++)
        {
            RequiredMaterialRessources l = h.requiredMaterialRessources.get(j);
            l.removeAssignedMaterialRessource();
        }
        for(int j=0;j<h.requiredEmployees.size();j++)
        {
            h.removeAssignedEmployee(j);
        }
        for(int j=0;j<h.requiredLogistics.size();j++)
        {
            RequiredMaterialRessources l = h.requiredLogistics.get(j);
            l.removeAssignedMaterialRessource();
        }
    
    processes.remove(a);notifyObservers();return"Process: "+b+" Removed Successfully!";
    }
    public boolean checkIfMissing(){for(int i=0;i<processes.size();i++){if(processes.get(i).checkIfMissing())return true;} return false;}
    public String toString(){String e="";if(checkIfMissing()){e=" ✘";}else{e=" ✓";}return "Task: "+name+e;}
    public int calcTotalTime(){int sum=0;for(int i=0;i<processes.size();i++){sum+=processes.get(i).time;}return sum;}
    public float calcCost(){float sum=0;for(int i=0;i<processes.size();i++){sum+=processes.get(i).calcCost();}return sum;}
    public void update(){System.out.println("Task: "+name);processes.set(0,processes.get(0)); notifyObservers();parent.update();}
}

class Process extends MyObservable implements Serializable,MyObserver
{
  Task parent;static int idinc=0;int id;String name;int time;
  DefaultListModel<RequiredMaterialRessources> requiredMaterialRessources;
  DefaultListModel<RequiredEmployee> requiredEmployees;
  DefaultListModel<RequiredMaterialRessources> requiredLogistics;
  //
  public Process(Task a,String b){id=idinc++;parent=a;name=b;requiredEmployees=new DefaultListModel<>();requiredMaterialRessources = new DefaultListModel<>();time=1; requiredLogistics =new DefaultListModel<>();addObserver(a);}
  public void addRequiredMaterial(RequiredMaterialRessources a){a.addObserver(this);requiredMaterialRessources.addElement(a);notifyObservers();parent.update();}
  public void addRequiredEmployee(RequiredEmployee a){a.addObserver(this);requiredEmployees.addElement(a);notifyObservers();parent.update();}
  public void addRequiredLogistic(RequiredMaterialRessources a){a.addObserver(this);requiredLogistics.addElement(a);notifyObservers();parent.update();}
  public void removeAssignedEmployee(int a)
  {
    int t= parent.parent.parent.checkIndexOfEmployeeAlreadyInEnterprise(requiredEmployees.get(a).employee);
    if(t>-1)
    {
    parent.parent.parent.EnterpriseAvailableEmployees.remove(t);
    requiredEmployees.get(a).employee.assigned=false;
    }
    requiredEmployees.remove(a);notifyObservers();parent.update();
  } 
  public boolean checkIfMissing()
  {
    for(int i=0;i<requiredMaterialRessources.size();i++){if(!requiredMaterialRessources.get(i).provided)return true;}
    for(int i =0;i<requiredEmployees.size();i++){if(requiredEmployees.get(i).employee==null)return true;}
    for(int i=0;i<requiredLogistics.size();i++){if(!requiredLogistics.get(i).provided)return true;}
    return false;
  }
  public float calcCost()
  {
    float sum=0;
    for(int i=0;i<requiredMaterialRessources.size();i++){if(!requiredMaterialRessources.get(i).provided)sum+=requiredMaterialRessources.get(i).cost();}
    for(int i=0;i<requiredEmployees.size();i++){if(requiredEmployees.get(i).employee!=null)sum+=requiredEmployees.get(i).employee.salary();}
    for(int i=0;i<requiredLogistics.size();i++){if(!requiredLogistics.get(i).provided)sum+=requiredLogistics.get(i).cost();}
    return sum;
}
  public String toString(){String e="";if(checkIfMissing()){e=" ✘";}else{e=" ✓";}return "Process: "+name+e;}
  public void update()
  {
    System.out.println("Process: "+name);
      calcCost();
      if(requiredMaterialRessources.size()>0)requiredMaterialRessources.set(0,requiredMaterialRessources.get(0));
      if(requiredLogistics.size()>0)requiredLogistics.set(0,requiredLogistics.get(0));
      if(requiredEmployees.size()>0)requiredEmployees.set(0,requiredEmployees.get(0));
      notifyObservers();parent.update();
  }
}
//#endregion



//#endregion

//#region Other Classes
class MyObservable
{
    private ArrayList<MyObserver> Observers;
    MyObservable(){Observers= new ArrayList<>();}
    public void addObserver(MyObserver a){Observers.add(a);}
    public void notifyObservers(){for(MyObserver a:Observers)if(a!=null)a.update();}
}
interface MyObserver
{
public void update();
}

//#endregion