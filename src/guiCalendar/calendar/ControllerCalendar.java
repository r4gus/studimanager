package guiCalendar.calendar;

import guiCalendar.Updatable;
import guiCalendar.info.ControllerLectureInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import Main.Main;
import timetable.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author David Sugar
 */
public class ControllerCalendar implements Initializable, Updatable {

    private static Timetable timetable = null;

    private final static String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    private static final int BIG_FONT_SIZE = 22;
    public static final int MEDIUM_FONT_SIZE = 16;
    private double COLUMN_PERCENTAGE_WIDTH = 100.0;
    private double ROW_PERCENTAGE_HEIGHT = 100.0;

    @FXML
    public AnchorPane tt_anchorPane;

    @FXML
    private Label date;

    @FXML
    private ScrollPane scrollPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MyLogger.LOGGER.entering(getClass().toString(), "initialize",
                new Object[]{url, resourceBundle});

        /* load stylesheet */
        tt_anchorPane.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
        tt_anchorPane.getStyleClass().add("background-color");

        COLUMN_PERCENTAGE_WIDTH = 100.0 / timetable.getDays();
        ROW_PERCENTAGE_HEIGHT = 100.0 / timetable.getUnitsPerDay();

        this.update();

        MyLogger.LOGGER.exiting(getClass().toString(), "initialize");
    }

    /**
     * Updates the view.
     */
    public void update() {
        MyLogger.LOGGER.entering(getClass().toString(), "update");

        date.setText(timetable.getDate());
        scrollPane.setContent(buildCalendar());

        MyLogger.LOGGER.exiting(getClass().toString(), "update");
    }

    private GridPane buildCalendar() {
        GridPane gridPane = new GridPane();
        adjustGridPane(gridPane);
        setDays(gridPane);  // add mon, tue , wed to the grid
        setTime(gridPane);  // add the beginning and end of each lecture to the grid
        populateGrid(gridPane);

        return gridPane;
    }


    /**
     * Add all existing lectures to the specified gridPane. The Timetable.lectureMap maps from lectures
     * to their position within the timetable. It's used to conveniently add lectures to the right field within
     * the grid.
     *
     * @param gridPane The targeted {@link GridPane}
     */
    private void populateGrid(GridPane gridPane) {
        MyLogger.LOGGER.entering(getClass().toString(), "populateGrid", gridPane);

        for (int day = 0; day < timetable.getDays(); day++) {             // iterate over days
            for (int u = 0; u < timetable.getUnitsPerDay(); u++) {  // iterate over all units per day

                Lectures unit = timetable.getUnit()[u][day];

                // add a button to each inner cell of the grid pane
                Button button = makeButton(unit, day);

                button.getStyleClass().add("lecture-button");
                button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);  // fill whole cell

                gridPane.add(button, day + 1, u + 1);
            }
        }


        MyLogger.LOGGER.exiting(getClass().toString(), "populateGrid");
    }

    private Button makeButton(Lectures unit, int day) {
        Lecture head = unit.getHead();
        VBox vBox;
        Button button = new Button();

        // get information about all lectures assigned to this unit as tooltip
        if(unit.getSize() > 0) {
            StringBuilder toolTip = new StringBuilder();
            for (Lecture l : unit.getContainer()) {
                toolTip.append(l.getTitle()).append(": ").append(l.getFacility()).append("\n");
            }
            Tooltip lectureTooltip = new Tooltip(toolTip.toString());
            button.setTooltip(lectureTooltip);
        }

        /* show all lectures assigned to a unit */
        ControllerCalendar parent = this;
        button.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // load info-page scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/info/layoutLectureInfo.fxml"));
                    Parent root = loader.load();

                    // get controller
                    ControllerLectureInfo controllerLectureInfo = loader.getController();
                    // pass Lectures object
                    controllerLectureInfo.setLectures(unit);
                    controllerLectureInfo.setParentController(parent);

                    // show info-page scene
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(ControllerCalendar.DAYS[day] + " - " +
                            unit.getFrom());

                    stage.setMinWidth(550.0);
                    stage.setMinHeight(550.0);

                    // prevents interaction with the primary stage until the new window is closed.
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(Main.getPrimaryStage());
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // display lecture information if possible
        if (head != null) {
            String brightness = calculateTextBrightness(head.getColor());

            vBox = new VBox(3);
            vBox.setAlignment(Pos.CENTER);

            // get text-info's about the lecture
            Label title = new Label(head.getTitle());
            title.setStyle("-fx-font-size: 14px; " + brightness);
            vBox.getChildren().add(title);

            Label room;
            if (head.getFacility() != null) {
                room = new Label(head.getFacility().toString());
                room.setStyle("-fx-font-weight: normal; -fx-font-size: 10px; " + brightness);
                vBox.getChildren().add(room);
            }

            Label lect;
            if (head.getLecturer() != null) {
                lect = new Label(head.getLecturer().toString());
                lect.setStyle("-fx-font-weight: normal; -fx-font-size: 10px; " + brightness);
                vBox.getChildren().add(lect);
            }

            // get the count of lectures assigned to the specified unit
            int lecture_count = unit.getSize();

            // place the text inside a VBox
            Label count;
            if (lecture_count > 1) {
                // shows how many elements the unit contains
                count = new Label("+ " + (lecture_count - 1));
                count.setStyle("-fx-font-size: 11; " + brightness);
                count.setTextFill(Color.BLUE);
                vBox.getChildren().add(count);
            }


            button.setGraphic(vBox);                                // add vBox to button

            /* ################### SET BACKGROUND COLOR ############################### */
            button.setStyle("-fx-background-color: rgb(" + (head.getColor().getRed() * 255) + "," +
                    (head.getColor().getGreen() * 255) + "," + (head.getColor().getBlue() * 255) +")");
        }

        return button;
    }

    /**
     * Uses the brightness attribute of the background color to decide if the text should be
     * black or white.
     * @param color color of the background
     * @return fxCss string that can be used as a parameter for a setStyle() method call. Null on failure.
     */
    private String calculateTextBrightness(Color color) {
        return (color == null || color.getBrightness() > 0.85 ? "-fx-text-fill: black;" : "-fx-text-fill: white;");
    }

    /**
     * Setup the specified {@link GridPane}. All adjustments to the gridPane should be made within this method to
     * keep everything at one place.
     *
     * @param gridPane The {@link GridPane} to adjust.
     */
    private void adjustGridPane(GridPane gridPane) {
        MyLogger.LOGGER.entering(getClass().toString(), "adjustGridPane", gridPane);

        gridPane.setVgap(5); // vertical space between elements
        gridPane.setHgap(5); // horizontal space between elements
        gridPane.setAlignment(Pos.TOP_LEFT); // alignment of the GridPane
        gridPane.setMinSize(1000.0, 700.0);
        gridPane.setMaxSize(1800.0, 1100.0);

        //gridPane.setGridLinesVisible(true);

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

        MyLogger.LOGGER.exiting(getClass().toString(), "adjustGridPane");
    }

    /**
     * Add n {@link #DAYS} to the first row of the gridPane starting with monday.
     * N depends on {@link Timetable#getDays()}.
     *
     * After calling this function the first row should look something like:
     * Mon | Tue | Wed | ... | Sat
     *  @param gridPane The {@link GridPane} to add the elements to.
     *
     */
    private void setDays(GridPane gridPane) {
        MyLogger.LOGGER.entering(getClass().toString(), "setDays");

        for(int i = 0; i < timetable.getDays(); i++) {
            Text t = new Text(Main.getBundle().getString(ControllerCalendar.DAYS[i]));
            t.setFont(new Font(BIG_FONT_SIZE)); // set font size
            GridPane.setHalignment(t, HPos.CENTER); // center node (text object)
            gridPane.add(t, i+1, 0);  // add to gridPane
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "setDays");
    }

    /**
     * Add text fields containing the duration of each unit to the specified {@link GridPane}.
     * How many elements are added depends on the {@link Timetable} object.
     * @param gridPane The object to add the {@link Text} fields to.
     */
    private void setTime(GridPane gridPane) {
        MyLogger.LOGGER.entering(getClass().toString(), "setTime");

        for(int i = 0; i < timetable.getUnitsPerDay(); i++) {
            Text t = new Text(timetable.getUnit()[i][0].getTime());
            t.setFont(new Font(MEDIUM_FONT_SIZE));  // set font size
            t.setTextAlignment(TextAlignment.CENTER); // center text
            GridPane.setHalignment(t, HPos.CENTER); // center node (text object)
            gridPane.add(t, 0, i+1);    // add to gridPane
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "setTime");
    }

    public static void setTimetable(Timetable timetable) {
        ControllerCalendar.timetable = timetable;
    }

    public static Timetable getTimetable() {
        return timetable;
    }
}
