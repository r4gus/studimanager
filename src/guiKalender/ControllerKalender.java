package guiKalender;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import timetable.Timetable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author David Sugar
 */
public class ControllerKalender implements Initializable {
    /* only for test purposes */
    Timetable timetable = new Timetable(7, 2);
    private static String days[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private static final int BIG_FONT_SIZE = 22;
    private static final int MEDIUM_FONT_SIZE = 16;
    private static final int SMALL_FONT_SIZE = 12;
    private final double COLUMN_PERCENTAGE_WIDTH = 100.0 / timetable.getDays(); // always fit columns to screen
    private final double ROW_PERCENTAGE_HEIGHT = 88.0 / timetable.getUnitsPerDay();

    @FXML
    public AnchorPane tt_anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GridPane gridPane = new GridPane();

        Text date = new Text(timetable.getDate());
        date.setFont(new Font(BIG_FONT_SIZE));

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

        /* add to children */
        tt_anchorPane.getChildren().add(gridPane);
        tt_anchorPane.getChildren().add(date);
    }

    /**
     * Setup the specified {@link GridPane}.
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
            RowConstraints row = new RowConstraints();
            col.setPercentWidth(COLUMN_PERCENTAGE_WIDTH); // width relative to the total size of the window
            row.setPercentHeight(ROW_PERCENTAGE_HEIGHT); // height relative to the total size of the window
            gridPane.getColumnConstraints().add(col); // add column constraint
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
