package edu.sdccd.cisc191.template;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
 * Unit tests for homework planner
 */
public class TestClass {

    //fixes the illegalstateexception toolkit error.
    @BeforeAll
    public static void setUp() {
        new JFXPanel();
    }


    /** Unit Test 1
     * 1. creates a new ViewStartScreen class for test screen
     * 2. Create subjects to be put into an ArrayList
     * 3. Adds subjects into ArrayLists
     * 4. Methods involving ArrayList
     * 5. assert that the items within the ArrayList are consistent with the methods that modified it
     *
     * @author Simon Nguyen & Willy Do
     */
    @Test
    void module1() {

        ViewStartScreen testClass = new ViewStartScreen();

        Subject testSubject = new Subject("AP Physics", true, 76);
        Subject testSubject2 = new Subject("AP Calculus BC", true, 91);
        Subject testSubject3 = new Subject("Culinary 1-2", false, 95);
        Subject testSubject4 = new Subject("Honors Biotechnology 1-2", true, 93);
        Subject testSubject5 = new Subject("AP US History", true, 45);
        Subject testSubject6 = new Subject("CISC191", true, 22);



        testClass.addSubject(testSubject);
        testClass.addSubject(testSubject2);
        testClass.addSubject(testSubject3);
        testClass.addSubject(testSubject4);
        testClass.addSubject(testSubject5);
        testClass.addSubject(testSubject6);
        assertEquals(testClass.getAtIndex(3), testSubject4);
        testClass.removeSubject(2);
        assertEquals(testClass.getAtIndex(3), testSubject5);
        assertEquals(testClass.findIndex(testSubject2), 1);
        testClass.setSubject(4, testSubject3);
        assertEquals(testClass.getAtIndex(4), testSubject3);
    }




    /** Unit Test 2
     *  1. Create new test ViewStartScreen, subject for unit test
     *  2. Create two sample assignments with whatever attributes I want.
     *  3. Create a sample arraylist with the 2 assignments to compare to later
     *  4. Create a sample 2D array to be able to test our 2darray to arraylist method
     *  5.  Use the method to create another test array list.
     *  6.  With iteration, compare the sample arraylist with the newly created arraylist
     *  created by the convert 2d array to arraylist method.
     *  7.  Assert the two arraylists and all their contents are equal, thus ensuring
     *  the method converting 2d array to arraylist works correctly
     *
     * @author Simon Nguyen
     */
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


        ArrayList<Assignment> expectedAssignments = new ArrayList<>();
        expectedAssignments.add(testAssignment);
        expectedAssignments.add(testAssignment2);

        String[][] testAssignmentArray =
                {{"Test 1", "9", "70", "80", "true"}, {"Test 2", "5", "50", "70", "false"}};

        ArrayList<Assignment> testArrayList = testSubject.convert2DArrayToAssignmentList(testAssignmentArray);


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




    /** Unit 3 Test
     * -  like test 5, Platform.runLater makes sure the UI stuff runs on JavaFX thread.  This
     *  fixed the annoying IllegalStateException error
     * 1. create a sample test class, and make some sample subjects and assignments.
     * 2.  Create a new stage and new scene, and set our testClass with that.
     * 3.  Create a new scene2 with different dimensions, and use our switchScene method to
     * change our ViewSmartScreen test class's scene to scene 2
     * 4.  Assert, using getter method, that the testClass's current scene is the same as scene 2,
     * which verifies that all our setter, getter and swtichScene methods work.
     *
     * @author Simon Nguyen
     */
    @Test
    void module3() {

        Platform.runLater(() -> {


            ViewStartScreen testClass = new ViewStartScreen();

            Subject testSubject = new Subject("AP Physics", false, 76);
            Assignment testAssignment = new Assignment();
            testSubject.addAssignment(testAssignment);
            testClass.addSubject(testSubject);
            ArrayList<Subject> testSubjectArray = testClass.getSubjectArray();


            Stage testStage = new Stage();
            testClass.setStage(testStage);
            Font font = Font.font("Montserrat", FontWeight.BOLD, 36);
            Parent root1 = new StackPane();
            Scene scene1 = new Scene(root1, 400, 300);
            OptionButton testButton = new OptionButton("Start Making Schedule", 100, 100);
            testButton.changeBackGroundColor();
            testButton.setFont(font);
            Label testLabel = new Label("Welcome!");
            testLabel.setFont(font);
            testClass.createLayout(testClass.createVBox(50.0, testLabel, testButton));

            testClass.setScene(scene1);

            Parent root2 = new StackPane();
            Scene scene2 = new Scene(root2, 400, 300);

            testClass.switchScene(scene1, "testTitle"); //Used to say scene2 which wouldn't make sense


            Assertions.assertEquals(scene2, testClass.getScene());


        });
    }





    /** Unit Test 4
     * - goal is to verify that moving between different classes work, thus
     * proving Object-Oriented Programming.  Or so I think.
     * 1. Create a new subject and a sample assignment, which will be added to the
     * subject's array list.
     * 2.  Using getters, assert that all content of the assignment taken from
     * our subject's assignment list is the same as our original assignment's
     * attributes
     *
     * @author Simon Nguyen
     */
    @Test
    void module4() {

        ViewStartScreen testClass = new ViewStartScreen();

        Subject testSubject = new Subject();
        Assignment testAssignment = new Assignment("Test 1");
        testAssignment.setBusyWork(true);
        testAssignment.setTotalPoints(80);
        testAssignment.setPointsOfAssignment(70);
        testAssignment.setDaysUntilDueDate(9);

        testSubject.addAssignment(testAssignment);

        // purposely left it at testSubject.get(0) to test all the getters and setters

        assertEquals(testAssignment, testSubject.getAssignmentList().get(0));
        assertEquals(testAssignment.getTotalPoints(), testSubject.getAssignmentList().get(0).getTotalPoints());
        assertEquals(testAssignment.getPointsOfAssignment(), testSubject.getAssignmentList().get(0).getPointsOfAssignment());
        assertEquals(testAssignment.getDaysUntilDueDate(), testSubject.getAssignmentList().get(0).getDaysUntilDueDate());
        assertEquals(testAssignment.isBusyWork(), testSubject.getAssignmentList().get(0).isBusyWork());
    }


    /** Unit Test 5
     * - Platform.runLater makes sure the UI stuff runs on JavaFX thread.  This
     * fixed the annoying IllegalStateException error
     *
     * 1. create new testClass and two sample test subjects with different attributes
     * for testing.  Put these subjects in an arraylist.
     * 2.  Create a sample string of what the CSV file is supposed to look like.
     * 3. Test our arraylist to CSV method by plugging in the sample arraylist.
     * 4. using a Scanner, the unit test will read the file.
     * 5. With iteration, read each line of file and build the string.
     * 6. Assert that the sample created from reading the CSV file is identical to the
     * sample string we created earlier.
     *
     * @author Simon Nguyen
     */
    @Test
    void module5() {

        Platform.runLater(() -> {
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
        //file created is My_Schedule.txt right

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
        });
    }


}
