package custom_exceptions;

/**
 * Das <code>custom_exceptions</code> is a special Exception for the User, that informs him over a fault.
 * @author Lukas Mendel
 */

public class UserException extends Exception {

    public UserException(String userMessage)
    {
        super(userMessage);
    }

}
