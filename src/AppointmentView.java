
import java.util.ArrayList;
import java.util.List;

public class AppointmentView implements Observer {

    private List<String> displayedAppointments;

    /**
     * Creates an empty AppointmentView.
     */
    public AppointmentView() {
        displayedAppointments = new ArrayList<String>();
    }

    /**
     * Updates the displayed appointment list.
     */
    @Override
    public void update(List<String> appointments) {
        displayedAppointments = new ArrayList<String>(appointments);

        System.out.println("Appointment List:");

        for (String appointment : displayedAppointments) {
            System.out.println(appointment);
        }
    }

    /**
     * Displays an error message.
     */
    public void showError(String message) {
        System.out.println("Appointment Error: " + message);
    }

    /**
     * Returns the currently displayed appointments.
     */
    public List<String> getDisplayedAppointments() {
        return new ArrayList<String>(displayedAppointments);
    }
}
