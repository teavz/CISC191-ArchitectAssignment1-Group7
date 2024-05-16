package edu.sdccd.cisc191.template;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicReference;


import static edu.sdccd.cisc191.template.Schedule.arrayListToQueue;

public class ViewStartScreen extends Application {
    private int screenWidth, screenHeight; //allows buttons to be scaled accordingly
    private int selectedIndex;
    private BorderPane layout;
    private Stage stage;
    private Date date;
    private SimpleDateFormat dateFormat;
    private Label time;
    private Scene sceneClassName;
    private ArrayList<Subject> subjectArrayList = new ArrayList<>();
    private ConcurrentLinkedDeque<Subject> stack = new ConcurrentLinkedDeque<>();
    private boolean done = false;
    private Calendar calendar;

    public void addSubject(Subject temp) {
        subjectArrayList.add(temp);
    }

    public void removeSubject(int index) {
        subjectArrayList.remove(index);
    }

    public int findIndex(Subject find) {
        return subjectArrayList.indexOf(find);
    }

    public void setSubject(int i, Subject subject) {
        subjectArrayList.set(i, subject);
    }

    public Subject getAtIndex(int index) {
        return subjectArrayList.get(index);
    }

    public ArrayList<Subject> getSubjectArray() {
        return subjectArrayList;
    }

    public VBox createVBox(Double height, Label label, OptionButton button) {
        VBox vBox = new VBox(height, label, button);
        return vBox;
    }

    public BorderPane createLayout(VBox vBox) {
        return layout = new BorderPane(vBox);
    }

    public Scene getScene() {
        return sceneClassName;
    }

    public Stage getStage() {
        return stage;
    }

    public void setScene(Scene temp) {
        sceneClassName = temp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private static String savedSchedule;

    /**
     * @param stage the primary stage for this application, onto which
     *              the application scene can be set. The primary stage will be embedded in
     *              the browser if the application was launched as an applet.
     *              Applications may create other stages, if needed, but they will not be
     *              primary stages and will not be embedded in the browser.
     *              Initial screen that the user sees
     *              Hooray for javafx
     */
    public void start(Stage stage) throws IOException {
        //variables???
        Glow glow = new Glow();
        glow.setLevel(0.2);
        this.stage = stage;
        // 720x1200 resolution
        screenWidth = 1000;
        screenHeight = 1000;
        Font font = Font.font("Montserrat", FontWeight.EXTRA_BOLD, 36);
        Font smallFont = Font.font("Montserrat", FontWeight.BOLD, 18);

        //button to direct the user to set up
        OptionButton setupButton = new OptionButton("Make your Schedule", 500, 100);
        setupButton.changeTextColor(Color.web("#34A3ED"));
        setupButton.changeBackGroundColor();
        setupButton.setFont(font);

        //button for import of csv file of saved schedule
        OptionButton importCSVButton = new OptionButton("Import from File", 500, 100);
        importCSVButton.changeTextColor(Color.web("#34A3ED"));
        importCSVButton.changeBackGroundColor();
        importCSVButton.setFont(font);
        importCSVButton.setWrapText(true);

        OptionButton importDatabaseButton = new OptionButton("Import from Database", 500, 100);
        importCSVButton.changeTextColor(Color.web("#34A3ED"));
        importCSVButton.changeBackGroundColor();
        importCSVButton.setFont(font);
        importCSVButton.setWrapText(true);

        //button for import from text field (from website save)
        OptionButton importTextButton = new OptionButton("Import from Text", 500, 100);
        importTextButton.changeTextColor(Color.web("#34A3ED"));
        importTextButton.changeBackGroundColor();
        importTextButton.setFont(font);
        importTextButton.setWrapText(true);

        //title and credits to the authors
        Label title = new Label("Schedule & Homework Tracker");
        Label credits = new Label("Credits: Logan, Simon, Theo, Willy");
        date = new Date();
        dateFormat = new SimpleDateFormat("MMMM/dd/y hh:mm:ss a");
        time = new Label(dateFormat.format((date)));
        credits.setFont(smallFont);
        title.setFont(font);
        //organize title and setup button to be spaced accordingly, set it in center
        Image image = new Image(Files.newInputStream(Paths.get("Server/src/main/java/edu/sdccd/cisc191/template/Homework-modified.png")));
        Image color = new Image(Files.newInputStream(Paths.get("Server/src/main/java/edu/sdccd/cisc191/template/Homework.png")));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        VBox buttons = new VBox((double) screenHeight / 120, title, setupButton, importDatabaseButton, importCSVButton, importTextButton, imageView, credits);


        buttonEffects(glow, setupButton, image, color, imageView);

        buttonEffects(glow, importCSVButton, image, color, imageView);

        buttonEffects(glow, importDatabaseButton, image, color, imageView);

        buttonEffects(glow, importTextButton, image, color, imageView);

        setupButton.setOnAction((ActionEvent e) -> {
            try {
                done = true;
                runSetup2();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        importCSVButton.setOnAction((ActionEvent e) -> {
            subjectArrayList = convertCSVFileToSubject();
            try {
                done = true;
                runMainScreen(subjectArrayList, selectedIndex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        importDatabaseButton.setOnAction((ActionEvent e) -> {
            try {
                done = true;
                convertDatabaseToSubject();

            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        importTextButton.setOnAction((ActionEvent e) -> {
            try {
                done = true;
                convertPlainTextToSubject();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        buttons.setStyle("-fx-background-color: #FFF1DC");
        buttons.setAlignment(Pos.CENTER);
        layout = new BorderPane(buttons);
        buttons = new VBox(credits);
        buttons.setStyle("-fx-background-color: #FFF1DC");
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        layout.setBottom(buttons);
        //buttons = new VBox(time);
        //buttons.setAlignment(Pos.BOTTOM_RIGHT);
        layout.setBottom(buttons);
        Scene startScene = new Scene(layout, screenWidth, screenHeight);
        startScene.setFill(Color.web("#81c483"));
        stage.setTitle("Schedule & Homework Tracker");
        stage.setScene(startScene);
        stage.show();

    }

    public void setTime() {
        while (done == false) {
            String currentTime = dateFormat.format(Calendar.getInstance().getTime());
            System.out.println(currentTime);
            time.setText(currentTime);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param glow,        the amount of the glow when hovering/interacting with a button
     * @param setupButton, the button which will be imbued with given effects
     * @param image,
     * @param color
     * @param imageView
     */
    private void buttonEffects(Glow glow, OptionButton setupButton, Image image, Image color, ImageView imageView) {
        setupButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            setupButton.setEffect(glow);
            imageView.setImage(color);

        });
        setupButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            setupButton.setEffect(null);
            imageView.setImage(image);
        });
    }

    public static void main(String[] args) {
        launch();
        TaskClass taskClass = new TaskClass();
        taskClass.start();
    }


    /**
     * First screen the user sees when
     *
     * @throws Exception when there's error :)
     */
    public void runSetup2() throws Exception {
        Font font = Font.font("Montserrat", FontWeight.BOLD, 18);

        Label promptName = new Label("Enter the name of the class you would like to add");
        TextField name = new TextField();
        name.setFont(font);
        name.setPrefSize(screenWidth / 2.0, screenHeight / 10.0);
        name.setMaxWidth(screenWidth / 2.0);
        promptName.setFont(font);

        CheckBox promptWeighted = new CheckBox("Is the class weighted?");
        promptWeighted.setFont(font);

        Label promptGrade = new Label("What is your current grade in the class?");
        promptGrade.setFont(font);

        TextField grade = new TextField();
        grade.setPrefSize(screenWidth / 2.0, screenHeight / 8.0);
        grade.setMaxWidth(screenWidth / 2.0);
        grade.setFont(font);
        //prevents any compiler errors when adding to subjectArrayList
        OptionButton confirm = new OptionButton("Confirm", screenWidth / 6.0, screenHeight / 24.0);
        Label colorChoice = new Label("What color would you like the subject to be?");
        colorChoice.setFont(font);
        ChoiceBox<String> dropDown = new ChoiceBox<>();
        dropDown.getItems().add("Red");
        dropDown.getItems().add("Blue");
        dropDown.getItems().add("Yellow");
        dropDown.getItems().add("Green");
        dropDown.getItems().add("Orange");
        dropDown.getItems().add("Purple");
        dropDown.setOnAction((event) -> {
            int selectedIndex = dropDown.getSelectionModel().getSelectedIndex();
        });
            /* adds first class to subject array list
               directs to main interface
             */
        confirm.setOnAction((ActionEvent yes) -> {
            try {

                selectedIndex = dropDown.getSelectionModel().getSelectedIndex();
                Subject tempSubject = new Subject(name.getText(), Double.parseDouble(grade.getText()));
                tempSubject.setColor(selectedIndex);
                int tempColor = tempSubject.getColor();



                subjectArrayList.add(tempSubject);
                stack.push(tempSubject);
                runMainScreen(subjectArrayList, tempColor);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("INPUT ERROR");
                alert.setContentText("YOUR GRADE, " + "'" + ((grade.getText())) + "'" + " IS NOT A NUMBER" +
                        "\nTRY AGAIN");
                alert.showAndWait();
            }
        });

        VBox buttons = new VBox(50);
        //TODO Add something to get the information of the color


        buttons.setStyle("-fx-background-color: #FFF1DC;");
        buttons.getChildren().addAll(promptName, name, promptGrade, grade, promptWeighted, colorChoice, dropDown, confirm);
        buttons.setAlignment(Pos.CENTER);
        layout = new BorderPane(buttons);

        name.clear();
        grade.clear();
        sceneClassName = new Scene(layout, screenWidth, screenHeight);
        switchScene(sceneClassName, "Set your classes");
        stage.show();
    }

    /**
     * switchScene to handle repetitive action
     *
     * @param scene scene to switch to
     * @param title title of new scene
     */
    public void switchScene(Scene scene, String title) {
        stage.setScene(scene);
        stage.setTitle(title);
        sceneClassName = scene;
    }

    /**
     * @param a subjectArrayList, used to have the proper amount of buttons
     * @throws Exception prevents anything from not compiling
     */
    public void runMainScreen(ArrayList<Subject> a, int colorNumber) throws Exception {
        Label classList = new Label("Your Classes");
        classList.setStyle("-fx-font-size: 20; -fx-underline: true; -fx-font-weight: bold;");
        VBox classes = new VBox(screenHeight / 240.0, classList);
        classes.setStyle("-fx-background-color: #FFF1DC;");
        classes.setAlignment(Pos.TOP_LEFT);

        // set individual color for each subject
        for (Subject subject : a) {
            OptionButton button = new OptionButton(subject.getNameOfSubject(), screenWidth / 3, screenHeight / 10);

            button.buttonGlow();
            button.changeTextColor(subject.getColor()); // Set color based on subject
            button.setOnAction((ActionEvent e) -> {
                try {
                    int subjectIndex = a.indexOf(subject);
                    viewAssignmentList(subjectIndex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            classes.getChildren().add(button);
        }

        //allows user to add another class to the list
        OptionButton addClass = new OptionButton("Add Class", screenWidth / 3, screenHeight / 17.5);
        addClass.changeBackGroundColor();
        addClass.buttonGlow();
        addClass.setOnAction((ActionEvent e) -> {
            try {
                runSetup2();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        OptionButton saveSchedule = new OptionButton("Save Schedule", screenWidth / 3, screenHeight / 17.5);
        saveSchedule.setOnAction((ActionEvent e) -> {
            chooseSchedule(a);
        });
        saveSchedule.buttonGlow();
        saveSchedule.changeBackGroundColor();
        HBox bottomButtons = new HBox(screenWidth / 1.5, addClass, saveSchedule);
        bottomButtons.setStyle("-fx-background-color: #FFF1DC");
        bottomButtons.setAlignment(Pos.BOTTOM_LEFT);
        layout = new BorderPane(classes);
        layout.setBottom(bottomButtons);
        sceneClassName = new Scene(layout, screenWidth, screenHeight);
        switchScene(sceneClassName, "View Information");
        stage.show();
    }

    /**
     * @param subjectArrayIndex index of subjectArray i.e which subject does the user want to access
     *                          dear God did I do anything correctly
     *                                                                            TODO deal with weird user inputs
     */
    public void viewAssignmentList(int subjectArrayIndex) {
        Subject subject = new Subject(subjectArrayList.get(subjectArrayIndex));
        ArrayList<Assignment> tempArray = subject.getAssignmentList();
        Label nameOfSubject = new Label(subjectArrayList.get(subjectArrayIndex).getNameOfSubject());
        nameOfSubject.setStyle("-fx-font-size: 20; -fx-underline: true; -fx-font-weight: bold;");

        VBox assignments = new VBox(screenHeight / 240.0, nameOfSubject);
        assignments.setStyle("-fx-background-color: #FFF1DC;");
        assignments.setAlignment(Pos.TOP_LEFT);
        OptionButton addAssignment = new OptionButton("Add Assignment", screenWidth / 3.0, screenHeight / 17.5);
        addAssignment.changeBackGroundColor();
        addAssignment.buttonGlow();
        addAssignment.setOnAction((ActionEvent e) -> {
            try {
                addAssignment(subjectArrayIndex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        OptionButton backButton = new OptionButton("Back", screenWidth / 3.0, screenHeight / 17.5);
        backButton.changeBackGroundColor();
        backButton.buttonGlow();
        backButton.setOnAction((ActionEvent e) -> {
            try {
                runMainScreen(subjectArrayList, selectedIndex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        for (int i = 0; i < tempArray.size(); i++) {
            Assignment assignment = tempArray.get(i);
            OptionButton button = new OptionButton(assignment.getNameOfAssignment(), screenWidth / 3.0, screenHeight / 10.0);
            int finalI = i;
            button.changeBackGroundColor();
            button.buttonGlow();
            button.setOnAction((ActionEvent e) -> {
                try {
                    viewAssignmentInfo(subjectArrayIndex, finalI);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
            assignments.getChildren().add(button);
        }
        HBox bottomButtons = new HBox(screenWidth / 1.5, addAssignment, backButton);
        bottomButtons.setStyle("-fx-background-color: #FFF1DC");
        layout = new BorderPane(assignments);
        layout.setBottom(bottomButtons);
        sceneClassName = new Scene(layout, screenWidth, screenHeight);
        switchScene(sceneClassName, "Assignments");
        stage.show();
    }

    public void viewAssignmentInfo(int subjectIndex, int assignmentIndex) {
        Subject subject = subjectArrayList.get(subjectIndex);
        ArrayList<Assignment> assignments = subject.getAssignmentList();
        Assignment selectedAssignment = assignments.get(assignmentIndex);


        Label assignmentLabel = new Label("Assignment Name: " + selectedAssignment.getNameOfAssignment() + "\n" +
                "Total Points Possible: " + Double.toString(selectedAssignment.getTotalPoints()));


        // Create a button to go back to the assignment list
        OptionButton backButton = new OptionButton("Back to Assignment List", screenWidth / 5.0, screenHeight / 17.5);
        backButton.buttonGlow();
        backButton.changeBackGroundColor();
        backButton.setOnAction((ActionEvent e) -> {
            try {
                viewAssignmentList(subjectIndex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Create a VBox to hold the labels and button
        VBox assignmentInfoLayout = new VBox(screenHeight / 60.0, assignmentLabel, backButton);
        assignmentInfoLayout.setStyle("-fx-background-color: #FFF1DC");
        assignmentInfoLayout.setAlignment(Pos.CENTER);

        // Set the layout for the scene
        layout = new BorderPane(assignmentInfoLayout);
        sceneClassName = new Scene(layout, screenWidth, screenHeight);
        switchScene(sceneClassName, "Assignment Details");
        stage.show();
    }

    // points earned is killed bc homework planner assumes assignments have not been done yet
    public void addAssignment(int subjectIndex) {
        Subject subject = subjectArrayList.get(subjectIndex); // Get the subject from the ArrayList
        Label assignmentNameLabel = new Label("Enter name of Assignment:");
        TextField assignmentNameField = new TextField();

        //Label assignmentPointsLabel = new Label("Enter amount of points earned on Assignment:");
        // TextField assignmentPointsField = new TextField();

        Label assignmentTotalPointsLabel = new Label("Enter number of points for Assignment:");
        TextField assignmentTotalPointsField = new TextField();

        assignmentNameField.setPrefSize(screenWidth / 2.0, screenHeight / 8.0);
        // assignmentPointsField.setPrefSize(screenWidth / 2.0, screenHeight / 20.0);
        assignmentTotalPointsField.setPrefSize(screenWidth / 2.0, screenHeight / 20.0);

        OptionButton confirmButton = new OptionButton("Confirm", screenWidth / 6.0, screenHeight / 24.0);
        confirmButton.setOnAction((ActionEvent e) -> {
            try {
                String assignmentName = assignmentNameField.getText();
                // int assignmentPoints = Integer.parseInt(assignmentPointsField.getText());
                int totalAssignmentPoints = Integer.parseInt(assignmentTotalPointsField.getText());
                // Create a new Assignment object with the entered name
                Assignment assignment = new Assignment(assignmentName);
                // assignment.setPointsOfAssignment(assignmentPoints);
                assignment.setTotalPoints(totalAssignmentPoints);
                // Add the assignment to the subject's ArrayList
                subject.addAssignment(assignment);
            } catch (Exception error) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText("INPUT ERROR");
                alert.setContentText("YOUR GRADE, " + "'" + ((assignmentTotalPointsField.getText())) + "'" + " IS NOT A NUMBER" +
                        "\nTRY AGAIN");
                alert.showAndWait();
            }
            try {
                runMainScreen(subjectArrayList, selectedIndex); // Refresh the main screen with the updated assignment list
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        VBox buttons = new VBox(screenHeight / 60.0, assignmentNameLabel, assignmentNameField, assignmentTotalPointsLabel, assignmentTotalPointsField, confirmButton);
        layout = new BorderPane(buttons);
        sceneClassName = new Scene(layout, screenWidth, screenHeight);
        switchScene(sceneClassName, "Add Assignment");
        stage.show();
    }

    /**
     * Convert the list of subjects to a CSV file
     *
     */

    public void convertSubjectToDatabase(ArrayList<Subject> subjectArrayList) {
        try {
            Database database = new Database();

            Schedule schedule = new Schedule(subjectArrayList);

            database.createSchedule(schedule);

            for (Subject subject : subjectArrayList) {
                database.create(subject, schedule);
            }

            database.getConnection().commit();
            database.getConnection().setAutoCommit(true);

            showScheduleIDDialog(schedule.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * so users can actually see their scheduleID without Intellj lol
     * @param scheduleID
     */
    private void showScheduleIDDialog(long scheduleID) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Schedule Saved");
        alert.setHeaderText(null);
        alert.setContentText("Schedule saved successfully! Save this ID to access your schedule at a later time!  Schedule ID: " + scheduleID);
        alert.showAndWait();
    }

    public void convertSubjectToCSV(ArrayList<Subject> subjectArrayList) {
        FileChooser.ExtensionFilter availableFiles = new FileChooser.ExtensionFilter("txt files", "*.txt");
        FileChooser fc = new FileChooser();
        ConcurrentLinkedDeque<Subject> subjects = new ConcurrentLinkedDeque<>();

        fc.setTitle("Save Schedule");
        fc.setInitialFileName("My_Schedule.txt");
        fc.getExtensionFilters().add(availableFiles);
        File saveLocation = fc.showSaveDialog(stage);

        if (saveLocation != null) {
            try (FileWriter writer = new FileWriter(saveLocation)) {
                for (Subject subject : subjectArrayList) {
                    writer.append(subject.getNameOfSubject())
                            .append(',')
                            .append(String.valueOf(subject.getGradeInClass()))
                            .append(',')
                            .append(String.valueOf(subject.isWeighted()))
                            .append(',')
                            .append(String.valueOf(subject.getColor()))
                            .append(',');

                    ArrayList<Assignment> temp = subject.getAssignmentList();
                    for (Assignment assignment : temp) {
                        writer.append(assignment.getNameOfAssignment()).append(',');
                    }
                    writer.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


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

    /**
     * lol whoever decided to name AtomicReference
     * better than synchronized when it comes to concurrency????
     * it works fine
     * https://www.baeldung.com/java-atomic-variables#:~:text=The%20most%20commonly%20used%20atomic,which%20can%20be%20atomically%20updated.
     */
    public void convertDatabaseToSubject() {
        TextArea scheduleText = new TextArea();
        scheduleText.setPrefSize(screenWidth, screenHeight / 3.0);

        OptionButton confirmButton = new OptionButton("Confirm", screenWidth / 6.0, screenHeight / 10);
        AtomicReference<ArrayList<Subject>> subjectSave = new AtomicReference<>(new ArrayList<>());
        confirmButton.setOnAction((ActionEvent e) -> {
            long scheduleID = Long.parseLong(scheduleText.getText());
            Database database;
            try {
                database = new Database();
                subjectSave.set(database.getSubjectsByScheduleID(scheduleID));
                subjectArrayList = subjectSave.get();
                runMainScreen(subjectArrayList, selectedIndex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        VBox buttons = new VBox(20, scheduleText, confirmButton);
        buttons.setAlignment(Pos.CENTER);

        BorderPane layout = new BorderPane(buttons);
        Scene scene = new Scene(layout, screenWidth, screenHeight);
        stage.setScene(scene);
        stage.setTitle("Import Schedule From Database");
        stage.show();
    }

    public void convertPlainTextToSubject() {
        // Create a TextArea on new page for the user to input the schedule text
        TextArea scheduleText = new TextArea();
        scheduleText.setPrefSize(screenWidth, screenHeight / 3.0);

        // Creates a confirm button to process the entered text when done
        OptionButton confirmButton = new OptionButton("Confirm", screenWidth / 6.0, screenHeight / 10);
        ArrayList<Subject> subjectSave = new ArrayList<>();
        confirmButton.setOnAction((ActionEvent e) -> {
            String[] tokens = scheduleText.getText().split("\n");
            String[] tokens1;
            for (int i = 0; i < tokens.length; i++) {
                ArrayList<Assignment> assignments = new ArrayList<>();
                tokens1 = tokens[i].split(",");
                Subject tempSubject = new Subject(tokens1[0], Boolean.parseBoolean(tokens1[2]), Double.parseDouble(tokens1[1]));
                tempSubject.setColor(Integer.parseInt(tokens1[3]));
                for (int j = 4; j < tokens1.length; j++) {
                    assignments.add(new Assignment(tokens1[j]));
                }
                tempSubject.setAssignmentList(assignments);
                subjectSave.add(tempSubject);
                subjectArrayList = subjectSave;
            }
            try {
                // After processing the text, switch to the main screen
                runMainScreen(subjectArrayList, selectedIndex);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Create a VBox to hold the text input field and the confirm button
        VBox buttons = new VBox(20, scheduleText, confirmButton);
        buttons.setAlignment(Pos.CENTER);

        // Set up the layout
        layout = new BorderPane(buttons);
        sceneClassName = new Scene(layout, screenWidth, screenHeight); // Set scene dimensions
        switchScene(sceneClassName, "Import Schedule From Text");
        stage.show();
    }

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

    public void chooseSchedule(ArrayList<Subject> a) {
        OptionButton saveFile = new OptionButton("Save as File", screenWidth / 3.0, screenHeight / 2.0);
        OptionButton saveRemote = new OptionButton("Save Remotely", screenWidth / 3.0, screenHeight / 2.0);
        OptionButton saveDatabase = new OptionButton("Save on Database", screenWidth / 3.0, screenHeight / 2.0);
        HBox buttons = new HBox(50, saveFile, saveRemote, saveDatabase);
        buttons.setAlignment(Pos.CENTER);
        buttons.setStyle("-fx-background-color: #FFF1DC;");

        saveFile.setOnAction((ActionEvent e) -> {
            ConcurrentLinkedDeque<Subject> stack = arrayListToQueue(a);
            convertSubjectToCSV(a);
        });

        saveRemote.setOnAction((ActionEvent e) -> {
            //DOES NOT WORK. Alternate networking implementation:
            // File Upload/Download on web server
            savedSchedule = convertEverythingToString(a);
        });

        saveDatabase.setOnAction((ActionEvent e) -> {
            ConcurrentLinkedDeque<Subject> stack = arrayListToQueue(a);
            convertSubjectToDatabase(a);

        });
        //set layout
        layout = new BorderPane(buttons);
        sceneClassName = new Scene(layout, screenWidth, screenHeight);
        switchScene(sceneClassName, "Choose Save Option");
        stage.show();
    }


    public boolean checkSceneEquals(Scene scene1, Scene scene2) {
        if (scene1.getWidth() == scene2.getWidth() && scene1.getHeight() == scene2.getHeight()) {
            return true;
        } else {
            return false;
        }
    }


    /** check this please logan theo willy
     * have not made it visible to program yet bruh
     *
     * @param assignments
     * @param dueDate
     */
    public void displaySearchResult(ArrayList<Assignment> assignments, int dueDate) {
        int index = Sorting.searchAssignmentByDueDate(assignments, dueDate);
        if (index != -1) {

            Assignment assignment = assignments.get(index);
            Label resultLabel = new Label("Assignment Found:\n" +
                    "Name: " + assignment.getNameOfAssignment() + "\n" +
                    "Due Date: " + assignment.getDaysUntilDueDate() + "\n" +
                    "Days Until Due: " + assignment.getDaysUntilDueDate());

            layout.setCenter(resultLabel);
        } else {

            Label resultLabel = new Label("No assignment found with the specified due date.");
            layout.setCenter(resultLabel);
        }
    }

}
