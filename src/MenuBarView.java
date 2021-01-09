import javax.swing.*;
/**
 * @Author: Alex Cameron
 */
public class MenuBarView extends JMenuBar {

    private PlannerController pbc;

    public MenuBarView(PlannerModel plannerModel, PlannerView plannerView){
        super();
        pbc = new PlannerController(plannerModel,plannerView);
        initMenu();
    }
    public void initMenu(){
        //Creating the JMenus
        JMenu fileMenu = new JMenu("File Menu");

        this.add(fileMenu);

        JMenuItem exportItem = new JMenuItem("Export ");
        JMenuItem deleteFile = new JMenuItem("Delete File");

        fileMenu.add(exportItem);
        fileMenu.add(deleteFile);


        exportItem.addActionListener(pbc);
        exportItem.setActionCommand("export");

        deleteFile.addActionListener(pbc);
        deleteFile.setActionCommand("deleteFile");

    }
}
