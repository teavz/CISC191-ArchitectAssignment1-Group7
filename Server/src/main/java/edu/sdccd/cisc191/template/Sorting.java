package edu.sdccd.cisc191.template;
import java.util.ArrayList;
public class Sorting {
    public static ArrayList<Assignment> sortAssignmentByDueDate(ArrayList<Assignment> assignments, int endIndex) {
//        if (endIndex == 1)
//            return assignments;
//        for (int i = 0; i < endIndex - 1; i++) {
//            if (assignments.get(i).getDaysUntilDueDate() > assignments.get(i+1).getDaysUntilDueDate()) {
//                //swap position of i, i+1
//                Assignment temp = assignments.get(i);
//                assignments.set(i, assignments.get(i+1));
//                assignments.set(i+1, temp);
//            }
//        }
//        sortAssignmentByDueDate(assignments, endIndex-1);
//        return assignments;
        //base
        if (endIndex <= 1)
            return assignments;

        sortAssignmentByDueDate(assignments, endIndex - 1);

        //insert last element
        Assignment tempAssignment = assignments.get(endIndex - 1);
        int i = endIndex - 2;

        //move elements
        while (i >= 0 && assignments.get(i).getDaysUntilDueDate() > tempAssignment.getDaysUntilDueDate()) {
            assignments.set(i+1, assignments.get(i));
            i--;
        }
        assignments.set(i + 1, tempAssignment);
        return assignments;
    }

    /**
     * binary search search assignments based on key, which is due date
     * did I even use recursion correctly God knows
     */
    public static int binarySearch(ArrayList<Assignment> assignments, int startIndex, int endIndex, int key) {
        if (endIndex >= startIndex) {
            int mid = startIndex + (endIndex - startIndex) / 2;

            if (assignments.get(mid).getDaysUntilDueDate() == key)
                return mid;

            if (assignments.get(mid).getDaysUntilDueDate() > key)
                return binarySearch(assignments, startIndex, mid - 1, key);

            return binarySearch(assignments, mid + 1, endIndex, key);
        }

        return -1;
    }

    /**
     * Please check this guys im lowkey lost in search
     * @param assignments
     * @param key
     * @return
     */
    public static int searchAssignmentByDueDate(ArrayList<Assignment> assignments, int key)
    {
        return binarySearch(assignments, 0, assignments.size() - 1, key);
    }


}
