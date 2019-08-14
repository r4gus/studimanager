package custom_exceptions;

import custom_exceptions.InfoWindow.ControllerMessageWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Das <code>custom_exceptions</code> is a special Exception for the User, that informs him over a fault.
 * If an exception is thrown, the error is automatically written to the log.
 * All excetions that should pass on information to the user inherit from this exception.
 *
 * @author Lukas Mendel
 */

public class UserException extends Exception {

    public UserException() {

        LoadUserMessageInfoWindow();
    }

    public UserException(String userMessage) {
        super(userMessage);
        MyLogger.LOGGER.log(Level.WARNING, userMessage, this.getStackTrace());
        LoadUserMessageInfoWindow();
    }

    public UserException(Throwable cause) {
        super(cause);
        MyLogger.LOGGER.log(Level.WARNING, cause.toString(), this.getStackTrace());
        LoadUserMessageInfoWindow();
    }

    public UserException(String userMessage, Throwable cause) {
        super(userMessage, cause);
        MyLogger.LOGGER.log(Level.WARNING, "msg:" + userMessage + "cause:" + cause.toString(), this.getStackTrace());
    }

    private void LoadUserMessageInfoWindow() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InfoWindow/layout_MessageWindow.fxml"));
            ControllerMessageWindow controllerMessageWindow = new ControllerMessageWindow(this);
            fxmlLoader.setController(controllerMessageWindow);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("MasterFehlermeldung");
            stage.show();

        } catch (IOException ex) {

        }

    }


}
