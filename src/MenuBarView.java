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
        JMenu fileMenu = new JMenu("File");
        JMenuItem fileListMenu = new JMenu("Create List");

        this.add(fileMenu);
        this.add(fileListMenu);

        JMenuItem exportFile = new JMenuItem("Export");
        JMenuItem importFile = new JMenuItem("Import");
        JMenuItem deleteFile = new JMenuItem("Delete File");
        JMenuItem exportListFile = new JMenuItem("Export List");
        JMenuItem importListFile = new JMenuItem("Import List");

        fileMenu.add(exportFile);
        fileMenu.add(importFile);
        fileMenu.add(deleteFile);

        fileListMenu.add(exportListFile);
        fileListMenu.add(importListFile);

        exportFile.addActionListener(pbc);
        exportFile.setActionCommand("export");

        importFile.addActionListener(pbc);
        importFile.setActionCommand("import");

        exportListFile.addActionListener(pbc);
        exportListFile.setActionCommand("exportList");

        importListFile.addActionListener(pbc);
        importListFile.setActionCommand("importList");
       /**
        //fix this
        importFile.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Risk Game Saves", "rgd");
            fileChooser.setFileFilter(filter);
            fileChooser.addActionListener(pbc);
            fileChooser.showOpenDialog(this);
        });
        */

        deleteFile.addActionListener(pbc);
        deleteFile.setActionCommand("deleteFile");

    }
}
