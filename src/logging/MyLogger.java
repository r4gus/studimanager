package logging;

import java.util.logging.*;

/**
 * A logging Class utilizing the {@link java.util.logging.Logger} class. It's meant to log in two modes, <code>User</code>
 * and <code>Dev</code>.
 *
 * In <code>User</code> mode only Exceptions are logged to keep the logfile small.
 * In <code>Dev</code> mode Exceptions as well as all important method calls are logged.
 *
 * All logger configurations are derived from an extra config file.
 *
 * @author Lukas Mendel, David Sugar
 */
public class MyLogger {
    private static final String LOGGER_NAME1 = "studimanager";
    public static final Logger LOGGER ;

    static {
        System.setProperty("java.util.logging.config.file", "config/dev.properties");

        try{
            LogManager.getLogManager().readConfiguration();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        LOGGER = Logger.getLogger(LOGGER_NAME1);
    }
}
