import org.jdatepicker.impl.JDatePickerImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
        String s ="<SchoolPlanner>\n";
        while(taskList.size()>0){
            temp = lowestDate(tasks); //returns task with most recent due date
            tempList = sameDate(taskList,temp.getDueDate());
            s+= dateXML(tempList);
            taskList.removeAll(tempList);
        }

        s+= "</SchoolPlanner>";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
        bufferedWriter.write(s);
        bufferedWriter.close();
    }

    public void importList(String fileName) throws ParserConfigurationException, IOException, SAXException, ParseException {
        if(tasks.size()>0){
            tasks = new ArrayList<>();
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new FileInputStream(fileName));


        NodeList nodeList = document.getElementsByTagName("Task");

        for(int i = 0; i< nodeList.getLength();i++) {
            Element element = (Element) nodeList.item(i);
            addTask(Task.newTaskFromElement(element));
        }
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
        String s = "";
        for(Task task : tasks){
            s += task.toXML();
        }
        return s;
    }


    public ArrayList<Integer> getPriorities(){
        return priorities;
    }
    public ArrayList<String> getCourses(){
        return courses;
    }
    public ArrayList<Task> getTasks(){ return tasks;}

}
