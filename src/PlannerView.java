import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class PlannerView extends JFrame {

    private PlannerModel plannerModel;

    public PlannerView(){
        super("School Planner");

        this.plannerModel = new PlannerModel();

        this.getContentPane().setLayout(new BorderLayout());

        setupView();

        setupFields();

        this.setVisible(true);
        this.setSize(500,500);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setupView(){
        this.setJMenuBar(new MenuBarView(plannerModel,this));
        taskAddBar();
    }

    public void taskAddBar(){
        JPanel taskBar = new JPanel();
        JButton newTask = new JButton("New Task");
        newTask.setFocusPainted(false);
        taskBar.add(newTask);
        this.add(taskBar, BorderLayout.PAGE_START);
    }

    public void setupFields(){
        JPanel taskPanel = new JPanel();
        //Course setup
        JPanel coursePanel = new JPanel();
        JLabel selectCourse = new JLabel("Select Course");
        JComboBox courses = new JComboBox<String>();

        for(int i=0; i<plannerModel.getCourses().size();i++){
            courses.addItem(plannerModel.getCourses().get(i));
        }
        coursePanel.add(selectCourse);
        coursePanel.add(courses);


        //Priority setup
        JPanel priorityPanel = new JPanel();
        JLabel priorityLevel = new JLabel("Select Priority");
        JComboBox priorities = new JComboBox<Integer>();

        //populate priorities JComboBox
        for(int i=0; i<plannerModel.getPriorities().size();i++){
            priorities.addItem(plannerModel.getPriorities().get(i));
        }
        priorityPanel.add(priorityLevel);
        priorityPanel.add(priorities);

        //Description Setup
        JPanel descPanel = new JPanel();
        JLabel descLabel = new JLabel("Description of Task: ");
        JTextArea descField = new JTextArea();
        descPanel.add(descLabel);
        descPanel.add(descField);

        //Date setup
        JPanel dueDatePanel = new JPanel();
        JLabel dueDateLabel = new JLabel("Select Due Date");

        //JDate API (Code used from https://stackoverflow.com/questions/26794698/how-do-i-implement-jdatepicker)
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        dueDatePanel.add(dueDateLabel);
        dueDatePanel.add(datePicker);

        taskPanel.add(descPanel);
        taskPanel.add(coursePanel);
        taskPanel.add(priorityPanel);
        taskPanel.add(dueDatePanel);

        this.add(taskPanel);
    }


    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
    public static void main(String[] args) {
        new PlannerView();
    }
}

