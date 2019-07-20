package logging;

import java.util.logging.*;

/**
 * <code>MyLogger</code> is a logging Class utilizing {@link java.util.logging.Logger}. It's meant to log in
 * two modes, <code>User</code> and <code>Verbose</code>. User mode is set by default, if one wants to log in Verbose
 * mode the <code>VERBOSE=true</code> environment variable must be set.
 *
 * In <code>User</code> mode only Exceptions (loglevel WARNING and above) are logged to keep the logfile small.
 * In <code>Verbose</code> mode Exceptions as well as almost all method calls are logged (loglevel FINER and above)
 *
 * All logger configurations are derived from config files found at <code>log/myfile.properties</code>.
 * User and Verbose mode use different property files for configuration and they log into different log files found at
 * <code>log/logfile</code>.
 *
 * USED LOG LEVELS:
 * 1. SEVERE  := For Exceptions that lead to premature program termination
 * 2. WARNING := For all other Exceptions
 * 3. FINER   := For method calls
 *
 * @author Lukas Mendel, David Sugar
 */
public class MyLogger {
    private static final String LOGGER_NAME1 = "studimanager";
    public static final Logger LOGGER ;
    private static String verbose = System.getenv("VERBOSE");

    static {
        System.out.println(verbose);

        if(verbose != null && verbose.equals("true"))  // enter verbose mode
            System.setProperty("java.util.logging.config.file", "config/verbose.properties");
        else   // enter user mode
            System.setProperty("java.util.logging.config.file", "config/user.properties");

        try{
            LogManager.getLogManager().readConfiguration();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        LOGGER = Logger.getLogger(LOGGER_NAME1);
    }
}
