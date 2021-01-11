import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlannerController implements ActionListener, ListSelectionListener {
    private PlannerView plannerView;
    private PlannerModel plannerModel;

    public PlannerController(PlannerModel plannerModel, PlannerView plannerView){
        this.plannerModel = plannerModel;
        this.plannerView = plannerView;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("submit")){
            try {
                if((Date) plannerView.getDate()==null) {
                    JOptionPane.showMessageDialog(null, "Invalid Entry: Date Error");
                }else if(plannerView.getTaskName()==null || plannerView.getTaskName().equals("")){
                    JOptionPane.showMessageDialog(null, "Invalid Entry: No Task Name");
                }else if(plannerView.getCourse()== null){
                    JOptionPane.showMessageDialog(null, "Invalid Entry: No Course Name");
                }else if(plannerView.getPriority()<0 || plannerView.getPriority()>3){
                    JOptionPane.showMessageDialog(null, "Invalid Entry: No Priority Selected");
                }else{
                    try {
                        plannerView.addTask(plannerView.getTaskName(),plannerView.getDesc(),plannerView.getCourse(),plannerView.getDate(),plannerView.getPriority());
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Task Created!");
                    plannerView.setupFields();
                }
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("newTask")){
            plannerView.setupFields();
        }
        else if(e.getActionCommand().equals("removeTask")){
            plannerView.removeTask();
        }
        else if(e.getActionCommand().equals("export")){
            String fileName = JOptionPane.showInputDialog(null, "Enter file name to export: ");
            plannerModel.export(fileName);
        }
        else if(e.getActionCommand().equals("import")){
            String fileName = JOptionPane.showInputDialog(null, "Enter file name to import: ");
            plannerModel.importFile(fileName);
            plannerView.removeAllTasks();
            plannerView.addAllTasks();
        }
        else if(e.getActionCommand().equals("deleteFile")){
            String fileName = JOptionPane.showInputDialog(null, "Enter file name to export file: ");
            if(new File(fileName).delete()){
                JOptionPane.showMessageDialog(null, "File '" + fileName + "' deleted");
            }
        }
        else if(e.getActionCommand().equals("exportList")){
            String fileName = JOptionPane.showInputDialog(null, "Enter file name to export: ");
            try {
                plannerModel.exportList(fileName);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("importList")){
            String fileName = JOptionPane.showInputDialog(null, "Enter file name to import: ");
            try {
                plannerModel.importList(fileName);
                plannerView.addAllTasks();
            } catch (ParserConfigurationException | IOException | SAXException | ParseException parserConfigurationException) {
                JOptionPane.showMessageDialog(null,"This file cannot be read or imported.");
            }
        }
        else if(e.getActionCommand().equals("removeOldTasks")){
            plannerModel.removeOldTasks(plannerModel.getTasks());
            plannerView.removeOldTasks();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if(plannerView.getCurrentTaskPanel()==null){
                plannerView.taskView();
            }
            if(plannerView.getListTasks().getSelectedValue()!=null) {
                String name = plannerView.getListTasks().getSelectedValue().getTaskName();
                String course = plannerView.getListTasks().getSelectedValue().getCourse();
                String desc = plannerView.getListTasks().getSelectedValue().getDesc();
                String priority = plannerView.getListTasks().getSelectedValue().priorityToString(plannerView.getListTasks().getSelectedValue().getPriority());
                String date = plannerView.getListTasks().getSelectedValue().dateFormatted();
                plannerView.setTaskLabel(name, course, desc, priority, date);
            }
        }
    }
}
