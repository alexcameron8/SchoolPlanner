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

        this.add(fileMenu);

        JMenuItem exportFile = new JMenuItem("Export");
        JMenuItem importFile = new JMenuItem("Import");
        JMenuItem deleteFile = new JMenuItem("Delete File");

        //DELETE LATER ONLY FOR TESTING
        JMenuItem testMenu = new JMenu("Test Menu");
        this.add(testMenu);
        JMenuItem testing = new JMenuItem("test");
        testMenu.add(testing);
        testing.addActionListener(pbc);
        testing.setActionCommand("test");

        fileMenu.add(exportFile);
        fileMenu.add(importFile);
        fileMenu.add(deleteFile);
        //

        exportFile.addActionListener(pbc);
        exportFile.setActionCommand("export");
        //fix this
        importFile.addActionListener((e) -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Risk Game Saves", "rgd");
            fileChooser.setFileFilter(filter);
            fileChooser.addActionListener(pbc);
            fileChooser.showOpenDialog(this);
        });

        deleteFile.addActionListener(pbc);
        deleteFile.setActionCommand("deleteFile");

    }
}
