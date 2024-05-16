package edu.sdccd.cisc191.template;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.*;
import java.lang.Math;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Schedule {
    private ArrayList<Subject> schedule;
    private ConcurrentLinkedDeque<Subject> subjects;
    private double gpa;
    private int id;
    public Schedule(ArrayList<Subject> a){
        schedule = a;
        gpa = calculateGPA(a);
        id = (int)(Math.random() * 10000000);
        subjects = arrayListToQueue(a);
    }

    public Schedule(ArrayList<Subject> a, double gpa) {
        schedule = a;
        this.gpa = gpa;
        id = (int)(Math.random() * 10000000);
        matchIDS(a, id);
        subjects = arrayListToQueue(a);
    }
    public Schedule(ConcurrentLinkedDeque<Subject> a) {
        this.gpa = gpa;
        id = (int)(Math.random() * 10000000);
        matchIDS(a, id);
        subjects = a;
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

    public ConcurrentLinkedDeque<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ConcurrentLinkedDeque<Subject> subjects) {
        this.subjects = subjects;
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

    /**
     * @param a
     * @param id
     */
    public static void matchIDS(ArrayList<Subject> a, int id) {
        for (int i = 0; i < a.size(); i++) {
            a.get(i).setId(id);
        }
    }
    public static void matchIDS(ConcurrentLinkedDeque<Subject> a, int id) {
        while (a.peekFirst() != null)
            a.pollFirst().setId(id);
    }
    public static ConcurrentLinkedDeque<Subject> arrayListToQueue(ArrayList<Subject> a) {
        ConcurrentLinkedDeque<Subject> stack = new ConcurrentLinkedDeque<>();
        for (Subject subject : a) {
            stack.addLast(subject);
        }
        return stack;
    }
}
