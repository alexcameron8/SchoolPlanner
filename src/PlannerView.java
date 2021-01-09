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
        taskListPanel.setLayout(new BoxLayout(taskListPanel,BoxLayout.PAGE_AXIS));
        JLabel listTasksLabel = new JLabel("List of Tasks:");
        JButton removeTaskButton = new JButton("Remove Selected Task");
        removeTaskButton.setFocusPainted(false);

        listTasks = new JList<>(modelTasks);
        listTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        taskListPanel.add(listTasksLabel);
        taskListPanel.add(listTasks);
        taskListPanel.add(removeTaskButton);
        this.add(taskListPanel,BorderLayout.EAST);
        //ActionListener for task removal button
        removeTaskButton.addActionListener(pbc);
        removeTaskButton.setActionCommand("removeTask");
    }
    public void addTask(String name, String desc, String course, Date dueDate, int priority){
        Task task = new Task(name,desc,course,dueDate,priority);
        plannerModel.addTask(task);
        modelTasks.addElement(task);
    }

    public void removeTask(){
        Task task = listTasks.getSelectedValue();
        if(task!=null){
            modelTasks.remove(listTasks.getSelectedIndex());
            plannerModel.removeTask(task);
        }

    }

    //come back to after if you want to add pop up of whether to merge the tasks with whatever is already in model tasks
    public void mergeList(){

    }
    public void removeAllTasks(){
        if(modelTasks.size()>0){
            for(int i=0;i<modelTasks.size();i++){
                modelTasks.removeElementAt(i);
            }
            System.out.println("Removing all tasks");
        }
    }
    //used for importing eventually add an argument of boolean true if merged and false no merge
    public void addAllTasks(){
        for(Task tasks : plannerModel.getTasks()){
            modelTasks.addElement(tasks);
        }
    }

    public void test(){
        System.out.println("ArrayList elements");
        for(Task tasks : plannerModel.getTasks()){
            System.out.println(tasks);
        }
        System.out.println("Model Elements");
        for(int i=0;i<modelTasks.size();i++){
            System.out.println(modelTasks.get(i));
        }
    }

    public Date getDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getJFormattedTextField().getText());
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

