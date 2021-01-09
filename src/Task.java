import java.util.Date;

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
}
