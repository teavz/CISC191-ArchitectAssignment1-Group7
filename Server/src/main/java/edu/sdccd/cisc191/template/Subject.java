package edu.sdccd.cisc191.template;

import java.util.ArrayList;

public class Subject extends Thread{
    private String nameOfSubject;
    private boolean weighted;
    private double gradeInClass;
    private ArrayList<Assignment> assignmentList;
    private int id;


    private int color;

    public Subject() {
        nameOfSubject = "";
        weighted = false;
        gradeInClass = 0.0;
        assignmentList = new ArrayList<Assignment>();
        color = 0;
    }
    public Subject(String name){
        nameOfSubject =name;
        weighted = false;
        gradeInClass = 0.0;
        assignmentList = new ArrayList<Assignment>();
        color = 0;
        id = 0;
    }
    public Subject(String name, int id){
        nameOfSubject = name;
        this.id = id;
    }


    public Subject(String name, boolean weight, double grade) {
        nameOfSubject = name;
        weighted = weight;
        gradeInClass = grade;
        assignmentList = new ArrayList<Assignment>();
        color = 0;
    }

    public Subject(String name, double grade) {
        nameOfSubject = name;
        gradeInClass = grade;
        assignmentList = new ArrayList<Assignment>();
        color = 0;
    }

    /**
     * Copy constructor
     *
     * @param subject edu.sdccd.cisc191.template.Subject object to copy
     */
    public Subject(Subject subject) {
        nameOfSubject = subject.getNameOfSubject();
        weighted = subject.isWeighted();
        gradeInClass = subject.getGradeInClass();
        assignmentList = subject.getAssignmentList();
        color = 0;

    }

    public String getNameOfSubject() {
        return nameOfSubject;
    }

    public void setNameOfSubject(String nameOfSubject) {
        this.nameOfSubject = nameOfSubject;
    }

    public boolean isWeighted() {
        return weighted;
    }

    public void setWeighted(boolean weighted) {
        this.weighted = weighted;
    }

    public double getGradeInClass() {
        return gradeInClass;
    }
    public boolean getWeighted(){
        return weighted;
    }

    public void setGradeInClass(double gradeInClass) {
        this.gradeInClass = gradeInClass;
    }

    public ArrayList<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(ArrayList<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }


    public void addAssignment(Assignment assignment) {
        assignmentList.add(assignment);
    }

    /**
     * getId is used for thread class.  If there is issues with renaming, just rename old subject getId to getID - simon
     * @return id
     */
    public int getID(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    /**
     * for the 2D array thing also should we have javadocs for any of these methods in our project
     *
     * @return
     * @author Simon Nguyen
     */
    public String[][] convertAssignmentListTo2DArray() {
        int numRows = assignmentList.size();
        int numCols = 0;


        /*  make sure we have enough slots to fit all the assignments if they have different
        amount of stuff in it
        */

        for (Assignment assignment : assignmentList) {
            int assignmentNumCols = assignment.getAssignmentArray().length;
            if (assignmentNumCols > numCols) {
                numCols = assignmentNumCols;
            }
        }

            String[][] assignmentArray = new String[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                Assignment temp = assignmentList.get(i);

                String[] assignmentDetails = temp.getAssignmentArray();
                assignmentArray[i] = assignmentDetails;
            }



        return assignmentArray;
        }

    public ArrayList<Assignment> convert2DArrayToAssignmentList(String[][] a) {
        ArrayList<Assignment> assignments = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            Assignment tempAssignment = new Assignment();
            if (a[i].length > 0)
                tempAssignment.setNameOfAssignment(a[i][0]);
            if (a[i].length > 1)
                tempAssignment.setDaysUntilDueDate(Integer.parseInt(a[i][1]));
            if (a[i].length > 2)
                tempAssignment.setPointsOfAssignment(Integer.parseInt(a[i][2]));
            if (a[i].length > 3)
                tempAssignment.setTotalPoints(Integer.parseInt(a[i][3]));
            if (a[i].length > 5)
                tempAssignment.setBusyWork(Boolean.parseBoolean(a[i][5]));
            assignments.add(tempAssignment);
        }


        return assignments;
    }
    public String convertAssignmentListToString() {
        String result = "";

        String[][] assignmentArray = convertAssignmentListTo2DArray();

        for (String[] row : assignmentArray) {
            for (String element : row) {
                result += element + " ";
            }
            result += "\n";
        }

        return result;
    }
}



