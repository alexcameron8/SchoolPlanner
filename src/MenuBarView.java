import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        JMenuItem fileListMenu = new JMenu("File");

        this.add(fileListMenu);

        JMenuItem exportListFile = new JMenuItem("Save");
        JMenuItem importListFile = new JMenuItem("Load");

        fileListMenu.add(exportListFile);
        fileListMenu.add(importListFile);

        exportListFile.addActionListener(pbc);
        exportListFile.setActionCommand("exportList");

        importListFile.addActionListener(pbc);
        importListFile.setActionCommand("importList");

        //fix this
        importListFile.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addActionListener(pbc);
            fileChooser.showOpenDialog(this);
        });
    }
}
