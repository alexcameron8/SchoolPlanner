import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannerController implements ActionListener {
    private PlannerView plannerView;
    private PlannerModel plannerModel;

    public PlannerController(PlannerModel plannerModel, PlannerView plannerView){
        this.plannerModel = plannerModel;
        this.plannerView = plannerView;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("submit")){
            if((Date) plannerView.getJDatePicker().getModel().getValue()==null) {
                JOptionPane.showMessageDialog(null, "Invalid Entry: Date Error");
            }else if(plannerView.getTaskName()==null || plannerView.getTaskName().equals("")){
                JOptionPane.showMessageDialog(null, "Invalid Entry: No Task Name");
            }else if(plannerView.getCourse()== null){
                JOptionPane.showMessageDialog(null, "Invalid Entry: No Course Name");
            }else if(plannerView.getPriority()<0 || plannerView.getPriority()>3){
                JOptionPane.showMessageDialog(null, "Invalid Entry: No Priority Selected");
            }else{
                plannerView.addTask(plannerView.getTaskName(),plannerView.getDesc(),plannerView.getCourse(),plannerView.getJDatePicker(),plannerView.getPriority());
                JOptionPane.showMessageDialog(null, "Task Created!");
                plannerView.setupFields();
            }
        }else if(e.getActionCommand().equals("newTask")){
            plannerView.setupFields();
        }else if(e.getActionCommand().equals("export")){
            String fileName = JOptionPane.showInputDialog(null, "Enter file name to export file: ");
            plannerModel.export(fileName);
        }else if(e.getActionCommand().equals("deleteFile")){
            String fileName = JOptionPane.showInputDialog(null, "Enter file name to export file: ");
            if(new File(fileName).delete()){
                JOptionPane.showMessageDialog(null, "File '" + fileName + "' deleted");
            }
        }
    }
}
