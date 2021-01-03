import java.util.Date;

/**
 * This class represents a possible Task that can be entered into the planner.
 */
public class Task {
    private String desc, course;
    private Date dueDate;
    private int priority;

    public Task(String desc, String course, Date dueDate, int priority) {
        this.dueDate = dueDate;
        this.course = course;
        this.desc = desc;
        this.priority = priority;
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
}
