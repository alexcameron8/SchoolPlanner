import org.jdatepicker.impl.JDatePickerImpl;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

    public String priorityToString(int priority){
        if(priority==1){
            return "Low";
        }else if(priority==2){
            return "Medium";
        }else{
            return "High";
        }
    }
    public static int priorityToInt(String priority){
        if(priority.equals("Low")){
            return 1;
        }else if(priority.equals("Medium")){
            return 2;
        }else{
            return 3;
        }
    }

    public String dateFormatted(){
        return new SimpleDateFormat("EEE d MMM").format(dueDate);
    }
    public static Task importTask(String task) throws ParseException {

        String[] info = task.split("#");
        String name = info[0];
        String desc = info[1];
        String course = info[2];
        Date dueDate = new SimpleDateFormat("MM-dd-yyyy").parse(info[3]);
        int priority = Integer.parseInt(info[4]);

        return new Task(name,desc,course,dueDate,priority);
    }

    public String serialize(){
        return name + "#" + desc + "#" + course + "#" + new SimpleDateFormat("MM-dd-yyyy").format(dueDate) + "#" + priority;
    }
    public String toXML(){
        String s = "<Task>\n\t<Course>" + course + "</Course>\n\t<Date>" + dateFormatted() +"</Date>\n \t<Name>" + name + "</Name>\n\t<Priority>" + priorityToString(priority) + "</Priority>\n";
        if(!desc.isEmpty()){
            s+="\t<Description>\n\t\t"+ desc + "\n\t</Description>\n";
        }
        s+= "</Task>\n";
        return s;
    }

    public static Task newTaskFromElement(Element node) throws ParseException {
        NodeList nodeList;
        String name ="";
        String desc = "";
        String course = "";
        int priority = 0;
        Date dueDate = null;
        if(node.getElementsByTagName("Name").getLength()>0){
            name = node.getElementsByTagName("Name").item(0).getTextContent();
        }
        if(node.getElementsByTagName("Description").getLength()>0){
            desc = node.getElementsByTagName("Description").item(0).getTextContent();
        }
        if(node.getElementsByTagName("Course").getLength()>0){
            course = node.getElementsByTagName("Course").item(0).getTextContent();
        }
        if(node.getElementsByTagName("Priority").getLength()>0){
            priority = priorityToInt(node.getElementsByTagName("Priority").item(0).getTextContent());
        }
        if(node.getElementsByTagName("Date").getLength()>0){
            dueDate = new SimpleDateFormat("EEE d MMM").parse(node.getElementsByTagName("Date").item(0).getTextContent());
        }

        return new Task(name,desc,course,dueDate,priority);
    }

    @Override
    public String toString(){
        return course+ ": " + name + " - " + new SimpleDateFormat("MM-dd-yyyy").format(dueDate);
    }
}
