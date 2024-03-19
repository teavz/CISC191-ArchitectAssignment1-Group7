package edu.sdccd.cisc191.template;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
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
    void module3() {

        ViewStartScreen testClass = new ViewStartScreen();

        Subject testSubject = new Subject("AP Physics", false, 76);
        Assignment testAssignment = new Assignment();
        testClass.addSubject(testSubject);
        ArrayList <Subject> testSubjectArray = testClass.getSubjectArray();

        //test out the first Subject (sample for this test) with index 0
        testClass.viewAssignmentList(0);


        Parent root1 = new StackPane();
        Scene scene1 = new Scene(root1, 400, 300);
        testClass.setScene(scene1);

        Parent root2 = new StackPane();
        Scene scene2 = new Scene(root2, 400, 300);

        testClass.switchScene(scene2, "testTitle");
        Scene testScene = testClass.getScene();


        Assertions.assertEquals(scene2, testClass.getScene());



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

        Subject testSubject = new Subject("Subject", true, 94);
        testSubject.setColor(0);
        Subject testSubject2 = new Subject("Subject2", false, 89);
        testSubject2.setColor(1);

        ArrayList<Subject> testArray = new ArrayList<Subject>();
        testArray.add(testSubject);
        testArray.add(testSubject2);


        File testFile = new File("My_Schedule.txt");

        String actual = "Subject,94.0,false,0,\nSubject2,89.0,false,1,";

        testClass.convertSubjectToCSV(testArray);
        //file created is My_Schedule.txt???

        try {
            Scanner readFile = new Scanner(testFile);
            readFile.useDelimiter(",");

                assertEquals(actual, readFile.next());
        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }


    }

    @Test
    void module6() {

    }
}
