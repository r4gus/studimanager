package custom_exceptions.InfoWindow;

import custom_exceptions.UserException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMessageWindow implements Initializable {

    @FXML
    public ImageView imageViewErrorImage;

    @FXML
    public VBox vBoxErrorMessage;

    private UserException userException;
    private final String filepathERROR ="custom_exceptions/InfoWindow/Icons/icons8-Error-48.png";
    private final String filepathWARNING ="custom_exceptions/InfoWindow/Icons/icons8-fehler-48.png";
    private final String filepathINFO ="custom_exceptions/InfoWindow/Icons/icons8-info-48.png";

    private final String error ="ERROR: ";
    private final String warning ="WARNING: ";
    private final String info ="INFO: ";


    public ControllerMessageWindow(UserException userException){

        this.userException = userException;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image = new Image(filepathERROR);
        imageViewErrorImage.setImage(image);
        Label labelMessagetext = new Label(userException.getMessage());
        labelMessagetext.setWrapText(true);
        vBoxErrorMessage.getChildren().add(labelMessagetext);

    }
}
