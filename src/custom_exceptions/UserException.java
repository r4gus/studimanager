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

    private String errorstatus;

    public String getErrorstatus() {
        return errorstatus;
    }

    public UserException(String status) {

        this.errorstatus = status;
        LoadUserMessageInfoWindow();
    }

    public UserException(String status ,String userMessage ) {
        super(userMessage);
        MyLogger.LOGGER.log(Level.WARNING, userMessage, this.getStackTrace());
        this.errorstatus = status;
        LoadUserMessageInfoWindow();
    }

    public UserException(String status, Throwable cause) {
        super(cause);
        MyLogger.LOGGER.log(Level.WARNING, cause.toString(), this.getStackTrace());
        this.errorstatus = status;
        LoadUserMessageInfoWindow();
    }

    public UserException(String status, String userMessage, Throwable cause) {
        super(userMessage, cause);
        MyLogger.LOGGER.log(Level.WARNING, "msg:" + userMessage + "cause:" + cause.toString(), this.getStackTrace());
        this.errorstatus = status;
        LoadUserMessageInfoWindow();
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
            stage.setTitle("Fehlermeldung");
            stage.show();

        } catch (IOException ex) {

        }

    }


}
