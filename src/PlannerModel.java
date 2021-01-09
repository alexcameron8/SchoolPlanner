import org.jdatepicker.impl.JDatePickerImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Author: Alex Cameron
 */
public class PlannerModel {

    private ArrayList<Integer> priorities;
    private ArrayList<String> courses;
    private ArrayList<Task> tasks;

    public PlannerModel(){
        courses = new ArrayList<>();
        addCourses();
        priorities = new ArrayList<>();
        addPriority();
        tasks = new ArrayList<>();
    }

    public void addCourses(){
        //Currently these will need to be updated manually, however I deem this easier than manually entering in course names each task entry
        courses.add("SYSC4001");
        courses.add("SYSC3303");
        courses.add("SYSC3120");
        courses.add("SYSC4106");
        courses.add("Other");

    }
    public void addPriority(){
        priorities.add(3); //highest
        priorities.add(2); //medium
        priorities.add(1); //low
    }
    public void addTask(Task task){
        tasks.add(task);
    }

    public String serialize(){
        String s = "";
        for(int i = 0; i < getTasks().size(); i++){
            s += getTasks().get(i).serialize() + "\n";
        }
        return s;
    }
    public void export(String filename){
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            bufferedWriter.write(serialize());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Integer> getPriorities(){
        return priorities;
    }
    public ArrayList<String> getCourses(){
        return courses;
    }
    public ArrayList<Task> getTasks(){ return tasks;}
}
