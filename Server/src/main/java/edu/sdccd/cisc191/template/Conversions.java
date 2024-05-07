package edu.sdccd.cisc191.template;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Conversions {
    /**
     * allows the user to import a previously saved schedule in the form of a csv file
     *
     * @return subject array to be imported
     */
    public ArrayList<Subject> convertCSVFileToSubject() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT Files", "*.TXT"),
                new FileChooser.ExtensionFilter("txt files", "*.txt"));
        File inFile = fc.showOpenDialog(null);
        ArrayList<Subject> subjectSave = new ArrayList<>();
        if (inFile != null) {
            try {
                Scanner inputStream = new Scanner(inFile);
                while (inputStream.hasNextLine()) {
                    ArrayList<Assignment> assignments = new ArrayList<>();
                    String temp = inputStream.nextLine();
                    String[] tokens = temp.split(",");
                    Subject tempSubject = new Subject(tokens[0], Boolean.parseBoolean(tokens[2]), Double.parseDouble(tokens[1]));
                    tempSubject.setColor(Integer.parseInt(tokens[3]));
                    for (int i = 4; i < tokens.length; i++) {
                        assignments.add(new Assignment(tokens[i]));
                    }
                    tempSubject.setAssignmentList(assignments);
                    subjectSave.add(tempSubject);
                }
            } catch (FileNotFoundException e) {
                e.getMessage();
            }
        } else {
            System.out.println("Invalid file");
        }
        return subjectSave;
    }

//    public void convertPlainTextToSubject() {
//        // Create a TextArea on new page for the user to input the schedule text
//        TextArea scheduleText = new TextArea();
//        scheduleText.setPrefSize(screenWidth, screenHeight / 3.0);
//
//        // Creates a confirm button to process the entered text when done
//        OptionButton confirmButton = new OptionButton("Confirm", screenWidth / 6.0, screenHeight / 10);
//        ArrayList<Subject> subjectSave = new ArrayList<>();
//        confirmButton.setOnAction((ActionEvent e) -> {
//            String[] tokens = scheduleText.getText().split("\n");
//            String[] tokens1;
//            for (int i = 0; i < tokens.length; i++) {
//                ArrayList<Assignment> assignments = new ArrayList<>();
//                tokens1 = tokens[i].split(",");
//                Subject tempSubject = new Subject(tokens1[0], Boolean.parseBoolean(tokens1[2]), Double.parseDouble(tokens1[1]));
//                tempSubject.setColor(Integer.parseInt(tokens1[3]));
//                for (int j = 4; j < tokens1.length; j++) {
//                    assignments.add(new Assignment(tokens1[j]));
//                }
//                tempSubject.setAssignmentList(assignments);
//                subjectSave.add(tempSubject);
//                subjectArrayList = subjectSave;
//            }
//            try {
//                // After processing the text, switch to the main screen
//                runMainScreen(subjectArrayList, selectedIndex);
//            } catch (Exception ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//
//        // Create a VBox to hold the text input field and the confirm button
//        VBox buttons = new VBox(20, scheduleText, confirmButton);
//        buttons.setAlignment(Pos.CENTER);
//
//        // Set up the layout
//        layout = new BorderPane(buttons);
//        sceneClassName = new Scene(layout, screenWidth, screenHeight); // Set scene dimensions
//        switchScene(sceneClassName, "Import Schedule From Text");
//        stage.show();
//    }

    /**
     * used for tomcat activities
     *
     * @param a subject array to convert
     * @return string consisting of all schedule info
     */
    public String convertEverythingToString(ArrayList<Subject> a) {
        //IGNORE METHOD. New implementation of networking on web server itself.
        String finalString = "";
        String temp = "";
        for (Subject subject : a) {
            temp = subject.getNameOfSubject() + ',' + String.valueOf(subject.getGradeInClass()) + ',' +
                    String.valueOf(subject.isWeighted()) + ',' + String.valueOf(subject.getColor()) + ',';
            finalString += temp;
            ArrayList<Assignment> assignments = subject.getAssignmentList();
            for (int j = 0; j < assignments.size(); j++) {
                finalString += assignments.get(j).getNameOfAssignment() + (',');
            }
            finalString += "\n";
        }
        return finalString;
    }
}
