import org.jdatepicker.impl.JDatePickerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents a possible Task that can be entered into the planner.
 *
 * @Author: Alex Cameron
 */
public class Task {
    private String desc, course, name;
    private Date dueDate;
    private int priority;

    public Task(String name, String desc, String course, Date dueDate, int priority) {
        this.dueDate = dueDate;
        this.course = course;
        this.desc = desc;
        this.priority = priority;
        this.name = name;
    }
    public Date getDueDate(){
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

    public static Task importTask(String task) throws ParseException {

        String[] info = task.split("#");
        String name = info[0];
        String desc = info[1];
        String course = info[2];
        Date dueDate = new SimpleDateFormat("yyyy-MM-dd").parse(info[3]);
        int priority = Integer.parseInt(info[4]);

        return new Task(name,desc,course,dueDate,priority);
    }

    public String serialize(){
        return name + "#" + desc + "#" + course + "#" + new SimpleDateFormat("MM-dd-yyyy").format(dueDate) + "#" + priority;
    }
    public String toXML(){
        return "<" + course +">" + course + "</" + course +">" + "< Task desc:" + desc +">" + course + "</Task>";
    }

    @Override
    public String toString(){
        return course+ ": " + name + " - " + new SimpleDateFormat("MM-dd-yyyy").format(dueDate);
    }
}
