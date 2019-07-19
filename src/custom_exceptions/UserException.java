package custom_exceptions;

/**
 * Das <code>custom_exceptions</code> is a special Exception for the User, that informs him over a fault.
 * If an exception is thrown, the error is automatically written to the log.
 * @author Lukas Mendel
 */

public class UserException extends Exception {

    public UserException()
    {}

    public UserException(String userMessage)
    {
        super(userMessage);
    }

    public UserException(Throwable cause)
    {
        super(cause);
    }

    public UserException(String userMessage, Throwable cause)
    {
        super(userMessage, cause);
    }


}
