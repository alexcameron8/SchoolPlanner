import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
            if((Date) plannerView.getJDatePicker().getModel().getValue()!=null) {
                Date selectedDate = (Date) plannerView.getJDatePicker().getModel().getValue();
                DateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
                String reportDate = df.format(selectedDate);
                JOptionPane.showMessageDialog(null, reportDate);
            }else{
                System.out.println("Date Error");
            }
        }else if(e.getActionCommand().equals("newTask")){
            plannerView.setupFields();
        }
    }
}
