package guiCalendar;

/**
 * Controller-Classes that implement {@code Updatable} update their view by calling the {@code update()} method.
 *
 * An example would be the {@code ControllerCalendar} class. In order to display newly assigned
 * {@code Lecture}s the scene must be redrawn. This is accomplished by calling {@code ControllerCalendar.update()}.
 *
 * @author David Sugar
 */
public interface Updatable {
    void update();
}
