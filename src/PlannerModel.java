import org.jdatepicker.impl.JDatePickerImpl;

import java.io.*;
import java.text.ParseException;
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
    public void removeTask(Task task){
        tasks.remove(task);
    }
    public void removeAllTasks(){
        tasks.clear();
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
    public void importFile(String fileName){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String lines = bufferedReader.readLine();
            while(lines !=null){
                Task newTask = Task.importTask(lines);
                addTask(newTask);
                lines = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            System.out.println("The file "+ fileName + " cannot be found.");
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
