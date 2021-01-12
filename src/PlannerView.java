import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Flow;

/**
 * @Author: Alex Cameron
 */
public class PlannerView extends JFrame {

    private PlannerModel plannerModel;
    private JDatePickerImpl datePicker;
    private PlannerController pbc;
    private JPanel taskPanel, currentTask, centerPanel;
    private JList<Task> listTasks;
    private DefaultListModel<Task> modelTasks;
    private JComboBox<Integer> priorities;
    private JComboBox<String> courses;
    private JTextArea nameField, descField;
    private JLabel taskCourseLabel,taskNameLabel,taskDescLabel,taskPriorityLabel, taskDateLabel;

    public PlannerView(){
        super("School Planner");

        this.plannerModel = new PlannerModel();
        this.getContentPane().setLayout(new BorderLayout());

        pbc = new PlannerController(plannerModel,this);
        this.modelTasks = new DefaultListModel<>();

        setupView();

        setupFields();

        displayTaskList();

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setSize(900,700);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setupView(){
        this.setJMenuBar(new MenuBarView(plannerModel,this));
        taskAddBar();
    }

    public void taskAddBar(){
        JPanel taskBar = new JPanel();
        taskBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton newTask = new JButton("New Task");
        newTask.setFocusPainted(false);
        //Remove Button
        JButton removeTaskButton = new JButton("Remove Selected Task");
        removeTaskButton.setFocusPainted(false);
        removeTaskButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        JButton removeOldTasks = new JButton("Remove Old Tasks");
        removeOldTasks.setFocusPainted(false);

        JButton generateList = new JButton("Generate List");
        generateList.setFocusPainted(false);

        taskBar.add(removeOldTasks);
        taskBar.add(removeTaskButton);
        taskBar.add(Box.createRigidArea(new Dimension(40,0)));
        taskBar.add(newTask);
        taskBar.add(generateList);

        this.add(taskBar, BorderLayout.PAGE_START);
        //Adding Action Listener for new task JButton
        newTask.addActionListener(pbc);
        newTask.setActionCommand("newTask");

        //ActionListener for task removal button
        removeTaskButton.addActionListener(pbc);
        removeTaskButton.setActionCommand("removeTask");

        //Remove old tasks
        removeOldTasks.addActionListener(pbc);
        removeOldTasks.setActionCommand("removeOldTasks");

        //generate list
        generateList.addActionListener(pbc);
        generateList.setActionCommand("generateList");

        //Design
        newTask.setBackground(Color.WHITE);
        removeTaskButton.setBackground(Color.WHITE);
        removeOldTasks.setBackground(Color.WHITE);
        generateList.setBackground(Color.WHITE);

    }

    public void setupFields(){
        if(taskPanel!=null){
            taskPanel.setVisible(false);
            centerPanel.setVisible(false);
        }
        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel,BoxLayout.PAGE_AXIS));
        //Course setup
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel selectCourse = new JLabel("Select Course");
        courses = new JComboBox<String>();

        for(int i=0; i<plannerModel.getCourses().size();i++){
            courses.addItem(plannerModel.getCourses().get(i));
        }
        coursePanel.add(selectCourse);
        coursePanel.add(courses);

        //Submit Task button
        JButton submitTask = new JButton("Submit Task");
        submitTask.setFocusPainted(false);

        //priority setup
        JPanel priorityPanel = new JPanel();
        priorityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel priorityLevel = new JLabel("Select Priority");
        priorities = new JComboBox<Integer>();

        //populate priorities JComboBox
        for(int i=0; i<plannerModel.getPriorities().size();i++){
            priorities.addItem(plannerModel.getPriorities().get(i));
        }
        priorityPanel.add(priorityLevel);
        priorityPanel.add(priorities);

        //setup areas for name and description
        setupTextFields();

        //date setup
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
        datePicker.setButtonFocusable(false);
        dueDatePanel.add(dueDateLabel);
        dueDatePanel.add(datePicker);

        taskPanel.add(Box.createRigidArea(new Dimension(0,10)));
        taskPanel.add(coursePanel);
        taskPanel.add(Box.createRigidArea(new Dimension(0,10)));
        taskPanel.add(priorityPanel);
        taskPanel.add(Box.createRigidArea(new Dimension(10,10)));
        taskPanel.add(dueDatePanel);
        taskPanel.add(Box.createRigidArea(new Dimension(0,10)));
        taskPanel.add(submitTask);
        taskPanel.add(Box.createRigidArea(new Dimension(0,10)));
        centerPanel.add(Box.createHorizontalStrut(10));
        centerPanel.add(taskPanel);
        this.add(centerPanel);

        //ActionListeners
        //Submit JButton
        submitTask.addActionListener(pbc);
        submitTask.setActionCommand("submit");

        //Design
        Border border = BorderFactory.createEtchedBorder(1);
        //taskPanel
        TitledBorder title;
        title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "New Task");
        title.setTitleJustification(3);
        taskPanel.setBorder(new TitledBorder(title));
        //Priority Panel
        priorityPanel.setBorder(border);

        //coursePanel
        coursePanel.setBorder(border);

        submitTask.setBackground(Color.WHITE);

        //name & description field
        nameField.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        descField.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        courses.setBackground(Color.white);
        priorities.setBackground(Color.white);
        datePicker.setBackground(Color.WHITE);
        dueDatePanel.setBorder(border);

    }

    public void setupTextFields(){
        //Name Setup
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameField = new JTextArea(1,15);

        nameField.addKeyListener(new KeyAdapter() {
            @Override
            /**
             * This allows user to hit tab to focus to the next JTextArea field and not enter "useless" spaces in the field.
             * Code used from:
             * https://kodejava.org/how-do-i-move-focus-from-jtextarea-using-tab-key/
             */
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    if (e.getModifiersEx() > 0) {
                        nameField.transferFocusBackward();
                    } else {
                        nameField.transferFocus();
                    }
                    e.consume();
                }
            }
        });

        namePanel.add(nameField);

        //Description Setup
        JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        descField = new JTextArea(1,25);

        descField.addKeyListener(new KeyAdapter() {
            @Override
            /**
             * This allows user to hit tab to focus to the next JTextArea field and not enter "useless" spaces in the field.
             * Code used from:
             * https://kodejava.org/how-do-i-move-focus-from-jtextarea-using-tab-key/
             */
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    if (e.getModifiersEx() > 0) {
                        descField.transferFocusBackward();
                    } else {
                        descField.transferFocus();
                    }
                    e.consume();
                }
            }
        });

        descPanel.add(descField);

        taskPanel.add(namePanel);
        taskPanel.add(descPanel);

        //Design
        TitledBorder nameTitle, descTitle;
        nameTitle = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Name of Task:");
        namePanel.setBorder(nameTitle);

        descTitle= BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED), "Description of Task:");
        descPanel.setBorder(descTitle);
    }

    public void displayTaskList(){
        JPanel taskListPanel = new JPanel();

        listTasks = new JList<>(modelTasks);
        listTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        taskListPanel.add(new JScrollPane(listTasks));
        this.add(taskListPanel, BorderLayout.WEST);

        //Design
        TitledBorder title;
        title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "List of Tasks");
        taskListPanel.setBorder(new TitledBorder(title));

        //Selection Listener
        listTasks.addListSelectionListener(pbc);
    }

    public void taskView(){
        currentTask = new JPanel();
        currentTask.setLayout(new BoxLayout(currentTask,BoxLayout.Y_AXIS));
        taskCourseLabel = new JLabel("Course: " );
        taskNameLabel = new JLabel("Name: " );
        taskDescLabel = new JLabel("Description: " );
        taskPriorityLabel = new JLabel("Priority:  " );
        taskDateLabel = new JLabel("Date: " );

        currentTask.add(taskCourseLabel);
        currentTask.add(taskNameLabel);
        currentTask.add(taskDescLabel);
        currentTask.add(taskPriorityLabel);
        currentTask.add(taskDateLabel);
        this.add(currentTask,BorderLayout.EAST);

        TitledBorder title;
        title = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(1), "Task Description");
        currentTask.setBorder(new TitledBorder(title));
    }

    public JPanel getCurrentTaskPanel(){
        return currentTask;
    }

    public void setTaskLabel(String name,String course, String desc, String priority, String date){
        taskCourseLabel.setText("Course: "+ course);
        taskDateLabel.setText("Due Date: "+ date);
        taskDescLabel.setText("Description: "+ desc);
        taskNameLabel.setText("Name: "+ name);
        taskPriorityLabel.setText("Priority:" + priority);
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
    public void removeOldTasks(){
        Date currentDate = new Date();
        for(int i=0;i<modelTasks.size();i++){
            if(modelTasks.getElementAt(i).getDueDate().before(currentDate)){
                modelTasks.remove(i);
            }
        }
    }

    public void removeAllTasks(){
        if(modelTasks.size()>0){
            modelTasks.removeAllElements();
            System.out.println("REMOVEALL");
        }
    }
    //used for importing eventually add an argument of boolean true if merged and false no merge
    public void addAllTasks(){
        boolean contains = false;
        for(Task tasks : plannerModel.getTasks()){
            for(int i = 0; i<modelTasks.size();i++){
                if(modelTasks.getElementAt(i).getTaskName().equals(tasks.getTaskName())){
                    contains=true;
                }
            }
            if(!contains) {
                modelTasks.addElement(tasks);
            }
            contains = false;
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
        if (datePicker.getJFormattedTextField().getText().equals("")) {
            return null;
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").parse(datePicker.getJFormattedTextField().getText());
        }
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

    public JList<Task> getListTasks(){
        return listTasks;
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

