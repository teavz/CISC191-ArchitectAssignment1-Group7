package edu.sdccd.cisc191.template;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;

public class Calendar {
    private final ArrayList<Assignment>[][] calendar = new ArrayList[6][7];
    public void addAssignment(int date, Assignment assignment){
        calendar[4][4] = new ArrayList<Assignment>();
        calendar[4][4].add(assignment);
    }
    public Assignment getAssignments(int index, int row, int column){
        return calendar[row][column].get(index);
    }

}

