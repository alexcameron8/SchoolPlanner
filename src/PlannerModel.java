import org.jdatepicker.impl.JDatePickerImpl;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    public void exportList(String fileName) throws IOException {
        Task temp = null;
        ArrayList<Task> taskList = tasks;
        ArrayList<Task> tempList;
        String s ="<School Planner>\n";
        while(taskList.size()>0){
            temp = lowestDate(tasks); //returns task with most recent due date
            tempList = sameDate(taskList,temp.getDueDate());
            s+= dateXML(tempList);
            taskList.removeAll(tempList);
        }

        s+= "</School Planner>";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        bufferedWriter.write(s);
        bufferedWriter.close();
    }

    public Task lowestDate(ArrayList<Task> tasks){
        Task temp=null;
        for(int i=0; i< tasks.size();i++){
            if(i==0){
                temp = tasks.get(i);
            }else if(tasks.get(i).getDueDate().before(temp.getDueDate())){
                temp = tasks.get(i);
            }
        }
        System.out.println(temp.getDueDate());
        return temp;
    }

    public ArrayList<Task> sameDate(ArrayList<Task> tasks, Date date ){
        ArrayList<Task> commonTaskDueDates = new ArrayList<>();
        for(Task task : tasks){
            if(task.getDueDate().equals(date)){
                commonTaskDueDates.add(task);
            }
        }
        return commonTaskDueDates;
    }
    public String dateXML(ArrayList<Task> tasks){
        String s = "<" + tasks.get(0).dateFormatted() + ">\n";
        for(Task task : tasks){
            s += task.toXML();
        }
        s += "</" + tasks.get(0).dateFormatted() + ">\n\n";
        return s;
    }

    public <T extends Comparable<? super T>> void recursiveBubbleSortDate (List<Task> tasks,int n)
    {
        Task[] theArray = tasks.toArray(new Task[0]);

        if(n == 1){ //Base case (final element of array)
            for(int i=0;i<tasks.size();i++){
                System.out.println(tasks.get(i).getTaskName() + "  "+ tasks.get(i).getDueDate());
            }
            return;
        }
        int currIndex = theArray.length - n; //recursively marks the index being checked each recursive call
        if(theArray[currIndex].getDueDate().after(theArray[currIndex+1].getDueDate())){ //compares the 2 adjacent elements of the index
            Task temp = theArray[currIndex]; //swaps the current index with the index ahead of curr
            theArray[currIndex] = theArray[currIndex+1];
            theArray[currIndex+1] = temp;
        }

        List<Task> orderedTasks = Arrays.asList(theArray);
        recursiveBubbleSortDate(orderedTasks, n-1); //decreases the size by 1 each time moving to next element of array to sort
    }

    public ArrayList<Integer> getPriorities(){
        return priorities;
    }
    public ArrayList<String> getCourses(){
        return courses;
    }
    public ArrayList<Task> getTasks(){ return tasks;}

    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);


        ArrayList<Integer> list2 = new ArrayList<>();

        list2.add(2);
        list2.add(3);

        list1.removeAll(list2);
        for(int i=0; i<list1.size();i++){
            System.out.println(list1.toString());
        }

    }
}
