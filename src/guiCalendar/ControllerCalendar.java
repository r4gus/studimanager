package guiCalendar;

import custom_exceptions.UserException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.util.Pair;
import timetable.Facility;
import timetable.Lecture;
import timetable.Lecturer;
import timetable.Timetable;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author David Sugar
 */
public class ControllerCalendar implements Initializable {
    /* only for test purposes */
    private Timetable timetable = new Timetable(7, 2, 6);
    private static String days[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private static final int BIG_FONT_SIZE = 22;
    private static final int MEDIUM_FONT_SIZE = 16;
    private static final int SMALL_FONT_SIZE = 12;
    private final double COLUMN_PERCENTAGE_WIDTH = 100.0 / timetable.getDays(); // always fit columns to screen
    private final double ROW_PERCENTAGE_HEIGHT = 100.0 / timetable.getUnitsPerDay();

    @FXML
    public AnchorPane tt_anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GridPane gridPane = new GridPane();

        Text date = new Text(timetable.getDate());
        date.setFont(new Font(BIG_FONT_SIZE));

        sampleLectures();

        /* setup gridPane */
        adjustGridPane(gridPane);

        /* anchor gridPane */
        tt_anchorPane.setTopAnchor(gridPane, 85.0);
        tt_anchorPane.setBottomAnchor(gridPane, 8.0);
        tt_anchorPane.setRightAnchor(gridPane, 8.0);
        tt_anchorPane.setLeftAnchor(gridPane, 8.0);

        /* anchor date */
        tt_anchorPane.setTopAnchor(date, 25.0);
        tt_anchorPane.setLeftAnchor(date, 8.0);

        /* add elements */
        setDays(gridPane, days);  // add mon, tue , wed to the grid
        setTime(gridPane);  // add the beginning and end of each lecture to the grid
        populateGrid(gridPane);

        /* add to children */
        tt_anchorPane.getChildren().add(gridPane);
        tt_anchorPane.getChildren().add(date);
    }

    private void sampleLectures() {
        Facility f1 = timetable.newFacility("G2", "0.23", "", "73434", "Aalen");
        Facility f2 = timetable.newFacility("G2", "0.23", "", "73434", "Aalen");
        Facility f3 = timetable.newFacility("G1", "1.44", "", "73434", "Aalen");

        Lecturer lect1 = timetable.newLecturer("Max", "Mustermann", "", f1);
        Lecturer lect2 = timetable.newLecturer("Max", "Mustermann", "", f2);
        Lecturer lect3 = timetable.newLecturer("Maya", "Mustermann", "", f3);

        Lecture l1 = timetable.newLecture("Algorithmen", f1, lect1, false, null);
        Lecture l2 = timetable.newLecture("Algorithmen", f2, lect2, false, null);
        Lecture l3 = timetable.newLecture("OOP", f3, lect3, true, null);

        try {
            timetable.addLecture(0, 0, l1);
            timetable.addLecture(1, 0, l1);
            timetable.addLecture(3,2, l2);
            timetable.addLecture(1,4, l3);
        } catch (UserException exc) {
            System.out.println("FUCK!");
        }
    }

    /**
     * Add all existing lectures to the specified gridPane. The Timetable.lectureMap maps from lectures
     * to their position within the timetable. It's used to conveniently add lectures to the right field within
     * the grid.
     *
     * @param gridPane The targeted {@link GridPane}
     */
    private void populateGrid(GridPane gridPane) {
        HashMap<Lecture, ArrayList<Pair<Integer, Integer>>> lectureMap = timetable.getLectureMap();

        lectureMap.forEach((k, v) -> {              // iterate over each key, value pair
            for(Pair<Integer, Integer> elem: v) {   // iterate over every tuple
                Text text = new Text(k.toString());
                gridPane.add(text, elem.getValue() + 1, elem.getKey() + 1);
            }
        });
    }

    /**
     * Setup the specified {@link GridPane}. All adjustments to the gridPane should be made within this method to
     * keep everything at one place.
     *
     * @param gridPane The {@link GridPane} to adjust.
     */
    private void adjustGridPane(GridPane gridPane) {

        gridPane.setVgap(3); // vertical space between elements
        gridPane.setHgap(5); // horizontal space between elements
        gridPane.setAlignment(Pos.TOP_LEFT); // alignment of the GridPane
        gridPane.setGridLinesVisible(true);

        /* specify the width of each column dependent on the number of days */
        for(int i = 0; i <= timetable.getDays(); i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(COLUMN_PERCENTAGE_WIDTH); // width relative to the total size of the window
            gridPane.getColumnConstraints().add(col); // add column constraint
        }

        /* specify the height of each row dependent on the number of units per day */
        for(int i = 0; i <= timetable.getUnitsPerDay(); i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(ROW_PERCENTAGE_HEIGHT); // height relative to the total size of the window
            gridPane.getRowConstraints().add(row); // add row constraint
        }
    }

    /**
     * Add n {@link #days} to the first row of the gridPane starting with monday.
     * N depends on {@link Timetable#getDays()}.
     *
     * After calling this function the first row should look something like:
     * Mon | Tue | Wed | ... | Sat
     *
     * @param gridPane The {@link GridPane} to add the elements to.
     * @param days String array holding "Monday", "Tuesday", ...
     */
    private void setDays(GridPane gridPane, String days[]) {
        for(int i = 0; i < timetable.getDays(); i++) {
            Text t = new Text(days[i]);
            t.setFont(new Font(BIG_FONT_SIZE)); // set font size
            gridPane.setHalignment(t, HPos.CENTER); // center node (text object)
            gridPane.add(t, i+1, 0);  // add to gridPane
        }
    }

    /**
     * Add text fields containing the duration of each unit to the specified {@link GridPane}.
     * How many elements are added depends on the {@link Timetable} object.
     * @param gridPane The object to add the {@link Text} fields to.
     */
    private void setTime(GridPane gridPane) {
        for(int i = 0; i < timetable.getUnitsPerDay(); i++) {
            Text t = new Text(timetable.getUnit()[i][0].getTime());
            t.setFont(new Font(MEDIUM_FONT_SIZE));  // set font size
            t.setTextAlignment(TextAlignment.CENTER); // center text
            gridPane.setHalignment(t, HPos.CENTER); // center node (text object)
            gridPane.add(t, 0, i+1);    // add to gridPane
        }
    }
}
