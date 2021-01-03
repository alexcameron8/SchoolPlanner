import java.util.ArrayList;
/**
 * @Author: Alex Cameron
 */
public class PlannerModel {

    private ArrayList<Integer> priorities;
    private ArrayList<String> courses;

    public PlannerModel(){
        courses = new ArrayList<>();
        addCourses();
        priorities = new ArrayList<>();
        addPriority();
    }
    public void addCourses(){
        //Currently these will need to be updated manually, however I deem this easier than manually entering in course names each task entry
        courses.add("SYSC4001");
        courses.add("SYSC3303");
        courses.add("SYSC3120");
        courses.add("SYSC4106");
        courses.add("Other");

    }    public void addPriority(){
        priorities.add(3); //highest
        priorities.add(2); //medium
        priorities.add(1); //low
    }

    public ArrayList<Integer> getPriorities(){
        return priorities;
    }
    public ArrayList<String> getCourses(){
        return courses;
    }
}
