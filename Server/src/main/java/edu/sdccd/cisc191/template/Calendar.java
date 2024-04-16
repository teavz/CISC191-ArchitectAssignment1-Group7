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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import java.util.ArrayList;

public class Calendar {
    private final ArrayList<Assignment>[][] calendar = new ArrayList[6][7];
    public void createArrayList(){
        for(int row = 0; row < calendar.length; row++){
            for(int col = 0; col < calendar[row].length; col++){
                calendar[row][col] = new ArrayList<Assignment>();
            }
        }
    }
    public BorderPane createCalendar(){
        BorderPane layout = new BorderPane();
        HBox box = new HBox();
        for(int row = 0; row < calendar.length; row++){
            HBox grid = new HBox(10);
            for(int col = 0; col < calendar[row].length; col++) {
                grid.getChildren().addAll(new OptionButton("", 12, 10));
            }
            layout = new BorderPane(grid);
        }
        return layout;

    }
    public void addAssignment(int date, Assignment assignment){
        calendar[date/7][date%7].add(assignment);
    }
    public void removeAssignment(int date, int index){
        calendar[date/7][date%7].remove(index);
    }
    public void setAssignment(int date, int index, Assignment assignment){
        calendar[date/7][date%7].set(index, assignment);
    }
    public int findIndex(int date, Assignment assignment){
        return calendar[date/7][date%7].indexOf(assignment);
    }
    public Assignment getAssignment(int date, int index){
        return calendar[date/7][date%7].get(index);
    }

}

