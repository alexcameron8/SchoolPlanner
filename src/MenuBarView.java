import javax.swing.*;

public class MenuBarView extends JMenuBar {
    public MenuBarView(PlannerModel plannerModel, PlannerView plannerView){
        super();
        initMenu();
    }
    public void initMenu(){
        //Creating the JMenus
        JMenu test = new JMenu("Test");

        this.add(test);

        JMenuItem testItem = new JMenuItem("TestItem");

        test.add(testItem);
    }
}
