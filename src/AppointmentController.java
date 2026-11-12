
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentController {

    private AppointmentManager appointmentManager;
    private AppointmentView view;

    /**
     * Creates an AppointmentController.
     */
    public AppointmentController(
        AppointmentManager appointmentManager, AppointmentView view) {

        if (appointmentManager == null || view == null) {
            throw new IllegalArgumentException(
                "Appointment manager and view cannot be null.");
        }

        this.appointmentManager = appointmentManager;
        this.view = view;

        // Register the view as an observer.
        appointmentManager.addObserver(view);
    }

    /**
     * Schedules a new appointment.
     */
    public void schedule(Appointment a) {
        try {
            appointmentManager.scheduleAppointment(a);
        }
        catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    /**
     * Reschedules an existing appointment.
     */
    public void reschedule(
        int appointmentNum, LocalDate date, LocalTime time) {

        try {
            appointmentManager.reschedule(appointmentNum, date, time);
        }
        catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }

    /**
     * Cancels an existing appointment.
     */
    public void cancel(int appointmentNum) {
        try {
            appointmentManager.cancel(appointmentNum);
        }
        catch (IllegalArgumentException e) {
            view.showError(e.getMessage());
        }
    }
}
