package edu.sdccd.cisc191.template;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import javafx.scene.paint.Paint;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Module 1:
 * verify at least one of the methods relating to an arraylist
 * module 3:
 * verify a label is properly assigned like on line 311
 * module 4:
 * verify the assignment arraylist translates well into the Subject class
 * module 5:
 * verify save schedule works
 */
public class TestClass {

    @Test
    void module1() {

        ViewStartScreen testClass = new ViewStartScreen();

        Subject testSubject = new Subject("AP Physics", false, 76);

        Assignment testAssignment = new Assignment();

        testClass.addSubject(testSubject);


        ArrayList <Subject> testSubjectArray = testClass.getSubjectArray();

        assertEquals(testSubject, testClass.getSubjectArray().get(0));



    }


    @Test
    void module2() {

        ViewStartScreen testClass = new ViewStartScreen();


        Subject testSubject = new Subject("AP Physics", false, 76);

        Assignment testAssignment = new Assignment("Test 1");
        testAssignment.setBusyWork(true);
        testAssignment.setTotalPoints(80);
        testAssignment.setPointsOfAssignment(70);
        testAssignment.setDaysUntilDueDate(9);


        Assignment testAssignment2 = new Assignment("Test 2");
        testAssignment2.setBusyWork(false);
        testAssignment2.setTotalPoints(70);
        testAssignment2.setPointsOfAssignment(50);
        testAssignment2.setDaysUntilDueDate(5);

        ArrayList<Assignment> testArrayList1 = new ArrayList<Assignment>();
        testArrayList1.add(testAssignment);


        ArrayList<Assignment> expectedAssignments = new ArrayList<>();
        expectedAssignments.add(testAssignment);
        expectedAssignments.add(testAssignment2);

        String[][] testAssignmentArray =
                {{"Test 1", "9", "70", "80", "true"}, {"Test 2", "5", "50", "70", "false"}};

        ArrayList<Assignment> testArrayList = testSubject.convert2DArrayToAssignmentList(testAssignmentArray);


        ArrayList<Assignment> actualAssignments = testSubject.convert2DArrayToAssignmentList(testAssignmentArray);

        assertEquals(expectedAssignments.size(), testArrayList.size());
        // Compare the lists by iterating over each element
        for (int i = 0; i < expectedAssignments.size(); i++) {
            Assignment expected = testArrayList.get(i);
            Assignment actual = testSubject.convert2DArrayToAssignmentList(testAssignmentArray).get(i);

            assertEquals(expected.getNameOfAssignment(), actual.getNameOfAssignment());
            assertEquals(expected.getDaysUntilDueDate(), actual.getDaysUntilDueDate());
            assertEquals(expected.getPointsOfAssignment(), actual.getPointsOfAssignment());
            assertEquals(expected.getTotalPoints(), actual.getTotalPoints());
            assertEquals(expected.isBusyWork(), actual.isBusyWork());

        }
    }


    /**
     * See if our application updates information for labels accordingly, like around line 311
     */
    @Test
    void module3() {
        //tbd
    }


    @Test
    void module4() {
        Subject testSubject = new Subject();
        Assignment testAssignment = new Assignment();

        testSubject.addAssignment(testAssignment);

        // see if methods work
        Assignment sampleAssignment = testSubject.getAssignmentList().get(0);

        assertEquals(testAssignment, sampleAssignment);
    }

    @Test
    void module5() {
        ViewStartScreen testClass = new ViewStartScreen();

        Subject testSubject = new Subject("Subject", true, 94.0);
        testSubject.setColor(0);
        Subject testSubject2 = new Subject("Subject2", false, 89.0);
        testSubject2.setColor(1);

        ArrayList<Subject> testArray = new ArrayList<Subject>();
        testArray.add(testSubject);
        testArray.add(testSubject2);


        File testFile = new File("My_Schedule.txt");

        String actual = "Subject,94.0,false,0,\nSubject2,89.0,false,1,";

        testClass.convertSubjectToCSV(testArray);
        //file created is My_Schedule.txt???

        String test = "";
        try {
            Scanner readFile = new Scanner(testFile);
            readFile.useDelimiter(",");

            while (readFile.hasNextLine()) {
                String temp = readFile.nextLine();
                test += temp;
            }
                assertEquals(actual, test);

        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }


    }


}
