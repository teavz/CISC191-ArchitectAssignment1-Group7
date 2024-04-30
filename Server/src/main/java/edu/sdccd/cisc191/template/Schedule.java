package edu.sdccd.cisc191.template;
import java.util.*;

public class Schedule {
    private ArrayList<Subject> schedule;
    private double gpa;
    private int id;
    public Schedule(ArrayList<Subject> a){
        schedule = a;
        gpa = 0;
    }

    public Schedule(ArrayList<Subject> a, double gpa) {
        schedule = a;
        this.gpa = gpa;
    }
    public double getGpa() {
        return gpa;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public static double calculateGPA(ArrayList<Subject> a){
        double grade;
        double totalGrades = 0;
        for (int i = 0; i < a.size(); i++) {
            grade = a.get(i).getGradeInClass();
            if (grade >= 89.5)
                totalGrades += 4;
            else if (grade >= 79.5)
                totalGrades += 3;
            else if (grade >= 69.5)
                totalGrades += 2;
            else if (grade >= 59.5)
                totalGrades++;
            if (a.get(i).isWeighted())
                totalGrades++;
        }
        return totalGrades/a.size();
    }

}
