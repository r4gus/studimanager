package guiCalendar.edit;

import guiCalendar.IFacility;
import guiCalendar.ILecturer;
import guiCalendar.create.lecture.NewLectureController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import Main.Main;
import timetable.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author David Sugar
 */
public class ControllerLectureEdit extends NewLectureController implements Initializable, IFacility, ILecturer {

    private Lecture lecture;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        Platform.runLater(this::setValues);
    }

    private void setValues() {
        super.lectureTitleTextField.setText(lecture.getTitle());
        super.isElectiveBox.setSelected(lecture.isElective());
        super.facilityComboBox.setValue(lecture.getFacility());
        super.lecturerComboBox.setValue(lecture.getLecturer());
        super.create.setText(Main.getBundle().getString("Edit"));
    }

    @FXML
    public void handleEditButtonAction() {
        String title;
        boolean isElective;
        Facility facility = null;
        Lecturer lecturer = null;

        if (super.lectureTitleTextField.getText().isEmpty()) {
            lectureTitleTextField.showError(Main.getBundle().getString("EnterTitle"));
            return;
        } else {
            title = lectureTitleTextField.getText();
        }

        isElective = super.isElectiveBox.isSelected();

        if (super.facilityComboBox.getValue() != null) {
            facility = super.facilityComboBox.getValue();
        }

        if (super.lecturerComboBox.getValue() != null) {
            lecturer = super.lecturerComboBox.getValue();
        }

        lecture.setTitle(title);

        lecture.setFacility(facility);

        lecture.setLecturer(lecturer);

        lecture.setElective(isElective);

        Stage stage = (Stage) super.newLecture_grid.getScene().getWindow();
        super.parentController.update();
        stage.close();
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
