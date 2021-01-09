import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

/**
 * @Author: Alex Cameron
 */
public class PlannerView extends JFrame {

    private PlannerModel plannerModel;
    private JDatePickerImpl datePicker;
    private PlannerController pbc;
    private JPanel taskPanel, currentTask;
    private JList<Task> listTasks;
    private DefaultListModel<Task> modelTasks;
    private JComboBox<Integer> priorities;
    private JComboBox<String> courses;
    private JTextArea nameField, descField;


    public PlannerView(){
        super("School Planner");

        this.plannerModel = new PlannerModel();

        this.getContentPane().setLayout(new BorderLayout());

        pbc = new PlannerController(plannerModel,this);
        this.modelTasks =new DefaultListModel<>();

        setupView();

        setupFields();

        displayTaskList();

        this.setVisible(true);
        this.setSize(700,700);
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
        //Adding Action Listener for new task JButton
        newTask.addActionListener(pbc);
        newTask.setActionCommand("newTask");
    }

    public void setupFields(){
        if(taskPanel!=null){
            taskPanel.setVisible(false);
            currentTask.setVisible(false);
        }
        taskView();
        taskPanel = new JPanel();
        //Course setup
        JPanel coursePanel = new JPanel();
        JLabel selectCourse = new JLabel("Select Course");
        courses = new JComboBox<String>();

        for(int i=0; i<plannerModel.getCourses().size();i++){
            courses.addItem(plannerModel.getCourses().get(i));
        }
        coursePanel.add(selectCourse);
        coursePanel.add(courses);


        //Priority setup
        JPanel priorityPanel = new JPanel();
        JLabel priorityLevel = new JLabel("Select Priority");
        priorities = new JComboBox<Integer>();

        //populate priorities JComboBox
        for(int i=0; i<plannerModel.getPriorities().size();i++){
            priorities.addItem(plannerModel.getPriorities().get(i));
        }
        priorityPanel.add(priorityLevel);
        priorityPanel.add(priorities);

        //Name Setup
        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Name of Task: ");
        nameField = new JTextArea(1,15);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        //Description Setup
        JPanel descPanel = new JPanel();
        JLabel descLabel = new JLabel("Description of Task: ");
        descField = new JTextArea(1,25);
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
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        dueDatePanel.add(dueDateLabel);
        dueDatePanel.add(datePicker);

        //Submit Task button
        JButton submitTask = new JButton("Submit Task");
        submitTask.setFocusPainted(false);

        taskPanel.add(namePanel);
        taskPanel.add(descPanel);
        taskPanel.add(coursePanel);
        taskPanel.add(priorityPanel);
        taskPanel.add(dueDatePanel);
        taskPanel.add(submitTask);


        //Adding ActionListeners
        //Submit JButton
        submitTask.addActionListener(pbc);
        submitTask.setActionCommand("submit");

        this.add(taskPanel);
    }

    public void displayTaskList(){
        JPanel taskListPanel = new JPanel();
        JLabel listTasksLabel = new JLabel("List of Tasks:");
        listTasks = new JList<>(modelTasks);
        listTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        taskListPanel.add(listTasksLabel);
        taskListPanel.add(listTasks);
        this.add(taskListPanel,BorderLayout.EAST);

    }
    public void addTask(String name, String desc, String course, JDatePickerImpl dueDate, int priority){
        Task task = new Task(name,desc,course,dueDate,priority);
        plannerModel.addTask(task);
        modelTasks.addElement(task);
    }

    public JDatePickerImpl getJDatePicker(){
        return datePicker;
    }
    public int getPriority(){
        return Integer.valueOf((Integer) priorities.getSelectedItem());
    }
    public String getCourse(){
        return String.valueOf(courses.getSelectedItem());
    }
    public String getTaskName(){
        return nameField.getText();
    }
    public String getDesc(){
        return descField.getText();
    }


    public void taskView(){
        currentTask = new JPanel();
        JLabel taskCourseLabel = new JLabel("Course: " );
        JLabel taskNameLabel = new JLabel("Name: " );
        JLabel taskDescLabel = new JLabel("Description: " );
        JLabel taskPriority = new JLabel("Priority: " );
        JLabel taskDateLabel = new JLabel("Date: " );

        currentTask.add(taskCourseLabel);
        currentTask.add(taskNameLabel);
        currentTask.add(taskDescLabel);
        currentTask.add(taskPriority);
        currentTask.add(taskDateLabel);
        this.add(currentTask,BorderLayout.PAGE_END);
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

