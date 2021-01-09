import org.jdatepicker.impl.JDatePickerImpl;

import java.util.Date;

/**
 * This class represents a possible Task that can be entered into the planner.
 *
 * @Author: Alex Cameron
 */
public class Task {
    private String desc, course, name;
    private JDatePickerImpl dueDate;
    private int priority;

    public Task(String name, String desc, String course, JDatePickerImpl dueDate, int priority) {
        this.dueDate = dueDate;
        this.course = course;
        this.desc = desc;
        this.priority = priority;
        this.name = name;
    }
    public JDatePickerImpl getDueDate(){
        return dueDate;
    }
    public int getPriority() {
        return priority;
    }
    public String getDesc() {
        return desc;
    }
    public String getCourse() {
        return course;
    }
    public String getTaskName(){
        return name;
    }
    public String serialize(){
        return name + "#" + desc + "#" + priority + "#" + dueDate + "#" + course;
    }
    public String toXML(){
        return "<" + course +">" + course + "</" + course +">" + "< Task desc:" + desc +">" + course + "</Task>";
    }

    @Override
    public String toString(){
        return course+ ": " + name + " - " + dueDate.getJFormattedTextField().getText();
    }
}
