package custom_exceptions;

import logging.MyLogger;
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
    }

    public UserException(String userMessage) {
        super(userMessage);
        MyLogger.LOGGER.log(Level.WARNING, userMessage, this.getStackTrace());
    }

    public UserException(Throwable cause) {
        super(cause);
        MyLogger.LOGGER.log(Level.WARNING, cause.toString(), this.getStackTrace());
    }

    public UserException(String userMessage, Throwable cause) {
        super(userMessage, cause);
        MyLogger.LOGGER.log(Level.WARNING, "msg:" + userMessage + "cause:" +  cause.toString() , this.getStackTrace());
    }


}
